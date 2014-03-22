Propial.view.Publication = function (container, publication) {

  var initEventListeners = function () {
    container.find("a").click(function () {
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
    }
  };
}
