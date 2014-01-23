Ext.define('Propial.view.window.PublicationWindow', {
  extend: 'Ext.window.Window',
  requires: ['Propial.view.form.PublicationForm'],
  alias: 'widget.Publicationwindow',
  title: 'Editar la publicacion',
  height: 300,
  width: 500, 
  layout: 'fit',
  initComponent: function() {
    var me = this;
    this.editor = Ext.create('Propial.view.form.PublicationForm', {
      listeners: {
        onSaved: function (form) {
          me.fireEvent ('onContentUpdated', me);
          me.close();
        },
        onClosed: function (form) {
          me.close();
        }
      }
    });
    this.items = [this.editor];
    this.addEvents ('onContentUpdated');
    this.callParent();
  },
  open: function(id) {
		this.editor.objectId = id;
    var form = this.editor.getForm();
		form.reset();
		if (id) {
			form.load({
        url: '/services/publications/' + id,
        method: 'GET',
        waitMsg: 'Loading'
      });
		}
		this.show();
  }
});