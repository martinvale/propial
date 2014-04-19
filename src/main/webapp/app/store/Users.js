Ext.define('Propial.store.Users', {
  extend: 'Ext.data.Store',
  requires: 'Propial.model.User',
  model: 'Propial.model.User',
  autoLoad: true,
  proxy: {
    type: 'ajax',
    url: '/services/users/',
    reader: {
      type: 'json',
      root: 'items'
    }
  }
});