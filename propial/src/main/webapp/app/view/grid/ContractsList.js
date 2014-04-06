Ext.define('Propial.view.grid.ContractsList', {
  extend: 'Ext.grid.Panel',
  requires: [
    'Propial.store.Contracts'
  ],
  alias: 'widget.contractslist',
  store: Ext.create('Propial.store.Contracts'),

  initComponent: function() {
    var me = this;
    this.columns = [{
      dataIndex: 'name',
      text: 'Nombre',
      flex: 1
    }];

    this.dockedItems = [{
      dock: 'bottom',
      xtype: 'toolbar',
      items: [{
        xtype: 'button',
        text: 'Nuevo contrato',
        handler: function () {
          me.fireEvent ('onCreate', me);
        }
      }, {
        xtype: 'button',
        text: 'Editar contrato',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length == 1) {
            me.fireEvent ('onEdit', me, selections[0].get('id'));
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

    this.addEvents ('onCreate', 'onEdit');
    this.callParent();
  }
});