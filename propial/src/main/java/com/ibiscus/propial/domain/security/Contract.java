package com.ibiscus.propial.domain.security;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Contract implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public enum TYPE {
    USER,
    REALSTATE
  }

  @Id
  private Long id;

  @Index
  private String name;
  private String address;
  private String telephone;
  private String email;
  private BlobKey logo;
  private TYPE type;

  /** Default constructor. */
  Contract() {}

  public Contract(final long theId, final TYPE theType, final String theName) {
    Validate.isTrue(theId > 0, "The id must be greater than 0");
    Validate.notNull(theType, "The type cannot be null");
    Validate.notNull(theName, "The name cannot be null");
    id = theId;
    type = theType;
    name = theName;
  }

  public Contract(final TYPE theType, final String theName) {
    Validate.notNull(theType, "The type cannot be null");
    Validate.notNull(theName, "The name cannot be null");
    type = theType;
    name = theName;
  }

  public void update(final String theAddress, final String theTelephone,
      final String theEmail, final BlobKey theLogo) {
    address = theAddress;
    telephone = theTelephone;
    email = theEmail;
    logo = theLogo;
  }

  public long getId() {
    return id;
  }

  public TYPE getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getTelephone() {
    return telephone;
  }

  public String getEmail() {
    return email;
  }

  public BlobKey getLogo() {
    return logo;
  }
}
