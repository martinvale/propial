Ext.define('Propial.store.Publications', {
  extend: 'Ext.data.Store',
  requires: 'Propial.model.Publication',
  model: 'Propial.model.Publication',
  autoLoad: true,
  proxy: {
    type: 'ajax',
    url: '/services/publications/',
    reader: {
      type: 'json',
      root: 'items'
    }
  }
});