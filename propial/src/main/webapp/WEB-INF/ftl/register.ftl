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
    <!--link rel="stylesheet" href="css/register.css"-->

    <title>Propial</title>

<style>

input[type="button"], input[type="submit"], input[type="reset"] {
  background-color: #D9D9D9;
  border: 1px solid rgba(0, 0, 0, 0);
  color: #222222;
  cursor: pointer;
  font-size: 14px;
  margin: auto;
  padding: 4px 12px;
}

form {
  font-size: 120%;
}

fieldset {
  border: 0 none;
  padding: 0;
}

legend {
  border-color: #E5E5E5;
  border-style: none none solid;
  border-width: 0 0 1px;
  color: #333333;
  font-size: 22px;
  line-height: 40px;
  margin-bottom: 18px;
  width: 100%;
}

label {
  display: block;
  margin: 5px 0;
}

.field input {
  border: 1px solid #D9D9D9;
  font-size: 170%;
  padding: 2px 5px;
  width: 99%;
}

.field {
  margin-bottom: 12px; 
}

.status {
  background-color: #FFC0C3;
  border: 1px solid #FCA8AB;
  font-size: 110%;
  font-weight: bold;
  margin: 10px 0;
  padding: 5px;
}

.buttons {
  margin-top: 15px;
}

</style>

  <script type="text/javascript">

    window.Propial = window.Propial || {};

    Propial.view = Propial.view || {};

    /*jQuery(document).ready(function() {

    });*/
  </script>

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
      <form action="register" method="POST">
        <fieldset>
          <legend>Datos personales</legend>
          <label for="name">Nombre y Apellido (*)</label>
          <div class="field">
            <input id="name" type="text" name="name" required="required" value="${model["name"]!""}" />
          </div>
          <label for="username">Usuario (*)</label>
          <div class="field">
            <input id="username" type="text" name="username" required="required" value="${model["username"]!""}" />
          </div>
          <label for="email">Email (*)</label>
          <div class="field">
            <input id="email" type="email" name="email" required="required" value="${model["email"]!""}"/>
          </div>
          <label for="password">Password (*)</label>
          <div class="field">
            <input id="password" type="password" name="password" required="required" />
          </div>
          <label for="rePassword">Reingrese la password (*)</label>
          <div class="field">
            <input id="rePassword" type="password" name="rePassword" required="required" />
          </div>
          <p>Los campos con * son obligatorios</p>
        </fieldset>
        <#if model["errors"]??>
        <div class="status">
          <#list model["errors"] as error>
            <p>${error}</p>
          </#list>
        </div>
        </#if>
        <div class="buttons">
          <input type="submit" value="Registrarse" />
        </div>
      </form>
    </div>
  </div>
  <footer>
    <div class="copy">Copyright © 2014</div>
  </footer>
</body>