Ext.define('Propial.model.Publication', {
  extend: 'Ext.data.Model',
  fields: ['id', 'title', 'type', 'address', 'age', 'description', 'price', 'currencyType', 'expenses', 'surface', 'forProfessional'],
  hasMany: [
    {model: 'Propial.model.Resource', name: 'resources'}
  ],
  proxy: {
    type: 'rest',
    url: '/services/publications/',
    reader: {
      root: 'data'
    }
  }
});
