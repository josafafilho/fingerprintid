package com.josafafilho.fingerprintid;

import org.json.JSONObject;

public interface FingerprintCallbackHandlerInterface {

    public void onSuccess(JSONObject args);

    public void onError(int code, String message, JSONObject args);

    public void onFailure(int code, String message, JSONObject args);
}
