package com.ibiscus.propial.domain.business;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.ibiscus.propial.infraestructure.objectify.OfyService;
import com.ibiscus.propial.web.utils.QueryResults;

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
  public List<Publication> find(final int start, final int limit,
      final String order, final boolean asc,
      final Map<String, Object> filters) {
    Query<Publication> query = getQuery(filters);
    query.limit(limit);
    if (order != null) {
      query = query.order(order);
    }
    if (start > 0) {
      query = query.offset(start);
    }
    return query.list();
  }

  private Query<Publication> getQuery(final Map<String, Object> filters) {
    Query<Publication> query = OfyService.ofy().load().type(Publication.class);
    if (filters != null) {
      if (filters.containsKey("contract")) {
        query = query.filter("contract", filters.get("contract"));
      }
      if (filters.containsKey("location")) {
        query = query.filter("locations", filters.get("location"));
      }
      if (filters.containsKey("type")) {
        query = query.filter("type", filters.get("type"));
      }
      if (filters.containsKey("published")) {
        query = query.filter("status", Publication.STATUS.PUBLISHED);
      }
    }
    return query;
  }

  /** Get the count of hits with this filters applied.
   *
   * @param filters The filters to apply to the search.
   *
   * @return The count of hits.
   */
  public int getCount(final Map<String, Object> filters) {
    Query<Publication> query = getQuery(filters);
    return query.count();
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
