package com.ibiscus.propial.web.controller.site;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.ibiscus.propial.application.business.RegistrationService;
import com.ibiscus.propial.domain.business.Location;
import com.ibiscus.propial.domain.business.LocationRepository;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;
import com.ibiscus.propial.domain.filters.Dimension;
import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.ContractRepository;
import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;
import com.ibiscus.propial.domain.services.FilterService;
import com.ibiscus.propial.web.security.GaeUserAuthentication;

@Controller
public class SiteController {

  /** The page size of publications. */
  private static final int PAGE_SIZE = 50;

  /** Repository of publications. */
  @Autowired
  private PublicationRepository publicationRepository;

  /** Repository of publications. */
  @Autowired
  private LocationRepository locationRepository;

  /** Repository of users. */
  @Autowired
  private UserRepository userRepository;

  /** Repository of contracts. */
  @Autowired
  private ContractRepository contractRepository;

  @RequestMapping(value = "/")
  public String home(@ModelAttribute("model") ModelMap model,
      @CookieValue(value = "visits", required = false) String visits) {
    Map<String, Object> filters = new HashMap<String, Object>();
    filters.put("published", Boolean.TRUE);
    List<Publication> publications = publicationRepository.find(0,
        PAGE_SIZE, "creation", false, filters);
    model.addAttribute("publications", publications);

    if (visits != null) {
      List<Publication> lastVisited = new ArrayList<Publication>();
      String[] publicationIds = visits.split(",");
      for (String publicationId : publicationIds) {
        Publication publication = publicationRepository.get(
            new Long(publicationId));
        if (publication != null) {
          lastVisited.add(publication);
        }
      }
      model.addAttribute("lastVisited", lastVisited);
    }
    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();
    if (authentication != null
        && !(authentication instanceof AnonymousAuthenticationToken)) {
      User user = (User) authentication.getPrincipal();
      model.addAttribute("user", user);
    }
    return "home";
  }

  @RequestMapping(value = "/search/{locationId}")
  public String search(@ModelAttribute("model") ModelMap model,
      @PathVariable Long locationId) {
    Map<String, Object> filters = new HashMap<String, Object>();
    filters.put("published", Boolean.TRUE);
    if (locationId != null) {
      Location location = locationRepository.get(locationId);
      if (location != null) {
        filters.put("location", location);
        model.addAttribute("location", location);
        List<Location> parents = new LinkedList<Location>();
        Location parent = location.getParent();
        while (parent != null && parent.getParent() != null) {
          parents.add(parent);
          parent = parent.getParent();
        }
        model.addAttribute("parents", parents);
      }
    }
    model.addAttribute("locationId", locationId);
    List<Publication> publications = publicationRepository.find(0,
        PAGE_SIZE, "creation", false, filters);
    model.addAttribute("publications", publications);

    int size = publicationRepository.getCount(filters);
    FilterService service = new FilterService(publicationRepository,
        locationRepository);
    List<Dimension> dimensions = service.getDimensions(filters, size);
    model.addAttribute("dimensions", dimensions);
    return "search";
  }

  @RequestMapping(value = "/detail/{id}")
  public String detail(@ModelAttribute("model") ModelMap model,
      @PathVariable Long id) {
    if (id != null) {
      Publication publication = publicationRepository.get(id);
      if (publication != null) {
        model.addAttribute("publication", publication);
      }
    }
    return "detail";
  }

  @RequestMapping(value = "/register")
  public String register(@ModelAttribute("model") ModelMap model) {
    BlobstoreService service = BlobstoreServiceFactory.getBlobstoreService();
    model.put("uploadUrl", service.createUploadUrl("/register"));
    return "register";
  }

  @SuppressWarnings("deprecation")
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String register(@ModelAttribute("model") ModelMap model,
      HttpServletRequest request,
      String name, String type, String realStateName, String address,
      String telephone, String email) {
    BlobstoreService blobService = BlobstoreServiceFactory
        .getBlobstoreService();
    model.put("uploadUrl", blobService.createUploadUrl("/register"));
    Map<String, BlobKey> blobs = blobService.getUploadedBlobs(request);
    BlobKey pictureKey = blobs.get("picture");
    if (pictureKey != null) {
      model.put("picture", pictureKey.getKeyString());
    }

    model.put("name", name);
    List<String> errors = new LinkedList<String>();

    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();
    User user = (User) authentication.getPrincipal();
    if (!errors.isEmpty()) {
      model.put("errors", errors);
      return "register";
    }
    RegistrationService service = new RegistrationService(userRepository,
        contractRepository, getSiteUrl(request));
    user.update(name, User.ROLE.PUBLISHER);
    Contract contract = new Contract(Contract.TYPE.valueOf(type),
        user.getDisplayName());
    if (Contract.TYPE.REALSTATE.toString().equals(type)) {
      contract.update(realStateName, address, telephone, email);
      if (pictureKey != null) {
        contract.updateLogo(pictureKey);
      }
    }
    service.register(user, contract);
    SecurityContextHolder.getContext().setAuthentication(
        new GaeUserAuthentication(user, authentication.getDetails()));
    return "redirect:/admin/";
  }

  @RequestMapping(value = "/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    SecurityContextHolder.clearContext();
    request.getSession().invalidate();

    UserService userService = UserServiceFactory.getUserService();
    String logoutUrl = userService.createLogoutURL("/");
    response.sendRedirect(logoutUrl);
  }

  private String getSiteUrl(final HttpServletRequest request) {
    String siteUrl = "";
    try {
      URL url = new URL(request.getRequestURL().toString());
      siteUrl = url.getProtocol() + "://" + url.getHost();
    } catch (MalformedURLException e) {
      throw new RuntimeException("Cannot extract the site URL", e);
    }
    return siteUrl;
  }
}
