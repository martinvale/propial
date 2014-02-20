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
      }
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
