<#macro renderPublication publication>
  <#assign price="consultar" />
  <#if publication.price?? && publication.currencyType??>
    <#assign price="${publication.currencyType}${publication.price}" />
  </#if>
  <#assign age="-" />
  <#if publication.age??>
    <#assign age="${publication.age} años" />
  </#if>
  <#assign surface="-" />
  <#if publication.surface??>
    <#assign surface="${publication.surface} m2" />
  </#if>
  <#assign ambients="-" />
  <#if (publication.ambients?size > 0)>
    <#assign ambients="${publication.ambients?size}" />
  </#if>
  <@renderItem publicationId="${publication.id?c}" price="${price}" age="${age}" surface="${surface}"
      ambients="${ambients}" type="${publication.type}" resources=publication.resources
      location="${publication.locations[0].name}" />
</#macro>

<#macro renderItem publicationId="" price="consultar" age="-" surface="-" ambients="-"
    type="" resources=[] location="">
  <div id="${publicationId}" class="box item">
    <div class="photos">
      <#if (resources?size > 0)>
        <img src="/services/publications/resource/${resources[0].key.keyString}" />
      <#else>
        <img src="/img/no_foto.gif" class="no-photo" />
      </#if>
      <div class="description">
        <div>
          <#assign title="${type} en ${location}" />
          <a href="/detail/${publicationId}" class="title js-title" title="${title}">
            ${title}
          </a>
          <span class="price">${price}</span>
        </div>
        <div class="details">
          <ul>
            <li><strong>Ambientes:</strong> <span class="js-ambients">${ambients}</span></li>
            <li><strong>Antiguedad:</strong> <span class="js-age">${age}</span></li>
            <li><strong>Superficie:</strong> <span class="js-surface">${surface}</span></li>
          </ul>
        </div>
      </div>
    </div>
    <div class="actions">
      <button class="button small js-fb-share"><i class="fa fa-facebook"></i> compartir</button>
    </div>
    <!--div class="comments">
      <div class="comment">This is a test.</div>
      <div class="comment">This is a test.</div>
      <div class="comment">This is a test.</div>
      <button class="button link">Agregar una pregunta</button>
    </div-->
  </div>
</#macro>