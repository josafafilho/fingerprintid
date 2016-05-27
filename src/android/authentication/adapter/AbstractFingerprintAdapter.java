package com.josafafilho.fingerprintid.authentication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;

import com.josafafilho.fingerprintid.FingerprintCallbackHandlerInterface;
import com.samsung.android.sdk.pass.Spass;

public abstract class AbstractFingerprintAdapter implements FingerprintAdapterInterface {

    FingerprintCallbackHandlerInterface handler;

    public AbstractFingerprintAdapter(FingerprintCallbackHandlerInterface handler) {
        this.handler = handler;
    }

    @SuppressLint("InlinedApi")
    public static AbstractFingerprintAdapter getInstance(FingerprintCallbackHandlerInterface handler, Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            FingerprintManager fpManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);

            return new GoogleAdapter(fpManager, handler);
        } else {
            Spass spass = new Spass();
            SamsungAdapter adapter = new SamsungAdapter(handler, context, spass);
            if (adapter.hasHardware()) {
                return adapter;
            }
        }
        return new NoHardwareAdapter(handler);
    }
}
