Ext.define('Propial.view.form.UploadPhotoForm', {
  extend: 'Ext.form.Panel',
  alias: 'widget.uploadphotoform',
  border: false,
  url: '/services/publications/upload',
  bodyPadding: 5,
  items: [
    {
      name: 'file',
      fieldLabel: 'Foto',
      xtype: 'file',
      width: 400,
      allowBlank: false
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
              url: 'upload',
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
