Ext.define('Propial.view.form.UploadPhotoForm', {
  extend: 'Ext.form.Panel',
  alias: 'widget.uploadphotoform',
  border: false,
  hidden: true,
  bodyPadding: 5,
  items: [
    {
      name: 'file',
      fieldLabel: 'Foto',
      xtype: 'filefield',
      width: 400,
      allowBlank: false
    }, {
      name: 'publicationId',
      xtype: 'hiddenfield'
    }
  ],
  initComponent: function() {
    var me = this;
    me.buttons = [
      {
        text: 'Subir',
        handler: function (button, event) {
          var form = me.form;
          if (form.isValid()) {
            form.submit({
              url: me.uploadUrl,
              waitMsg: 'Subiendo la imagen...',
              success: function(fp, o) {
                var resource = Ext.create('Propial.model.Resource', {
                  key: o.result.data.key.keyString
                });
                Ext.Ajax.request({
                  url: '/services/publications/resources/uploadUrl',
                  method: 'GET',
                  success: function(response){
                    me.uploadUrl = response.responseText;
                  }
                });
                me.fireEvent ('onUploaded', me, resource);
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
