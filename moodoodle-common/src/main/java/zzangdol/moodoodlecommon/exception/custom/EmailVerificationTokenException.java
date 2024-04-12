package zzangdol.moodoodlecommon.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

public class EmailVerificationTokenException extends GeneralException {

    public EmailVerificationTokenException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
