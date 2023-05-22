package com.example.fighteam.user.service;
import com.example.fighteam.user.domain.dto.KaKaoProfile;
import com.example.fighteam.user.domain.dto.OAuthToken;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.management.Query;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(User user){
        user.setRole("USER");
        user.setJoinDate(LocalDateTime.now());
        user.setScore(36);
        User save = userRepository.save(user);
        System.out.println("saveUser = " + save);
    }

    public User loginUser(String email, String passwd){
        User user = userRepository.selectUserInfo(email,passwd);
        return user;
    }

    public User loginUserId(String email){
        User user = userRepository.selectUserId(email);
        return user;
    }

    public User findUser(Long id) {
        return userRepository.findById(id).orElse(null);

    }

    public OAuthToken getAccessTokenfromKakao(String code) throws JsonProcessingException {
        OAuthToken oAuthToken;
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","1b1b0fcb03c7cc2e4abdd64087aba89e");
        params.add("redirect_uri","http://localhost:8080/user/kakao/callback");
        params.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Http 요청하기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        oAuthToken = objectMapper.readValue((String) response.getBody(), OAuthToken.class);
        return oAuthToken;
    }

    public KaKaoProfile getInfoFromKakao(OAuthToken oAuthToken) throws JsonProcessingException {
        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+ oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        //Http 요청하기
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );


        ObjectMapper objectMapper2 = new ObjectMapper();

        KaKaoProfile kaKaoProfile = objectMapper2.readValue((String) response2.getBody(), KaKaoProfile.class);
        return kaKaoProfile;
    }
}

