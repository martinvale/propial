package com.ibiscus.propial.domain.security;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.ibiscus.propial.infraestructure.objectify.OfyService;
import com.ibiscus.propial.web.utils.ResultSet;

public class UserRepository {

  public User get(final long id) {
    return OfyService.ofy().load().type(User.class).id(id).now();
  }

  public long save(final User user) {
    Validate.notNull(user, "The user cannot be null");
    Key<User> key = OfyService.ofy().save().entity(user).now();
    return key.getId();
  }

  /** Deletes a user from the repository.
   *
   * @param id The id of the user to remove. Must be a value greater than 0.
   */
  public void delete(final long id) {
    Validate.isTrue(id > 0, "The id of the contract must be greater than 0");
    User user = get(id);
    OfyService.ofy().delete().entity(user).now();
  }

  public ResultSet<User> find(final int start, final int limit) {
    Query<User> query = OfyService.ofy().load().type(User.class)
        .limit(limit).order("displayName");
    if (start > 0) {
      query = query.offset(start);
    }
    List<User> users = query.list();
    int size = OfyService.ofy().load().type(User.class).count();
    return new ResultSet<User>(users, size);
  }

}
