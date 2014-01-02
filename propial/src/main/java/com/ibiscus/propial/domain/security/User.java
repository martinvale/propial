package com.ibiscus.propial.domain.security;

public class User {

  private String displayName;

  public User(final String theDisplayName) {
    displayName = theDisplayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
