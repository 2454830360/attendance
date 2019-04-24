package com.system.attendance.model;

public class Admin {
    private String adminId;

    private String adminPassword;

    private String adminPermission;

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminPermission='" + adminPermission + '\'' +
                '}';
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword == null ? null : adminPassword.trim();
    }

    public String getAdminPermission() {
        return adminPermission;
    }

    public void setAdminPermission(String adminPermission) {
        this.adminPermission = adminPermission == null ? null : adminPermission.trim();
    }
}