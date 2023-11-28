package com.swe444.demo.entity;


import java.io.Serializable;
import java.util.Objects;

public class RoleId implements Serializable {

    private String role;

    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleId roleId = (RoleId) o;
        return Objects.equals(role, roleId.role) && Objects.equals(username, roleId.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, username);
    }
}
