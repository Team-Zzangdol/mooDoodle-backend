package zzangdol.moodoodleapi.global.annotation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import zzangdol.member.domain.Member;
import zzangdol.moodoodleapi.jwt.JwtProvider;
import zzangdol.moodoodleapi.member.implement.MemberQueryService;
import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

@RequiredArgsConstructor
@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final MemberQueryService memberQueryService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final boolean isRegUserAnnotation = parameter.getParameterAnnotation(AuthMember.class) != null;
        final boolean isMember = parameter.getParameterType().equals(Member.class);
        return isRegUserAnnotation && isMember;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new GeneralException(ErrorStatus.AUTHENTICATION_REQUIRED);
        }

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = jwtProvider.resolveToken(request);

        if (!StringUtils.hasText(token) || !jwtProvider.validateToken(token)) {
            throw new GeneralException(ErrorStatus.TOKEN_INVALID);
        }

        String id = jwtProvider.getAuthentication(token).getName();
        return memberQueryService.findById(Long.valueOf(id));
    }
}
