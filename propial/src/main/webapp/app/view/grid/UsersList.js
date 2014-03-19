Ext.define('Propial.view.grid.UsersList', {
  extend: 'Ext.grid.Panel',
  requires: [
    'Propial.store.Users',
    'Propial.view.window.UserWindow'
  ],
  alias: 'widget.userslist',
  store: Ext.create('Propial.store.Users'),
  title: 'Usuarios',
  
  initComponent: function() {
    var me = this;
    this.columns = [
      {
        dataIndex: 'displayName',
        text: 'Nombre',
        flex: 1
      }, {
        dataIndex: 'username',
        text: 'Usuario',
        width: 150
      }, {
        dataIndex: 'email',
        text: 'Email',
        width: 250
      }
    ];

    this.dockedItems = [{
      dock: 'bottom',
      xtype: 'toolbar',
      items: [{
        xtype: 'button',
        text: 'Nuevo usuario',
        handler: function () {
          var userEditionWindow = Ext.create('widget.userwindow', {
            listeners: {
              onContentUpdated: function (window) {
                me.getStore().reload();
              }
            }
          });
          userEditionWindow.open();
        }
      }, {
        xtype: 'button',
        text: 'Editar usuario',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length == 1) {
            var userEditionWindow = Ext.create('widget.userwindow', {
              listeners: {
                onContentUpdated: function (window) {
                  me.getStore().reload();
                }
              }
            });
            userEditionWindow.open(selections[0].get('id'));
          }
        }
      }, {
        xtype: 'button',
        text: 'Borrar usuario',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length > 0) {
            Ext.MessageBox.confirm('Confirmar', 'Esta seguro que desea borrar el usuario seleccionado?', 
              function () {
                for (var i = 0; i < selections.length; i++) {
                  Ext.Ajax.request({
                    headers: { 'Content-Type': 'application/json' },
                    method: 'DELETE',
                    url: '/services/users/' + selections[i].get('id'),
                    success: function(response) {
                      me.getStore().reload();
                    },
                    failure: function(response){}
                  });
                }
              }
            );
          }
        }
      }]
    }];

    this.callParent();
  }
});