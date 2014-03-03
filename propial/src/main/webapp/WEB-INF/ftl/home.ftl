<#import "publication/publication.ftl" as pub>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="product" content="Propial">
    <meta name="author" content="Martin Valletta, Buenos Aires, Argentina">
    <meta name="description" content="Publicacion de avisos inmobiliarios">
    <meta name="keywords" content="avisos, inmobiliaria, casas, departamentos, locales, alquiler, compra, venta">

    <!--link rel="stylesheet" href="css/main.css"-->
    <link rel="stylesheet" href="css/index.css">

    <link rel="stylesheet" href="css/jquery-ui/jquery-ui.css">

    <script src="script/jquery.js"></script>
    <script src="script/jquery-ui.js"></script>

    <title>Propial</title>

  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

    jQuery(document).ready(function() {

      var publications = [
      <#list model["publications"] as publication>
        {
          id: ${publication.id?c}
        }<#if publication_has_next>,</#if>
      </#list>];

      jQuery.each(publications, function (index, item) {
        var publicationContainer = jQuery("#" + item.id);
        var publication = new Propial.view.Publication(publicationContainer,
            item);
        publication.render();
      })

      var locationContainer = jQuery(".js-location-search");
      var locationFilter = new Propial.view.LocationFilter(locationContainer);
      locationFilter.render();
    });
  </script>


</head>
<body>
  <header>
    <div class="container">
      <a href="/"><img src="img/logo.png" alt="Propial" class="logo"></a>
      <div class="actions">
        <a href="register">Registrarse</a>
        <a href="login">Identificarse</a>
      </div>
    </div>
  </header>
  <div class="body">
    <div class="container clearfix">
      <div class="search js-location-search">
        <form action="search">
          <label for="search">En donde desea buscar?</label>
          <div class="field">
            <input class="js-location-filter" type="text" />
            <input class="js-location-field" type="hidden" name="location"/>
            <button><span class="button-content">buscar</span></button>
          </div>
        </form>
      </div>
      <div class="content">
        <!-- inicio column -->
        <div class="column">
          <#list model["publications"] as publication>
          <!-- inicio item -->
            <#if publication_index % 2 == 0>
              <@pub.renderPublication publication />
            </#if>
          <!-- fin item -->
          </#list>
        </div>
        <!-- fin column -->

        <!-- inicio column -->
        <div class="column second">
          <#list model["publications"] as publication>
          <!-- inicio item -->
            <#if publication_index % 2 == 1>
              <@pub.renderPublication publication />
            </#if>
          <!-- fin item -->
          </#list>

        </div>
        <!-- fin column -->

        <div class="menu">
          <div class="ads">
          </div>
          <div class="suggestions">
            <#list model["publications"] as publication>
            <!-- inicio item -->
              <#if publication_index % 2 == 1>
                <@pub.renderPublication publication />
              </#if>
            <!-- fin item -->
            </#list>
          </div>
        </div>
      </div>
    </div>
  </div>
  <footer>
    <div class="copy">Copyright © 2014</div>
  </footer>
</body>