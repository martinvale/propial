Ext.define('Propial.view.form.PublicationForm', {
  extend: 'Ext.form.Panel',
  alias: 'widget.publicationform',
  defaultType: 'textfield',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'direccion',
      fieldLabel: 'Direccion',
      allowBlank: false
    }, {
      name: 'tipo',
      fieldLabel: 'Tipo',
      xtype: 'combobox',
      allowBlank: false,
      queryMode: 'local',
      valueField: 'tipo',
      displayField: 'tipo',
      store: Ext.create('Ext.data.ArrayStore', {
        fields: ['tipo'],
        data: [['casa'], ['departamento']]
      })
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
            var user = form.getValues();
            var methodType = 'PUT';
            if (me.userId) {
              user.id = me.userId;
              methodType = 'POST';
            }
            Ext.Ajax.request({
              headers: { 'Content-Type': 'application/json' },
              method: methodType,
              url: '/services/publications/',
              params: Ext.encode(user),
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