package com.example.demo.src.social_login;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    private OAuthService kakao;

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println(code);
        String access_Token = kakao.getKakaoAccessToken(code);
        System.out.println("controller access_token : " + access_Token);
    }

//    @ResponseBody
//    @GetMapping("/naver")
//    public String naverOAuthRedirect(@RequestParam String code, @RequestParam String state){
//        return "code : " + code;
//    }

    @ResponseBody
    @GetMapping("/naver")
    public String naverOAuthRedirect(@RequestParam String code, @RequestParam String state){
        // Rest Template 인스턴스 생성
        RestTemplate rt = new RestTemplate();

        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String ,String> accessTokenParams = new LinkedMultiValueMap<>();
        accessTokenParams.add("grant_type", "authorization_code");
        accessTokenParams.add("client_id", "mwqcgafhrZzyugKSz5ev");
        accessTokenParams.add("client_secret", "z6uhURrfVM");
        accessTokenParams.add("code", code);
        accessTokenParams.add("state", state);

        HttpEntity<MultiValueMap<String ,String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, accessTokenHeaders);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        System.out.println(accessTokenResponse.getBody());

        return "accessToken : "+accessTokenResponse.getBody();
    }
}
