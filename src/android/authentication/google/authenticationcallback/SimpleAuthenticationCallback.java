package com.josafafilho.fingerprintid.authentication.google.authenticationcallback;

import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;

import com.josafafilho.fingerprintid.FingerprintCallbackHandlerInterface;

public class SimpleAuthenticationCallback extends AbstractAuthenticationCallback {

    public SimpleAuthenticationCallback(FingerprintCallbackHandlerInterface handler) {
        super(handler);
    }

    @Override
    public void onAuthenticationSucceeded(AuthenticationResult result) {
        this.handler.onSuccess(null);
    }

}
