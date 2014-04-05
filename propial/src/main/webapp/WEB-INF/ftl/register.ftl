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
      <a href="/"><img src="/img/logo.png" alt="Propial" class="logo"></a>
    </div>
  </header>

  <div class="body">
    <div class="container clearfix">
      <form action="${model["uploadUrl"]}" method="POST" enctype="multipart/form-data" class="registration">
        <fieldset>
          <legend>Datos personales</legend>
          <label for="name">Nombre y Apellido (*)</label>
          <div class="field">
            <input id="name" type="text" name="name" required="required" value="${model["name"]!""}" />
          </div>
          <label>Tipo de usuario (*)</label>
          <div class="field">
            <input id="user" type="radio" name="type" required="required" value="USER" /><label for="user">Usuario</label><br/>
            <input id="realstate" type="radio" name="type" required="required" value="REALSTATE" /><label for="realstate">Inmobiliaria</label>
          </div>
          <label for="picture">Imagen</label>
          <div class="field">
            <input id="picture" type="file" name="picture" />
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

  <#include "footer.ftl" />
</body>