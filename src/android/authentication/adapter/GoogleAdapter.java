package com.josafafilho.fingerprintid.authentication.adapter;

import android.annotation.SuppressLint;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;

import com.josafafilho.fingerprintid.FingerprintCallbackHandlerInterface;
import com.josafafilho.fingerprintid.authentication.google.authenticationcallback.AbstractAuthenticationCallback;
import com.josafafilho.fingerprintid.authentication.google.authenticationcallback.SimpleAuthenticationCallback;

public class GoogleAdapter extends AbstractFingerprintAdapter {

    private FingerprintManager fingerprintManager;
    private CancellationSignal cancellationSignal;

    public GoogleAdapter(FingerprintManager fingerprintManager, FingerprintCallbackHandlerInterface handler) {
        super(handler);
        this.fingerprintManager = fingerprintManager;
    }

    @Override
    public boolean hasEnrolledFingerprints() {
        return fingerprintManager.hasEnrolledFingerprints();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean hasHardware() {
        return fingerprintManager.isHardwareDetected();
    }

    @Override
    public void authenticate() {
        AbstractAuthenticationCallback authCallback = new SimpleAuthenticationCallback(this.handler);
        this.authenticate(authCallback);
    }

    @Override
    public void cancel() {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
    }

    @SuppressLint("NewApi")
    private void authenticate(AbstractAuthenticationCallback callback) {
        this.cancellationSignal = new CancellationSignal();
        this.fingerprintManager.authenticate(null, cancellationSignal, 0, callback, null);
    }

}
