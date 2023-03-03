package com.example.demo.src.social_login;

import com.example.demo.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoUserService {
    private final Oauth2Kakao oauth2Kakao;

    // 카카오로 인증받기
    public void oauth2AuthorizationKakao(String code) throws BaseException {
        AuthorizationKakao authorization = oauth2Kakao.callTokenApi(code);
        String userInfoFromKakao = oauth2Kakao.callGetUserByAccessToken(authorization.getAccess_token());
        System.out.println("userInfoFromKakao = " + userInfoFromKakao);
    }
}
