Propial.view.Publication = function (container, publication) {

  var initEventListeners = function () {
    container.find("a").click(function () {
      alert(publication.id);
    });
  }

  return {
    render: function() {
      initEventListeners();
    }
  };
}
