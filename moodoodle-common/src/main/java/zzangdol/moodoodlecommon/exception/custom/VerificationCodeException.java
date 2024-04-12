package zzangdol.moodoodlecommon.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

public class VerificationCodeException extends GeneralException {

    public VerificationCodeException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
