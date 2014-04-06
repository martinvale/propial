Ext.define('Propial.view.form.ContractForm', {
  extend: 'Ext.form.Panel',
  alias: 'widget.contractform',
  requires: [
    'Propial.view.form.field.Image'
  ],
  defaultType: 'textfield',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'id',
      xtype: 'hidden'
    }, {
      name: 'changeImage',
      xtype: 'hidden',
      value: 'false'
    }, {
      name: 'name',
      fieldLabel: 'Nombre',
      allowBlank: false
    }, {
      name: 'address',
      fieldLabel: 'Direcci&oacute;n',
      allowBlank: false
    }, {
      name: 'telephone',
      fieldLabel: 'Telefono',
      allowBlank: false
    }, {
      name: 'email',
      fieldLabel: 'Email',
      allowBlank: false
    }, {
      name: 'logo',
      xtype: 'imagefield',
      imageHeight: 100,
      fieldLabel: 'Logo',
      listeners: {
        change: function() {
          var form = this.up('form');
          form.getForm().setValues({changeImage: true});
        }
      }
    }
  ],
  initComponent: function() {
    var me = this;
    me.buttons = [
      {
        text: 'Guardar',
        handler: function (button, event) {
          var form = me.form;
          if (form.isValid()) {
            form.submit({
              url: me.uploadUrl,
              waitMsg: 'Subiendo la imagen...',
              success: function(fp, o) {
                me.uploadUrl = o.result.url;
                me.fireEvent ('onSaved', me);
              }
            });
          }
        }
      }, {
        text: 'Cerrar',
        handler: function (button, event) {
          me.fireEvent ('onClosed', me);
        }
      }
    ];
    this.addEvents ('onSaved', 'onClosed');
    this.callParent();
  },
  loadObject: function (id) {
    var formPanel = this;
    var form = this.getForm();
    form.reset();

    this.objectId = id;
    if (id) {
      var model = Ext.ModelMgr.getModel('Propial.model.Contract');
      model.load(id, {
        success: function(contract) {
          form.loadRecord(contract);
        }
      });
    }
  }
});