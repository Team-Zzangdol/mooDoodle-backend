package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class VerificationCodeException extends GeneralException {

    public static final GeneralException EXCEPTION = new VerificationCodeException();

    public VerificationCodeException() {
        super(ErrorStatus.INVALID_VERIFICATION_CODE);
    }

    public VerificationCodeException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
