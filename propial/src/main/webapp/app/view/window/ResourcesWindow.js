Ext.define('Propial.view.window.ResourcesWindow', {
  extend: 'Ext.window.Window',
  requires: [
    'Propial.model.Publication',
    'Propial.view.form.PublicationForm'
  ],
  alias: 'widget.resourceswindow',
  title: 'Administrar multimedia',
  layout: 'fit',
  height: 500,
  width: 500,
  initComponent: function() {
    var me = this;
    this.viewer = Ext.create('Propial.view.panel.ImageGallery', {
      uploadUrl: me.uploadUrl
    });
    this.items = [this.viewer];
    this.callParent();
  },
  open: function(id) {
    var me = this;
    if (id) {
      var model = Ext.ModelMgr.getModel('Propial.model.Publication');
      model.load(id, {
        success: function(publication) {
          me.viewer.loadImages(publication);
        }
      });
    }
    this.show();
  }
});
