Ext.define('Propial.model.Publication', {
  extend: 'Ext.data.Model',
  requieres: ['Propial.model.Inmueble'],
  fields: [
    'id',
    'title',
    {model: 'Propial.model.Inmueble', name: 'inmueble'}
  ]
});