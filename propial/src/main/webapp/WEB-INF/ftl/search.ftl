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

<style>

.location {
  height: 50px;
  margin: 5px 0;
}

li.dimension {
  margin-top: 5px;
}

a.dimension {
  font-weight: bold;
}

.filters a.dimension {
  background: url("/img/filter_exp.png") no-repeat scroll 0 0.3em rgba(0, 0, 0, 0);
  padding-left: 15px;
}

.filters a.dimension.collapsed {
  background: url("/img/filter_cond.png") no-repeat scroll 0 0.3em rgba(0, 0, 0, 0);
}

.applied-filters a.dimension {
  background: url("/img/delete_filter.png") no-repeat scroll 0 0.1em rgba(0, 0, 0, 0);
  padding-left: 20px;
}

.applied-filters {
  border-bottom: 1px dotted #999999;
  padding: 0 0 5px 0;
}

.member {
  padding-left: 10px;
}

.selected .crumb {
  font-size: 18px;
  margin-right: 5px;
}

.selected .current {
  font-weight: bold;
}

</style>

  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

    Propial.widget = Propial.widget || {};

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

Propial.widget.AppliedFilters = function (container, templateId,
    filtersManager) {

  var dimensions = [];

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
            ".member": "member.name",
            ".member@class": function () {
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
        removeDimension(dimension);
      });
      jQuery.each(dimension.members, function (indexMember, member) {
        var memberElement = container.find(".js-member-" + member.value);
        memberElement.click(function (event) {
          if (dimension.id != 'location') {
            event.preventDefault();
            removeMember(dimension, member);
          }
        });
      });
    });
  };

  var getDimension = function (dimensionId) {
    var appliedDimension = null;
    var n = 0;
    while (n < dimensions.length && !appliedDimension) {
      if (dimensions[n].id === dimensionId) {
        appliedDimension = dimensions[n];
      }
      n++;
    }
    return appliedDimension;
  };

  var removeDimension = function (dimension) {
    var newDimensions = [];
    var n = 0;
    while (n < dimensions.length) {
      if (dimensions[n].id !== dimension.id) {
        newDimensions.push(dimensions[n]);
      }
      n++;
    }
    dimensions = newDimensions;
    container = container.render({filters: dimensions}, template);
    filtersManager.updateContext();
  };

  var removeMember = function (dimension, member) {
    var appliedDimension = getDimension(dimension.id)
    if (appliedDimension) {
      var newMembers = [];
      jQuery.each(appliedDimension.members, function (index, appliedMember) {
        if (appliedMember.id !== member.id) {
          newMembers.push(member);
        }
      });
      if (newMembers.length === 0) {
        removeDimension(dimension);
      } else {
        appliedDimension.members = newMembers;
      }
    }
    container = container.render({filters: dimensions}, template);
    filtersManager.updateContext();
  };

  var addMember = function (dimension, member) {
    var appliedDimension = getDimension(dimension.id)
    if (!appliedDimension) {
      appliedDimension = {id: dimension.id, name: dimension.name, members: []};
      dimensions.push(appliedDimension);
    }
    appliedDimension.members.push(member);
  };

  return {
    render: function () {
      initEventListeners();
    },

    addFilter: function (dimension, member) {
      addMember(dimension, member);
      container = container.render({filters: dimensions}, template);
      initEventListeners();
    },

    getAppliedFilters: function () {
      var params = {};
      for (var i = 0; i < dimensions.length; i++) {
        var dimension = dimensions[i];
        for (var j = 0; j < dimension.members.length; j++) {
          params[dimension.id] = dimension.members[j].value;
        }
      }
      return params;
    }
  };
}

Propial.widget.Filters = function (container, filters, templateId,
    filtersManager) {

  var dimensions = filters;

  var filtersHtml = $p(templateId);

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

  var template = filtersHtml.compile(directive);

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
            filtersManager.addFilter(dimension, member);
          }
        });
      });
    });
  };

  return {
    render: function () {
      initEventListeners();
    },
    update: function (data) {
      dimensions = [];
      jQuery.each(data.filters, function (index, dimension) {
        if (dimension.id !== 'location') {
          dimensions.push(dimension);
        }
      });
      container = container.render({'filters': dimensions}, template);
      initEventListeners();
    }
  };
};

Propial.view.FiltersManager = function (container, filters, templateId,
    resultsManager, locationStatus, locationId) {

  var filtersApplied;

  var filters;

  var update = function (data) {
    var params = filtersApplied.getAppliedFilters();
    params.locationId = locationId;
    params.start = 0;
    params.limit = 0;
    jQuery.ajax({
      url: "/services/publications/",
      data: params,
      dataType: 'json',
      success: function (data, textStatus, jqXHR) {
        filters.update(data);
        resultsManager.update(data);
        locationStatus.update(data);
      }
    })
  };

  return {
    render: function () {
      var filterAppliedContainer = container.find(".js-applied-filters");
      filtersApplied = new Propial.widget.AppliedFilters(
          filterAppliedContainer, templateId + " .js-applied-filters", this);
      filtersApplied.render();

      var filtersContainer = container.find(".js-dimensions");
      filters = new Propial.widget.Filters(filtersContainer, filters,
          templateId + " .js-dimensions", this);
      filters.render();
    },
    addFilter: function (dimension, member) {
      filtersApplied.addFilter(dimension, member);
      update();
    },
    updateContext: function () {
      update();
    }
  }
};

Propial.widget.LocationStatus = function (container, templateId) {

  var html = $p(templateId + " .js-location-filters");

  var directive = {
    ".js-location": {
      "location <- locations": {
        ".js-name": "location.name",
        ".js-count": function () {
          return "(" + this.count + ")";
        },
        "a@href": function () {
          return "/search/" + this.value;
        },
        ".js-sep": function (arg) {
          if (arg.pos !== arg.items.length - 1) {
            return ", ";
          } else {
            return null;
          }
        }
      }
    }
  };

  var template = html.compile(directive);

  return {
    update: function (data) {
      var locations = [];
      jQuery.each(data.filters, function (index, dimension) {
        if (dimension.id === 'location') {
          locations = dimension.members;
        }
      });
      container = container.render({'locations': locations}, template);
    }
  };
};

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
  <footer>
    <div class="copy">Copyright © 2014</div>
  </footer>
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