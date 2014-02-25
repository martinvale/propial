Ext.define('Propial.view.form.field.Location2', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.locationfield',
  layout: 'hbox',
  initComponent: function () {
    var aFieldContainer = this;

    var location = null;
    if (this.data) {
      location = this.data.get('location');
    }
    this.items = [
      {
        xtype: 'textfield',
        value: dimension,
        name: 'location'
      }
    ]
  
    this.callParent(arguments);
  },
  getJsonValue: function () {
    var fieldValues = this.query('textfield');
    var tipoAmbiente = fieldValues[0].getValue();
    var dimension = fieldValues[1].getValue();
    var observacion = fieldValues[2].getValue();
    return {'type': tipoAmbiente, 'dimension': dimension,
      'observation': observacion};
  }
});
