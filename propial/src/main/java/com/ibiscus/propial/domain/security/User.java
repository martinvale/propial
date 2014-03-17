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
    PUBLISHER,
    UNREGISTERED
  }

  public enum STATUS {
    ACTIVE,
    INACTIVE
  }

  @Id
  private Long id;

  @Index
  private String googleId;

  private String nickname;

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

    status = STATUS.ACTIVE;
    contract = Ref.create(Key.create(Contract.class, theContract.getId()));
  }

  /** This constructor is used whenever the login comes from Google.
   *
   * @param theGoogleId
   * @param theNickname
   * @param theEmail
   */
  public User(final String theGoogleId, final String theNickname,
      final String theEmail) {
    Validate.notNull(theGoogleId, "The Google id cannot be null");
    Validate.notNull(theNickname, "The nickname cannot be null");
    Validate.notNull(theEmail, "The email cannot be null");

    googleId = theGoogleId;
    nickname = theNickname;
    email = theEmail;
    role = ROLE.UNREGISTERED;
    status = STATUS.ACTIVE;
  }

  /** This constructor is used when the user is registered.
   *
   * @param theGoogleId
   * @param theNickname
   * @param theEmail
   * @param theDisplayName
   */
  public User(final String theGoogleId, final String theNickname,
      final String theEmail, String theDisplayName) {
    Validate.notNull(theGoogleId, "The Google id cannot be null");
    Validate.notNull(theNickname, "The nickname cannot be null");
    Validate.notNull(theEmail, "The email cannot be null");

    googleId = theGoogleId;
    nickname = theNickname;
    email = theEmail;
    displayName = theDisplayName;
    role = ROLE.PUBLISHER;
    status = STATUS.ACTIVE;
  }

  public void update(final String theDisplayName,
      final ROLE theRole) {
    Validate.notNull(theDisplayName, "The display name cannot be null");
    Validate.notNull(theRole, "role");

    displayName = theDisplayName;
    role = theRole;
  }

  public void updatePicture(final String thePicture) {
    picture = thePicture;
  }

  public long getId() {
    return id;
  }

  public String getGoogleId() {
    return googleId;
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

  public String getNickname() {
    return nickname;
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

  /** The user registration is confirmed. */
  public void confirm() {
    status = STATUS.ACTIVE;
    role = ROLE.PUBLISHER;
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
