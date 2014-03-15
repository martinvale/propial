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

      Ext.create('Propial.view.LocationViewport', {});
    })
  </script>

</head>
<body>
  <#include "admin/header.ftl" />
</body>
</html>