package com.ibiscus.propial.domain.business;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.ibiscus.propial.infraestructure.objectify.OfyService;
import com.ibiscus.propial.web.utils.ResultSet;

public class LocationRepository {

  public Location get(final long id) {
    return OfyService.ofy().load().type(Location.class).id(id).now();
  }

  public ResultSet<Location> find(final Location parent, final int start,
      final int limit) {
    Query<Location> query = OfyService.ofy().load().type(Location.class)
        .limit(limit);
    if (start > 0) {
      query = query.offset(start);
    }
    query = query.filter("parent", parent);
    List<Location> locations = query.list();
    int size = OfyService.ofy().load().type(Location.class).count();
    return new ResultSet<Location>(locations, size);
  }

  public long save(final Location Location) {
    Validate.notNull(Location, "The Location cannot be null");
    Key<Location> key = OfyService.ofy().save().entity(Location).now();
    return key.getId();
  }

  /** Deletes a location from the repository.
   *
   * @param id The id of the location to remove. Must be a value greater than 0.
   */
  public void delete(final long id) {
    Validate.isTrue(id > 0, "The id of the location must be greater than 0");
    Location Location = get(id);
    OfyService.ofy().delete().entity(Location).now();
  }
}
