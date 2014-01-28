Ext.define('Propial.model.Publication2', {
  extend: 'Ext.data.Model',
  fields: ['id', 'title', 'type', 'address', 'age', 'description', 'price'],
  proxy: {
    type: 'rest',
    url: '/services/publications/'
    reader: {
      root: 'data'
    }
  }
});