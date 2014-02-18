Ext.define('Propial.model.Location', {
  extend: 'Ext.data.Model',
  fields: [
    'id',
    'name'
  ],
  proxy: {
    type: 'rest',
    url: '/services/locations/',
    reader: {
      root: 'data'
    }
  }
});