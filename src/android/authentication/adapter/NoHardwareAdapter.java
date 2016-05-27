package com.josafafilho.fingerprintid.authentication.adapter;

import com.josafafilho.fingerprintid.FingerprintCallbackHandlerInterface;

/**
 * Created by josafafilho on 5/22/16.
 */
public class NoHardwareAdapter extends AbstractFingerprintAdapter {

    public NoHardwareAdapter(FingerprintCallbackHandlerInterface handler) {
        super(handler);
    }

    @Override
    public void authenticate() {
        this.handler.onFailure(0, null, null);
    }

    @Override
    public boolean hasHardware() {
        return false;
    }

    @Override
    public boolean hasEnrolledFingerprints() {
        return false;
    }

    @Override
    public void cancel() {
        this.handler.onFailure(0, null, null);
    }
}
