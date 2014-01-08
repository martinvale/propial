package com.ibiscus.propial.domain.security;

import org.apache.commons.lang.Validate;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class UserRepository {

  private final DatastoreService datastore;

  public UserRepository(final DatastoreService theDatastoreService) {
    Validate.notNull(theDatastoreService, "The datastore cannot be null");
    datastore = theDatastoreService;
  }

  private User entityToUser(final Entity userEntity) {
    Validate.notNull(userEntity, "The user entity cannot be null");

    User user = new User(userEntity.getKey().getId(),
        userEntity.getProperty("username").toString(),
        userEntity.getProperty("password").toString(),
        userEntity.getProperty("displayName").toString(),
        userEntity.getProperty("email").toString(),
        userEntity.getProperty("role").toString());
    String picture = null;
    if (userEntity.hasProperty("picture")) {
      picture = userEntity.getProperty("picture").toString();
    }
    user.update(picture);
    return user;
  }

  private Entity userToEntity(final User user) {
    Validate.notNull(user, "The user cannot be null");

    Entity userEntity;
    if (user.getId() > 0) {
      userEntity = new Entity("User", user.getId());
    } else {
      userEntity = new Entity("User");
    }
    userEntity.setProperty("username", user.getUsername());
    userEntity.setProperty("password", user.getPassword());
    userEntity.setProperty("displayName", user.getDisplayName());
    userEntity.setProperty("email", user.getEmail());
    userEntity.setProperty("picture", user.getPicture());
    userEntity.setProperty("role", user.getRole());
    return userEntity;
  }

  public User get(final long id) {
    Key userKey = KeyFactory.createKey("User", id);
    try {
      Entity userEntity = datastore.get(userKey);
      return entityToUser(userEntity);
    } catch (EntityNotFoundException e) {
      return new User(5l, "martinvale", "test", "Martin Valletta",
          "martinvale@gmail.com", "admin");
    }
  }

  public void save(final User user) {
    Transaction transaction = datastore.beginTransaction();
    try {
      Entity userEntity = userToEntity(user);
      datastore.put(userEntity);
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
