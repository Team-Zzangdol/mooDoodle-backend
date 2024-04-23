package zzangdol.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class DiaryAccessDeniedException extends GeneralException {

    public static final GeneralException EXCEPTION = new DiaryAccessDeniedException();

    public DiaryAccessDeniedException() {
        super(ErrorStatus.DIARY_ACCESS_DENIED);
    }

    public DiaryAccessDeniedException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
