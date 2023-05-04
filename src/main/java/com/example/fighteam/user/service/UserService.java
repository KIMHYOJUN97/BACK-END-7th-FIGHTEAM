package com.example.fighteam.user.service;

import com.example.fighteam.user.domain.dto.FindPwdRequestDto;
import com.example.fighteam.user.domain.dto.JoinRequestDto;
import com.example.fighteam.user.domain.dto.LoginRequestDto;
import com.example.fighteam.user.domain.repository.User;
import lombok.RequiredArgsConstructor;
import org.apache.naming.factory.SendMailFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final User userRepository;

    public void save(LoginRequestDto loginRequestDto){

    }

    public UserService(User userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(JoinRequestDto joinRequestDto) {
        System.out.println("은정님");
    }

    public void login(LoginRequestDto loginRequestDto) {

        System.out.println("지안님");
    }

    public List<LoginRequestDto> findAll() {
        List<MemberEntity> memberEntityList = User.findAll();
        List<LoginRequestDto> memberLoginRequestDtoList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            LoginRequestDto.add(LoginRequestDto.toLoginRequestDto(memberEntity));
            LoginRequestDto lRD = LoginRequestDto.toLoginRequestDto(memberLoginRequestDtoList);
            LoginRequestDto.add(memberLoginRequestDtoList);
        }
        return memberLoginRequestDtoList;
    }

    public LoginRequestDto findById(String id) {
        Optional<MemberEntity> optionalMemberEntity = User.findById(id);
        if (optionalMemberEntity.isPresent()) {
            return LoginRequestDto.toLoginRequestDto(optionalMemberEntity.get());
        }else{
            return null;
        }

     public LoginRequestDto updateForm(String email) {
            Optional<MemberEntity> optionalMemberEntity = User.findByEmail(email);
            if (optionalMemberEntity.isPresent()) {
                return LoginRequestDto.toLoginRequestDto(optionalMemberEntity.get());
            }else {
                return null;
            }
        }
    }

    public void update(LoginRequestDto memberLoginRequestDtoList) {
        User.save(MemberEntity.toUpdateMemberEntity(memberLoginRequestDtoList));
    }

    public void findPassword(FindPwdRequestDto findPwdRequestDto) {

    }

}
