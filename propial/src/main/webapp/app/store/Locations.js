Ext.define('Propial.store.Locations', {
  extend: 'Ext.data.Store',
  requires: 'Propial.model.Location',
  model: 'Propial.model.Location',
  autoLoad: true,
  proxy: {
    type: 'ajax',
    url: '/services/locations/',
    reader: {
      type: 'json',
      root: 'items'
    }
  }
});