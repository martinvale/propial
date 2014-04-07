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

    <script src="script/jquery.js"></script>

    <script type="text/javascript">

      window.Propial = window.Propial || {};

      Propial.view = Propial.view || {};

Propial.view.Registration = function (container, registration) {

  var showRealStateData = function () {
    container.find(".js-realstate-data").show("slow");
    container.find("input[name='realStateName']").attr("required", "required");
    container.find("input[name='email']").attr("required", "required");
  };

  var hideRealStateData = function () {
    container.find(".js-realstate-data").hide("slow");
    container.find("input[name='realStateName']").removeAttr("required");
    container.find("input[name='email']").removeAttr("required");
  };

  var initEventListeners = function () {
    container.find(".js-user").change(function () {
      hideRealStateData();
    });
    container.find(".js-realstate").change(function () {
      showRealStateData();
    });
  }

  return {
    render: function() {
      initEventListeners();
    }
  };
}

      jQuery(document).ready(function() {

        var registrationData = {
          name: "${model["name"]!""}",
          type: "${model["type"]!""}",
          realState: {
            name: "${model["realStateName"]!""}",
            address: "${model["address"]!""}",
            telephone: "${model["telephone"]!""}",
            email: "${model["email"]!""}",
            logo: "${model["logo"]!""}"
          }
        };

        var registrationContainer = jQuery(".js-registration");
        var registration = new Propial.view.Registration(registrationContainer, registrationData);
        registration.render();
      });
    </script>

    <script src="script/Registration.js"></script>

</head>
<body>
  <header>
    <div class="container">
      <a href="/"><img src="/img/logo.png" alt="Propial" class="logo"></a>
    </div>
  </header>

  <div class="body">
    <div class="container clearfix">
      <form action="${model["uploadUrl"]}" method="POST" enctype="multipart/form-data" class="registration js-registration">
        <fieldset>
          <legend>Datos personales</legend>
          <label for="name">Nombre y Apellido (*)</label>
          <div class="field">
            <input id="name" type="text" name="name" required="required" value="${model["name"]!""}" />
          </div>
          <label>Tipo de usuario (*)</label>
          <div class="field">
            <input id="user" type="radio" name="type" required="required" value="USER" class="js-user"/><label for="user">Usuario</label><br/>
            <input id="realstate" type="radio" name="type" required="required" value="REALSTATE" class="js-realstate"/><label for="realstate">Inmobiliaria</label>
          </div>
        </fieldset>
        <fieldset class="js-realstate-data" <#if !model["type"]?? || model["type"] == "USER">style="display:none"</#if>>
          <legend>Datos de la inmobiliaria</legend>
          <label for="realStateName">Nombre (*)</label>
          <div class="field">
            <input id="realStateName" type="text" name="realStateName" required="required" value="${model["realStateName"]!""}" />
          </div>
          <label for="email">Email (*)</label>
          <div class="field">
            <input id="email" type="email" name="email" required="required" value="${model["email"]!""}" />
          </div>
          <label for="address">Direcci&oacute;n</label>
          <div class="field">
            <input id="address" type="text" name="address" value="${model["address"]!""}" />
          </div>
          <label for="telephone">Tel&eacute;fono</label>
          <div class="field">
            <input id="telephone" type="text" name="telephone" value="${model["telephone"]!""}" />
          </div>
          <label for="picture">Imagen</label>
          <div class="field">
            <input id="picture" type="file" name="picture" />
          </div>
        </fieldset>
        <p>Los campos con * son obligatorios</p>
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