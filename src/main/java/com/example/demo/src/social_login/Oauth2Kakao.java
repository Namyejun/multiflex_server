package com.example.demo.src.social_login;

import com.example.demo.config.BaseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class Oauth2Kakao {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String kakaoOauth2ClinetId = "bd699a578711354000bda5a0aaead50a";
    private final String frontendRedirectUrl = "http://localhost:8080";


    public AuthorizationKakao callTokenApi(String code) throws BaseException {
        String grantType = "authorization_code";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", kakaoOauth2ClinetId);
        params.add("redirect_uri", frontendRedirectUrl + "/callback/kakao");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String url = "https://kauth.kakao.com/oauth/token";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            AuthorizationKakao authorization = objectMapper.readValue(response.getBody(), AuthorizationKakao.class);

            return authorization;
        } catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
//            throw new ProcyanException(E00001);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    /**
     * accessToken 을 이용한 유저정보 받기
     * @return
     */
    public String callGetUserByAccessToken(String accessToken) throws BaseException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String url = "https://kapi.kakao.com/v2/user/me";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            // 값 리턴
            return response.getBody();
        }catch (RestClientException ex) {
            ex.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}