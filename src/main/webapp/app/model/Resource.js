Ext.define('Propial.model.Resource', {
  extend: 'Ext.data.Model',
  fields: [{name: 'key', mapping: 'key.keyString'}],
  belongsTo: 'Propial.model.Publication'
});