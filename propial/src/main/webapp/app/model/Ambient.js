Ext.define('Propial.model.Ambient', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'type'},
    {name: 'dimension'},
    {name: 'observation'}
  ],
  belongsTo: 'Propial.model.Publication'
});