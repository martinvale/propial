Propial.widget.Carousel = function (containerId, resources) {

  var container = jQuery(containerId);

  var currentIndex = 0;

  var move = function (index) {
    currentIndex = index;
    replace(resources[index]);
  }

  var replace = function (resource) {
    container.find(".js-resource")
        .attr("src", "/services/publications/resource/" + resource.id);
  };

  var initEventListeners = function () {
    container.find(".js-prev").click(function (event) {
      if (currentIndex > 0) {
        move(currentIndex - 1);
      }
    });

    container.find(".js-next").click(function (event) {
      if (currentIndex < resources.length - 1) {
        move(currentIndex + 1);
      }
    });
  };

  return {
    render: function() {
      initEventListeners();
    },
    show: function(index) {
      move(index);
      container.dialog({
        height: 600,
        width: 1000,
        modal: true
      });
    }
  }
}
