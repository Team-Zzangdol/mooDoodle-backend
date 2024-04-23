package zzangdol.moodoodlecommon.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

public class DiaryDateOutOfBoundsException extends GeneralException {

    public static final GeneralException EXCEPTION = new DiaryDateOutOfBoundsException();

    public DiaryDateOutOfBoundsException() {
        super(ErrorStatus.DIARY_DATE_OUT_OF_BOUNDS);
    }

    public DiaryDateOutOfBoundsException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
