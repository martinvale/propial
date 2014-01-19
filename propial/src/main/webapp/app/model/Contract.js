Ext.define('Propial.model.Contract', {
  extend: 'Ext.data.Model',
  fields: ['id', 'name'],
  
  proxy: {
    type: 'ajax',
    url: 'data/stations.json',
    reader: {
      type: 'json',
      root: 'results'
    }
  }
});