package com.ibiscus.propial.domain.security;

import org.apache.commons.lang.Validate;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class ContractRepository {

  private final DatastoreService datastore;

  public ContractRepository(final DatastoreService theDatastoreService) {
    Validate.notNull(theDatastoreService, "The datastore cannot be null");
    datastore = theDatastoreService;
  }

  private Contract entityToContract(final Entity contractEntity) {
    Validate.notNull(contractEntity, "The contract entity cannot be null");

    Contract contract = new Contract(contractEntity.getKey().getId(),
        contractEntity.getProperty("name").toString());
    String address = null;
    String telephone = null;
    String email = null;
    String logo = null;
    if (contractEntity.hasProperty("address")) {
      address = contractEntity.getProperty("address").toString();
    }
    if (contractEntity.hasProperty("telephone")) {
      telephone = contractEntity.getProperty("telephone").toString();
    }
    if (contractEntity.hasProperty("email")) {
      email = contractEntity.getProperty("email").toString();
    }
    if (contractEntity.hasProperty("logo")) {
      logo = contractEntity.getProperty("logo").toString();
    }
    contract.update(address, telephone, email, logo);
    return contract;
  }

  private Entity contractToEntity(final Contract contract) {
    Validate.notNull(contract, "The contract cannot be null");

    Entity contractEntity;
    if (contract.getId() > 0) {
      contractEntity = new Entity("Contract", contract.getId());
    } else {
      contractEntity = new Entity("Contract");
    }
    contractEntity.setProperty("name", contract.getName());
    contractEntity.setProperty("address", contract.getAddress());
    contractEntity.setProperty("telephone", contract.getTelephone());
    contractEntity.setProperty("email", contract.getEmail());
    contractEntity.setProperty("logo", contract.getLogo());
    return contractEntity;
  }

  public Contract get(final long id) {
    Key contractKey = KeyFactory.createKey("Contract", id);
    try {
      Entity contractEntity = datastore.get(contractKey);
      return entityToContract(contractEntity);
    } catch (EntityNotFoundException e) {
      return null;
    }
  }

  public void save(final Contract contract) {
    Transaction transaction = datastore.beginTransaction();
    try {
      Entity contractEntity = contractToEntity(contract);
      datastore.put(contractEntity);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
    }
  }

  /** Deletes a user from the repository.
   *
   * @param userId The id of the user to remove. Must be a value greater than 0.
   */
  public void delete(final long userId) {
    Key userKey = KeyFactory.createKey("User", userId);
    datastore.delete(userKey);
  }
}
