package com.restdocs.practice.user;

import com.restdocs.practice.core.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdGen")
    @SequenceGenerator(name = "userIdGen", sequenceName = "USER_ID_SEQ_GEN", allocationSize = 30)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    @Builder
    public User(long id, String fullName, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
}
