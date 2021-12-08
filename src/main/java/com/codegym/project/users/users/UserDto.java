package com.codegym.project.users.users;

public class UserDto {
    private Long id;
    private String name;
    private String address;
    private String thumbnail;

    public UserDto(Long id, String name, String address, String thumbnail) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.thumbnail = thumbnail;
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
