package com.rfs.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by alvaro on 10/29/17.
 */
public class RFSUserDetails extends User {

    private Integer id;

    private String fullName;

    public RFSUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username,password,authorities);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
