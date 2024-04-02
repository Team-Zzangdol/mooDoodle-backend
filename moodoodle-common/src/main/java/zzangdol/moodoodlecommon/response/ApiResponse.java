package zzangdol.moodoodlecommon.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import zzangdol.moodoodlecommon.response.status.BaseStatus;
import zzangdol.moodoodlecommon.response.status.SuccessStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    private final Boolean isSuccess;
    private final Integer code;
    private final String message;
    private T result;

    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, 2000, SuccessStatus.SUCCESS.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(BaseStatus baseStatus, T result) {
        return new ApiResponse<>(true, baseStatus.getCode(), baseStatus.getMessage(), result);
    }

    public static <T> ApiResponse<T> onFailure(Integer code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }

}