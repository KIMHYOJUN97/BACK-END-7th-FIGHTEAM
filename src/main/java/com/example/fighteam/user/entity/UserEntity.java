package com.example.fighteam.user.entity;

import com.example.fighteam.user.domain.dto.LoginRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class UserEntity {
    @Entity
    @Setter
    @Getter
    @Table(name = "member_table")
    public class UserEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private String id;

        @Column
        private String passwd;

        @Column
        private String phone;

        @Column(unique = true)
        String email;

        public static MemberEntity toMemberEntity(LoginRequestDto LRD) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setId(LoginRequestDto.getId());
            memberEntity.setPassword(LoginRequestDto.getPassword());
            memberEntity.setPhone(LoginRequestDto.getPhoone());
            memberEntity.setEmail(LoginRequestDto.getEmail());
            return memberEntity;



    }
}
