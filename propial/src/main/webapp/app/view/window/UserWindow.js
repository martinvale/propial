Ext.define('Propial.view.window.UserWindow', {
  extend: 'Ext.window.Window',
  requires: ['Propial.view.form.UserForm'],
  alias: 'widget.userwindow',
  title: 'Editar el usuario',
  height: 300,
  width: 500, 
  layout: 'fit',
  initComponent: function() {
    var me = this;
    this.editor = Ext.create('Propial.view.form.UserForm', {
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
      var model = Ext.ModelMgr.getModel('Propial.model.User');
      model.load(id, {
        success: function(user) {
          form.loadRecord(user);
        }
      });    

			/*form.load({
        url: '/services/users/' + id,
        method: 'GET',
        waitMsg: 'Loading'
      });*/
		}
		this.show();
  }
});