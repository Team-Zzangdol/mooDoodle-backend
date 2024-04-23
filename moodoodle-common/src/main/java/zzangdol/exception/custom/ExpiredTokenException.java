package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class ExpiredTokenException extends GeneralException {

    public static final GeneralException EXCEPTION = new ExpiredTokenException();

    public ExpiredTokenException() {
        super(ErrorStatus.TOKEN_EXPIRED);
    }

    public ExpiredTokenException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
