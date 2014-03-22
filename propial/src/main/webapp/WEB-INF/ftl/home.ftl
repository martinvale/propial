<#import "publication/publication.ftl" as pub>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="product" content="Propial">
    <meta name="author" content="Martin Valletta, Buenos Aires, Argentina">
    <meta name="description" content="Publicacion de avisos inmobiliarios">
    <meta name="keywords" content="avisos, inmobiliaria, casas, departamentos, locales, alquiler, compra, venta">

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/index.css">

    <link rel="stylesheet" href="css/jquery-ui/jquery-ui.css">

    <script src="script/jquery.js"></script>
    <script src="script/jquery-ui.js"></script>

    <title>Propial</title>

<style>

</style>


  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.util = Propial.util || {};
    Propial.util.domain = "http://www.propial.com";

    Propial.view = Propial.view || {};

    jQuery(document).ready(function() {

      var publications = [
      <#list model["publications"] as publication>
        {
          id: ${publication.id?c},
          title: "${publication.type} en ${publication.locations[0].name}",
          description: "${publication.description}"
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

    <script src="script/Publication.js"></script>
    <script src="script/LocationFilter.js"></script>

</head>
<body>
  <div id="fb-root"></div>
  <script>
    window.fbAsyncInit = function() {
      FB.init({
        appId      : '304951509551215',
        status     : true,
        xfbml      : true
      });
    };

    (function(d, s, id){
       var js, fjs = d.getElementsByTagName(s)[0];
       if (d.getElementById(id)) {return;}
       js = d.createElement(s); js.id = id;
       js.src = "//connect.facebook.net/en_US/all.js";
       fjs.parentNode.insertBefore(js, fjs);
     }(document, 'script', 'facebook-jssdk'));
  </script>

  <#include "header.ftl" />
  <div class="body">
    <div class="container clearfix">
      <div class="context js-location-search">
        <form action="search" class="search js-search">
          <label for="search">En donde desea buscar?</label>
          <div class="field">
            <input class="js-location-filter" type="text" required="required" />
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
  <#include "footer.ftl" />
</body>