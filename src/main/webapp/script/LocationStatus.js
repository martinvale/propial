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
