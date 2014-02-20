Ext.define('Propial.view.panel.Breadcrumb', {
  extend: 'Ext.panel.Panel',
  height: 20,
  border: false,
  //margin: '0 0 0 2',
  padding: '2 0 0 8',
  cls: 'crumbs',  
  initComponent : function(){
    var me = this;

    this.data = [],
    
    this.store = Ext.create('Ext.data.ArrayStore', {
      // store configs
      autoDestroy: true,
      storeId: 'path',
      // reader configs
      idIndex: 0,
      fields: [{name: 'id'}, {name: 'name'}]
    })

    this.items = [Ext.create('Ext.DataView', {
      itemSelector: 'a.path',
      store: me.store,
      tpl: new Ext.XTemplate('<ul>'
          +'<tpl for="."><li class="{[xindex == 1 ? "first" : ""]} {[xindex == xcount ? "current" : ""]}">'
            +'<a class="path">{name}</a>'
          +'</li></tpl>'
        +'</ul>'),
       
        listeners: {
          itemclick: function (dv, record, node, index, evObj, opts) {
            me.removeItem (record, index);
          }
        }
    })];
    
    me.callParent();

    this.addEvents ('onItemRemoved');
  },
  
  reload : function () {
    this.store.loadData(this.data);
  },

  addItem : function (item) {
    this.data.push(item);
    this.reload();
  },
  
  clearAll : function () {
    this.data = [];
    this.reload();
  },
  
  removeItem : function (record, index) {
    var count = this.data.length;
    var cant = count - index;
    while (this.data.length != 0 && cant > 0) {
      this.data.pop();
      cant = cant - 1;
    }
    this.reload();
    this.fireEvent ('onItemRemoved', record, index, this.data.length);
  },

  getCurrentValue: function() {
    var result = null;
    if (this.data.length > 0) {
      result = this.data[this.data.length - 1][0];
    }
    return result;
  }
});
