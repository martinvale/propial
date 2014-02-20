Ext.define('Propial.model.Location', {
  extend: 'Ext.data.Model',
  fields: [
    'id',
    'name',
    {name: 'priority', type: 'int'},
    {name: 'parentId', mapping: 'parent.id'}
  ],
  proxy: {
    type: 'rest',
    url: '/services/locations/',
    reader: {
      root: 'data'
    }
  }
});