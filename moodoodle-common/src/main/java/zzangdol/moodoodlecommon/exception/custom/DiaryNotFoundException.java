package zzangdol.moodoodlecommon.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

public class DiaryNotFoundException extends GeneralException {

    public static final GeneralException EXCEPTION = new DiaryNotFoundException();

    public DiaryNotFoundException() {
        super(ErrorStatus.DIARY_NOT_FOUND);
    }

    public DiaryNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
