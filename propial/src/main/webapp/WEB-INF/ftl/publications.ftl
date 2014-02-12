<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title>Propial Admin</title>

    <link rel="stylesheet" href="/extjs/resources/css/ext-all.css">
    <link rel="stylesheet" href="/css/admin.css">

    <script type="text/javascript" src="/extjs/ext-all.js"></script>

    <script type="text/javascript">

      Ext.onReady(function() {
        Ext.Loader.setConfig({
          enabled: true,
          paths: {
            'Propial': '../app'
          }
        });

Ext.define('Propial.view.form.UploadPhotoForm', {
  extend: 'Ext.form.Panel',
  alias: 'widget.uploadphotoform',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'file',
      fieldLabel: 'Foto',
      xtype: 'filefield',
      width: 400,
      allowBlank: false
    }, {
      name: 'name',
      xtype: 'hiddenfield',
      value: 'photo'
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
            form.submit({
              url: '/services/publications/upload',
              waitMsg: 'Subiendo la imagen...',
              success: function(fp, o) {
                msg('Success', 'Processed file "' + o.result.file + '" on the server');
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
		this.addEvents ('onUploaded', 'onClosed');
    this.callParent();
  }
});

        Ext.create('Propial.view.PublicationViewport', {});
      })
    </script>

</head>
<body>

</body>
</html>