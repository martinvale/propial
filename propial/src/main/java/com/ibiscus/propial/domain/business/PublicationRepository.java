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

    Key userKey = (Key) publicationEntity.getProperty("author");
    Publication publication = new Publication(
        publicationEntity.getKey().getId());
    String type = null;
    String address = null;
    Integer age = null;
    Double expenses = null;
    String description = null;
    Double price = null;
    String currencyType = null;
    Integer surface = null;
    boolean forProfessional = false;
    List<Ambient> ambients = new ArrayList<Ambient>();
    if (publicationEntity.getProperty("type") != null) {
      type = publicationEntity.getProperty("type").toString();
    }
    if (publicationEntity.getProperty("address") != null) {
      address = publicationEntity.getProperty("address").toString();
    }
    if (publicationEntity.getProperty("age") != null) {
      age = new Integer(publicationEntity.getProperty("age").toString());
    }
    if (publicationEntity.getProperty("expenses") != null) {
      expenses = new Double(publicationEntity.getProperty("expenses").toString());
    }
    if (publicationEntity.getProperty("description") != null) {
      description = publicationEntity.getProperty("description").toString();
    }
    if (publicationEntity.getProperty("price") != null) {
      price = new Double(publicationEntity.getProperty("price").toString());
    }
    if (publicationEntity.getProperty("currencyType") != null) {
      currencyType = publicationEntity.getProperty("currencyType").toString();
    }
    if (publicationEntity.getProperty("surface") != null) {
      surface = new Integer(publicationEntity.getProperty("surface").toString());
    }
    if (publicationEntity.getProperty("forProfessional") != null) {
      forProfessional = new Boolean(publicationEntity
          .getProperty("forProfessional").toString());
    }
    publication.update(type, address, age, expenses, description,
        price, surface, currencyType, forProfessional, ambients);
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

    publicationEntity.setProperty("type", publication.getType());
    publicationEntity.setProperty("address",
        publication.getAddress());
    publicationEntity.setProperty("currencyType",
        publication.getCurrencyType());
    publicationEntity.setProperty("price", publication.getPrice());
    publicationEntity.setProperty("age", publication.getAge());
    publicationEntity.setProperty("expenses",
        publication.getExpenses());
    publicationEntity.setProperty("description",
        publication.getDescription());
    publicationEntity.setProperty("surface",
        publication.getSurface());
    publicationEntity.setProperty("forProfessional",
        publication.isForProfessional());
    Key userKey = KeyFactory.createKey("User", publication.getAuthor().getId());
    publicationEntity.setProperty("author", userKey);

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
    //query.addSort("displayName", SortDirection.ASCENDING);

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

  public Key save(final Publication publication) {
    try {
      Entity publicationEntity = publicationToEntity(publication);
      return datastore.put(publicationEntity);
    } catch (Exception e) {
      return null;
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
