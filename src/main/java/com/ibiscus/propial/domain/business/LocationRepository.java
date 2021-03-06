package com.ibiscus.propial.domain.business;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.ibiscus.propial.infraestructure.objectify.OfyService;
import com.ibiscus.propial.web.utils.QueryResults;

public class LocationRepository {

  public Location get(final long id) {
    return OfyService.ofy().load().type(Location.class).id(id).now();
  }

  public QueryResults<Location> suggest(final String name) {
    Validate.notNull(name, "The location name cannot be null");
    Query<Location> query = OfyService.ofy().load().type(Location.class);
    query = query.filter("tokenizedName", name.toLowerCase());
    List<Location> locations = query.list();
    return new QueryResults<Location>(locations, locations.size());
  }

  public QueryResults<Location> find(final Location parent, final int start,
      final int limit) {
    Query<Location> query = OfyService.ofy().load().type(Location.class);
    if (limit >= -1) {
        query.limit(limit);
    }
    if (start > 0) {
      query = query.offset(start);
    }
    query = query.filter("parent", parent);
    List<Location> locations = query.list();
    int size = OfyService.ofy().load().type(Location.class).count();
    return new QueryResults<Location>(locations, size);
  }

  public long save(final Location location) {
    Validate.notNull(location, "The Location cannot be null");
    Key<Location> key = OfyService.ofy().save().entity(location).now();
    return key.getId();
  }

  /** Deletes a location from the repository.
   *
   * @param id The id of the location to remove. Must be a value greater than 0.
   */
  public void delete(final long id) {
    Validate.isTrue(id > 0, "The id of the location must be greater than 0");
    Location location = get(id);
    OfyService.ofy().delete().entity(location).now();
  }
}
