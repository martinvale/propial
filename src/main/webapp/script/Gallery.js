Propial.widget.Gallery = function (container, targetId, resources) {

  var target = jQuery(targetId);

  var carousel = null;

  var selectedIndex = 0;

  var select = function (index) {
    selectedIndex = index;
    target.attr("src", "/services/publications/resource/" + resources[index].id);
  }

  var initEventListeners = function () {
    jQuery.each(resources, function (index, resource) {
      var resourceElement = container.find(".js-resource-" + resource.id);
      resourceElement.click(function (event) {
        event.preventDefault();
        select(index);
      });
    });
    target.click(function () {
      carousel.show(selectedIndex);
    });
  };

  return {
    render: function() {
      carousel = new Propial.widget.Carousel("#carousel-template .js-carousel",
          resources);
      carousel.render();
      initEventListeners();
    }
  }
}
