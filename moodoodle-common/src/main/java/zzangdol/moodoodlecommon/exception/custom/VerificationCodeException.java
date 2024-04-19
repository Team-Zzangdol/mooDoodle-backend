package zzangdol.moodoodlecommon.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

public class VerificationCodeException extends GeneralException {

    public static final GeneralException EXCEPTION = new VerificationCodeException();

    public VerificationCodeException() {
        super(ErrorStatus.INVALID_VERIFICATION_CODE);
    }

    public VerificationCodeException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
