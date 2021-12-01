package com.codegym.project.users.users;

import com.codegym.project.role.Role;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.userProfile.UserProfile;
import com.codegym.project.users.userStatus.UserStatus;
import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 20)
    private String username;

    @Column(nullable = false)
    @Size(min = 6, max = 18)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany
    private List<Role> roles;

    @OneToOne
    private UserProfile userProfile;

    @OneToOne
    private MerchantProfile merchantProfile;

    @ManyToOne
    private UserStatus userStatus;

    public User(String username, String password, String email, List<Role> roles, UserProfile userProfile, MerchantProfile merchantProfile, UserStatus userStatus) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.userProfile = userProfile;
        this.merchantProfile = merchantProfile;
        this.userStatus = userStatus;
    }
}
