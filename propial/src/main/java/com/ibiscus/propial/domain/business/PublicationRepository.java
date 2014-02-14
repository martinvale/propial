package com.ibiscus.propial.domain.business;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.ibiscus.propial.infraestructure.objectify.OfyService;
import com.ibiscus.propial.web.utils.ResultSet;

public class PublicationRepository {

  /** Search for the publications in the database.
   *
   * @param start The start position of the hits.
   * @param limit The count of hits to retrieve.
   * @param order The order to search.
   * @param asc The direction of the order to apply.
   * @param filters The filters to apply to the search.
   *
   * @return The result set of publications.
   */
  public ResultSet<Publication> find(final int start, final int limit,
      final String order, final boolean asc,
      final Map<String, String> filters) {
    Query<Publication> query = OfyService.ofy().load().type(Publication.class)
        .limit(limit);
    if (order != null) {
      query = query.order(order);
    }
    if (start > 0) {
      query = query.offset(start);
    }
    List<Publication> publications = query.list();
    int size = OfyService.ofy().load().type(Publication.class).count();
    return new ResultSet<Publication>(publications, size);
  }

  public long save(final Publication publication) {
    Validate.notNull(publication, "The publication cannot be null");
    Key<Publication> key = OfyService.ofy().save().entity(publication).now();
    return key.getId();
  }

  public String saveResource(final Resource resource) {
    Validate.notNull(resource, "The resource cannot be null");
    Key<Resource> key = OfyService.ofy().save().entity(resource).now();
    return key.getName();
  }

  public Publication get(final long id) {
    return OfyService.ofy().load().type(Publication.class).id(id).now();
  }

  /** Deletes a publication from the repository.
   *
   * @param id The id of the publication to remove. Must be a value greater than
   *  0.
   */
  public void delete(final long id) {
    Validate.isTrue(id > 0, "The id of the publication must be greater than 0");
    Publication publication = OfyService.ofy().load().type(Publication.class)
        .id(id).now();
    OfyService.ofy().delete().entity(publication).now();
  }
}
