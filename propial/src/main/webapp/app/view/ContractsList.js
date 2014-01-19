Ext.define('Propial.view.ContractsList', {
  extend: 'Ext.grid.Panel',
  requires: [
    'Propial.store.Contracts',
    'Propial.view.window.ContractWindow'
  ],
  alias: 'widget.contractslist',
  store: Ext.create('Propial.store.Contracts'),
  title: 'Contratos',
  hideHeaders: true,
  
  initComponent: function() {
    var me = this;
    this.columns = [{
      dataIndex: 'name',
      flex: 1
    }];
    
    this.dockedItems = [{
      dock: 'bottom',
      xtype: 'toolbar',
      items: [{
        xtype: 'button',
        text: 'Nuevo contrato',
        handler: function () {
          var contractEditionWindow = Ext.create('widget.contractwindow', {
            listeners: {
              onContentUpdated: function (window) {
                me.getStore().reload();
              }
            }
          });
          contractEditionWindow.open();
        }
      }, {
        xtype: 'button',
        text: 'Editar contrato',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length == 1) {
            var contractEditionWindow = Ext.create('widget.contractwindow', {
              listeners: {
                onContentUpdated: function (window) {
                  me.getStore().reload();
                }
              }
            });
            contractEditionWindow.open(selections[0].get('id'));
          }
        }
      }, {
        xtype: 'button',
        text: 'Borrar contrato',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length > 0) {
            Ext.MessageBox.confirm('Confirmar', 'Esta seguro que desea borrar el contrato seleccionado?', 
              function () {
                for (var i = 0; i < selections.length; i++) {
                  Ext.Ajax.request({
                    headers: { 'Content-Type': 'application/json' },
                    method: 'DELETE',
                    url: '/services/contracts/' + selections[i].get('id'),
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