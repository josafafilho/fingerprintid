//
// Created by Josafá Filho on 4/12/16.
//

#import <Cordova/CDV.h>

#ifndef FingerprintID_Constants
#define FingerprintID_Constants

// Error codes
#define kErrorNoHardware 100
#define kErrorNoFingerprintsEnrolled 101
#define kErrorTouchIdNotAvailable 102
#define kErrorAuthenticationFailed 103
#define kErrorAuthenticationCanceled 104
#define kErrorUnknown 999

#endif


@interface FingerprintID : CDVPlugin

- (BOOL)hasHardware;

- (BOOL)hasFingerprints;

- (void)isHardwareDetected:(CDVInvokedUrlCommand *)command;

- (void)hasEnrolledFingerprints:(CDVInvokedUrlCommand *)command;

- (void)isAvailable:(CDVInvokedUrlCommand *)command;

- (void)authenticate:(CDVInvokedUrlCommand *)command;

- (void)cancel:(CDVInvokedUrlCommand *)command;

@end