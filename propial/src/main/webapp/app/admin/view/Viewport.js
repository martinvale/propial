Ext.define('Propial.view.Viewport', {
  extend: 'Ext.container.Viewport',
  layout: 'fit',
  
  requires: [
    'Propial.view.ContractsList'
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
        xtype: 'contractslist',
        flex: 1
      }]
    };
    
    this.callParent();
  }
});