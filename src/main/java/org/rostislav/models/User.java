package org.rostislav.models;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String licenseNumber;
    private int roleId;

    public User() {
    }

    public User(String fullName, String email, String password, String phone, String licenseNumber, int roleId) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
