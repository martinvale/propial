package com.ibiscus.propial.domain.security;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class User implements Serializable {

  private static final long serialVersionUID = -8525317709258541855L;

  public enum ROLE {
    ADMIN,
    CUSTOMER_ADMIN,
    PUBLISHER
  }

  public enum STATUS {
    NEW,
    ACTIVE,
    INACTIVE
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
  private ROLE role;
  private STATUS status;

  @Index
  private Ref<Contract> contract;

  /** Default constructor. */
  User() {}

  public User(final Contract theContract) {
    Validate.notNull(theContract, "The contract cannot be null");

    status = STATUS.NEW;
    contract = Ref.create(Key.create(Contract.class, theContract.getId()));
  }

  /** This constructor is used whenever a user registers from the public site.
   *
   * @param theUsername
   * @param thePassword
   * @param theDisplayName
   * @param theEmail
   */
  public User(final String theUsername, final String thePassword,
      final String theDisplayName, final String theEmail) {
    Validate.notNull(theUsername, "The username cannot be null");
    Validate.notNull(thePassword, "The password cannot be null");
    Validate.notNull(theDisplayName, "The display name cannot be null");
    Validate.notNull(theEmail, "The email cannot be null");

    username = theUsername;
    password = thePassword;
    displayName = theDisplayName;
    email = theEmail;
    role = ROLE.PUBLISHER;
    status = STATUS.NEW;
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

  public void update(final String theUsername, final String thePassword,
      final String theDisplayName, final String theEmail,
      final ROLE theRole) {
    Validate.notNull(theUsername, "The username cannot be null");
    Validate.notNull(thePassword, "The password cannot be null");
    Validate.notNull(theDisplayName, "The display name cannot be null");
    Validate.notNull(theEmail, "The email cannot be null");
    Validate.notNull(theRole, "role");

    username = theUsername;
    password = thePassword;
    displayName = theDisplayName;
    email = theEmail;
    role = theRole;
  }

  public void updatePicture(final String thePicture) {
    picture = thePicture;
  }

  public long getId() {
    return id;
  }

  public Contract getContract() {
    if (contract != null) {
      return contract.get();
    } else {
      return null;
    }
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
  public ROLE getRole() {
    return role;
  }

  /** Indicates if the user can use the application or not.
   *
   * @return True if the user can access the application, false otherwise.
   */
  public boolean isEnabled() {
    return status == STATUS.ACTIVE;
  }

  /** Enable the user to use the application. */
  public void enable() {
    status = STATUS.ACTIVE;
  }

  /** Disable the user to use the application. */
  public void disable() {
    status = STATUS.INACTIVE;
  }
}
