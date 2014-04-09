package com.ibiscus.propial.infraestructure.freemarker;

import java.io.File;
import java.io.IOException;

import com.ibiscus.propial.application.config.ConfigurationService;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;

public class FreeMarkerConfigurer extends
    org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer {

  /** Whether debug mode is enabled.
   *
   * In debug mode, the templates are first search from the file system. This
   * makes it possible to edit ftl files and see the result without a redeploy.
   */
  private final boolean debug;

  public FreeMarkerConfigurer(final ConfigurationService configurationService) {
    super();
    String debugProp = configurationService.getValue("debugMode", "true");
    debug = new Boolean(debugProp);
    setTemplateLoaderPath(configurationService.getValue("templatePath", 
        "src/main/webapp/WEB-INF/ftl/"));
  }

  /* (non-Javadoc)
   * @see org.springframework.ui.freemarker.FreeMarkerConfigurationFactory#getTemplateLoaderForPath(java.lang.String)
   */
  @Override
  protected TemplateLoader getTemplateLoaderForPath(String templateLoaderPath) {
    TemplateLoader templateLoader;
    if (debug) {
      try {
        templateLoader = new FileTemplateLoader(new File(templateLoaderPath));
      } catch (IOException e) {
        // We fall back to the standard loader.
        //log.debug("Could not find {}, skipping ...", fileTemplatePath);
        templateLoader = super.getTemplateLoaderForPath(templateLoaderPath);
      }
    } else {
      templateLoader = super.getTemplateLoaderForPath(templateLoaderPath);
    }
    return templateLoader;
  }

}
