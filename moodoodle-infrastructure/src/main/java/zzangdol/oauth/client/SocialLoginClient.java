package zzangdol.oauth.client;

import zzangdol.oauth.dto.SocialUserInfoResponse;

public interface SocialLoginClient<T extends SocialUserInfoResponse> {

    T getUserInfo(String authorizationCode);

}
