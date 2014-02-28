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

        Ext.create('Propial.view.PublicationViewport', {
          uploadUrl: '${model["uploadUrl"]}'
        });
      })
    </script>

</head>
<body>

</body>
</html>