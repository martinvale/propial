package com.ibiscus.propial.domain.security;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.ibiscus.propial.infraestructure.objectify.OfyService;
import com.ibiscus.propial.web.utils.QueryResults;

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

  public QueryResults<User> find(final int start, final int limit) {
    Query<User> query = OfyService.ofy().load().type(User.class)
        .limit(limit).order("displayName");
    if (start > 0) {
      query = query.offset(start);
    }
    List<User> users = query.list();
    int size = OfyService.ofy().load().type(User.class).count();
    return new QueryResults<User>(users, size);
  }

  /** Finds a user in the repository that match with the Google id.
   *
   * @param googleId The Google id used to find the user, cannot be null.
   * @return The user whose Google id match, null if do not find any user with
   *  that Google id.
   */
  public User findByGoogleId(final String googleId) {
    User user = OfyService.ofy().load().type(User.class)
        .filter("googleId", googleId).first().now();
    return user;
  }

  /** Finds a user in the repository with the specified username.
   *
   * @param username The username of the user to find, cannot be null.
   * @return The user whose username match, null if do not find any user with
   *  that username.
   */
  public User findByUsername(final String username) {
    User user = OfyService.ofy().load().type(User.class)
        .filter("email", username).first().now();
    return user;
  }
}
