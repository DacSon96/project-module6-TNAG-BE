package com.codegym.project.orders.order;

import com.codegym.project.users.userAddress.UserDeliverAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderFindBy {


    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<orderDto> findOrderDto(String value,Long merchantId){
        String sql = "";
        try{
            Long idValue = Long.parseLong(value);
            sql = "select t1.id, t1.note, t1.order_time, t1.total_payment, t3.customer_name, t3.phone From orders as t1 left join user as t2 on t1.user_id = t2.id left join user_deliver_address as t3 on t2.id = t3.user_id where   t1.merchant_id="+merchantId+"  and   + t3.customer_name like '%"+idValue+"%'or t3.phone like '%"+idValue+"%' or t1.id = "+idValue;
        }catch (Exception e){
            sql = "select t1.id, t1.note, t1.order_time, t1.total_payment, t3.customer_name, t3.phone From orders as t1 left join user as t2 on t1.user_id = t2.id left join user_deliver_address as t3 on t2.id = t3.user_id where   t1.merchant_id="+merchantId+"  and   + t3.customer_name like '%"+value+"%'or t3.phone like '%"+value+"%' ";

        }

//        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("value", value);
         List<orderDto> orderDtoList = jdbcTemplate.query(sql, (rs,i) -> toOrderDto(rs));
         return orderDtoList;
    }

    private orderDto toOrderDto(ResultSet rs) throws SQLException {
        orderDto orderDto = new orderDto();
        orderDto.setId(rs.getLong("id"));
        orderDto.setNote(rs.getString("note"));
        orderDto.setOrderTime(rs.getString("order_time"));
        orderDto.setTotalPayment(rs.getString("total_payment"));
        orderDto.setCustomerName(rs.getString("customer_name"));
        orderDto.setPhone(rs.getString("phone"));
        return orderDto;
    }

    public List<Orders> getOrders(String value,Long merchantId){
        List<Orders> orderList = new ArrayList<>();
        List<orderDto> orderDto = findOrderDto(value,merchantId);
        orderDto.forEach(orderDto1 -> {
            Orders orders = new Orders();
            UserDeliverAddress userDeliverAddress = new UserDeliverAddress();
            userDeliverAddress.setCustomerName(orderDto1.getCustomerName());
            userDeliverAddress.setPhone(orderDto1.getPhone());
            orders.setId(orderDto1.getId());
            orders.setNote(orderDto1.getNote());
            orders.setOrderTime(LocalDateTime.parse(orderDto1.getOrderTime(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            orders.setTotalPayment(Double.parseDouble(orderDto1.getTotalPayment()));
            orders.setAddress(userDeliverAddress);
            orderList.add(orders);
        });
        return orderList;
    }
}
