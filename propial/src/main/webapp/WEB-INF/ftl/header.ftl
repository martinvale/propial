  <header>
    <div class="container">
      <a href="/"><img src="/img/logo.png" alt="Propial" class="logo"></a>
      <div class="actions">
        <#assign publishUrl="/admin/publish"/>
        <#if model["user"]?? && model["user"].role == "UNREGISTERED">
          <#assign publishUrl="/register"/>
        </#if>
        <a href="${publishUrl}" class="button primary">Publicar un aviso</a>
        <#if model["user"]??>
          <span class="logged"><i class="fa fa-user"></i> ${model["user"].displayName}</span>

          <#assign adminUrl="/admin/"/>
          <#if model["user"].role == "UNREGISTERED">
            <#assign adminUrl="/register"/>
          </#if>
          <a href="${adminUrl}">Admin</a>
          <a href="/logout">Salir</a>
        <#else>
          <a href="/admin/">Identificarse</a>
        </#if>
      </div>
    </div>
  </header>
