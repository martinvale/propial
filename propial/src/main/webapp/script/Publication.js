Propial.view.Publication = function (container, publication) {

  var initEventListeners = function () {
    container.find("a").click(function () {
      location.href = "/detail/" + publication.id;
    });
  }

  return {
    render: function() {
      initEventListeners();
    }
  };
}
