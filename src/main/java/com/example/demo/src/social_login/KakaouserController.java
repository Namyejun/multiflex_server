package com.example.demo.src.social_login;

import com.example.demo.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaouserController {
    private final KakaoUserService userService;

//    @GetMapping("/oauth2/authorization/kakao")
//    public void oauth2AuthorizationKakao(@RequestParam("code") String code) throws BaseException {
//        userService.oauth2AuthorizationKakao(code);
//    }

    @GetMapping("/kakao/login")
    public void kakao_login(@RequestParam("code") String code) throws BaseException {
        userService.oauth2AuthorizationKakao(code);
    }
}
