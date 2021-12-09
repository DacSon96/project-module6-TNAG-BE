package com.codegym.project.users.users;

import com.codegym.project.orders.order.Orders;
import com.codegym.project.orders.order.orderDto;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserFindBy {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<UserDto> findUserDto(Long categoryId){
        String sql = "select t1.id, t2.name, t2.address, t2.thumbnail from user as t1 " +
                "left join merchant_profile as t2 on t1.merchant_profile_id = t2.id " +
                "left join merchant_profile_categories mpc on t2.id = mpc.merchant_profile_id " +
                "where categories_id = "+ categoryId;

        List<UserDto> userDtoList = jdbcTemplate.query(sql, (rs,i) -> toUserDto(rs));
        return userDtoList;
    }


    private UserDto toUserDto(ResultSet rs) throws SQLException{
        UserDto userDto = new UserDto();
        userDto.setId(rs.getLong("id"));
        userDto.setName(rs.getString("name"));
        userDto.setAddress(rs.getString("address"));
        userDto.setThumbnail(rs.getString("thumbnail"));
        return userDto;
    }

    public List<User> getUsers(Long categoryId){
        List<User> userList= new ArrayList<>();
        List<UserDto> userDto = findUserDto(categoryId);
        userDto.forEach(userDto1 -> {
            User user= new User();
            MerchantProfile merchantProfile = new MerchantProfile();
            merchantProfile.setAddress(userDto1.getAddress());
            merchantProfile.setThumbnail(userDto1.getThumbnail());
            merchantProfile.setName(userDto1.getName());
            user.setId(userDto1.getId());
            userList.add(user);
        });
        return userList;
    }
}
