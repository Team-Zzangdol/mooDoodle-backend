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
    INVALID_EMAIL_VERIFICATION_TOKEN(HttpStatus.BAD_REQUEST, 4052, "유효하지 않은 이메일 인증 토큰입니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, 4053, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, 4054, "만료된 토큰입니다."),
    TOKEN_UNSUPPORTED(HttpStatus.BAD_REQUEST, 4055, "지원되지 않는 토큰 형식입니다."),
    TOKEN_CLAIMS_EMPTY(HttpStatus.BAD_REQUEST, 4056, "토큰 클레임이 비어있습니다."),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST,4057, "유효하지 않은 회원 정보입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 4058, "존재하지 않는 회원입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, 4059, "비밀번호가 일치하지 않습니다."),
    AUTHENTICATION_REQUIRED(HttpStatus.BAD_REQUEST, 4060, "인증 정보가 필요합니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, 4061, "저장소에 해당 토큰이 존재하지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, 4062, "이미 사용 중인 이메일입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}