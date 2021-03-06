package com.ibiscus.propial.web.controller.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.ibiscus.propial.application.business.PublicationDto;
import com.ibiscus.propial.application.business.PublicationService;
import com.ibiscus.propial.domain.business.Location;
import com.ibiscus.propial.domain.business.LocationRepository;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.Publication.OPERATION;
import com.ibiscus.propial.domain.business.PublicationRepository;
import com.ibiscus.propial.domain.business.Resource;
import com.ibiscus.propial.domain.filters.Dimension;
import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.ContractRepository;
import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;
import com.ibiscus.propial.domain.services.FilterService;
import com.ibiscus.propial.web.security.GaeUserAuthentication;
import com.ibiscus.propial.web.utils.Packet;
import com.ibiscus.propial.web.utils.QueryResults;

@Controller
@RequestMapping(value="/services/publications")
public class PublicationController {

  /** Logger. */
  private final static Logger LOG = Logger.getLogger(
      PublicationController.class.getName());

  /** Repository of publications. */
  @Autowired
  private PublicationRepository publicationRepository;

  /** Repository of users. */
  @Autowired
  private UserRepository usersRepository;

  /** Repository of contracts. */
  @Autowired
  private ContractRepository contractRepository;

  /** Repository of locations. */
  @Autowired
  private LocationRepository locationRepository;

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public @ResponseBody Packet<Publication> save(
      @RequestBody PublicationDto publicationDto) {
    Publication publication;
    if (publicationDto.getId() != null) {
      LOG.info("Update publication with ID: " + publicationDto.getId());
      publication = publicationRepository.get(publicationDto.getId());
    } else {
      LOG.info("Create new publication");
      GaeUserAuthentication authentication;
      authentication = (GaeUserAuthentication) SecurityContextHolder
          .getContext().getAuthentication();
      User user = (User) authentication.getPrincipal();
      Contract contract;
      LOG.info("Current user role: " + user.getRole());
      if (user.getRole().equals(User.ROLE.ADMIN)) {
        LOG.info("Get contract with id: " + publicationDto.getContractId());
        contract = contractRepository.get(publicationDto.getContractId());
      } else {
        LOG.info("Get contract from user email: " + user.getEmail() + ", role: "
            + user.getRole());
        contract = authentication.getContract();
      }
      LOG.info("Current context user: " + user.getEmail() + ", contract: "
          + contract);
      publication = new Publication(contract, user);
    }
    List<Location> locations = new ArrayList<Location>();
    Location location = locationRepository.get(publicationDto.getLocationId());
    locations.add(location);
    while (location.getParent() != null) {
      location = location.getParent();
      locations.add(location);
    }
    publication.update(publicationDto.getType(),
        OPERATION.valueOf(publicationDto.getOperation()),
        publicationDto.getAddress(),
        publicationDto.getAge(), publicationDto.getExpenses(),
        publicationDto.getDescription(), publicationDto.getPrice(),
        publicationDto.getSurface(), publicationDto.getCurrencyType(),
        publicationDto.isForProfessional(), publicationDto.getAmbients(),
        locations);
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
  public @ResponseBody QueryResults<Publication> get(@RequestParam int start,
      @RequestParam int limit,
      @RequestParam(required = false) Long contractId,
      @RequestParam(required = false) Long locationId,
      @RequestParam(required = false) String type,
      @RequestParam(required = false) Boolean published) {
    Map<String, Object> filters = new HashMap<String, Object>();
    if (contractId != null) {
      Contract contract = contractRepository.get(contractId);
      filters.put("contract", contract);
    } else {
      UserService userService = UserServiceFactory.getUserService();
      com.google.appengine.api.users.User googleUser = userService
          .getCurrentUser();
      if (googleUser != null) {
        User user = usersRepository.findByEmail(googleUser.getEmail());
        if (!user.getRole().equals(User.ROLE.ADMIN)) {
          filters.put("contract", user.getContract());
        }
      }
    }
    if (locationId != null) {
      Location location = locationRepository.get(locationId);
      filters.put("location", location);
    }
    if (type != null) {
      filters.put("type", type);
    }
    if (published != null) {
      filters.put("published", published);
    }
    List<Publication> publications = publicationRepository.find(start, limit,
        null, true, filters);
    int size = publicationRepository.getCount(filters);
    FilterService service = new FilterService(publicationRepository,
        locationRepository);
    List<Dimension> dimensions = service.getDimensions(filters,
        size);
    return new QueryResults<Publication>(publications, size, dimensions);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public @ResponseBody boolean delete(@PathVariable long id) {
    PublicationService service = new PublicationService(
        publicationRepository);
    service.delete(id);
    return true;
  }

  @RequestMapping(value = "/publish/{id}", method = RequestMethod.POST)
  public @ResponseBody Packet<Publication> publish(@PathVariable long id) {
    Publication publication = publicationRepository.get(id);
    publication.publish();
    publicationRepository.save(publication);
    return new Packet<Publication>(publication);
  }

  @RequestMapping(value = "/unpublish/{id}", method = RequestMethod.POST)
  public @ResponseBody Packet<Publication> unpublish(@PathVariable long id) {
    Publication publication = publicationRepository.get(id);
    publication.unpublish();
    publicationRepository.save(publication);
    return new Packet<Publication>(publication);
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
