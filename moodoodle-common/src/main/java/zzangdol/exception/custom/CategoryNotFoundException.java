package zzangdol.exception.custom;

import zzangdol.exception.GeneralException;
import zzangdol.response.status.ErrorStatus;

public class CategoryNotFoundException extends GeneralException {

    public static final GeneralException EXCEPTION = new CategoryNotFoundException();

    public CategoryNotFoundException() {
        super(ErrorStatus.CATEGORY_NOT_FOUND);
    }

    public CategoryNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
