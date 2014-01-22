package com.ibiscus.propial.web.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;
import com.ibiscus.propial.web.utils.Packet;
import com.ibiscus.propial.web.utils.ResultSet;

@Controller
@RequestMapping(value="/services/publications")
public class PublicationController {

  /** Repository of publications. */
  @Autowired
  private PublicationRepository publicationRepository;

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public @ResponseBody Publication create(@RequestBody Publication publication) {
    publicationRepository.save(publication);
    return publication;
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public @ResponseBody Publication update(@RequestBody Publication publication) {
    publicationRepository.save(publication);
    return publication;
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

  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public @ResponseBody boolean delete(@PathVariable long userId) {
    publicationRepository.delete(userId);
    return true;
  }
}
