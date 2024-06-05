package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class DefaultCategoryAccessDeniedException extends GeneralException {

    public static final GeneralException EXCEPTION = new DefaultCategoryAccessDeniedException();

    public DefaultCategoryAccessDeniedException() {
        super(ErrorStatus.DEFAULT_CATEGORY_ACCESS_DENIED);
    }

    public DefaultCategoryAccessDeniedException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
