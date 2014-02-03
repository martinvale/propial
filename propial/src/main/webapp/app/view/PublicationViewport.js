Ext.define('Propial.view.PublicationViewport', {
  extend: 'Ext.container.Viewport',
  layout: 'fit',

  requires: [
    'Propial.view.grid.PublicationsList',
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
        xtype: 'publicationslist',
        flex: 1
      }]
    };
    
    this.callParent();
  }
});