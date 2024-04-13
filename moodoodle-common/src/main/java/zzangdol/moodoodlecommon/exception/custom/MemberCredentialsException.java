package zzangdol.moodoodlecommon.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

public class MemberCredentialsException extends GeneralException {

    public MemberCredentialsException() {
        super(ErrorStatus.INVALID_CREDENTIALS);
    }

    public MemberCredentialsException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
