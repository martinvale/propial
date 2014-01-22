Ext.define('Propial.view.grid.PublicationsList', {
  extend: 'Ext.grid.Panel',
  requires: [
    'Propial.store.Publications',
    'Propial.view.window.PublicationWindow'
  ],
  alias: 'widget.publicationslist',
  store: Ext.create('Propial.store.Publications'),
  title: 'Avisos',
  hideHeaders: true,
  
  initComponent: function() {
    var me = this;
    this.columns = [{
      dataIndex: 'displayName',
      flex: 1
    }];
    
    this.dockedItems = [{
      dock: 'bottom',
      xtype: 'toolbar',
      items: [{
        xtype: 'button',
        text: 'Nuevo aviso',
        handler: function () {
          var publicationEditionWindow = Ext.create('widget.publicationwindow', {
            listeners: {
              onContentUpdated: function (window) {
                me.getStore().reload();
              }
            }
          });
          publicationEditionWindow.open();
        }
      }, {
        xtype: 'button',
        text: 'Editar aviso',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length == 1) {
            var publicationEditionWindow = Ext.create('widget.publicationwindow', {
              listeners: {
                onContentUpdated: function (window) {
                  me.getStore().reload();
                }
              }
            });
            publicationEditionWindow.open(selections[0].get('id'));
          }
        }
      }, {
        xtype: 'button',
        text: 'Borrar aviso',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length > 0) {
            Ext.MessageBox.confirm('Confirmar', 'Esta seguro que desea borrar el aviso seleccionado?', 
              function () {
                for (var i = 0; i < selections.length; i++) {
                  Ext.Ajax.request({
                    headers: { 'Content-Type': 'application/json' },
                    method: 'DELETE',
                    url: '/services/publications/' + selections[i].get('id'),
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