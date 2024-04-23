package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class InvalidTokenException extends GeneralException {

    public static final GeneralException EXCEPTION = new InvalidTokenException();

    public InvalidTokenException() {
        super(ErrorStatus.TOKEN_INVALID);
    }

    public InvalidTokenException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
