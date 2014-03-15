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

    <title>Propial</title>
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
      <form action="register" method="POST" class="registration">
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
        <div class="status error">
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