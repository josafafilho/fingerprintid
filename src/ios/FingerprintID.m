//
// Created by Josafá Filho on 4/12/16.
//

#import "FingerprintID.h"

#import <LocalAuthentication/LocalAuthentication.h>

@interface FingerprintID ()

- (BOOL)hasLAContext;

#pragma mark - Callback messaging headers

- (void)sendSuccess:(CDVInvokedUrlCommand *)command;

- (void)sendSuccess:(CDVInvokedUrlCommand *)command messageDictionary:(NSDictionary *)messageDict;

- (void)sendError:(CDVInvokedUrlCommand *)command;

- (void)sendError:(CDVInvokedUrlCommand *)command wihtCode:(int)code andMessage:(NSString *)message;

- (void)sendError:(CDVInvokedUrlCommand *)command messageDictionary:(NSDictionary *)messageDict;

@end

@implementation FingerprintID

NSString *kServiceName = @"cordova-plugin-fingerprintid";

- (BOOL)hasLAContext {
    return NSClassFromString(@"LAContext") != NULL;
}

- (NSError *)canEvaluatePolicy {
    NSError *error = nil;
    LAContext * context = [[LAContext alloc] init];
    [context canEvaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics error:&error];
    return error;
}

- (BOOL)hasHardware {
    BOOL hasHardware = false;
    if ([self hasLAContext]) {
        NSError *error = [self canEvaluatePolicy];
        hasHardware = error == nil || error.code != LAErrorTouchIDNotAvailable;
    }

    return hasHardware;
}

- (BOOL)hasFingerprints {
    BOOL hasFingerprints = false;
    if ([self hasLAContext]) {
        NSError *error = [self canEvaluatePolicy];
        hasFingerprints = error == nil || error.code != LAErrorTouchIDNotEnrolled;
    }

    return hasFingerprints;
}

- (void)isHardwareDetected:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL hasHardware = [self hasHardware];

        if (hasHardware) {
            [self sendSuccess:command];
        } else {
            [self sendError:command wihtCode:kErrorNoHardware andMessage:@"TouchID Not Available"];
        }
    }];
}

- (void)hasEnrolledFingerprints:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL hasEnrolledFingerprints = [self hasFingerprints];

        if (hasEnrolledFingerprints) {
            [self sendSuccess:command];
        } else {
            [self sendError:command wihtCode:kErrorNoFingerprintsEnrolled andMessage:@"Fingerprints not enrolled"];
        }
    }];
}

- (void)isAvailable:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL isAvailable = [self hasHardware] && [self hasFingerprints];

        if (isAvailable) {
            [self sendSuccess:command];
        } else {
            [self sendError:command wihtCode:kErrorTouchIdNotAvailable andMessage:@"TouchID not available"];
        }
    }];

}

- (void)authenticate:(CDVInvokedUrlCommand *)command {
    NSError *error = [self canEvaluatePolicy];
    LAContext * context = [[LAContext alloc] init];
    NSString * message = command.arguments[0];
    if (error == nil) {
        [context evaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics
         localizedReason:message
         reply:^(BOOL success, NSError *error) {
             dispatch_async(dispatch_get_main_queue(), ^{
                 if (success) {
                     [self sendSuccess:command];
                 } else if (error != nil) {
                     [self sendError:command wihtCode:kErrorAuthenticationFailed andMessage:@"There was a problem verifying your identity"];
                 } else {
                     NSLog(@"What the heck?");
                     [self sendError:command];
                 }
             });
         }];
    }else{
        [self sendError:command wihtCode:kErrorTouchIdNotAvailable andMessage:@"TouchID not available"];
    }
}

- (void)cancel:(CDVInvokedUrlCommand *)command {
    //NOTHING TO DO HERE, EXISTS FOR PLUGIN COMPATIBILITY
    [self sendSuccess:command];
}

#pragma mark - Callback messaging headers

- (void)sendSuccess:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK]
                                callbackId:command.callbackId];
}

- (void)sendSuccess:(CDVInvokedUrlCommand *)command messageDictionary:(NSDictionary *)messageDict {
    CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:messageDict];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)sendError:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR]
                                callbackId:command.callbackId];
}

- (void)sendError:(CDVInvokedUrlCommand *)command wihtCode:(int)code andMessage:(NSString *)message {
    NSDictionary *errorMessage = @{
            @"error" : @{
                    @"code" : @(code),
                    @"message" : message
            }
    };
    [self sendError:command messageDictionary:errorMessage];
}

- (void)sendError:(CDVInvokedUrlCommand *)command messageDictionary:(NSDictionary *)messageDict {
    CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:messageDict];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end