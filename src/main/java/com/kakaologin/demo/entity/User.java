package com.kakaologin.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @Column
    private String email;

    @Column
    private String userName;

    @Column
    private String phone;

    @Column
    private Long deptId;

    @Column
    private String oauthId;

    @Column
    private String nickname;

    @Column
    private Date connectedAt;

    @Column
    private String WebName;

    public User() {

    }
}
