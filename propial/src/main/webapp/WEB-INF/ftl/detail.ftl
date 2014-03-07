<#import "publication/publication.ftl" as pub>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="product" content="Propial">
    <meta name="author" content="Martin Valletta, Buenos Aires, Argentina">
    <meta name="description" content="Publicacion de avisos inmobiliarios">
    <meta name="keywords" content="avisos, inmobiliaria, casas, departamentos, locales, alquiler, compra, venta">

    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/detail.css">

    <link rel="stylesheet" href="/css/jquery-ui/jquery-ui.css">

    <script src="/script/jquery.js"></script>
    <script src="/script/jquery-ui.js"></script>
    <script src="/script/pure.min.js"></script>

    <title>Propial</title>

<style>

h3 {
  font-size: 22px;
  font-weight: 300;
  margin: 0 0 5px 0;
}

footer {
  margin-top: 20px;
}

p, dl {
  margin: 0;
}

.publication {
  float: left;
  width: 800px;
}

.block {
  border: 1px solid #EAEAEA;
  margin: 10px 0 0 0;
  padding: 10px;
}

.address {
  font-size: 23px;
}

.price {
  float: right;
  margin-right: 5px;
}

.main {
  min-height: 400px;
}

.details {
  float: left;
  font-size: 14px;
  margin-bottom: 10px;
  width: 285px;
}

.details dt {
  float: left;
  margin-right: 5px;
  width: 90px;
}

.description {
  font-size: 18px;
  font-weight: 300;
}

.resources {
  float: left;
  margin-right: 10px;
  width: 480px;
}

.resources .resource {
  height: 400px;
  position: relative;
}

.resources img {
  bottom: 0;
  left: 0;
  margin: auto;
  max-height: 400px;
  max-width: 480px;
  position: absolute;
  right: 0;
  top: 0;
}

.gallery {
  min-height: 60px;
}

.gallery img {
  border: 1px solid #999999;
  max-height: 60px;
  max-width: 60px;
}

</style>

  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

    Propial.widget = Propial.widget || {};

Propial.widget.Gallery = function (container, targetId, resources) {

  var target = jQuery(targetId);

  var replace = function (resource) {
    target.attr("src", "/services/publications/resource/" + resource.id);
  }

  var initEventListeners = function () {
    jQuery.each(resources, function (index, resource) {
      var resourceElement = container.find(".js-resource-" + resource.id);
      resourceElement.click(function (event) {
        event.preventDefault();
        replace(resource);
      });
    });
  };

  return {
    render: function() {
      initEventListeners();
    }
  }
}

    jQuery(document).ready(function() {

      var resources = [
      <#list model["publication"].resources as resource>
        {
          id: '${resource.key.keyString}'
        }<#if resource_has_next>,</#if>
      </#list>];

      var gallery = Propial.widget.Gallery(jQuery(".js-gallery"),
        ".js-preview", resources);
      gallery.render();

    });
  </script>

    <script src="/script/Publication.js"></script>

</head>
<body>
  <header>
    <div class="container">
      <a href="/"><img src="../img/logo.png" alt="Propial" class="logo"></a>
      <div class="actions">
        <a href="register">Registrarse</a>
        <a href="login">Identificarse</a>
      </div>
    </div>
  </header>
  <div class="body">
    <div class="container clearfix">
      <!-- inicio publication -->
      <div class="publication">
        <#assign publication=model["publication"] />
        <#assign price="consultar" />
        <#if publication.price?? && publication.currencyType??>
          <#assign price="${publication.currencyType}${publication.price}" />
        </#if>

        <p class="address">${publication.address} <span class="price">${price}</span></p>
        <p class="location">
          <#list publication.locations?reverse as location>
            <#if location_index != 0>
              <a href="/search/${location.id}">${location.name}</a><#if location_has_next> /</#if>
            </#if>
          </#list>
        </p>
        <!-- inicio main -->
        <div class="main block">
          <div class="resources">
            <div class="resource">
              <#if (publication.resources?size > 0)>
                <img src="/services/publications/resource/${publication.resources[0].key.keyString}" class="js-preview" />
              <#else>
                <img src="/img/no_foto.gif" class="no-photo" />
              </#if>
            </div>
          </div>
          <div class="details">
            <#assign age="-" />
            <#if publication.age??>
              <#assign age="${publication.age} aÃ±os" />
            </#if>
            <#assign surface="-" />
            <#if publication.surface??>
              <#assign surface="${publication.surface} m2" />
            </#if>
            <#assign ambients="-" />
            <#if (publication.ambients?size > 0)>
              <#assign ambients="${publication.ambients?size}" />
            </#if>
            <dl>
              <dt>Ambientes</dt>
              <dd>: ${ambients}</dd>
              <dt>Antiguedad</dt>
              <dd>: ${age}</dd>
              <dt>Superficie</dt>
              <dd>: ${surface}</dd>
            </dl>
          </div>
          <div class="gallery js-gallery">
            <#list publication.resources as resource>
              <img src="/services/publications/resource/${resource.key.keyString}" class="js-resource-${resource.key.keyString} <#if resource_index == 0>selected</#if>" />
            </#list>
          </div>
        </div>
        <!-- fin main -->

        <!-- inicio description -->
        <div class="description block">
          <h3>Descripcion:</h3>
          <div>${publication.description}</div>
        </div>
        <!-- fin description -->

      </div>
      <!-- fin publication -->

      <div class="menu">
        <div class="ads">
        </div>
      </div>
    </div>
  </div>
  <footer>
    <div class="copy">Copyright © 2014</div>
  </footer>
  <!--div id="locations-template" style="display:none">
    <span class="js-location-filters">
      <span class="js-location">
        <a href=""><span class="js-name"></span> <span class="js-count"></span></a><span class="js-sep"></span>
      </span>
    </span>
  </div-->
</body>