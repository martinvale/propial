  <header>
    <div class="container">
      <a href="/"><img src="img/logo.png" alt="Propial" class="logo"></a>
      <div class="actions">
        <#if model["user"]??>
          <span class="logged"><i class="fa fa-user"></i> ${model["user"].displayName}</span>
          <a href="/admin/">Admin</a>
          <a href="${model["logouturl"]}">Salir</a>
        <#else>
          <a href="register">Registrarse</a>
          <a href="${model["loginurl"]}">Identificarse</a>
        </#if>
      </div>
    </div>
  </header>
