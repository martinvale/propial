Ext.define('Propial.view.form.PublicationForm2', {
  extend: 'Ext.form.Panel',
  alias: 'widget.publicationform',
  defaultType: 'textfield',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'address',
      fieldLabel: 'Direccion',
      allowBlank: false
    }, {
      name: 'type',
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
            var methodType = 'PUT';
            if (me.objectId) {
              publication.id = me.objectId;
              methodType = 'POST';
            }
            form.submit({
              method: methodType,
              standardSubmit: true,
              url: '/services/publications/',
              success: function(response) {
                me.fireEvent ('onSaved', me);
              }
            });
            /*var publication = form.getValues();
            var methodType = 'PUT';
            if (me.objectId) {
              publication.id = me.objectId;
              methodType = 'POST';
            }
            Ext.Ajax.request({
              headers: { 'Content-Type': 'application/json' },
              method: methodType,
              url: '/services/publications/',
              params: Ext.encode(publication),
              success: function(response) {
                me.fireEvent ('onSaved', me);
              },
              failure: function(response){}
            });*/
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