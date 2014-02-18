Ext.define('Propial.view.grid.LocationsList', {
  extend: 'Ext.grid.Panel',
  requires: [
    'Propial.store.Locations',
    'Propial.view.window.LocationWindow'
  ],
  alias: 'widget.locationslist',
  store: Ext.create('Propial.store.Locations'),
  title: 'Ubicacion',
  hideHeaders: true,
  
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
        text: 'Nueva ubicacion',
        handler: function () {
          var locationEditionWindow = Ext.create('widget.locationwindow', {
            listeners: {
              onContentUpdated: function (window) {
                me.getStore().reload();
              }
            }
          });
          locationEditionWindow.open();
        }
      }, {
        xtype: 'button',
        text: 'Editar ubicacion',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length == 1) {
            var locationEditionWindow = Ext.create('widget.locationwindow', {
              listeners: {
                onContentUpdated: function (window) {
                  me.getStore().reload();
                }
              }
            });
            locationEditionWindow.open(selections[0].get('id'));
          }
        }
      }, {
        xtype: 'button',
        text: 'Borrar ubicacion',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length > 0) {
            Ext.MessageBox.confirm('Confirmar', 'Esta seguro que desea borrar la ubicacion seleccionada?', 
              function () {
                for (var i = 0; i < selections.length; i++) {
                  Ext.Ajax.request({
                    headers: { 'Content-Type': 'application/json' },
                    method: 'DELETE',
                    url: '/services/locations/' + selections[i].get('id'),
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