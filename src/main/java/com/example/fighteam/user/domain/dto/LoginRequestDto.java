package com.example.fighteam.user.domain.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginRequestDto {
    private String id;
    private String passwd;
    private String phone;
    private String email;

    public static LoginRequestDto toLoginRequestDto(MemberEntity memberEntity) {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setId(memberEntity.getId());
        loginRequestDto.setpasswd(memberEntity.getPasswd());
        loginRequestDto.setphone(memberEntity.getPhone());
        loginRequestDto.setemail(memberEntity.getEmail());
        return loginRequestDto;
    }
}
