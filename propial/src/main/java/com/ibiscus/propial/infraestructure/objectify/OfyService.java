package com.ibiscus.propial.infraestructure.objectify;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.ibiscus.propial.application.config.Configuration;
import com.ibiscus.propial.domain.business.Location;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.Resource;
import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.User;

public class OfyService {

  static {
    factory().register(Contract.class);
    factory().register(User.class);

    factory().register(Publication.class);
    factory().register(Resource.class);
    factory().register(Location.class);

    factory().register(Configuration.class);
}

  public static Objectify ofy() {
    return ObjectifyService.ofy();
  }

  public static ObjectifyFactory factory() {
    return ObjectifyService.factory();
  }

}
