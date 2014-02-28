Ext.define('Propial.view.form.field.Location', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.locationfield',
  layout: 'hbox',
  initComponent: function () {
    var aFieldContainer = this;

    var location = null;
    var locationId = null;
    if (this.data) {
      location = this.data.get('location');
      locationId = this.data.get('locationId');
    }
    this.items = [
      {
        xtype: 'displayfield',
        value: location,
        frame: true,
        width: 200
      }, {
        xtype: 'hidden',
        value: locationId,
        name: 'locationId'
      }, {
        xtype: 'button',
        text: 'Seleccionar',
        handler: function () {
          var window = Ext.create('widget.locationselectorwindow', {
            listeners: {
              onSelected: function (window, record) {
              }
            }
          });
          window.open(aFieldContainer);
        }
      }
    ]
  
    this.callParent(arguments);
  },
  getValue: function () {
    var fieldValues = this.query('hidden');
    var locationId = fieldValues[0].getValue();
    return locationId;
  },
  setSelected: function (value) {
    var fieldValues = this.query('hidden');
    fieldValues[0].setValue(value.get('id'));

    fieldValues = this.query('displayfield');
    var path = value.get('name');
    var parent = value.get('parent');
    while (parent) {
      path = parent.get('name') + ' / ' + path;
      parent = parent.get('parent');
    }
    fieldValues[0].setValue(path);
  }
});
