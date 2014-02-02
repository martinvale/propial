package com.ibiscus.propial.web.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

    User user = userRepository.findByUsername(googleUser.getUserId());

    if (user == null) {
        // User not in registry. Needs to register
        user = new User(googleUser.getUserId(), googleUser.getNickname(),
            googleUser.getEmail());
    }

    if (!user.isEnabled()) {
        throw new DisabledException("Account is disabled");
    }

    return new GaeUserAuthentication(user, authentication.getDetails());
  }

  public final boolean supports(Class<?> authentication) {
    return PreAuthenticatedAuthenticationToken.class
        .isAssignableFrom(authentication);
  }

  public void setUserRepository(UserRepository theUserRepository) {
    userRepository = theUserRepository;
  }
}
