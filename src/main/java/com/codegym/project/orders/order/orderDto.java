package com.codegym.project.orders.order;

import lombok.Data;

@Data
public class orderDto {
    private Long id;
    private String address;
    private String orderTime;
    private String totalPayment;
    private String fullName;
    private String phone;

    public orderDto(Long id, String address, String orderTime, String totalPayment, String fullName, String phone) {
        this.id = id;
        this.address = address;
        this.orderTime = orderTime;
        this.totalPayment = totalPayment;
        this.fullName = fullName;
        this.phone = phone;
    }
}
