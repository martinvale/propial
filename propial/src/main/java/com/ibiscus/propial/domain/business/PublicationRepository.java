package com.ibiscus.propial.domain.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;
import com.ibiscus.propial.web.utils.ResultSet;

public class PublicationRepository {

  private final DatastoreService datastore;

  public PublicationRepository(final DatastoreService theDatastoreService) {
    Validate.notNull(theDatastoreService, "The datastore cannot be null");
    datastore = theDatastoreService;
  }

  private Publication entityToPublication(final Entity publicationEntity) {
    Validate.notNull(publicationEntity, "The publication entity cannot be "
        + "null");

    Publication publication = new Publication();
    return publication;
  }

  private Entity publicationToEntity(final Publication publication) {
    Validate.notNull(publication, "The user cannot be null");

    Entity publicationEntity;
    if (publication.getId() > 0) {
      publicationEntity = new Entity("Publication", publication.getId());
    } else {
      publicationEntity = new Entity("Publication");
    }
    /*userEntity.setProperty("username", user.getUsername());
    userEntity.setProperty("password", user.getPassword());
    userEntity.setProperty("displayName", user.getDisplayName());
    userEntity.setProperty("email", user.getEmail());
    userEntity.setProperty("picture", user.getPicture());
    userEntity.setProperty("role", user.getRole());*/
    return publicationEntity;
  }

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
    Query query = new Query("Publication");
    query.addSort("displayName", SortDirection.ASCENDING);

    PreparedQuery preparedQuery = datastore.prepare(query);
    FetchOptions fetch = FetchOptions.Builder.withDefaults();
    int size = preparedQuery.countEntities(fetch);

    fetch = FetchOptions.Builder.withOffset(start);
    fetch.limit(limit);
    List<Entity> entities = preparedQuery.asList(fetch);

    List<Publication> publications = new ArrayList<Publication>(
        entities.size());
    for (Entity entity : entities) {
      publications.add(entityToPublication(entity));
    }
    return new ResultSet<Publication>(publications, size);
  }

  public void save(final Publication publication) {
    Transaction transaction = datastore.beginTransaction();
    try {
      Entity publicationEntity = publicationToEntity(publication);
      datastore.put(publicationEntity);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      throw new RuntimeException("Cannot save the publication", e);
    }
  }

  public Publication get(final long id) {
    Key publicationKey = KeyFactory.createKey("Publication", id);
    try {
      Entity publicationEntity = datastore.get(publicationKey);
      return entityToPublication(publicationEntity);
    } catch (EntityNotFoundException e) {
      return null;
    }
  }

  /** Deletes a publication from the repository.
   *
   * @param publicationId The id of the publication to remove. Must be a value
   *  greater than 0.
   */
  public void delete(final long publicationId) {
    Key publicationKey = KeyFactory.createKey("Publication", publicationId);
    datastore.delete(publicationKey);
  }
}
