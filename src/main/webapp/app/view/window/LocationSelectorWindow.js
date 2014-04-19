Ext.define('Propial.view.window.LocationSelectorWindow', {
  extend: 'Ext.window.Window',
  requires: [
    'Propial.view.grid.LocationsList',
    'Propial.view.panel.Breadcrumb'
  ],
  alias: 'widget.locationselectorwindow',
  title: 'Seleccione una ubicacion',
  height: 300,
  width: 500,
  layout: 'fit',
  initComponent: function() {
    var me = this;
    this.grid = Ext.create('Propial.view.grid.LocationsList', {
      region: 'center',
      border: false,
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
          text: 'Seleccionar',
          handler: function () {
            var selections = me.grid.getSelectionModel().getSelection();
            if (selections.length > 0) {
              me.opener.setSelected(selections[0]);
              me.close();
            }
          }
        }, {
          xtype: 'button',
          text: 'Cancelar',
          handler: function () {
            me.close();
          }
        }]
      }]
    });

    this.breadcrumb = Ext.create('Propial.view.panel.Breadcrumb', {
      region: 'north',
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
    this.items = [{
      border: false,
      layout: 'border',
      items: [this.breadcrumb, this.grid]
    }];
    this.addEvents ('onSelected');
    this.callParent();
  },
  open: function (opener) {
    this.opener = opener;
    this.show();
  }
});
