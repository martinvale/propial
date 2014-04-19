package com.ibiscus.propial.web.security;

import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;

public class GoogleAccountsAuthenticationProvider implements
    AuthenticationProvider {

  /** Logger. */
  private final static Logger LOG = Logger.getLogger(
      GoogleAccountsAuthenticationProvider.class.getName());

  /** The user repository. */
  private UserRepository userRepository;

  public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {
    com.google.appengine.api.users.User googleUser
        = (com.google.appengine.api.users.User) authentication.getPrincipal();
    LOG.info("Currently logged GAE user " + googleUser);

    User user = userRepository.findByEmail(googleUser.getEmail());
    if (user == null) {
      LOG.info("User not registered. Create new user.");
      // User not in registry. Needs to register
      user = new User(googleUser.getUserId(), googleUser.getNickname(),
          googleUser.getEmail());
    }

    if (!user.isEnabled()) {
      throw new DisabledException("Account is disabled");
    }

    LOG.info("Current user in context email: " + user.getEmail() + ", role: "
        + user.getRole());
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
