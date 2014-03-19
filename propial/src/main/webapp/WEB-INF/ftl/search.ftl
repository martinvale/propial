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
    <link rel="stylesheet" href="/css/search.css">

    <link rel="stylesheet" href="/css/jquery-ui/jquery-ui.css">

    <script src="/script/jquery.js"></script>
    <script src="/script/jquery-ui.js"></script>
    <script src="/script/pure.min.js"></script>

    <title>Propial</title>

  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

    Propial.widget = Propial.widget || {};

    jQuery(document).ready(function() {

      var publications = [
      <#list model["publications"] as publication>
        {
          id: ${publication.id?c}
        }<#if publication_has_next>,</#if>
      </#list>];

      var dimensions = [
      <#list model["dimensions"] as dimension>
        {
          id: '${dimension.id}',
          name: '${dimension.name}',
          members: [
          <#list dimension.members as member>
            {
              name: '${member.name}',
              value: '${member.value}',
              count: '${member.count}'
            }<#if member_has_next>,</#if>
          </#list>]
        }<#if dimension_has_next>,</#if>
      </#list>];

      var locationId = ${model["locationId"]};

      jQuery.each(publications, function (index, item) {
        var publicationContainer = jQuery("#" + item.id);
        var publication = new Propial.view.Publication(publicationContainer,
            item);
        publication.render();
      })

      var locationContainer = jQuery(".js-location-filters");
      var locationStatus = new Propial.widget.LocationStatus(
          locationContainer, "#locations-template");

      var resultsManagerElement = jQuery(".js-results");
      var resultsManager = new Propial.view.ResultsManager(
          resultsManagerElement, "#publication-template .column");
      resultsManager.render();

      var filtersManagerElement = jQuery(".js-filters");
      var filtersManager = new Propial.view.FiltersManager(
          filtersManagerElement, dimensions, "#filters-template",
          resultsManager, locationStatus, locationId);
      filtersManager.render();
    });
  </script>

    <script src="/script/Publication.js"></script>
    <script src="/script/LocationStatus.js"></script>
    <script src="/script/ResultsManager.js"></script>
    <script src="/script/FiltersManager.js"></script>
    <script src="/script/AppliedFilters.js"></script>
    <script src="/script/Filters.js"></script>

</head>
<body>
  <#include "header.ftl" />
  <div class="body">
    <div class="container clearfix">
      <div class="location js-location">
        <div class="selected">
          <span class="crumb">
            <span>Estas buscando en:</span>
            <#list model["parents"] as parent>
              <a href="/search/${parent.id}">${parent.name}</a> / 
            </#list>
            <span class="current">${model["location"].name}</span>
          </span>
          <!--a href="#" class="action">buscar otra ubicacion</a-->
        </div>
        <div>
          <#list model["dimensions"] as dimension>
            <#if dimension.id = "location">
              Tambien podes seguir refinando en:
              <span class="js-location-filters">
              <#list dimension.members as member>
                <span class="js-location">
                  <a href="/search/${member.value}"><span class="js-name">${member.name}</span> <span class="js-count">(${member.count})</span></a><span class="js-sep"><#if member_has_next>, </#if></span>
                </span>
              </#list>
              </span>
            </#if>
          </#list>
        </div>
        <!-- form action="search" class="js-search">
          <label for="search">En donde desea buscar?</label>
          <div class="field">
            <input class="js-location-filter" type="text" />
            <button><span class="button-content">buscar</span></button>
          </div>
        </form-->
      </div>
      <div class="content js-results">
        <!-- inicio column -->
        <div class="column first">
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
          <div class="js-filters">
            <ul class="applied-filters js-applied-filters">
            </ul>
            <ul class="filters js-dimensions">
            <#list model["dimensions"] as dimension>
              <#if dimension.id != "location">
              <li class="dimension">
                <a href="#" class="dimension js-dimension-${dimension.id}">${dimension.name}</a>
                <ul class="members js-members-${dimension.id}">
                  <#list dimension.members as member>
                    <li>
                      <a href="/search/${member.value}" class="member js-member-${member.value}"><span class="name">${member.name}</span> <span class="count">(${member.count})</span></a>
                    </li>
                  </#list>
                </ul>
              </li>
              </#if>
            </#list>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>

  <#include "footer.ftl" />

  <div id="publication-template" style="display:none">
    <div class="column">
      <@pub.renderItem />
    </div>
  </div>
  <div id="filters-template" style="display:none">
    <ul class="applied-filters js-applied-filters">
      <li class="dimension">
        <a href="#" class="dimension"></a>
        <ul class="members">
          <li>
            <a href="#" class="member"></a>
          </li>
        </ul>
      </li>
    </ul>
    <ul class="filters js-dimensions">
      <li class="dimension">
        <a href="#" class="dimension"></a>
        <ul class="members">
          <li>
            <a href="#" class="member"><span class="name"></span> <span class="count"></span></a>
          </li>
        </ul>
      </li>
    </ul>
  </div>
  <div id="locations-template" style="display:none">
    <span class="js-location-filters">
      <span class="js-location">
        <a href=""><span class="js-name"></span> <span class="js-count"></span></a><span class="js-sep"></span>
      </span>
    </span>
  </div>
</body>