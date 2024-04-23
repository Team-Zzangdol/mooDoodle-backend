package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class DiaryDateOutOfBoundsException extends GeneralException {

    public static final GeneralException EXCEPTION = new DiaryDateOutOfBoundsException();

    public DiaryDateOutOfBoundsException() {
        super(ErrorStatus.DIARY_DATE_OUT_OF_BOUNDS);
    }

    public DiaryDateOutOfBoundsException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
