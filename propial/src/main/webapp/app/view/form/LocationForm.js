Ext.define('Propial.view.form.LocationForm', {
  extend: 'Ext.form.Panel',
  alias: 'widget.locationform',
  defaultType: 'textfield',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'name',
      fieldLabel: 'Nombre',
      allowBlank: false
    }, {
      name: 'priority',
      fieldLabel: 'Prioridad',
      value: 0,
      allowBlank: false
    }, {
      name: 'id',
      xtype: 'hidden'
    }, {
      name: 'parentId',
      xtype: 'hidden'
    }
  ],
  initComponent: function() {
    var me = this;
    me.buttons = [
      {
        text: 'Guardar',
        handler: function (button, event) {
          var form = me.form;
          if (form.isValid()) {
            var values = form.getValues();
            var methodType = 'PUT';
            if (me.userId) {
              values.id = me.userId;
              methodType = 'POST';
            }
            Ext.Ajax.request({
              method: methodType,
              params: values,
              url: '/services/locations/',
              success: function(response) {
                me.fireEvent ('onSaved', me);
              },
              failure: function(response){}
            });
          }
        }
      }, {
        text: 'Cerrar',
        handler: function (button, event) {
          me.fireEvent ('onClosed', me);
        }
      }
    ];
    this.addEvents ('onSaved', 'onClosed');
    this.callParent();
  }
});