package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class ScrapDuplicateException extends GeneralException {

    public static final GeneralException EXCEPTION = new ScrapDuplicateException();

    public ScrapDuplicateException() {
        super(ErrorStatus.SCRAP_DUPLICATION);
    }

    public ScrapDuplicateException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
