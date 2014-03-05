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
