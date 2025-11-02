package com.boost.wallet_service.constant;

public class Constants {

    public static final String FLAG_REQUEST = "REQUEST : [string]";
    public static final String FLAG_RESPONSE = "RESPONSE : [string]";

    public static final String LBL_RESPONSE_CODE = "response_code_";
    public static final String RESPONSE_CODE_SUCCESS = "00";
    public static final String RESPONSE_CODE_ERROR = "99";

    public static final String ERROR_EMPTY_REQUEST = "error.empty_request";
    public static final String ERROR_BAD_REQUEST = "error.bad_request";
    public static final String ERROR_RECORD_NOT_FOUND = "error.record_not_found";
    public static final String ERROR_INVALID_EMAIL = "error.invalid_email";

    public static final String ENDPOINT_WALLET_CREDIT = "wallet/credit";
    public static final String ENDPOINT_WALLET_DEBIT = "wallet/debit";
    public static final String ENDPOINT_WALLET_TRANSFER = "wallet/transfer";

}
