package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class UserCredentialsException extends GeneralException {

    public static final GeneralException EXCEPTION = new UserCredentialsException();

    public UserCredentialsException() {
        super(ErrorStatus.INVALID_CREDENTIALS);
    }

    public UserCredentialsException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
