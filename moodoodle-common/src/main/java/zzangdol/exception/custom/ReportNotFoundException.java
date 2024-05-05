package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class ReportNotFoundException extends GeneralException {

    public static final GeneralException EXCEPTION = new ReportNotFoundException();

    public ReportNotFoundException() {
        super(ErrorStatus.REPORT_NOT_FOUND);
    }

    public ReportNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
