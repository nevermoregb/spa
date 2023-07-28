package com.park.spa.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.park.spa.vo.MemberVo;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = -3838975279235142409L;

    private String username;
    private String password;
    private MemberVo memberVo;
    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public MemberVo getMemberVo() {
    	return memberVo;
    }
    
    public void setMemberVo(MemberVo memberVo) {
    	this.memberVo = memberVo;
    }

}
