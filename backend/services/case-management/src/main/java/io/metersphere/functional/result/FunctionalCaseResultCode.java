package io.metersphere.functional.result;

import io.metersphere.sdk.exception.IResultCode;

public enum FunctionalCaseResultCode implements IResultCode {

    FUNCTIONAL_CASE_NOT_FOUND(105001, "case_comment.case_is_null");

    private final int code;
    private final String message;

    FunctionalCaseResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return getTranslationMessage(this.message);
    }
}
