package com.codegym.project.users.userProfile;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 8, max = 20)
    private String fullName;

    @Column(nullable = false, unique = true)
    @Size(min = 9, max = 11)
    private String phone;

    private String avatar;

    private String sex;

    public UserProfile(){}

    public UserProfile(String fullName, String phone, String avatar, String sex) {
        this.fullName = fullName;
        this.phone = phone;
        this.avatar = avatar;
        this.sex = sex;
    }

    public UserProfile(String fullName, String phone, String sex) {
        this.fullName = fullName;
        this.phone = phone;
        this.sex = sex;
    }
}
