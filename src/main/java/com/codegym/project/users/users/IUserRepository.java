package com.codegym.project.users.users;

import com.codegym.project.role.Role;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.codegym.project.users.userStatus.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
//    public final static String sql = "SELECT t1.id, t2.name, t2.address, t2.thumbnail FROM user t1\n" +
//            "left join merchant_profile t2 on t1.merchant_profile_id = t2.id\n" +
//            "left join merchant_profile_categories mpc on t2.id = mpc.merchant_profile_id\n" +
//            "where categories_id = :id";

    User findByUsername(String username);
    Page<User> findAllByRolesContaining (Role role, Pageable pageable);

    User findByRolesContainingAndId(Role role, Long id);

    Page<User> findAllByRolesContainingAndUserStatus(Role role, UserStatus status,Pageable pageable);

    Page<User> findAllByRolesAndMerchantProfileNameContaining(Role role, String name, Pageable pageable);

    @Query(value = "select t1.id,t2.name,t2.address,t2.thumbnail from User  t1 left join MerchantProfile  t2 on t1.merchantProfile.id= t2.id " +
            "left join Category  mpc on t2.id = mpc.id where mpc.id = :id", nativeQuery = true)

//    @Query("select t1.id, t2.name, t2.address, t2.thumbnail from User t1 left join MerchantProfile t2 on t1.merchantProfile.id = t2.id left join ")
    List<UserDto> findUserByCategory(@Param("id") Long id);



}
