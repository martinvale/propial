Ext.define('Propial.view.form.ContractForm', {
  extend: 'Ext.form.Panel',
  alias: 'widget.contractform',
  defaultType: 'textfield',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'name',
      fieldLabel: 'Name',
      allowBlank: false
    }, {
      name: 'address',
      fieldLabel: 'Address',
      allowBlank: false
    }, {
      name: 'telephone',
      fieldLabel: 'Telephone',
      allowBlank: false
    }, {
      name: 'email',
      fieldLabel: 'Email',
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
            var contract = form.getValues();
            var methodType = 'PUT';
            if (me.contractId) {
              contract.id = me.contractId;
              methodType = 'POST';
            }
            Ext.Ajax.request({
              headers: { 'Content-Type': 'application/json' },
              method: methodType,
              url: '/services/contracts/',
              params: Ext.encode(contract),
              success: function(response) {
                me.fireEvent ('onSaved', me);
              },
              failure: function(response){}
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