package com.ibiscus.propial.web.controller.site;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibiscus.propial.domain.business.Location;
import com.ibiscus.propial.domain.business.LocationRepository;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;
import com.ibiscus.propial.domain.filters.Dimension;
import com.ibiscus.propial.domain.services.FilterService;

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

  @RequestMapping(value = "/")
  public String home(@ModelAttribute("model") ModelMap model) {
    Map<String, Object> filters = new HashMap<String, Object>();
    filters.put("published", Boolean.TRUE);
    List<Publication> publications = publicationRepository.find(0,
        PAGE_SIZE, "creation", false, filters);
    model.addAttribute("publications", publications);
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

  @RequestMapping(value = "/forgot")
  public String forgot(@ModelAttribute("model") ModelMap model) {
    return "forgot";
  }

  @RequestMapping(value = "/register")
  public String register(@ModelAttribute("model") ModelMap model) {
    return "register";
  }

  @RequestMapping(value = "/confirm")
  public String confirm(@ModelAttribute("model") ModelMap model) {
    return "confirm";
  }
}
