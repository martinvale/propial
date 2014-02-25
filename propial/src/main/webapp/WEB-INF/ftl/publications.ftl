<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title>Propial Admin</title>

    <link rel="stylesheet" href="/extjs/resources/css/ext-all.css">
    <link rel="stylesheet" href="/css/admin.css">

    <script type="text/javascript" src="/extjs/ext-all-debug.js"></script>

    <script type="text/javascript">

      Ext.onReady(function() {
        Ext.Loader.setConfig({
          enabled: true,
          paths: {
            'Propial': '../app'
          }
        });

Ext.define('Propial.view.window.LocationSelectorWindow', {
  extend: 'Ext.window.Window',
  requires: [
    'Propial.view.grid.LocationsList',
    'Propial.view.panel.Breadcrumb'
  ],
  alias: 'widget.locationselectorwindow',
  title: 'Seleccione una ubicacion',
  height: 300,
  width: 500,
  layout: 'fit',
  initComponent: function() {
    var me = this;
    this.grid = Ext.create('Propial.view.grid.LocationsList', {
      uploadUrl: this.uploadUrl,
      flex: 1,
      listeners: {
        onSelected: function (grd, record) {
          me.breadcrumb.addItem([record.get('id'), record.get('name')]);
        }
      }
    });

    this.breadcrumb = Ext.create('Propial.view.panel.Breadcrumb', {
      listeners: {
        onItemRemoved : function (item, index, count) {
          /*if (count > 0) {
            me.grid.locationSelected = this.data[count - 1][0];
            var params = {parentId: this.data[count - 1][0]};
            me.grid.getStore().load({'params': params});
          } else {
            me.grid.locationSelected = null;
            me.grid.getStore().load();
          }*/
        }
      }
    });
    this.items = [{
      layout: 'vbox',
      align: 'stretch',
      items: [this.breadcrumb]
    }];
    this.addEvents ('onSelected');
    this.callParent();
  }
});

Ext.define('Propial.view.form.field.Location', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.locationfield',
  layout: 'hbox',
  initComponent: function () {
    var aFieldContainer = this;

    var location = null;
    if (this.data) {
      location = this.data.get('location');
    }
    this.items = [
      {
        xtype: 'textfield',
        value: location,
        width: 200,
        name: 'location'
      }, {
        xtype: 'button',
        text: 'Seleccionar',
        handler: function () {
          var window = Ext.create('widget.locationselectorwindow', {
            listeners: {
              onSelected: function (window, record) {
              }
            }
          });
          window.show();
        }
      }
    ]
  
    this.callParent(arguments);
  },
  getJsonValue: function () {
    var fieldValues = this.query('textfield');
    var tipoAmbiente = fieldValues[0].getValue();
    var dimension = fieldValues[1].getValue();
    var observacion = fieldValues[2].getValue();
    return {'type': tipoAmbiente, 'dimension': dimension,
      'observation': observacion};
  }
});

Ext.define('Propial.view.form.PublicationForm', {
  extend: 'Ext.form.Panel',
  requires: [
    'Propial.view.form.field.Ambient'
  ],
  alias: 'widget.publicationform',
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
  loadPublication: function (publication) {
    var form = this.getForm();
    form.reset();
    form.loadRecord(publication);

    var panAmbientes = this.down('#ambientes');
    publication.ambients().each(function (ambient) {
      var newField = Ext.create('Propial.view.form.field.Ambient', {
        data: ambient
      });
      panAmbientes.insert(0, newField);
    });
  }
});

        Ext.create('Propial.view.PublicationViewport', {
          uploadUrl: '${model["uploadUrl"]}'
        });
      })
    </script>

</head>
<body>

</body>
</html>