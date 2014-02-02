package com.ibiscus.propial.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ibiscus.propial.domain.security.User;

public class GaeUserAuthentication implements Authentication {

  private static final long serialVersionUID = 1L;

  private final User user;
  private final Object details;

  GaeUserAuthentication(final User theUser, final Object theDetails) {
    user = theUser;
    details = theDetails;
  }

  public String getName() {
    return user.getDisplayName();
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    roles.add(new SimpleGrantedAuthority(user.getRole()));
    return roles;
  }

  public Object getCredentials() {
    return null;
  }

  public Object getDetails() {
    return details;
  }

  public Object getPrincipal() {
    return null;
  }

  public boolean isAuthenticated() {
    return false;
  }

  public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
  }

}
