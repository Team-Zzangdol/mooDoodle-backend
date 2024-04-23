package zzangdol.moodoodlecommon.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class UserNotFoundException extends GeneralException {

    public static final GeneralException EXCEPTION = new UserNotFoundException();

    public UserNotFoundException() {
        super(ErrorStatus.USER_NOT_FOUND);
    }

    public UserNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
