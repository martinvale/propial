Propial.view.ResultsManager = function (container, templateId) {

  var html = $p(templateId);

  var directive = {
    ".item": {
      "publication <- items": {
        "@id": "publication.id",
        ".type": "publication.type",
        ".location": function () {
          return this.locations[0].name;
        },
        ".price": function () {
          if (this.price && this.currencyType) {
            return this.currencyType + this.price;
          } else {
            return "-";
          }
        },
        ".js-ambients": function () {
          if (this.ambients.length > 0) {
            return this.ambients.length;
          } else {
            return "-";
          }
        },
        ".js-surface": function () {
          if (this.surface) {
            return this.surface;
          } else {
            return "-";
          }
        },
        ".js-age": function () {
          if (this.age) {
            return this.age;
          } else {
            return "-";
          }
        },
        ".photos img @src": function () {
          if (this.resources.length > 0) {
            return "/services/publications/resource/" + this.resources[0].key.keyString;
          } else {
            return "/img/no_foto.gif";
          }
        },
        ".photos img @class": function () {
          if (this.resources.length > 0) {
            return "";
          } else {
            return "no-photo";
          }
        }
      }
    }
  }

  var template = html.compile(directive);

  return {
    render: function () {
    },

    update: function (data) {
      var items = [];
      for (var i = 0; i < data.items.length; i++) {
        if (i % 2 == 0) {
          items.push(data.items[i]);
        }
      };
      var firstColumn = container.find(".column.first").render({'items': items},
          template);
      firstColumn.addClass("first");

      items = [];
      for (var i = 0; i < data.items.length; i++) {
        if (i % 2 == 1) {
          items.push(data.items[i]);
        }
      };
      var secondColumn = container.find(".column.second").render({'items': items},
          template);
      secondColumn.addClass("second");
    }
  }
}
