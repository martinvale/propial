Ext.define('Propial.store.Contracts', {
  extend: 'Ext.data.Store',
  requires: 'Propial.model.Contract',
  model: 'Propial.model.Contract',
  proxy: {
    type: 'ajax',
    url: 'data/stations.json',
    reader: {
      type: 'json',
      root: 'results'
    }
  }
});