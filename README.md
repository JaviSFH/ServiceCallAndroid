# ServiceCallAndroid
Ejemplo de cómo realizar la llamada a un servicio fuera del hilo principal (**UI Thread**) a través de AsyncTasks.

La funcionalidad del ejemplo será de cargar, de un servicio, una lista de contactos. En el objeto respuesta, cada contacto contendrá una URL de una imagen, un nombre y un número de teléfono.

Posteriormente, para cada contacto, se hará **otra llamada asíncrona** (con otro AsyncTask) para obtener las imágenes de cada contacto.

El objetivo de realizar el proceso en dos llamadas asíncronas y no recibir en un único servicio las imágenes y los datos es el de **no bloquear al usuario** y mantenerle a la espera mientras se cargan las imágenes y los datos de contacto. De este modo, el usuario puede ver la información de los contactos mientras que, en paralelo, se van cargando las imágenes. Esto mejora notablemente la experiencia de usuario (**UX**).

Este código está profusamente comentado y pretende contener las menos líneas de código posibles para el fácil entendimiento y seguimiento del mismo. Debido a esto, propongo las siguientes mejoras:

  - **Cachear las imágenes** que nos llegan del servicio para no tener que volver a cargarlas de su URL, con esto conseguimos mayor fluidez en la lista así como una reducción importante del uso de datos móviles.
  
  - **Usar el patrón ViewHolder** al cargar los items de la lista dentro del adapter, de este modo conseguimos reutilizar las vistas ya existentes al hacer scroll en la lista, mejorando notablemente la fluidez de dicho scroll. Esto será más notable cuanto más complejo sea el layout que representa una fila de la lista.
  
  - **Guardar el estado del AsyncTask** cuando se produzca un cambio de estado de la actividad, que podría darse por ejemplo al rotar el dispositivo, lo que provocaría un comportamiento inesperado del AsyncTask. Esto es así debido a la propia naturaleza del AsyncTask.

> **Nota:**

>Este código está realizado únicamente con métodos y librerías del proyecto AOSP de Android, por lo que no incluye librerías de terceros y en consecuencia, tampoco incluye librerías de Google. Por supuesto, hay librerías de terceros que nos facilitarían enormemente la labor realizada, alcanzando el mismo o incluso mejor resultado.
