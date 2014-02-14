package com.ibiscus.propial.domain.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.User;

@Entity
public class Publication implements Serializable {

  private static final long serialVersionUID = 1L;

  /** The id of the publication. */
  @Id
  private Long id;

  /** Fecha de creacion del aviso. */
  private Date creation;
  private Ref<User> author;
  private Ref<Contract> contract;

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
  //private Ref<List<Ambient>> ambients;
  private List<Ref<Resource>> resources = new ArrayList<Ref<Resource>>();

  Publication() {}

  public Publication(final Contract theContract, final User theAuthor) {
    Validate.notNull(theContract, "The contract cannot be null");
    Validate.notNull(theAuthor, "The author cannot be null");
    creation = new Date();
    contract = Ref.create(Key.create(Contract.class, theContract.getId()));
    author = Ref.create(Key.create(User.class, theAuthor.getId()));
  }

  public Publication(final long theId, final User theAuthor) {
    Validate.isTrue(theId > 0, "The id must be greater than 0");
    Validate.notNull(theAuthor, "The author cannot be null");
    id = theId;
    author = Ref.create(Key.create(User.class, theAuthor.getId()));
  }

  /** Gets the id of the publication.
   *
   * @return The id of the publication
   */
  public long getId() {
    return id;
  }

  public User getAuthor() {
    return author.get();
  }

  public Contract getContract() {
    return contract.get();
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

  /*public List<Ambient> getAmbients() {
    return ambients.get();
  }*/

  public List<Resource> getResources() {
    List<Resource> publicationResources = new ArrayList<Resource>();
    for (Ref<Resource> refs : resources) {
      publicationResources.add(refs.get());
    }
    return publicationResources;
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
    //ambients;
  }

  public void addResource(final Resource resource) {
    Validate.notNull(resource, "The resource cannot be null");
    Ref<Resource> resourceRef = Ref.create(Key.create(Resource.class,
        resource.getKey().getKeyString()));
    resources.add(resourceRef);
  }

  public void moveResource(final String resourceId, final int index) {
    Validate.notNull(resourceId, "The resource id cannot be null");
    removeResource(resourceId);
    Ref<Resource> resourceRef = Ref.create(Key.create(Resource.class,
        resourceId));
    resources.add(index, resourceRef);
  }

  public void removeResource(final String resourceId) {
    Validate.notNull(resourceId, "The resource id cannot be null");
    Ref<Resource> resourceRef = null;
    for (Ref<Resource> aRef : resources) {
      if (aRef.getKey().getName().equals(resourceId)) {
        resourceRef = aRef;
        break;
      }
    }
    Validate.notNull(resourceRef, "A resource reference must be found");
    resources.remove(resourceRef);
  }
}
