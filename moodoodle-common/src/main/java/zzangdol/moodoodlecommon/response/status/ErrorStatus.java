package zzangdol.moodoodlecommon.response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseStatus {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버 에러, 담당자에게 문의 바랍니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 4000, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 4001, "로그인이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 4002, "금지된 요청입니다."),

    // Auth (4050 ~ 4051)
    VERIFICATION_CODE_EXPIRED(HttpStatus.GONE, 4050, "인증 코드가 만료되었습니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, 4051, "유효하지 않은 인증 코드입니다."),
    INVALID_EMAIL_VERIFICATION_TOKEN(HttpStatus.BAD_REQUEST, 4052, "유효하지 않은 이메일 인증 토큰입니다.");



    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}