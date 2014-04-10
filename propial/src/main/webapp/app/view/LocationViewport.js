Ext.define('Propial.view.LocationViewport', {
  extend: 'Ext.container.Viewport',
  layout: 'fit',

  requires: [
    'Propial.view.grid.LocationsList',
    'Propial.view.panel.Breadcrumb',
    'Propial.view.panel.Menu'
  ],

  initComponent: function() {
    var me = this;
    this.grid = Ext.create('Propial.view.grid.LocationsList', {
      uploadUrl: this.uploadUrl,
      flex: 1,
      listeners: {
        onSelected: function (grd, record) {
          me.breadcrumb.addItem([record.get('id'), record.get('name')]);
        }
      },
      dockedItems: [{
        dock: 'bottom',
        xtype: 'toolbar',
        items: [{
          xtype: 'button',
          text: 'Nueva ubicaci&oacute;n',
          handler: function () {
            var locationEditionWindow = Ext.create('widget.locationwindow', {
              listeners: {
                onContentUpdated: function (window) {
                  me.grid.getStore().reload();
                }
              }
            });
            locationEditionWindow.open(me.grid.locationSelected);
          }
        }, {
          xtype: 'button',
          text: 'Editar ubicaci&oacute;n',
          handler: function () {
            var selections = me.grid.getSelectionModel().getSelection();
            if (selections.length == 1) {
              var locationEditionWindow = Ext.create('widget.locationwindow', {
                listeners: {
                  onContentUpdated: function (window) {
                    me.grid.getStore().reload();
                  }
                }
              });
              locationEditionWindow.open(me.grid.locationSelected,
                  selections[0].get('id'));
            }
          }
        }, {
          xtype: 'button',
          text: 'Importar ubicaci&oacute;n',
          handler: function () {
            var importLocationWindow;
            var importForm = Ext.create('widget.importlocationform', {
              listeners: {
                'onSaved': function(form) {
                  me.grid.getStore().reload();
                  importLocationWindow.close();
                },
                'onClosed': function(form) {
                  importLocationWindow.close();
                }
              }
            });
            importForm.loadRecord(me.grid.locationSelected);
            importLocationWindow = Ext.create('widget.window', {
              title: 'Importar ubicaciones',
              height: 300,
              width: 300,
              layout: 'fit',
              items: [importForm]
            });
            importLocationWindow.show();
          }
        }, {
          xtype: 'button',
          text: 'Borrar ubicaci&oacute;n',
          handler: function () {
            var selections = me.grid.getSelectionModel().getSelection();
            if (selections.length > 0) {
              Ext.MessageBox.confirm('Confirmar', 'Esta seguro que desea borrar la ubicacion seleccionada?', 
                function () {
                  for (var i = 0; i < selections.length; i++) {
                    Ext.Ajax.request({
                      headers: { 'Content-Type': 'application/json' },
                      method: 'DELETE',
                      url: '/services/locations/' + selections[i].get('id'),
                      success: function(response) {
                        me.grid.getStore().reload();
                      },
                      failure: function(response){}
                    });
                  }
                }
              );
            }
          }
        }]
      }]
    });

    this.breadcrumb = Ext.create('Propial.view.panel.Breadcrumb', {
      listeners: {
        onItemRemoved : function (item, index, count) {
          if (count > 0) {
            me.grid.locationSelected = this.data[count - 1][0];
            var params = {parentId: this.data[count - 1][0]};
            me.grid.getStore().load({'params': params});
          } else {
            me.grid.locationSelected = null;
            me.grid.getStore().load();
          }
        }
      }
    });

    this.items = {
      dockedItems: [{
        dock: 'top',
        xtype: 'menu'
      }, {
        dock: 'bottom',
        xtype: 'toolbar',
        height: 30,
        items: []
      }],
      layout: {
        type: 'vbox',
        align: 'stretch'
      },
      items: [me.breadcrumb, me.grid]
    };
    
    this.callParent();
  }
});