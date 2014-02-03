Ext.define('Propial.view.ContractViewport', {
  extend: 'Ext.container.Viewport',
  layout: 'fit',

  requires: [
    'Propial.view.grid.ContractsList',
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
        xtype: 'contractslist',
        flex: 1
      }]
    };
    
    this.callParent();
  }
});