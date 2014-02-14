Ext.define('Propial.model.Publication', {
  extend: 'Ext.data.Model',
  requires: [
    'Propial.model.Resource',
    'Propial.model.Ambient'
  ],
  fields: ['id', 'title', 'type', 'address', 'age', 'description', 'price', 'currencyType', 'expenses', 'surface', 'forProfessional'],
  hasMany: [
    {model: 'Propial.model.Resource', name: 'resources'},
    {model: 'Propial.model.Ambient', name: 'ambients'}
  ],
  proxy: {
    type: 'rest',
    url: '/services/publications/',
    reader: {
      root: 'data'
    }
  }
});
