Propial.view.LocationFilter = function (container) {

  var getPath = function (location) {
    var path = location.name;
    var parent = location.parent;
    while (parent) {
      path = parent.name + ' / ' + path;
      parent = parent.parent;
    }
    return path;
  };

  var initEventListeners = function () {
    var filterElement = container.find(".js-location-filter");
    var filter = filterElement.autocomplete({
      source: "/services/locations/suggest",
      minLength: 2,
      select: function(event, ui) {
        filterElement.val(getPath(ui.item));
        container.find(".js-location-field").val(ui.item.id);
        return false;
      }
    })
    filter.data( "ui-autocomplete" )._renderItem = function(ul, item) {
      return $("<li>")
        .append("<a>" + getPath(item) + "</a>")
        .appendTo(ul);
    };
    /*container.find("a").click(function () {
      alert(publication.id);
    });*/
  }

  return {
    render: function () {
      initEventListeners();
    }
  };
}
