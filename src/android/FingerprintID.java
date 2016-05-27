package com.josafafilho.fingerprintid;

import com.josafafilho.fingerprintid.authentication.adapter.AbstractFingerprintAdapter;
import com.josafafilho.fingerprintid.authentication.adapter.FingerprintAdapterInterface;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FingerprintID extends CordovaPlugin implements FingerprintCallbackHandlerInterface {

    public static final String ACTION_IS_HARDWARE_DETECTED = "isHardwareDetected";
    public static final String ACTION_HAS_ENROLLED_FINGERPRINTS = "hasEnrolledFingerprints";
    public static final String ACTION_IS_AVAILABLE = "isAvailable";
    public static final String ACTION_AUTHENTICATE = "authenticate";
    public static final String ACTION_CANCEL = "cancel";

    public static final int ERROR_NO_HARDWARE = 100;
    public static final int ERROR_NO_FINGERPRINT_ENROLLED = 101;
    public static final int ERROR_NOT_AVAILABLE = 102;
    public static final int ERROR_AUTH_FAILED = 103;
    public static final int ERROR_AUTH_CANCELED = 104;
    public static final int ERROR_UNKNOWN = 999;

    private CallbackContext callbackContext;

    private FingerprintAdapterInterface authenticationAdapter;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        this.authenticationAdapter = AbstractFingerprintAdapter.getInstance(this,
                this.cordova.getActivity().getApplicationContext());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (ACTION_IS_AVAILABLE.equals(action)) {
            checkFingerPrintAvailableAction();
        } else if (ACTION_IS_HARDWARE_DETECTED.equals(action)) {
            checkHardwareDetectedAction();
        } else if (ACTION_HAS_ENROLLED_FINGERPRINTS.equals(action)) {
            checkFingerprintsEnrolledAction();
        } else if (ACTION_AUTHENTICATE.equals(action)) {
            authenticateAction();
        } else if (ACTION_CANCEL.equals(action)) {
            cancelAction();
        }
        return true;
    }

    /************************* plugin actions **********************/

    /**
     * Verifica se a autentica��o atrav�s de impress�o digital est� dispon�vel.
     */
    private void checkFingerPrintAvailableAction() {
        if (isFingerprintAuthAvailable()) {
            this.sendSuccess();
        } else {
            this.sendError(ERROR_NOT_AVAILABLE, "Fingerprint authentication not available");
        }
    }

    /**
     * verifica se o hardware possui leitor de impress�o digital
     */
    private void checkHardwareDetectedAction() {
        if (isHardwareDetected()) {
            this.sendSuccess();
        } else {
            this.sendError(ERROR_NO_HARDWARE, "Fingerprint scanner not available");
        }
    }

    /**
     * verifica se h� impress�es digitais cadastradas nas configura��es do
     * sistema
     */
    private void checkFingerprintsEnrolledAction() {
        if (this.authenticationAdapter.hasEnrolledFingerprints()) {
            this.sendSuccess();
        } else {
            this.sendError(ERROR_NO_FINGERPRINT_ENROLLED, "Fingerprints not enrolled");
        }
    }

    /**
     * Executa autentica��o do usu�rio atrav�s de impress�o digital
     */
    private void authenticateAction() {
        if (isFingerprintAuthAvailable()) {
            this.authenticationAdapter.authenticate();
        } else {
            this.sendError(ERROR_NOT_AVAILABLE, "Fingerprint authentication not available");
        }
    }

    /**
     * Cancela a autentica��o atrav�s da impress�o digital
     */
    private void cancelAction() {
        this.authenticationAdapter.cancel();
        sendSuccess();
    }

    /******************
     * internal methods block
     *******************/

    public boolean isHardwareDetected() {
        return this.authenticationAdapter != null && this.authenticationAdapter.hasHardware();
    }

    /**
     * Verifica se o dispositivo tem hardware para leitura de impress�es
     * digitais e se o usu�rio adicionou pelo menos uma impress�o digital nas
     * configura��es do sistema
     *
     * @return
     */
    private boolean isFingerprintAuthAvailable() {
        return this.authenticationAdapter.hasHardware() && this.authenticationAdapter.hasEnrolledFingerprints();
    }

    /****************
     * callback handling block
     *******************/

    @Override
    public void onSuccess(JSONObject args) {
        this.sendSuccess();
    }

    @Override
    public void onError(int code, String message, JSONObject args) {
        this.sendError(ERROR_AUTH_FAILED, message);
    }

    @Override
    public void onFailure(int code, String message, JSONObject args) {
        this.sendError(ERROR_AUTH_FAILED, message);
    }

    private void sendError(int code, String message) {
        try {
            String json = "{error:{code:" + code + ",message:'" + message + "'}}";
            JSONObject error = new JSONObject(json);
            this.callbackContext.error(error);
        } catch (JSONException e) {
            this.callbackContext.error(ERROR_UNKNOWN);
        }

    }

    private void sendSuccess() {
        this.callbackContext.success();
    }

}
