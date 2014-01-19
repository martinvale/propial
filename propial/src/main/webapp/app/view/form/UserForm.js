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
      allowBlank: false
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
        data: [['admin'], ['normal']]
      })
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
            var user = form.getValues();
            var methodType = 'PUT';
            if (me.userId) {
              user.id = me.userId;
              methodType = 'POST';
            }
            Ext.Ajax.request({
              headers: { 'Content-Type': 'application/json' },
              method: methodType,
              url: '/services/users/',
              params: Ext.encode(user),
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