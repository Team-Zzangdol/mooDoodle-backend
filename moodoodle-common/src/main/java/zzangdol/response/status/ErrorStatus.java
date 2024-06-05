package zzangdol.response.status;

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

    // Auth (4050 ~ 4099)
    VERIFICATION_CODE_EXPIRED(HttpStatus.GONE, 4050, "인증 코드가 만료되었습니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, 4051, "유효하지 않은 인증 코드입니다."),
    INVALID_EMAIL_VERIFICATION_TOKEN(HttpStatus.BAD_REQUEST, 4052, "유효하지 않은 이메일 인증 토큰입니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, 4053, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, 4054, "만료된 토큰입니다."),
    TOKEN_UNSUPPORTED(HttpStatus.BAD_REQUEST, 4055, "지원되지 않는 토큰 형식입니다."),
    TOKEN_CLAIMS_EMPTY(HttpStatus.BAD_REQUEST, 4056, "토큰 클레임이 비어있습니다."),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST,4057, "유효하지 않은 회원 정보입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 4058, "회원이 존재하지 않습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, 4059, "비밀번호가 일치하지 않습니다."),
    AUTHENTICATION_REQUIRED(HttpStatus.BAD_REQUEST, 4060, "인증 정보가 필요합니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, 4061, "저장소에 해당 토큰이 존재하지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, 4062, "이미 사용 중인 이메일입니다."),
    AUTH_PROVIDER_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, 4063, "지원되지 않는 인증 제공자입니다."),

    // Diary (4100 ~ 4149)
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, 4100, "일기가 존재하지 않습니다."),
    DIARY_ACCESS_DENIED(HttpStatus.FORBIDDEN, 4101, "일기 접근이 거부되었습니다."),
    DIARY_DATE_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, 4102, "일기는 오늘 날짜 이후로 생성할 수 없습니다."),
    DIARY_DATE_DUPLICATION(HttpStatus.CONFLICT, 4103, "해당 날짜에 이미 일기가 존재합니다."),

    // Report (4150 ~ 4199)
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, 4150, "리포트가 존재하지 않습니다."),
    REPORT_ACCESS_DENIED(HttpStatus.FORBIDDEN, 4151, "리포트 접근이 거부되었습니다."),
    REPORT_EMOTION_DATA_MISSING(HttpStatus.BAD_REQUEST, 4152, "일기에 감정 데이터가 존재하지 않습니다."),

    // Scrap (4200 ~ 4250)
    SCRAP_NOT_FOUND(HttpStatus.NOT_FOUND, 4200, "스크랩이 존재하지 않습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 4201, "카테고리가 존재하지 않습니다."),
    SCRAP_DUPLICATION(HttpStatus.CONFLICT, 4202, "해당 사용자와 다이어리에 대해 이미 스크랩이 존재합니다."),
    CATEGORY_ACCESS_DENIED(HttpStatus.FORBIDDEN, 4203, "카테고리 접근이 거부되었습니다."),
    DEFAULT_CATEGORY_ACCESS_DENIED(HttpStatus.FORBIDDEN, 4204, "기본 카테고리 접근이 거부되었습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}