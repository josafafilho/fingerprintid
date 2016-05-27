package com.josafafilho.fingerprintid.authentication.samsung.identifylistener;

import com.josafafilho.fingerprintid.FingerprintCallbackHandlerInterface;
import com.samsung.android.sdk.pass.SpassFingerprint.IdentifyListener;

public abstract class AbstractIdentifyListener implements IdentifyListener {

    protected FingerprintCallbackHandlerInterface handler;

    public AbstractIdentifyListener(FingerprintCallbackHandlerInterface handler) {
        this.handler = handler;
    }

    @Override
    public void onCompleted() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onReady() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStarted() {
        // TODO Auto-generated method stub
    }
}
