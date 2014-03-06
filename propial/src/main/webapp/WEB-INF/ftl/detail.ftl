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
  margin: 5px 0 0 0;
}

.address {
  font-size: 23px;
}

.price {
  float: right;
  margin-right: 5px;
}

.main {
  min-height: 470px;
}

.details {
  float: left;
  font-size: 14px;
  margin-left: 10px;
  width: 290px;
}

.details dt {
  float: left;
  margin-right: 5px;
  width: 90px;
}

.resources {
  float: left;
  width: 500px;
}

.resources .resource {
  height: 400px;
  width: 500px;
}

.resources img {
  max-height: 400px;
  max-width: 500px;
}

.carrousel {
  min-height: 60px;
}

.carrousel img {
  border: 1px solid #999999;
  height: 60px;
  width: 60px;
}

</style>

  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

    Propial.widget = Propial.widget || {};

    jQuery(document).ready(function() {

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
                <img src="/services/publications/resource/${publication.resources[0].key.keyString}" />
              <#else>
                <img src="/img/no_foto.gif" class="no-photo" />
              </#if>
            </div>
            <div class="carrousel">
              <img src="/img/no_foto.gif" />
              <img src="/img/no_foto.gif" />
              <img src="/img/no_foto.gif" />
              <img src="/img/no_foto.gif" />
              <img src="/img/no_foto.gif" />
              <img src="/img/no_foto.gif" />
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