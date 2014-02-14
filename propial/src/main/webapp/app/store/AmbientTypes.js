Ext.define('Propial.store.AmbientTypes', {
  extend: 'Ext.data.ArrayStore',
  fields: ['name'],
  autoLoad: true,
  data: [
    ['Cocina'],
    ['Dormitorio'],
    ['Baño']
  ]
});