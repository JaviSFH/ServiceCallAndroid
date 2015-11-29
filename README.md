# ServiceCallAndroid
Ejemplo de cómo realizar la llamada a un servicio fuera del hilo principal (**UI Thread**) a través de AsyncTasks.

La funcionalidad del ejemplo será de cargar, de un servicio, una lista de contactos. En el objeto respuesta, cada contacto contendrá una URL de una imagen, un nombre y un número de teléfono.

Posteriormente, para cada contacto, se hará **otra llamada asíncrona** (con otro AsyncTask) para obtener las imágenes de cada contacto.

El objetivo de realizar el proceso en dos llamadas asíncronas y no recibir en un único servicio las imágenes y los datos es el de **no bloquear al usuario** y mantenerle a la espera mientras se cargan las imágenes y los datos de contacto. De este modo, el usuario puede ver la información de los contactos mientras que, en paralelo, se van cargando las imágenes. Esto mejora notablemente la experiencia de usuario (**UX**).

Este código está profusamente comentado y pretende contener las menos líneas de código posibles para el fácil entendimiento y seguimiento del mismo. 

Además, mejorado la primera versión (mirar **TAG 1.0**) y manteniendo la funcionalidad, incluyo el uso de librerías de terceros. Sin entrar en detalles de cada una, a continuación listo sus nombres y su página web para quien quiera profundizar más.

  - **Butter Knife:** http://jakewharton.github.io/butterknife/
  
  - **Picasso: ** http://square.github.io/picasso/
  
  - **Retrofit: ** http://square.github.io/retrofit/

> **Nota:**

>La primera y más importante premisa en el mundo del desarrollo es **no reinventar la rueda**, por lo que es recomendable antes de ponerse a tirar líneas de código, buscar entre las librerías de terceros disponibles si hay alguna que cubra la funcionalidad que estamos buscando.
