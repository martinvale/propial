package com.ibiscus.propial.domain.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.ibiscus.propial.domain.security.User;

public class Publication {

  /** The id of the publication. */
  private long id;

  /** Fecha de creacion del aviso. */
  private Date creation;
  private User author;

  private String type;
  private String address;
  private Integer age;

  private String code;

  private Double expenses;
  private String description;
  private Double price;
  private Integer surface;
  private String currencyType;
  private boolean forProfessional = false;
  private List<Ambient> ambients = new ArrayList<Ambient>();

  public Publication(final User theAuthor) {
    Validate.notNull(theAuthor, "The author cannot be null");
    creation = new Date();
    author = theAuthor;
  }

  public Publication(final long theId, final User theAuthor) {
    Validate.isTrue(theId > 0, "The id must be greater than 0");
    Validate.notNull(theAuthor, "The author cannot be null");
    id = theId;
    author = theAuthor;
  }

  /** Gets the id of the publication.
   *
   * @return The id of the publication
   */
  public long getId() {
    return id;
  }

  public User getAuthor() {
	return author;
  }

  public Date getCreation() {
    return creation;
  }

  public String getTitle() {
    return type + " " + address;
  }

  public String getType() {
    return type;
  }

  public String getAddress() {
    return address;
  }

  public Integer getAge() {
    return age;
  }

  public String getCode() {
    return code;
  }

  public Double getExpenses() {
    return expenses;
  }

  public String getDescription() {
    return description;
  }

  /**
   * @return the price
   */
  public Double getPrice() {
    return price;
  }

  /**
   * @return the superficie
   */
  public Integer getSurface() {
    return surface;
  }

  /**
   * @return the tipoMoneda
   */
  public String getCurrencyType() {
    return currencyType;
  }

  /**
   * @return the aptoProfesional
   */
  public boolean isForProfessional() {
    return forProfessional;
  }

  public List<Ambient> getAmbients() {
    return ambients;
  }

  public void update(final String theType, final String theAddress,
      final Integer theAge, final Double theExpenses,
      final String theDescription, final Double thePrice,
      final Integer theSurface, final String theCurrencyType,
      final boolean isForProfessional, final List<Ambient> theAmbients) {
    type = theType;
    address = theAddress;
    age = theAge;
    expenses = theExpenses;
    description = theDescription;
    price = thePrice;
    surface = theSurface;
    currencyType = theCurrencyType;
    forProfessional = isForProfessional;
    ambients.addAll(theAmbients);
  }

}
