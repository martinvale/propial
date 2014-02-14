package com.ibiscus.propial.application.business;

import java.util.ArrayList;
import java.util.List;

import com.ibiscus.propial.domain.business.Ambient;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.security.Contract;

public class PublicationDto {

  /** The id of the publication. */
  private Long id;

  private Contract contract;

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

  PublicationDto() {}

  /** Gets the id of the publication.
   *
   * @return The id of the publication
   */
  public Long getId() {
    return id;
  }

  public Contract getContract() {
    return contract;
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

  public void update(final Publication publication) {
    publication.update(type, address, age, expenses, description, price,
        surface, currencyType, forProfessional, ambients);
  }
}
