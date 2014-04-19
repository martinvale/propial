  <div id="header">
    <#if model["user"].role == "ADMIN">
      <a href="contracts">Contratos</a>
    </#if>
    <#if model["user"].role == "ADMIN" || model["user"].role == "CUSTOMER_ADMIN" >
      <a href="users">Usuarios</a>
    </#if>
      <a href="./">Publicaciones</a>
    <#if model["user"].role == "ADMIN">
      <a href="locations">Ubicaciones</a>
    </#if>
    <span class="actions">
      <span class="logged"><i class="fa fa-user"></i> ${model["user"].displayName}</span>
      <a href="${model["logouturl"]}">Salir</a>
    </span>
  </div>
