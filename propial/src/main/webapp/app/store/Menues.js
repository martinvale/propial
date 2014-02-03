Ext.define('Propial.store.Menues', {
  extend: 'Ext.data.ArrayStore',
  fields: ['label', 'url'],
  autoLoad: true,
  data: [
    ['Contratos', 'contracts'],
    ['Usuarios', 'users'],
    ['Publicaciones', 'publications']
  ]
});