package com.josafafilho.fingerprintid.authentication.adapter;

public interface FingerprintAdapterInterface {

    public boolean hasEnrolledFingerprints();

    public boolean hasHardware();

    public void authenticate();

    public void cancel();
}
