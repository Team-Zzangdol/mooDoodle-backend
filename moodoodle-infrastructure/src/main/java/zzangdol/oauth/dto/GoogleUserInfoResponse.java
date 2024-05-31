package zzangdol.oauth.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GoogleUserInfoResponse implements SocialUserInfoResponse {

    private String email;
    private String name;

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getNickname() {
        return this.name;
    }
}
