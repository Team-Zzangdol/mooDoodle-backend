package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class DiaryNotFoundException extends GeneralException {

    public static final GeneralException EXCEPTION = new DiaryNotFoundException();

    public DiaryNotFoundException() {
        super(ErrorStatus.DIARY_NOT_FOUND);
    }

    public DiaryNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
