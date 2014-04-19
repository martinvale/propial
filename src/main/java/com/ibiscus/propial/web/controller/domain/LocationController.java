package com.ibiscus.propial.web.controller.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiscus.propial.domain.business.Location;
import com.ibiscus.propial.domain.business.LocationRepository;
import com.ibiscus.propial.web.utils.Packet;
import com.ibiscus.propial.web.utils.QueryResults;

@Controller
@RequestMapping(value="/services/locations")
public class LocationController {

  /** Repository of locations. */
  @Autowired
  private LocationRepository locationRepository;

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public @ResponseBody Packet<Location> save(Long id, String name,
      Integer priority) {
    Location location = locationRepository.get(id);
    location.update(name, priority);
    locationRepository.save(location);
    return new Packet<Location>(location);
  }

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public @ResponseBody Packet<Location> create(Long parentId, String name,
      Integer priority) {
    Location parent = null;
    if (parentId != null) {
      parent = locationRepository.get(parentId);
    }
    Location location = new Location(parent, name, priority);
    locationRepository.save(location);
    return new Packet<Location>(location);
  }

  @RequestMapping(value = "/import", method = RequestMethod.PUT)
  public @ResponseBody Packet<Location> create(Long parentId,
      String locations) {
    Location parent = null;
    if (parentId != null) {
      parent = locationRepository.get(parentId);
    }
    String[] locationNames = locations.split("\n");
    Location location = null;
    for (String locationName: locationNames) {
      location = new Location(parent, locationName, 0);
      locationRepository.save(location);
    }
    return new Packet<Location>(location);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public @ResponseBody Packet<Location> get(@PathVariable long id) {
    Packet<Location> result;
    Location location = locationRepository.get(id);
    if (location != null) {
      result = new Packet<Location>(location);
    } else {
      result = new Packet<Location>();
    }
    return result;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public @ResponseBody QueryResults<Location> get(
      @RequestParam(required = false) Long parentId, @RequestParam int start,
      @RequestParam int limit) {
    Location parent = null;
    if (parentId != null) {
      parent = locationRepository.get(parentId);
    }
    return locationRepository.find(parent, start, limit);
  }

  @RequestMapping(value = "/suggest", method = RequestMethod.GET)
  public @ResponseBody List<Location> get(@RequestParam String term) {
    return locationRepository.suggest(term).getItems();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public @ResponseBody boolean delete(@PathVariable long id) {
    locationRepository.delete(id);
    return true;
  }

}
