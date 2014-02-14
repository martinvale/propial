Ext.define('Propial.view.form.field.Ambient', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.ambientfield',
  layout: 'hbox',
  initComponent: function () {
    var aFieldContainer = this;

    var type = null;
    var dimension = null;
    var observation;
    if (this.data) {
      type = this.data.get('type');
      dimension = this.data.get('dimension');
      observation = this.data.get('observation');
    }
    this.items = [
      {
        xtype: 'combobox',
        name: 'tipoAmbiente',
        store: Ext.create('Propial.store.AmbientTypes'),
        editable: false,
        queryMode: 'local',
        displayField: 'name',
        value: type,
        valueField: 'name'
      }, {
        xtype: 'splitter'
      }, {
        xtype: 'textfield',
        value: dimension,
        name: 'dimensionAmbiente'
      }, {
        xtype: 'splitter'
      }, {
        xtype: 'textfield',
        value: observation,
        name: 'observacionAmbiente'
      }, {
        xtype: 'splitter'
      }, {
        xtype: 'button',
        text: 'Eliminar',
        handler: function (b, e) {
          var aContainer = this.up('panel');
          aContainer.remove(aFieldContainer);
        }
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
