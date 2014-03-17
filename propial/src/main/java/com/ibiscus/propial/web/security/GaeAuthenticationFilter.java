package com.ibiscus.propial.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class GaeAuthenticationFilter extends GenericFilterBean {

  private static final String REGISTRATION_URL = "/register";

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> ads = new WebAuthenticationDetailsSource();
  private AuthenticationManager authenticationManager;
  private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User googleUser = UserServiceFactory.getUserService().getCurrentUser();

    if (authentication != null && !loggedInUserMatchesGaeUser(authentication,
        googleUser)) {
      SecurityContextHolder.clearContext();
      authentication = null;
      ((HttpServletRequest) request).getSession().invalidate();
    }

    if (authentication == null) {
      if (googleUser != null) {
        logger.debug("Currently logged on to GAE as user " + googleUser);
        logger.debug("Authenticating to Spring Security");
        // User has returned after authenticating via GAE. Need to authenticate
        // through Spring Security.
        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(googleUser, null);
        token.setDetails(ads.buildDetails((HttpServletRequest) request));

        try {
          authentication = authenticationManager.authenticate(token);
          // Setup the security context
          SecurityContextHolder.getContext().setAuthentication(authentication);

          // Send new users to the registration page.
          if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(com.ibiscus.propial.domain.security.User.ROLE.UNREGISTERED.toString()))) {
            logger.debug("New user authenticated. Redirecting to registration page");
            ((HttpServletResponse) response).sendRedirect(REGISTRATION_URL);
            return;
          }
        } catch (AuthenticationException e) {
          // Authentication information was rejected by the authentication manager
          failureHandler.onAuthenticationFailure((HttpServletRequest) request,
              (HttpServletResponse) response, e);
          return;
        }
      }
    }

    chain.doFilter(request, response);
  }

  private boolean loggedInUserMatchesGaeUser(
      final Authentication authentication, final User googleUser) {
    assert authentication != null;

    if (googleUser == null) {
      // User has logged out of GAE but is still logged into application
      return false;
    }

    com.ibiscus.propial.domain.security.User gaeUser = (com.ibiscus.propial.domain.security.User) authentication.getPrincipal();

    if (!gaeUser.getEmail().equals(googleUser.getEmail())) {
      return false;
    }

    return true;
  }

  @Override
  public void afterPropertiesSet() throws ServletException {
    Validate.notNull(authenticationManager, "The authentication manager cannot"
        + "be null");
    super.afterPropertiesSet();
  }

  public void setAuthenticationManager(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
    this.failureHandler = failureHandler;
  }
}
