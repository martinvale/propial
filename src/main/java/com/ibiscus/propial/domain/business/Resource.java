package com.ibiscus.propial.domain.business;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Resource implements Serializable {

  private static final long serialVersionUID = 1L;

  /** The id of the resource. */
  @Id
  private String id;
  private BlobKey key;
  //private String name;

  Resource() {
  }

  public Resource (final BlobKey theKey) {
    Validate.notNull(theKey, "The key cannot be null");
    id = theKey.getKeyString();
    key = theKey;
  }

  public BlobKey getKey() {
    return key;
  }
}
