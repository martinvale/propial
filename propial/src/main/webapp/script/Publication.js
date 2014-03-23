Propial.view.Publication = function (container, publication) {

  var MAXIMUM_REMEMBERED = 3;

  var initEventListeners = function () {
    container.find(".js-view-detail").click(function () {
      location.href = "/detail/" + publication.id;
    });

    container.find(".js-fb-share").click(function() {
      FB.ui({
        method: "feed",
        link: Propial.util.domain + "/detail/" + publication.id,
        caption: publication.title,
        description: publication.description
      }, function(response){});
    });
  }

  return {
    render: function() {
      initEventListeners();
    },
    visited: function() {
      var lastVisitsValue = jQuery.cookie("visits");
      if (lastVisitsValue) {
        var lastVisits = lastVisitsValue.split(",");
        lastVisitsValue = publication.id;
        var count = 1;
        for (var i = 0; i < lastVisits.length; i++) {
          if (lastVisits[i] != publication.id && count < MAXIMUM_REMEMBERED) {
            lastVisitsValue += "," + lastVisits[i];
            count++;
          }
        }
      } else {
        lastVisitsValue = publication.id;
      }
      jQuery.cookie("visits", lastVisitsValue, {path: "/", expires: 365});
    }
  };
}
