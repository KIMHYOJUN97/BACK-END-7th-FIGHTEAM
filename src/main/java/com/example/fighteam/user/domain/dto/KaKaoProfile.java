package com.example.fighteam.user.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

@Getter
@Setter
public class KaKaoProfile {

    public Long id;
    public String connected_at;
    public Properties properties;
    public kakaoAccount kakao_account;

    @Getter
    @Setter
    public class Properties {
        public String nickname;
    }

    @Getter
    @Setter
    public class kakaoAccount {

        public Boolean profile_nickname_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;

        @Getter
        @Setter
        public class Profile {

            public String nickname;

        }
    }

}




