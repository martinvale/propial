Ext.application({
  name: 'Propial',
  models: ['Contract'],    
  stores: ['Contracts'],
  controllers: ['Contract'],

  launch: function() {
    Ext.create('Propial.view.Viewport', {
    });
  }
});