var exec = require('cordova/exec');

function FingerprintID (){}

FingerprintID.prototype.callAction=  function (action, onSuccess, onError, scope, args) {
    var success = function (data) {
        onSuccess && onSuccess.call(scope || this, data);
    };

    var error = function (data) {
        onError && onError.call(scope || this, data);
    };

    exec(success, error, "FingerprintID", action, args || []);
};

FingerprintID.prototype.isHardwareDetected= function (onSuccess, onError, scope) {
    this.callAction("isHardwareDetected", onSuccess, onError, scope);
};

FingerprintID.prototype.hasEnrolledFingerprints= function (onSuccess, onError, scope) {
    this.callAction("hasEnrolledFingerprints", onSuccess, onError, scope);
};

FingerprintID.prototype.isAvailable= function (onSuccess, onError, scope) {
    this.callAction("isAvailable", onSuccess, onError, scope);
};

FingerprintID.prototype.authenticate= function (message, onSuccess, onError, scope) {

    this.callAction("authenticate", onSuccess, onError, scope, [message || "Use your fingerprint to confirm operation"]);
};

FingerprintID.prototype.cancel= function (onSuccess, onError, scope) {
    this.callAction("cancel", onSuccess, onError, scope);
};

module.exports = new FingerprintID();