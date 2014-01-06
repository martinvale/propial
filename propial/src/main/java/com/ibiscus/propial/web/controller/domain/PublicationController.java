package com.ibiscus.propial.web.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;

@Controller
@RequestMapping(value="/publication")
public class PublicationController {

  /** Repository of publications. */
  @Autowired
  private PublicationRepository publicationRepository;

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public Publication create(@RequestBody @ModelAttribute Publication publication) {
    publicationRepository.save(publication);
    return publication;
  }
}
