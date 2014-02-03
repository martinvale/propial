Ext.define('Propial.model.User', {
  extend: 'Ext.data.Model',
  fields: [
    'id',
    'displayName',
    'username',
    'password',
    'email',
    'role',
    {name: 'enabled', type: 'boolean'},
    {name: 'contractId', mapping: 'contract.id'}
  ],
  proxy: {
    type: 'rest',
    url: '/services/users/',
    reader: {
      root: 'data'
    }
  }
});