package com.josafafilho.fingerprintid.authentication.adapter;

import android.content.Context;
import android.util.Log;

import com.josafafilho.fingerprintid.FingerprintCallbackHandlerInterface;
import com.josafafilho.fingerprintid.authentication.samsung.identifylistener.SimpleIdentifyListener;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

public class SamsungAdapter extends AbstractFingerprintAdapter {

    public static final String TAG = "SamsungAdapter";

    Context context;
    SpassFingerprint sPassFingerprint;
    Spass sPass;

    public SamsungAdapter(FingerprintCallbackHandlerInterface handler, Context context, Spass pass) {
        super(handler);
        this.context = context;
        this.sPass = pass;
        try {
            this.sPass.initialize(this.context);

            if (this.hasHardware()) {
                this.sPassFingerprint = new SpassFingerprint(this.context);
            }
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        }
    }

    @Override
    public boolean hasEnrolledFingerprints() {
        return this.sPassFingerprint != null && this.sPassFingerprint.hasRegisteredFinger();
    }

    @Override
    public boolean hasHardware() {
        return this.sPass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);
    }

    @Override
    public void authenticate() {
        SpassFingerprint.IdentifyListener listener = new SimpleIdentifyListener(this.handler);
        this.authenticate(listener);
    }

    @Override
    public void cancel() {
        this.sPassFingerprint.cancelIdentify();
    }

    private void authenticate(SpassFingerprint.IdentifyListener listener) {
        this.sPassFingerprint.startIdentify(listener);
    }

}
