package zzangdol.oauth.client.google;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zzangdol.oauth.client.SocialLoginClient;
import zzangdol.oauth.dto.GoogleTokenResponse;
import zzangdol.oauth.dto.GoogleUserInfoResponse;

@RequiredArgsConstructor
@Service
public class GoogleLoginClient implements SocialLoginClient<GoogleUserInfoResponse> {

    private final GoogleLoginTokenClient googleLoginTokenClient;
    private final GoogleLoginUserClient googleLoginUserClient;

    @Override
    public GoogleUserInfoResponse getUserInfo(String authorizationCode) {
        GoogleTokenResponse tokenInfo = googleLoginTokenClient.getTokenInfo(authorizationCode);
        return googleLoginUserClient.getUserInfo(tokenInfo.getAccessToken());
    }
}
