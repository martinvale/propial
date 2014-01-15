Ext.application({
  name: 'Propial',
  autoCreateViewport: true,
  models: ['Contract'],    
  stores: ['Contracts'],
  controllers: ['Contract']

  /*launch: function() {
    Ext.create('Ext.container.Viewport', {
      layout: 'fit',
      items: [
        {
          title: 'Hello Ext 2',
          html : 'Hello! Welcome to Ext JS.'
        }
      ]
    });
  }*/
});