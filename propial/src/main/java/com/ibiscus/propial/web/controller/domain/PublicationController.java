package com.ibiscus.propial.web.controller.domain;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.ibiscus.propial.application.business.PublicationDto;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;
import com.ibiscus.propial.domain.business.Resource;
import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;
import com.ibiscus.propial.web.utils.Packet;
import com.ibiscus.propial.web.utils.ResultSet;

@Controller
@RequestMapping(value="/services/publications")
public class PublicationController {

  /** Repository of publications. */
  @Autowired
  private PublicationRepository publicationRepository;

  /** Repository of users. */
  @Autowired
  private UserRepository usersRepository;

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public @ResponseBody Packet<Publication> save(
      @RequestBody PublicationDto publicationDto) {
    Publication publication;
    if (publicationDto.getId() != null) {
      publication = publicationRepository.get(publicationDto.getId());
    } else {
      User user = (User) SecurityContextHolder.getContext().getAuthentication()
          .getPrincipal();
      publication = new Publication(user.getContract(), user);
    }
    publicationDto.update(publication);
    publicationRepository.save(publication);
    return new Packet<Publication>(publication);
  }

  @RequestMapping(value = "/{publicationId}", method = RequestMethod.GET)
  public @ResponseBody Packet<Publication> get(
      @PathVariable long publicationId) {
    Packet<Publication> result;
    Publication publication = publicationRepository.get(publicationId);
    if (publication != null) {
      result = new Packet<Publication>(publication);
    } else {
      result = new Packet<Publication>();
    }
    return result;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public @ResponseBody ResultSet<Publication> get(@RequestParam int start,
      @RequestParam int limit) {
    return publicationRepository.find(start, limit, null, true, null);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public @ResponseBody boolean delete(@PathVariable long id) {
    publicationRepository.delete(id);
    return true;
  }

  @RequestMapping(value = "/upload")
  public @ResponseBody Packet<Resource> upload(HttpServletRequest req,
      HttpServletResponse res) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory
        .getBlobstoreService();
    Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
    BlobKey key = blobs.get("file");
    String publicationId = (String) req.getParameter("publicationId");
    Publication publication = publicationRepository.get(
        Long.valueOf(publicationId));
    Resource resource = new Resource(key);
    publicationRepository.saveResource(resource);
    publication.addResource(resource);
    publicationRepository.save(publication);
    return new Packet<Resource>(resource);
  }

  @RequestMapping(value = "/resource/{key}")
  public void getResource(@PathVariable String key,
      HttpServletRequest req, HttpServletResponse res) throws IOException {
    BlobstoreService blobstoreService = BlobstoreServiceFactory
        .getBlobstoreService();
    BlobKey blobKey = new BlobKey(key);
    blobstoreService.serve(blobKey, res);
  }

  @RequestMapping(value = "/{id}/{resourceId}/move", method = RequestMethod.POST)
  public @ResponseBody boolean moveResource(@PathVariable long id,
      @PathVariable String resourceId, @RequestParam int position) {
    Publication publication = publicationRepository.get(id);
    publication.moveResource(resourceId, position);
    publicationRepository.save(publication);
    return true;
  }

  @RequestMapping(value = "/{id}/{resourceId}", method = RequestMethod.DELETE)
  public @ResponseBody boolean deleteResource(@PathVariable long id,
      @PathVariable String resourceId) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory
        .getBlobstoreService();
    Publication publication = publicationRepository.get(id);
    publication.removeResource(resourceId);
    publicationRepository.save(publication);
    BlobKey blobKey = new BlobKey(resourceId);
    blobstoreService.delete(blobKey);
    return true;
  }

  @RequestMapping(value = "/resources/uploadUrl")
  public @ResponseBody String generateUploadUrl() {
    BlobstoreService service = BlobstoreServiceFactory.getBlobstoreService();
    return service.createUploadUrl("/services/publications/upload");
  }
}
