Ext.define('Propial.view.ContractViewport', {
  extend: 'Ext.container.Viewport',
  layout: 'fit',

  requires: [
    'Propial.view.grid.ContractsList',
    'Propial.view.form.ContractsForm',
    'Propial.view.panel.Menu'
  ],

  initComponent: function() {
    var GRID_VIEW = 0;
    var FORM_VIEW = 1;
    var me = this;

    this.editor = Ext.create('Propial.view.form.ContractForm', {
      uploadUrl: this.uploadUrl,
      listeners: {
        onSaved: function (form) {
          me.panelContainer.child('gridpanel').getStore().reload();
          me.panelContainer.getLayout().setActiveItem(GRID_VIEW);
        },
        onClosed: function (form) {
          me.panelContainer.getLayout().setActiveItem(GRID_VIEW);
        }
      }
    });

    this.panelContainer = Ext.create('Ext.panel.Panel', {
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
        type: 'card'
      },
      items: [{
        xtype: 'contractslist',
        flex: 1,
        listeners: {
          onCreate: function (grid) {
            me.editor.loadObject();
            me.panelContainer.getLayout().setActiveItem(FORM_VIEW);
          },
          onEdit: function (grid, contractId) {
            me.editor.loadObject(contractId);
            me.panelContainer.getLayout().setActiveItem(FORM_VIEW);
          }
        }
      }, this.editor]
    });

    this.items = [this.panelContainer];

    this.callParent();
  }
});