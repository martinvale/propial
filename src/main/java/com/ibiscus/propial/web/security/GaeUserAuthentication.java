package com.ibiscus.propial.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.User;

public class GaeUserAuthentication implements Authentication {

  private static final long serialVersionUID = 1L;

  private final User user;
  private final Contract contract;
  private final Object details;
  private boolean authenticated;

  public GaeUserAuthentication(final User theUser, final Object theDetails) {
    user = theUser;
    contract = theUser.getContract();
    details = theDetails;
    authenticated = true;
  }

  public String getName() {
    return user.getDisplayName();
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
    return roles;
  }

  public Object getCredentials() {
    return new UnsupportedOperationException();
  }

  public Object getDetails() {
    return details;
  }

  public Object getPrincipal() {
    return user;
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(final boolean isAuthenticated) {
    authenticated = isAuthenticated;
  }

  public Contract getContract() {
    return contract;
  }
}
