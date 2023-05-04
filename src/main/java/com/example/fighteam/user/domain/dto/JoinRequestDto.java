package com.example.fighteam.user.domain.dto;

import com.example.fighteam.user.domain.repository.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class JoinRequestDto {
    private String id;
    private String email;
    private String passwd;
    private String name;
    private String tel;

    @Builder
    public JoinRequestDto(String id, String email, String passwd, String name, String tel) {
        this.id = id;
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.tel = tel;
    }

    public User toEntity(){
        return User.builder()
                .id(id)
                .email(email)
                .passwd(passwd)
                .name(name)
                .tel(tel)
                .build();
    }

}
