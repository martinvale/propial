package com.ibiscus.propial.web.controller.domain;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ibiscus.propial.domain.business.Ambient;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;
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

  @RequestMapping(value = "/save", method = RequestMethod.GET)
  public @ResponseBody Packet<Publication> save(Long id, String type, String address,
      Integer age, Double expenses, String description, Double price,
      Integer surface, String currencyType, boolean forProfessional) {
    Publication publication;
    if (id != null) {
      publication = publicationRepository.get(id);
    } else {
      User user = (User) SecurityContextHolder.getContext().getAuthentication()
          .getPrincipal();
      publication = new Publication(user.getContract(), user);
    }
    publication.update(type, address, age, expenses, description, price,
        surface, currencyType, forProfessional, new ArrayList<Ambient>());
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

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public @ResponseBody boolean upload(HttpServletRequest req, HttpServletResponse res) {
    try {
      ServletFileUpload upload = new ServletFileUpload();
      res.setContentType("text/plain");

      FileItemIterator iterator = upload.getItemIterator(req);
      while (iterator.hasNext()) {
        FileItemStream item = iterator.next();
        InputStream stream = item.openStream();

        if (item.isFormField()) {
          log.warning("Got a form field: " + item.getFieldName());
        } else {
          log.warning("Got an uploaded file: " + item.getFieldName() +
                      ", name = " + item.getName());

          // You now have the filename (item.getName() and the
          // contents (which you can read from stream). Here we just
          // print them back out to the servlet output stream, but you
          // will probably want to do something more interesting (for
          // example, wrap them in a Blob and commit them to the
          // datastore).
          int len;
          byte[] buffer = new byte[8192];
          while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
            res.getOutputStream().write(buffer, 0, len);
          }
        }
      }
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
  }

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public @ResponseBody boolean upload2(@RequestParam("file") MultipartFile file) {
    if (!file.isEmpty()) {
      try {
          byte[] bytes = file.getBytes();
          BufferedOutputStream stream =
                  new BufferedOutputStream(new FileOutputStream(new File(file.getName() + "-uploaded")));
          stream.write(bytes);
          stream.close();
          return true;
      } catch (Exception e) {
        throw new RuntimeException("You failed to upload " + file.getName(), e);
      }
    } else {
        throw new RuntimeException("You failed to upload " + file.getName()
            + " because the file was empty.");
    }
  }
}
