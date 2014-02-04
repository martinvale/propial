<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title>Propial Admin</title>

    <link rel="stylesheet" href="/extjs/resources/css/ext-all.css">
    <link rel="stylesheet" href="/css/admin.css">

    <script type="text/javascript" src="/extjs/ext-all-debug.js"></script>
    <!--script type="text/javascript" src="/app/contracts.js"></script-->

    <script type="text/javascript">
      Ext.onReady(function() {
        Ext.Loader.setConfig({
          enabled: true,
          paths: {
            'Propial': '../app'
          }
        });
        /*Ext.application({
          name: 'Propial',
          models: ['Contract'],    
          stores: ['Contracts'],
          controllers: ['Contract'],

          launch: function() {
            Ext.create('Propial.view.Viewport', {
            });
          }
        });*/        

        Ext.create('Propial.view.UserViewport', {});
      })
    </script>

</head>
<body>

</body>
</html>