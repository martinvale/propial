Ext.define('Propial.view.form.ImportLocation', {
  extend: 'Ext.form.Panel',
  alias: 'widget.importlocationform',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'parentId',
      xtype: 'hidden'
    }, {
      name: 'locations',
      height: 227,
      width: 278,
      allowBlank: false,
      xtype: 'textarea'
    }
  ],
  initComponent: function() {
    var me = this;
    me.buttons = [
      {
        text: 'Importar',
        handler: function (button, event) {
          var form = me.form;
          if (form.isValid()) {
            var values = form.getValues();
            Ext.Ajax.request({
              method: 'PUT',
              params: values,
              url: '/services/locations/import',
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
  },
  loadRecord: function(locationId) {
    this.getForm().setValues({parentId: locationId});
    this.show();
  }
});