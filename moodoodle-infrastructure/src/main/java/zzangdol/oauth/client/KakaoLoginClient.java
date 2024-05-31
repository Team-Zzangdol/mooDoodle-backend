package zzangdol.oauth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zzangdol.oauth.dto.KakaoTokenResponse;
import zzangdol.oauth.dto.KakaoUserInfoResponse;

@RequiredArgsConstructor
@Service
public class KakaoLoginClient implements SocialLoginClient<KakaoUserInfoResponse> {

    private final KakaoLoginTokenClient kakaoLoginTokenClient;
    private final KakaoLoginUserClient kakaoLoginUserClient;

    @Override
    public KakaoUserInfoResponse getUserInfo(String authorizationCode) {
        KakaoTokenResponse tokenInfo = kakaoLoginTokenClient.getTokenInfo(authorizationCode);
        return kakaoLoginUserClient.getUserInfo(tokenInfo.getAccessToken());
    }
}