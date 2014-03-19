Ext.define('Propial.view.form.field.ComboContracts', {
  extend: 'Ext.form.field.ComboBox',
  requires: [
    'Propial.store.Contracts'
  ],
  id: 'contract-selector',
  alias: 'widget.combocontracts',
  editable: false,
  disabled: true,
  valueField: 'id',
  displayField: 'name',
  initComponent: function() {
    var me = this;
    this.store = Ext.create('Propial.store.Contracts', {
      listeners: {
        load: function (store, records, succesful, opts) {
          if (records.length > 1) {
            var allContracts = Ext.create('Propial.model.Contract', {
              id: -1,
              name: 'Todos'
            })
            store.insert(0, allContracts);
            me.setValue(-1);
            me.enable();
          } else if (records.length == 1) {
            me.setValue(records[0].get('id'));
          }
        }
      }
    })
    this.callParent();
  }
});
