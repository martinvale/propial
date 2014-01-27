<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title>Propial Admin</title>

    <link rel="stylesheet" href="/extjs/resources/css/ext-all.css">

    <script type="text/javascript" src="/extjs/ext-all.js"></script>

    <script type="text/javascript">
Ext.define('Propial.view.form.PublicationForm', {
  extend: 'Ext.form.Panel',
  alias: 'widget.publicationform',
  defaultType: 'textfield',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'address',
      fieldLabel: 'Direccion',
      width: 400,
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
    }, {
      name: 'currencyType',
      value: '$',
      xtype: 'hidden'
    }, {
      name: 'price',
      fieldLabel: 'Precio',
      xtype: 'numberfield'
    }, {
      name: 'age',
      fieldLabel: 'Antiguedad',
      xtype: 'numberfield'
    }, {
      name: 'expenses',
      fieldLabel: 'Expensas',
      xtype: 'numberfield'
    }, {
      name: 'surface',
      fieldLabel: 'Superficie',
      xtype: 'numberfield'
    }, {
      name: 'forProfessional',
      fieldLabel: 'Apto profesional',
      xtype: 'checkbox'
    }, {
      name: 'description',
      fieldLabel: 'Descripcion',
      xtype: 'textarea',
      width: 400
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
              method: 'GET',
              url: '/services/publications/save',
              success: function(response) {
                me.fireEvent ('onSaved', me);
              }
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
      Ext.onReady(function() {
        Ext.Loader.setConfig({
          enabled: true,
          paths: {
            'Propial': '../app'
          }
        });
        Ext.create('Propial.view.PublicationViewport', {});
      })
    </script>

</head>
<body>

</body>
</html>