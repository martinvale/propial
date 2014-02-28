Ext.define('Propial.view.grid.LocationsList', {
  extend: 'Ext.grid.Panel',
  requires: [
    'Propial.store.Locations',
    'Propial.view.window.LocationWindow'
  ],
  alias: 'widget.locationslist',
  store: Ext.create('Propial.store.Locations'),
  title: 'Ubicacion',
  hideHeaders: true,
  
  initComponent: function() {
    var me = this;
    me.locationSelected = null;
    this.columns = [
      {
        dataIndex: 'name',
        text: 'Nombre',
        flex: 1
      }, {
        xtype: 'actioncolumn',
        width: 50,
        items: [{
          icon: '/img/flecha-der.png',  // Use a URL in the icon config
          tooltip: 'Ver el contenido',
          handler: function(grid, rowIndex, colIndex) {
            var rec = me.getStore().getAt(rowIndex);
            me.locationSelected = rec.get('id');
            var params = {parentId: me.locationSelected};
            me.getStore().load({'params': params});
            me.fireEvent ('onSelected', me, rec);
          }
        }]
      }
    ];

    this.addEvents ('onSelected');
    this.callParent();
  }
});