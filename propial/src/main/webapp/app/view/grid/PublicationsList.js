Ext.define('Propial.view.grid.PublicationsList', {
  extend: 'Ext.grid.Panel',
  requires: [
    'Propial.store.Publications',
    'Propial.view.window.PublicationWindow',
    'Propial.view.window.ResourcesWindow',
    'Propial.view.form.field.ComboContracts'
  ],
  alias: 'widget.publicationslist',
  store: Ext.create('Propial.store.Publications'),
  title: 'Publicaciones',
  
  initComponent: function() {
    var me = this;
    this.columns = [
      {
        dataIndex: 'title',
        text: 'Titulo',
        flex: 1
      }, {
        dataIndex: 'status',
        text: 'Estado'
      }, {
        dataIndex: 'type',
        text: 'Tipo'
      }, {
        dataIndex: 'address',
        text: 'Direccion'
      }
    ];
    
    this.dockedItems = [{
      dock: 'top',
      xtype: 'toolbar',
      items: ['->', {
        xtype: 'combocontracts',
        listeners: {
          select: function (combo, records, opts) {
            var contractId = records[0].get('id');
            if (contractId != -1) {
              me.getStore().getProxy().setExtraParam('contractId', contractId);
            } else {
              me.getStore().getProxy().setExtraParam('contractId', null);
            }
            me.getStore().reload();
          }
        }
      }]
    }, {
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
        text: 'Editar multimedia',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length == 1) {
            var multimediaEditionWindow = Ext.create('widget.resourceswindow', {
              uploadUrl: me.uploadUrl,
              listeners: {
                onContentUpdated: function (window) {
                  me.getStore().reload();
                }
              }
            });
            multimediaEditionWindow.open(selections[0].get('id'));
          }
        }
      }, {
        xtype: 'button',
        text: 'Publicar',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length > 0) {
            for (var i = 0; i < selections.length; i++) {
              Ext.Ajax.request({
                headers: { 'Content-Type': 'application/json' },
                method: 'POST',
                url: '/services/publications/publish/' + selections[i].get('id'),
                success: function(response) {
                  me.getStore().reload();
                },
                failure: function(response){}
              });
            }
          }
        }
      }, {
        xtype: 'button',
        text: 'Despublicar',
        handler: function () {
          var selections = me.getSelectionModel().getSelection();
          if (selections.length == 1) {
            for (var i = 0; i < selections.length; i++) {
              Ext.Ajax.request({
                headers: { 'Content-Type': 'application/json' },
                method: 'POST',
                url: '/services/publications/unpublish/' + selections[i].get('id'),
                success: function(response) {
                  me.getStore().reload();
                },
                failure: function(response){}
              });
            }
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