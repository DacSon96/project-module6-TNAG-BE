package com.codegym.project.orders.order;

import lombok.Data;

@Data
public class orderDto {

    private Long id;
    private String note;
    private String orderTime;
    private String totalPayment;
    private String customerName;
    private String phone;

    public orderDto() {
    }

    public orderDto(Long id, String note, String orderTime, String totalPayment, String customerName, String phone) {
        this.id = id;
        this.note = note;
        this.orderTime = orderTime;
        this.totalPayment = totalPayment;
        this.customerName = customerName;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
