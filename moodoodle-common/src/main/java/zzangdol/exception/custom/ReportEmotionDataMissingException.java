package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class ReportEmotionDataMissingException extends GeneralException {

    public static final GeneralException EXCEPTION = new ReportEmotionDataMissingException();

    public ReportEmotionDataMissingException() {
        super(ErrorStatus.REPORT_EMOTION_DATA_MISSING);
    }

    public ReportEmotionDataMissingException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
