<!DOCTYPE html>
<html>
<head>
  <#include "admin/commons.ftl" />

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
  <#include "admin/header.ftl" />
</body>
</html>