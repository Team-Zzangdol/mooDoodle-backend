package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class CategoryAccessDeniedException extends GeneralException {

    public static final GeneralException EXCEPTION = new CategoryAccessDeniedException();

    public CategoryAccessDeniedException() {
        super(ErrorStatus.CATEGORY_ACCESS_DENIED);
    }

    public CategoryAccessDeniedException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
