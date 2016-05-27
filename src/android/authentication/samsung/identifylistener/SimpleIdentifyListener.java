package com.josafafilho.fingerprintid.authentication.samsung.identifylistener;

import com.josafafilho.fingerprintid.FingerprintCallbackHandlerInterface;
import com.samsung.android.sdk.pass.SpassFingerprint;

public class SimpleIdentifyListener extends AbstractIdentifyListener {

    public SimpleIdentifyListener(FingerprintCallbackHandlerInterface handler) {
        super(handler);
    }

    @Override
    public void onFinished(int eventStatus) {
        if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS
                || eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS) {
            this.handler.onSuccess(null);
        } else {
            this.handler.onFailure(eventStatus, null, null);
        }
    }

}
