  <header>
    <div class="container">
      <a href="/"><img src="/img/logo.png" alt="Propial" class="logo"></a>
      <div class="actions">
        <a href="/admin/" class="button primary">Publicar un aviso</a>
        <#if model["user"]??>
          <span class="logged"><i class="fa fa-user"></i> ${model["user"].displayName}</span>
          <a href="/admin/">Admin</a>
          <a href="/logout">Salir</a>
        <#else>
          <a href="/admin/">Identificarse</a>
        </#if>
      </div>
    </div>
  </header>
