package com.ibiscus.propial.infraestructure.freemarker;

import java.io.File;
import java.io.IOException;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;

public class FreeMarkerConfigurer extends
    org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer {

  /** Whether debug mode is enabled.
   *
   * In debug mode, the templates are first search from the file system. This
   * makes it possible to edit ftl files and see the result without a redeploy.
   */
  private boolean debug = false;

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

  /** Sets the debug mode.
   *
   * @param debugEnabled true to enable debug mode, false by default.
   */
  public void setDebug(final boolean debugEnabled) {
    debug = debugEnabled;
  }

}
