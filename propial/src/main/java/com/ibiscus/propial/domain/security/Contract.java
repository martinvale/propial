package com.ibiscus.propial.domain.security;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Contract {

  @Id
  private Long id;

  @Index
  private String name;
  private String address;
  private String telephone;
  private String email;
  private String logo;

  /** Default constructor. */
  Contract() {
  }

  public Contract(final long theId, final String theName) {
    Validate.isTrue(theId > 0, "The id must be greater than 0");
    Validate.notNull(theName, "The name cannot be null");
    id = theId;
    name = theName;
  }

  public Contract(final String theName) {
    Validate.notNull(theName, "The name cannot be null");
    name = theName;
  }

  public void update(final String theAddress, final String theTelephone,
      final String theEmail, final String theLogo) {
    address = theAddress;
    telephone = theTelephone;
    email = theEmail;
    logo = theLogo;
  }

  public long getId() {
    return id;
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

  public String getLogo() {
    return logo;
  }
}
