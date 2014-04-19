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
