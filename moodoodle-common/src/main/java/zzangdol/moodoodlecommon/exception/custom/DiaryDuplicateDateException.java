package zzangdol.moodoodlecommon.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

public class DiaryDuplicateDateException extends GeneralException {

    public static final GeneralException EXCEPTION = new DiaryDuplicateDateException();

    public DiaryDuplicateDateException() {
        super(ErrorStatus.DIARY_DATE_DUPLICATION);
    }

    public DiaryDuplicateDateException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
