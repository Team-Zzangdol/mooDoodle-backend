package zzangdol.response.status;

import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseStatus {

    SUCCESS(OK, 2000, "성공");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}