Ext.define('Propial.view.form.UserForm', {
  extend: 'Ext.form.Panel',
  alias: 'widget.userform',
  defaultType: 'textfield',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'displayName',
      fieldLabel: 'Nombre',
      allowBlank: false
    }, {
      name: 'username',
      fieldLabel: 'Usuario',
      allowBlank: false
    }, {
      name: 'password',
      fieldLabel: 'Password',
      inputType: 'password',
      allowBlank: false
    }, {
      name: 'enabled',
      fieldLabel: 'Habilitado',
      xtype: 'checkbox',
      inputValue: 'true'
    }, {
      name: 'email',
      fieldLabel: 'Email',
      allowBlank: false
    }, {
      name: 'role',
      fieldLabel: 'Rol',
      xtype: 'combobox',
      allowBlank: false,
      queryMode: 'local',
      valueField: 'role',
      displayField: 'role',
      store: Ext.create('Ext.data.ArrayStore', {
        fields: ['role'],
        data: [['ADMIN'], ['CUSTOMER_ADMIN'], ['PUBLISHER']]
      })
    }, {
      name: 'contractId',
      fieldLabel: 'Contrato',
      xtype: 'combobox',
      valueField: 'id',
      displayField: 'name',
      store: Ext.create('Propial.store.Contracts')
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
            var values = form.getValues();
            if (me.userId) {
              values.id = me.userId;
            }
            Ext.Ajax.request({
              method: 'GET',
              params: values,
              url: '/services/users/save',
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