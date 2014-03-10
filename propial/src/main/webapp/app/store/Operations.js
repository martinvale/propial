Ext.define('Propial.store.Operations', {
  extend: 'Ext.data.ArrayStore',
  fields: ['id', 'name'],
  autoLoad: true,
  data: [
    ['ALQUILER', 'Alquiler'],
    ['VENTA', 'Venta'],
    ['ALQUILER_TEMPORAL', 'Alquiler Temporal']
  ]
});