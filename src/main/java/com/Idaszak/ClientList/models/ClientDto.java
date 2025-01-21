package com.Idaszak.ClientList.models;

import jakarta.validation.constraints.*;

public class ClientDto {

    @NotEmpty(message = "The First Name is required")
    private String firstName;

    @NotEmpty(message = "The Last Name is required")
    private String lastName;

    @NotEmpty(message = "The Email is required")
    @Email
    private String email;

    private String phone;

    private String address;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
