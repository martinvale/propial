package com.ibiscus.propial.domain.security;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class User {

  public enum ROLES {
    ADMIN,
    CUSTOMER_ADMIN
  }

  @Id
  private Long id;

  @Index
  private String googleId;

  @Index
  private String username;
  private String password;

  @Index
  private String displayName;
  @Index
  private String email;
  private String picture;
  private String role;

  private boolean enabled;

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

  /** This constructor is used whenever the login comes from Google.
   *
   * @param theId
   * @param theUsername
   * @param thePassword
   * @param theDisplayName
   * @param theEmail
   * @param theRole
   */
  public User(final String theGoogleId, final String theUsername,
      final String theEmail) {
    Validate.notNull(theGoogleId, "The Google id cannot be null");
    Validate.notNull(theUsername, "The username cannot be null");
    Validate.notNull(theEmail, "The email cannot be null");

    googleId = theGoogleId;
    username = theUsername;
    email = theEmail;
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

  /** Indicates if the user can use the application or not.
   *
   * @return True if the user can access the application, false otherwise.
   */
  public boolean isEnabled() {
    return enabled;
  }
}
