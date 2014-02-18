package com.ibiscus.propial.web.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiscus.propial.domain.business.Location;
import com.ibiscus.propial.domain.business.LocationRepository;
import com.ibiscus.propial.web.utils.Packet;

@Controller
@RequestMapping(value="/services/locations")
public class LocationController {

  /** Repository of locations. */
  @Autowired
  private LocationRepository locationRepository;

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public @ResponseBody Packet<Location> save(@RequestBody Location location) {
    locationRepository.save(location);
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

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public @ResponseBody boolean delete(@PathVariable long id) {
    locationRepository.delete(id);
    return true;
  }

}
