package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class ReportAccessDeniedException extends GeneralException {

    public static final GeneralException EXCEPTION = new ReportAccessDeniedException();

    public ReportAccessDeniedException() {
        super(ErrorStatus.REPORT_ACCESS_DENIED);
    }

    public ReportAccessDeniedException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
