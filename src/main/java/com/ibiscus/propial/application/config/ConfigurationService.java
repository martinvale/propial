package com.ibiscus.propial.application.config;

import com.ibiscus.propial.infraestructure.objectify.OfyService;

public class ConfigurationService {

  private Configuration configuration;

  public String getValue(final String property, final String defaultValue) {
    configuration = OfyService.ofy().load().type(Configuration.class).first()
        .now();
    if (configuration == null) {
      configuration = new Configuration();
      configuration.setValue(property, defaultValue);
      OfyService.ofy().save().entity(configuration).now();
    }
    String value = configuration.getValue(property);
    if (value == null && defaultValue != null) {
      value = defaultValue;
      configuration.setValue(property, defaultValue);
      OfyService.ofy().save().entity(configuration).now();
    }
    return value;
  }

}
