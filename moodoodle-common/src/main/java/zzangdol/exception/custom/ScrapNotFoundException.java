package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class ScrapNotFoundException extends GeneralException {

    public static final GeneralException EXCEPTION = new ScrapNotFoundException();

    public ScrapNotFoundException() {
        super(ErrorStatus.SCRAP_NOT_FOUND);
    }

    public ScrapNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
