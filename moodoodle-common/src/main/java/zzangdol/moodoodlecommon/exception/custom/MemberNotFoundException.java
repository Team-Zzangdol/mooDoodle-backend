package zzangdol.moodoodlecommon.exception.custom;

import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

public class MemberNotFoundException extends GeneralException {

    public MemberNotFoundException() {
        super(ErrorStatus.MEMBER_NOT_FOUND);
    }

    public MemberNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
