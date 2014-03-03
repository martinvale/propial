<#macro renderPublication publication>
  <div id="${publication.id}" class="box item">
    <div class="photos">
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
      <#if (publication.resources?size > 0)>
        <img src="/services/publications/resource/${publication.resources[0].key.keyString}" />
      <#else>
        <img src="/img/no_foto.gif" class="no-photo" />
      </#if>
      <div class="description">
        <div>
          <span class="title">
            <span class="type">${publication.type}</span>
            <span class="location">${publication.locations[0].name}</span>
          </span>
          <span class="price">${price}</span>
        </div>
        <div class="details">
          <ul>
            <li><strong>Ambientes:</strong> ${ambients}</li>
            <li><strong>Antiguedad:</strong> ${age}</li>
            <li><strong>Superficie:</strong> ${surface}</li>
          </ul>
        </div>
      </div>
    </div>
    <div class="actions">
      <a href="#">compartir</a>
    </div>
    <!--div class="comments">
      <div class="comment">This is a test.</div>
      <div class="comment">This is a test.</div>
      <div class="comment">This is a test.</div>
      <button class="button link">Agregar una pregunta</button>
    </div-->
  </div>
</#macro>