Ext.define('Propial.model.Contract', {
  extend: 'Ext.data.Model',
  fields: ['id', 'name', 'address', 'telephone', 'email', 'type', {name: 'logo', mapping: 'logo.keyString'}],
  proxy: {
    type: 'rest',
    url: '/services/contracts/',
    reader: {
      root: 'data'
    }
  }
});