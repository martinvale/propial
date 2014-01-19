Ext.define('Propial.view.UserViewport', {
  extend: 'Ext.container.Viewport',
  layout: 'fit',
  
  requires: [
    'Propial.view.grid.UsersList'
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
        xtype: 'userslist',
        flex: 1
      }]
    };
    
    this.callParent();
  }
});