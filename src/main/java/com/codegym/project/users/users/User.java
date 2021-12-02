package com.codegym.project.users.users;

import com.codegym.project.role.Role;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.userProfile.UserProfile;
import com.codegym.project.users.userStatus.UserStatus;
import io.micrometer.core.lang.Nullable;
import lombok.Data;
import org.hibernate.annotations.CollectionType;

import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 20)
    private String username;

    @Column(columnDefinition = "text")
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToOne
    private UserProfile userProfile;

    @OneToOne
    private MerchantProfile merchantProfile;

    @ManyToOne
    private UserStatus userStatus;
}
