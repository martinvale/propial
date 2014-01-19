package com.ibiscus.propial.domain.security;

import org.apache.commons.lang.Validate;

public class User {

  private long id;
  private String username;
  private String password;
  private String displayName;
  private String email;
  private String picture;
  private String role;

  /** Default constructor. */
  User() {}

  public User(final long theId, final String theUsername,
      final String thePassword, final String theDisplayName,
      final String theEmail, final String theRole) {
    Validate.isTrue(theId > 0, "The id must be greater than 0");
    Validate.notNull(theUsername, "The username cannot be null");
    Validate.notNull(thePassword, "The password cannot be null");
    Validate.notNull(theDisplayName, "The display name cannot be null");
    Validate.notNull(theEmail, "The email cannot be null");
    Validate.notNull(theRole, "role");

    id = theId;
    username = theUsername;
    password = thePassword;
    displayName = theDisplayName;
    email = theEmail;
    role = theRole;
  }

  public void update(final String thePicture) {
    picture = thePicture;
  }

  public long getId() {
    return id;
  }

  public String getDisplayName() {
    return displayName;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Gets the email of the user.
   *
   * @return the email of the user.
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return the picture
   */
  public String getPicture() {
    return picture;
  }

  /**
   * @return the role
   */
  public String getRole() {
    return role;
  }
}
