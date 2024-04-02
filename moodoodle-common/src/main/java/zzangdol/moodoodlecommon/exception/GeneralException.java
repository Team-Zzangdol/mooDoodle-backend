package zzangdol.moodoodlecommon.exception;

import lombok.Getter;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

@Getter
public class GeneralException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public GeneralException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

}