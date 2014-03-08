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
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
    <!--link rel="stylesheet" href="/css/chico.min.css"-->

    <script src="/script/jquery.js"></script>
    <script src="/script/jquery-ui.js"></script>
    <!--script src="/script/chico.min.js"></script-->
    <!--script src="/script/pure.min.js"></script-->

    <title>Propial</title>

<style>

p, dl {
  margin: 0;
}

.publication {
  float: left;
  width: 790px;
}

.block {
  border: 1px solid #EAEAEA;
  margin: 10px 0 0 0;
}

.block-header {
  background-color: #4390DF;
  color: #FFFFFF;
  font-size: 22px;
  padding: 5px 10px;
}

.block-content {
  padding: 10px;
}

.address {
  font-size: 23px;
}

.price {
  float: right;
}

.main {
  min-height: 460px;
}

.details {
  float: left;
  font-size: 14px;
  margin-bottom: 10px;
  width: 275px;
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

.centered {
  bottom: 0;
  left: 0;
  margin: auto;
  position: absolute;
  right: 0;
  top: 0;
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
  cursor: pointer;
  max-height: 400px;
  max-width: 480px;
}

.gallery {
  min-height: 60px;
}

.gallery img {
  border: 1px solid #999999;
  cursor: pointer;
  max-height: 60px;
  max-width: 60px;
}

.carousel-main img {
  max-height: 550px;
  max-width: 1000px;
}

.carousel-button {
  cursor: pointer;
  font-size: 3em;
  position: absolute;
  top: 50%;
  width: 25px;
}

.carousel-button:hover {
  opacity: 0.7;
}

.carousel-prev {
  left: 0;
  margin-left: 15px;
}

.carousel-next {
  margin-right: 15px;
  right: 0;
}

</style>

  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

    Propial.widget = Propial.widget || {};

Propial.widget.Carousel = function (containerId, resources) {

  var container = jQuery(containerId);

  var currentIndex = 0;

  var move = function (index) {
    currentIndex = index;
    replace(resources[index]);
  }

  var replace = function (resource) {
    container.find(".js-resource")
        .attr("src", "/services/publications/resource/" + resource.id);
  };

  var initEventListeners = function () {
    container.find(".js-prev").click(function (event) {
      if (currentIndex > 0) {
        move(currentIndex - 1);
      }
    });

    container.find(".js-next").click(function (event) {
      if (currentIndex < resources.length - 1) {
        move(currentIndex + 1);
      }
    });
  };

  return {
    render: function() {
      initEventListeners();
    },
    show: function(index) {
      move(index);
      container.dialog({
        height: 600,
        width: 1000,
        modal: true
      });
    }
  }

}

Propial.widget.Gallery = function (container, targetId, resources) {

  var target = jQuery(targetId);

  var carousel = null;

  var selectedIndex = 0;

  var select = function (index) {
    selectedIndex = index;
    target.attr("src", "/services/publications/resource/" + resources[index].id);
  }

  var initEventListeners = function () {
    jQuery.each(resources, function (index, resource) {
      var resourceElement = container.find(".js-resource-" + resource.id);
      resourceElement.click(function (event) {
        event.preventDefault();
        select(index);
      });
    });
    target.click(function () {
      carousel.show(selectedIndex);
    });
  };

  return {
    render: function() {
      carousel = new Propial.widget.Carousel("#carousel-template .js-carousel",
          resources);
      carousel.render();
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
          <div class="block-header">Detalle</div>
          <div class="block-content">
            <div class="resources">
              <div class="resource">
                <#if (publication.resources?size > 0)>
                  <img src="/services/publications/resource/${publication.resources[0].key.keyString}" class="centered js-preview" />
                <#else>
                  <img src="/img/no_foto.gif" class="centered no-photo" />
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
        </div>
        <!-- fin main -->

        <!-- inicio description -->
        <div class="description block">
          <div class="block-header">Descripcion</div>
          <div class="block-content">${publication.description}</div>
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
  <div id="carousel-template" style="display:none">
    <div class="js-carousel">
      <div class="carousel-button carousel-prev js-prev">
        <i class="fa fa-chevron-left"></i>
      </div>
      <div class="carousel-main">
        <img src="" class="centered js-resource"/>
      </div>
      <div class="carousel-button carousel-next js-next">
        <i class="fa fa-chevron-right"></i>
      </div>
    </div>
  </div>
</body>