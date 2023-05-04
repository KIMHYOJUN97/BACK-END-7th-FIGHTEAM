package com.example.fighteam.user.service;

import com.example.fighteam.user.domain.dto.FindPwdRequestDto;
import com.example.fighteam.user.domain.dto.JoinRequestDto;
import com.example.fighteam.user.domain.dto.LoginRequestDto;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;    //함수 구동 실패 시 Rollback 할 수 있는 안전장치

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원가입
    @Transactional
    public String signUp(JoinRequestDto joinRequestDto){
        User user = joinRequestDto.toEntity();
        userRepository.save(user);
        return user.getId();

    }

    /*
    //로그인
    public void login(LoginRequestDto loginRequestDto) {
    }
    public void findPassword(FindPwdRequestDto findPwdRequestDto) {
    }*/

}
