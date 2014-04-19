Ext.define('Propial.view.window.ContractWindow', {
  extend: 'Ext.window.Window',
  requires: ['Propial.view.form.ContractForm'],
  alias: 'widget.contractwindow',
  title: 'Editar el contrato',
  height: 300,
  width: 500, 
  layout: 'fit',
  initComponent: function() {
    var me = this;
    this.editor = Ext.create('Propial.view.form.ContractForm', {
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
		this.editor.contractId = id;
    var form = this.editor.getForm();
		form.reset();
		if (id) {
			form.load({
        url: '/services/contracts/' + id,
        method: 'GET',
        waitMsg: 'Loading'
      });
		}
		this.show();
  }
});