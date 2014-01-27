Ext.define('Propial.model.Publication', {
  extend: 'Ext.data.Model',
  fields: ['id', 'title', 'type', 'address', 'age', 'description', 'price'],
  proxy: {
    type: 'rest',
    url : '/services/publications/'
  }
});