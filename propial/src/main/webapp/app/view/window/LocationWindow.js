Ext.define('Propial.view.window.LocationWindow', {
  extend: 'Ext.window.Window',
  requires: ['Propial.view.form.LocationForm'],
  alias: 'widget.locationwindow',
  title: 'Editar la ubicacion',
  height: 300,
  width: 500, 
  layout: 'fit',
  initComponent: function() {
    var me = this;
    this.editor = Ext.create('Propial.view.form.LocationForm', {
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
		this.editor.userId = id;
    var form = this.editor.getForm();
		form.reset();
		if (id) {
			form.load({
        url: '/services/locations/' + id,
        method: 'GET',
        waitMsg: 'Loading'
      });
		}
		this.show();
  }
});