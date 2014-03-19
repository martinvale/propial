Ext.define('Propial.view.PublicationViewport', {
  extend: 'Ext.container.Viewport',
  layout: 'fit',

  requires: [
    'Propial.view.grid.PublicationsList',
    'Propial.view.form.PublicationForm',
    'Propial.view.panel.Menu'
  ],

  initComponent: function() {
    var GRID_VIEW = 0;
    var FORM_VIEW = 1;
    var me = this;

    this.editor = Ext.create('Propial.view.form.PublicationForm', {
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
        xtype: 'publicationslist',
        uploadUrl: this.uploadUrl,
        flex: 1,
        listeners: {
          onCreate: function (grid) {
            me.editor.loadPublication();
            me.panelContainer.getLayout().setActiveItem(FORM_VIEW);
          },
          onEdit: function (grid, publicationId) {
            me.editor.loadPublication(publicationId);
            me.panelContainer.getLayout().setActiveItem(FORM_VIEW);
          }
        }
      }, this.editor]
    });

    this.items = [this.panelContainer];

    if (this.publish) {
      this.panelContainer.getLayout().setActiveItem(FORM_VIEW);
    }
    this.callParent();
  }
});