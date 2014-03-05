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
