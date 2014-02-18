package com.ibiscus.propial.domain.business;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.ibiscus.propial.infraestructure.objectify.OfyService;

public class LocationRepository {

  public Location get(final long id) {
    return OfyService.ofy().load().type(Location.class).id(id).now();
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
