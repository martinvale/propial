Ext.define('Propial.view.form.PublicationForm', {
  extend: 'Ext.form.Panel',
  requires: [
    'Propial.view.form.field.Ambient',
    'Propial.view.form.field.Location',
    'Propial.view.window.LocationSelectorWindow'
  ],
  alias: 'widget.publicationform',
  autoScroll: true,
  defaultType: 'textfield',
  border: false,
  bodyPadding: 5,
  items: [
    {
      name: 'address',
      fieldLabel: 'Direccion',
      width: 400,
      allowBlank: false
    }, {
      name: 'type',
      fieldLabel: 'Tipo',
      xtype: 'combobox',
      allowBlank: false,
      queryMode: 'local',
      valueField: 'tipo',
      displayField: 'tipo',
      store: Ext.create('Ext.data.ArrayStore', {
        fields: ['tipo'],
        data: [['casa'], ['departamento']]
      })
    }, {
      name: 'operation',
      fieldLabel: 'Operacion',
      xtype: 'combobox',
      allowBlank: false,
      queryMode: 'local',
      valueField: 'id',
      displayField: 'value',
      store: Ext.create('Ext.data.ArrayStore', {
        fields: ['id', 'value'],
        data: [
          ['ALQUILER', 'Alquiler'],
          ['VENTA', 'Venta'],
          ['ALQUILER_TEMPORAL', 'Alquiler Temporal']
        ]
      })
    }, {
      name: 'currencyType',
      value: '$',
      xtype: 'hidden'
    }, {
      name: 'price',
      fieldLabel: 'Precio',
      xtype: 'numberfield'
    }, {
      name: 'age',
      fieldLabel: 'Antiguedad',
      xtype: 'numberfield'
    }, {
      name: 'expenses',
      fieldLabel: 'Expensas',
      xtype: 'numberfield'
    }, {
      name: 'surface',
      fieldLabel: 'Superficie',
      xtype: 'numberfield'
    }, {
      name: 'forProfessional',
      fieldLabel: 'Apto profesional',
      xtype: 'checkbox',
      inputValue: 'true'
    }, {
      name: 'location',
      fieldLabel: 'Ubicacion',
      xtype: 'locationfield'
    }, {
      name: 'description',
      fieldLabel: 'Descripcion',
      xtype: 'textarea',
      width: 520
    }, {
      xtype: 'fieldset',
      margin: 10,
      title: 'Ambientes',
      items: [
        {
          xtype: 'fieldcontainer',
          layout: 'hbox',
          defaults: {
            width: 155
          },
          items: [
            {
              xtype: 'displayfield',
              value: 'Tipo de ambiente'
            }, {
              xtype: 'displayfield',
              value: 'Dimension'
            }, {
              xtype: 'displayfield',
              value: 'Observacion'
            }
          ]
        }, {
          id: 'ambientes',
          xtype: 'panel',
          layout: 'anchor',
          border: false,
          dockedItems: [
            {
              xtype: 'toolbar',
              dock: 'bottom',
              items: [
                {
                  text: 'Agregar un ambiente',
                  handler: function (b, e) {
                    var fieldSet = this.up('panel');
                    fieldSet.insert(0, {
                      xtype: 'ambientfield'
                    })
                  }
                }
              ]
            }
          ]
        }
      ]
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
            var publication = form.getValues();
            delete publication['locationPath'];
            if (me.objectId) {
              publication.id = me.objectId;
            }

            var panAmbientes = me.down('#ambientes');
            var fields = panAmbientes.query('fieldcontainer');
            
            var ambientes = [];
            Ext.Array.each(fields, function (field) {
              ambientes.push(field.getJsonValue());
            });
            publication['ambients'] = ambientes;
            delete publication['dimensionAmbiente'];
            delete publication['observacionAmbiente'];
            delete publication['tipoAmbiente'];

            var contractSelector = Ext.getCmp('contract-selector');
            publication['contractId'] = contractSelector.getValue();

            Ext.Ajax.request({
              headers: { 'Content-Type': 'application/json' },
              method: 'POST',
              url: '/services/publications/save',
              params: Ext.encode(publication),
              success: function(response) {
                me.fireEvent ('onSaved', me);
              },
              failure: function(response){}
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
  loadPublication: function (id) {
    var formPanel = this;
    var form = this.getForm();
    form.reset();

    this.objectId = id;
    if (id) {
      var model = Ext.ModelMgr.getModel('Propial.model.Publication');
      model.load(id, {
        success: function(publication) {
          form.loadRecord(publication);

          var locations = publication.locations();
          if (locations) {
            var location = locations.first();
            if (location) {
              var locationField = formPanel.down('locationfield');
              locationField.setSelected(location);
            }
          }

          var panAmbientes = formPanel.down('#ambientes');
          publication.ambients().each(function (ambient) {
            var newField = Ext.create('Propial.view.form.field.Ambient', {
              data: ambient
            });
            panAmbientes.insert(0, newField);
          });
        }
      });    
    }
  }
});