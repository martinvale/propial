<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="product" content="Propial">
    <meta name="author" content="Martin Valletta, Buenos Aires, Argentina">
    <meta name="description" content="Publicacion de avisos inmobiliarios">
    <meta name="keywords" content="avisos, inmobiliaria, casas, departamentos, locales, alquiler, compra, venta">

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/index.css">

    <title>Propial</title>
</head>
<body>
  <header>
    <div class="container">
      <a href="/">Propial</a>
      <div class="actions">
        <a href="login">Identificarse</a>
      </div>
    </div>
  </header>
  <div class="body">
    <div class="container clearfix">
      <div class="search">
        <form action="search">
          <label for="search">En donde desea buscar?</label>
          <div class="field">
            <input id="search" type="text" />
            <button><span class="button-content">buscar</span></button>
          </div>
        </form>
      </div>
      <div class="content">
        <!-- inicio column -->
        <div class="column">

          <#list model["publications"] as publication>
          <!-- inicio item -->
          <div class="box item">
            <div class="photos">
              <img src="depto.jpg" />
              <div class="description">
                <div>
                  <span class="title">${publication.title}</span>
                  <span class="price">$5000</span>
                </div>
                <div class="details">
                  <ul>
                    <li><strong>Ambientes:</strong> 5</li>
                    <li><strong>Antiguedad:</strong> 15 años</li>
                    <li><strong>Superficie:</strong> 42 m2</li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="actions">
              <a href="#">compartir</a>
            </div>
            <div class="comments">
              <div class="comment">This is a test.</div>
              <div class="comment">This is a test.</div>
              <div class="comment">This is a test.</div>
              <button class="button link">Agregar una pregunta</button>
            </div>
          </div>
          <!-- fin item -->
          </#list>
        </div>
        <!-- fin column -->

        <!-- inicio column -->
        <div class="column second">

        </div>
        <!-- fin column -->

      </div>
    </div>
  </div>
  <footer>
    <div class="copy">Copyright © 2014</div>
  </footer>
</body>