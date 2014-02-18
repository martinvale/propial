Ext.define('Propial.view.LocationViewport', {
  extend: 'Ext.container.Viewport',
  layout: 'fit',

  requires: [
    'Propial.view.grid.LocationsList',
    'Propial.view.panel.Menu'
  ],

  initComponent: function() {
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
        type: 'hbox',
        align: 'stretch'
      },
      items: [{
        xtype: 'locationslist',
        flex: 1
      }]
    };
    
    this.callParent();
  }
});