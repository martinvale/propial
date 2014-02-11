Ext.define('Propial.view.window.ResourcesWindow', {
  extend: 'Ext.window.Window',
  requires: [
    'Propial.model.Publication',
    'Propial.view.form.PublicationForm'
  ],
  alias: 'widget.resourceswindow',
  title: 'Administrar multimedia',
  height: 500,
  width: 500, 
  layout: 'fit',
  initComponent: function() {
    var me = this;
    this.editor = Ext.create('Propial.view.form.UploadPhotoForm', {
      listeners: {
        onUploaded: function (form) {
          me.fireEvent ('onResourceUploaded', me);
          me.close();
        },
        onClosed: function (form) {
          me.close();
        }
      }
    });
    this.items = [this.editor];
    this.addEvents ('onResourceUploaded');
    this.callParent();
  },
  open: function(id) {
		this.editor.objectId = id;
    var form = this.editor.getForm();
		form.reset();
		if (id) {
      /*var model = Ext.ModelMgr.getModel('Propial.model.Publication');
      model.load(id, {
        success: function(publication) {
          form.loadRecord(publication);
        }
      });*/
		}
		this.show();
  }
});