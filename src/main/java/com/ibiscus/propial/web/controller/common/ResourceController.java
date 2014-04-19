package com.ibiscus.propial.web.controller.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Controller
@RequestMapping(value="/resources")
public class ResourceController {

  @RequestMapping(value = "/{key}")
  public void getResource(@PathVariable String key,
      HttpServletRequest req, HttpServletResponse res) throws IOException {
    BlobstoreService blobstoreService = BlobstoreServiceFactory
        .getBlobstoreService();
    BlobKey blobKey = new BlobKey(key);
    blobstoreService.serve(blobKey, res);
  }

}
