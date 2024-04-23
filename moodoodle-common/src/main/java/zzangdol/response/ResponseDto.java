package zzangdol.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import zzangdol.response.status.BaseStatus;
import zzangdol.response.status.SuccessStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ResponseDto<T> {

    private final Boolean isSuccess;
    private final Integer code;
    private final String message;
    private T result;

    public static <T> ResponseDto<T> onSuccess(T result) {
        return new ResponseDto<>(true, 2000, SuccessStatus.SUCCESS.getMessage(), result);
    }

    public static <T> ResponseDto<T> of(BaseStatus baseStatus, T result) {
        return new ResponseDto<>(true, baseStatus.getCode(), baseStatus.getMessage(), result);
    }

    public static <T> ResponseDto<T> onFailure(Integer code, String message, T data) {
        return new ResponseDto<>(false, code, message, data);
    }

}