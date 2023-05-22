package com.example.fighteam.user.controller;

import com.example.fighteam.user.domain.dto.KaKaoProfile;
import com.example.fighteam.user.domain.dto.OAuthToken;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import com.example.fighteam.user.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    public final UserService userService;


    @GetMapping("/user/login")
    public String mainForm() {
        return "joinlogin/login";
    }

    @GetMapping("/user/join")
    public String createUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "joinlogin/join";
    }

    @GetMapping("/user/edit")
    public String editUserForm(Model model, HttpSession session) {
        model.addAttribute("userScore", userRepository.findById((Long) session.getAttribute("loginId")).get().getScore());
        return "joinlogin/userEdit";
    }


    //회원가입
    @RequestMapping(value = "/user/join", method = RequestMethod.POST)
    public String joinUs(User user, Model model) {
        User dup = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(dup != null){
            model.addAttribute("user", user);
            return "joinlogin/join";
        }
        userService.signUp(user);
        return "joinlogin/login";
    }

    //로그인
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(User member, Model model, HttpSession session) {
        User user = userService.loginUser(member.getEmail(), member.getPasswd());

        // 아이디 비밀번호 불일치
        if (user == null) {
            model.addAttribute("loginMessage", "아이디 혹은 비밀번호가 일치하지 않습니다.");
            return "joinlogin/login";
        }

        session.setAttribute("loginId", user.getId());        //세션에 아이디를 저장함.
        //로그인한 유저가 누군지 확인가능

        model.addAttribute("name", user.getName());
        return "redirect:/post/home";
    }


    //회원정보수정
    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String editUser(@RequestParam("passwd") String password,@RequestParam("tel") String userTel,HttpSession session,Model model) {
        User user = userRepository.findById((Long) session.getAttribute("loginId")).get();
        user.setPasswd(password);
        user.setTel(userTel);
        userRepository.save(user);

        return "redirect:/user/edit";
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/post/home";
    }


    //카카오 로그인 콜백 함수
    @GetMapping("/user/kakao/callback")
    public String kakaoCallback(String code, HttpSession session) throws JsonProcessingException {

        OAuthToken oAuthToken = userService.getAccessTokenfromKakao(code);

        System.out.println("카카오 엑세스 토큰:" + oAuthToken.getAccess_token());

        KaKaoProfile kaKaoProfile = userService.getInfoFromKakao(oAuthToken);
        System.out.println("카카오 아이디: " + kaKaoProfile.getId());
        System.out.println("카카오 이메일: " + kaKaoProfile.getKakao_account().getEmail());
        System.out.println("카카오 이름: " + kaKaoProfile.getKakao_account().getProfile().getNickname());

        String name = kaKaoProfile.getKakao_account().getProfile().getNickname();
        String email = kaKaoProfile.getKakao_account().getEmail();
        String passwd = UUID.randomUUID().toString();
        User user = new User();
        user.setEmail(email);
        user.setPasswd(passwd);
        user.setName(name);
        User dup = userRepository.findByEmail(email).orElse(null);
        if(dup == null){
            userService.signUp(user);
        }
        User user2 = userService.loginUserId(email);
        System.out.println("userId"+user.getId());
        session.setAttribute("loginId", user2.getId());
        return "redirect:/post/home";
    }

}



