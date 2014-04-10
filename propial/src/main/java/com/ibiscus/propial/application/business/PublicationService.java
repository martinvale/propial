package com.ibiscus.propial.application.business;

import org.apache.commons.lang.Validate;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;
import com.ibiscus.propial.domain.business.Resource;

public class PublicationService {

  private final PublicationRepository publicationRepository;

  private final BlobstoreService blobstoreService;

  public PublicationService(final PublicationRepository thePublicationRepository) {
    Validate.notNull(thePublicationRepository, "The publication repository "
        + "cannot be null");
    publicationRepository = thePublicationRepository;
    blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  }

  public void publish(final Publication publication) {
  }

  /** Deletes a publication and the associated resources.
   *
   * @param publicationId The id of the publication to delete, must be greater
   *  than 0.
   */
  public void delete(final long publicationId) {
    Validate.isTrue(publicationId > 0, "The id of the publication must greater"
        + " than 0");
    Publication publication = publicationRepository.get(publicationId);
    for (Resource resource : publication.getResources()) {
      blobstoreService.delete(resource.getKey());
    }
    publicationRepository.delete(publicationId);
  }
}
