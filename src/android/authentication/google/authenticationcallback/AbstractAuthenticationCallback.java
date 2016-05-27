package com.josafafilho.fingerprintid.authentication.google.authenticationcallback;

import android.annotation.SuppressLint;
import android.hardware.fingerprint.FingerprintManager.AuthenticationCallback;

import com.josafafilho.fingerprintid.FingerprintCallbackHandlerInterface;

@SuppressLint("NewApi")
public class AbstractAuthenticationCallback extends AuthenticationCallback {
    protected FingerprintCallbackHandlerInterface handler;

    public AbstractAuthenticationCallback(FingerprintCallbackHandlerInterface handler) {
        this.handler = handler;
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.handler.onError(helpCode, (String) helpString, null);
    }

    @Override
    public void onAuthenticationFailed() {
        this.handler.onFailure(0, null, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.handler.onError(errMsgId, (String) errString, null);
    }
}
