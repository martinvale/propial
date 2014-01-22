Ext.define('Propial.view.PublicationViewport', {
  extend: 'Ext.container.Viewport',
  layout: 'fit',
  
  requires: [
    'Propial.view.grid.PublicationsList'
  ],
  
  initComponent: function() {
    this.items = {
      dockedItems: [{
        dock: 'top',
        xtype: 'toolbar',
        height: 50,
        items: []
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