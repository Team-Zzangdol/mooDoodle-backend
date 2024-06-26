package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class DiaryDuplicateDateException extends GeneralException {

    public static final GeneralException EXCEPTION = new DiaryDuplicateDateException();

    public DiaryDuplicateDateException() {
        super(ErrorStatus.DIARY_DATE_DUPLICATION);
    }

    public DiaryDuplicateDateException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
