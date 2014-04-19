Ext.define('Propial.store.Contracts', {
  extend: 'Ext.data.Store',
  requires: 'Propial.model.Contract',
  model: 'Propial.model.Contract',
  autoLoad: true,
  proxy: {
    type: 'ajax',
    url: '/services/contracts/',
    reader: {
      type: 'json',
      root: 'items'
    }
  }
});