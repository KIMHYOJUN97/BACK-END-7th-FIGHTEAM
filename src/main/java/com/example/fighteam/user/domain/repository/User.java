package com.example.fighteam.user.domain.repository;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)         //자동생성을 돕는
    private String id;

    private String email;
    private String name;
    private String passwd;
    private String tel;

    @Builder
    public User(String id, String email, String passwd, String name, String tel) {
        this.id = id;
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.tel = tel;


    }
}