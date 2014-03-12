<#import "publication/publication.ftl" as pub>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="product" content="Propial">
    <meta name="author" content="Martin Valletta, Buenos Aires, Argentina">
    <meta name="description" content="Publicacion de avisos inmobiliarios">
    <meta name="keywords" content="avisos, inmobiliaria, casas, departamentos, locales, alquiler, compra, venta">

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/register.css">

    <link rel="stylesheet" href="css/jquery-ui/jquery-ui.css">

    <script src="script/jquery.js"></script>
    <script src="script/jquery-ui.js"></script>

    <title>Propial</title>

<style>

legend {
  border-style: none none solid;
  border-width: 0 0 1px;
  font-size: 22px;
}

</style>

  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

    jQuery(document).ready(function() {

    });
  </script>

    <script src="script/Publication.js"></script>
    <script src="script/LocationFilter.js"></script>

</head>
<body>
  <header>
    <div class="container">
      <a href="/"><img src="img/logo.png" alt="Propial" class="logo"></a>
      <div class="actions">
        <a href="register">Registrarse</a>
        <a href="login">Identificarse</a>
      </div>
    </div>
  </header>
  <div class="body">
    <div class="container clearfix">
      <form action="registeruser" method="post">
        <fieldset>
          <legend>Datos personales</legend>
          <label for="name">Nombre y Apellido</label>
          <input type="text" name="name" required="required" />
        </fieldset>
      </form>
    </div>
  </div>
  <footer>
    <div class="copy">Copyright © 2014</div>
  </footer>
</body>