<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="product" content="Propial">
    <meta name="author" content="Martin Valletta, Buenos Aires, Argentina">
    <meta name="description" content="Publicacion de avisos inmobiliarios">
    <meta name="keywords" content="avisos, inmobiliaria, casas, departamentos, locales, alquiler, compra, venta">

    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/detail.css">

    <link rel="stylesheet" href="/css/jquery-ui/jquery-ui.css">

    <script src="/script/jquery.js"></script>
    <script src="/script/jquery-ui.js"></script>
    <script src="/script/jquery.cookie.js"></script>

    <title>Propial</title>

<style>

</style>
  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

    Propial.widget = Propial.widget || {};

    Propial.util = Propial.util || {};
    Propial.util.domain = "http://www.propial.com";

    jQuery(document).ready(function() {

      <#assign publication = model["publication"] />
      var publication = {
        id: ${publication.id?c},
        title: "${publication.type} en ${publication.locations[0].name}",
        description: "${publication.description}",
        resources: [
        <#list model["publication"].resources as resource>
          {
            id: '${resource.key.keyString}'
          }<#if resource_has_next>,</#if>
        </#list>
        ]
      };

      var publicationContainer = jQuery(".js-publication");
      var publicationWidget = new Propial.view.Publication(publicationContainer,
          publication);
      publicationWidget.render();
      publicationWidget.visited();

      var gallery = Propial.widget.Gallery(jQuery(".js-gallery"),
        ".js-preview", publication.resources);
      gallery.render();

    });
  </script>

    <script src="/script/Publication.js"></script>
    <script src="/script/Carousel.js"></script>
    <script src="/script/Gallery.js"></script>

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
      <!-- inicio publication -->
      <div class="publication js-publication">
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
                <#assign age="${publication.age} a&ntilde;os" />
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
            <div class="actions details">
              <button class="button small js-fb-share"><i class="fa fa-facebook"></i> compartir</button>
            </div>
            <div class="contact details">
              <#assign contract=publication.contract />
              <h3>Publicado por</h3>
              <p>${contract.name}</p>
              <img src="/resources/${contract.logo.keyString}" />
            </div>
            <div class="gallery js-gallery">
              <#list publication.resources as resource>
                <img src="/services/publications/resource/${resource.key.keyString}" class="js-resource-${resource.key.keyString} <#if resource_index == 0>selected</#if>" />
              </#list>
            </div>
          </div>
        </div>
        <!-- fin main -->

        <!-- inicio ambientes -->
        <div class="ambients block">
          <div class="block-header">Ambientes</div>
          <div class="block-content">
            <ul>
            <#list publication.ambients as ambient>
              <li>
                <span class="type">${ambient.type}</span> <#if ambient.dimension??>(${ambient.dimension})</#if>
                <span class="observation">${ambient.observation}</span>
              </li>
            </#list>
            </ul>
          </div>
        </div>
        <!-- fin ambientes -->

        <!-- inicio description -->
        <div class="description block">
          <div class="block-header">Descripci&oacute;n</div>
          <div class="block-content">${publication.description}</div>
        </div>
        <!-- fin description -->

      </div>
      <!-- fin publication -->

      <div class="menu">
        <div class="ads">
          <p>Publicidad</p>
        </div>
      </div>
    </div>
  </div>

  <#include "footer.ftl" />

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