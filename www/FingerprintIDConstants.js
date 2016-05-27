/**
 * @module FingerprintID
 */
module.exports = {
  /**
   * @enum {number}
   */
  ResultType:{
    NoHardware: 100,
    NoFingerprintsEnrolled: 101,
    NotAvailable: 102,
    AuthFailed: 103,
    AuthCanceled: 104,
    Unknown: 999    
  }
};