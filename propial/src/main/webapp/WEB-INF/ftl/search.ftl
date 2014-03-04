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
    <link rel="stylesheet" href="/css/index.css">

    <link rel="stylesheet" href="/css/jquery-ui/jquery-ui.css">

    <script src="/script/jquery.js"></script>
    <script src="/script/jquery-ui.js"></script>
    <script src="/script/pure.min.js"></script>

    <title>Propial</title>

<style>

li.dimension {
  margin-top: 5px;
}

a.dimension {
  background: url("/img/filter_exp.png") no-repeat scroll 0 0.3em rgba(0, 0, 0, 0);
  font-weight: bold;
  padding-left: 15px;
}

a.dimension.collapsed {
  background: url("/img/filter_cond.png") no-repeat scroll 0 0.3em rgba(0, 0, 0, 0);
}

.member {
  padding-left: 10px;
}

</style>

  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

Propial.view.ResultsManager = function (container, templateId) {

  var html = $p(templateId);

  var directive = {
    ".item": {
      "publication <- items": {
        "@id": "publication.id",
        ".type": "publication.type",
        ".location": function () {
          return this.locations[0].name;
        },
        ".price": function () {
          if (this.price && this.currencyType) {
            return this.currencyType + this.price;
          } else {
            return "-";
          }
        },
        ".js-ambients": function () {
          if (this.ambients.length > 0) {
            return this.ambients.length;
          } else {
            return "-";
          }
        },
        ".js-surface": function () {
          if (this.surface) {
            return this.surface;
          } else {
            return "-";
          }
        },
        ".js-age": function () {
          if (this.age) {
            return this.age;
          } else {
            return "-";
          }
        },
        ".photos img @src": function () {
          if (this.resources.length > 0) {
            return "/services/publications/resource/" + this.resources[0].key.keyString;
          } else {
            return "/img/no_foto.gif";
          }
        },
        ".photos img @class": function () {
          if (this.resources.length > 0) {
            return "";
          } else {
            return "no-photo";
          }
        }
      }
    }
  }

  var template = html.compile(directive);

  return {
    render: function () {
    },

    update: function (data) {
      var items = [];
      for (var i = 0; i < data.items.length; i++) {
        if (i % 2 == 0) {
          items.push(data.items[i]);
        }
      };
      var firstColumn = container.find(".column.first").render({'items': items},
          template);
      firstColumn.addClass("first");

      items = [];
      for (var i = 0; i < data.items.length; i++) {
        if (i % 2 == 1) {
          items.push(data.items[i]);
        }
      };
      var secondColumn = container.find(".column.second").render({'items': items},
          template);
      secondColumn.addClass("second");
    }
  }

}

Propial.view.FiltersManager = function (container, filters, templateId,
    resultsManager, locationId) {

  var dimensions = filters;

  var html = $p(templateId);

  var directive = {
    "li.dimension": {
      "dimension <- filters": {
        "a.dimension": "dimension.name",
        "a.dimension@class": function () {
          return "dimension js-dimension-" + this.id;
        },
        ".members li": {
          "member <- dimension.members": {
            ".member .name": "member.name",
            ".member .count": function () {
              return "(" + this.count + ")";
            },
            "a.member@class": function () {
              return "member js-member-" + this.value;
            }
          }
        }
      }
    }
  };

  var template = html.compile(directive);

  var initEventListeners = function () {
    jQuery.each(dimensions, function (indexDimension, dimension) {
      var dimensionElement = container.find(".js-dimension-" + dimension.id);
      dimensionElement.click(function (event) {
        event.preventDefault();
        jQuery(event.target).toggleClass("collapsed");
        container.find(".js-members-" + dimension.id).toggle();
      });
      jQuery.each(dimension.members, function (indexMember, member) {
        var memberElement = container.find(".js-member-" + member.value);
        memberElement.click(function (event) {
          if (dimension.id != 'location') {
            event.preventDefault();
            var params = {
              type: member.value,
              locationId: locationId,
              start: 0,
              limit: 0
            };
            jQuery.ajax({
              url: "/services/publications/",
              data: params,
              dataType: 'json',
              success: function (data, textStatus, jqXHR) {
                resultsManager.update(data);
                update(data);
              }
            })
          }
        });
      });
    });
  };

  var update = function (data) {
    this.dimensions = data.filters;
    container.find("ul.dimensions").render(data, template);
    initEventListeners();
  };

  return {
    render: function () {
      initEventListeners();
    }
  };
}


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

      var locationContainer = jQuery(".js-location-search");
      var locationFilter = new Propial.view.LocationFilter(locationContainer);
      locationFilter.render();

      var resultsManagerElement = jQuery(".js-results");
      var resultsManager = new Propial.view.ResultsManager(
          resultsManagerElement, "#publication-template .column");
      resultsManager.render();

      var filtersManagerElement = jQuery(".js-filters");
      var filtersManager = new Propial.view.FiltersManager(
          filtersManagerElement, dimensions, "#filters-template ul.dimensions",
          resultsManager, locationId);
      filtersManager.render();
    });
  </script>

    <script src="/script/Publication.js"></script>
    <script src="/script/LocationFilter.js"></script>

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
      <div class="search js-location-search">
        <form action="search" class="js-search">
          <label for="search">En donde desea buscar?</label>
          <div class="field">
            <input class="js-location-filter" type="text" />
            <button><span class="button-content">buscar</span></button>
          </div>
        </form>
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
            <ul class="dimensions">
            <#list model["dimensions"] as dimension>
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
            </#list>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <footer>
    <div class="copy">Copyright © 2014</div>
  </footer>
  <div id="publication-template" style="display:none">
    <div class="column">
      <@pub.renderItem />
    </div>
  </div>
  <div id="filters-template" style="display:none">
    <ul class="dimensions">
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
</body>