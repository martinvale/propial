package com.ibiscus.propial.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth
    .PreAuthenticatedAuthenticationToken;

import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;

public class GoogleAccountsAuthenticationProvider implements
    AuthenticationProvider {
  private UserRepository userRepository;

  public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {
    com.google.appengine.api.users.User googleUser
        = (com.google.appengine.api.users.User) authentication.getPrincipal();

    User user = userRepository.findByUsername(googleUser.getEmail());

    if (user == null) {
        // User not in registry. Needs to register
        user = new User(googleUser.getUserId(), googleUser.getNickname(),
            googleUser.getEmail());
    }

    if (!user.isEnabled()) {
        throw new DisabledException("Account is disabled");
    }

    List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
    return new UsernamePasswordAuthenticationToken(user,
        authentication.getDetails(), roles);
  }

  public final boolean supports(Class<?> authentication) {
    return PreAuthenticatedAuthenticationToken.class
        .isAssignableFrom(authentication);
  }

  public void setUserRepository(UserRepository theUserRepository) {
    userRepository = theUserRepository;
  }
}