package com.example.javi.servicecall.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.javi.servicecall.R;
import com.example.javi.servicecall.adapters.ContactsAdapter;
import com.example.javi.servicecall.data.APIConstants;
import com.example.javi.servicecall.models.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Martínez
 *         https://play.google.com/store/apps/developer?id=JavNez
 *         https://es.linkedin.com/in/jamafu
 * @since 28/11/15
 *
 * En esta actividad se cargará la lista de contactos, realizando la llamada asíncrona a través
 * de un AsynTask (una inner class dentro de esta clase)
 */
public class ContactsActivity extends AppCompatActivity {

    /**
     * Adapter que contendrá los resultados de la lista de contactos y que
     * deberemos bindear (enlazar) con el ListView
     */
    private ContactsAdapter contactsAdapter;

    /**
     * Widget de Android para mostrar elementos en forma de lista
     */
    private ListView listViewContacts;

    /**
     * Barra de progresso (que se mostrará como un spinner circular) para indicar al usuario que se
     * está ejecutando una tarea y que debe esperar su resultado (UX)
     */
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listViewContacts = (ListView) findViewById(R.id.listViewContacts);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new DownloadContacts(this).execute();
    }

    /**
     * Este método se encarga de pintar los resultados obtenidos del servicio en el ListView
     * a través de su adapter
     *
     * @param jsonArray lista de contactos en forma de objetos JSON
     */
    private void populateListView(JSONArray jsonArray) {
        List<Contact> contactsList = new ArrayList<>();

        //Se crea la lista de objetos para pasárselos al adapter
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonContact = jsonArray.getJSONObject(i);
                Contact contact = new Contact(jsonContact.getString("thumbnail"),
                        jsonContact.getString("name"),
                        jsonContact.getString("phone"));

                contactsList.add(contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Instanciamos el adapter con la lista de contactos ya creada y rellena
        contactsAdapter = new ContactsAdapter(this, contactsList);

        //Bindeamos el adapter a la lista
        listViewContacts.setAdapter(contactsAdapter);
    }

    /**
     * Tarea asíncrona (AsyncTask) que se encargará de preparar la interfaz gráfica para la
     * llamada asíncronar, realizar la propia llamada en otro hilo y, al terminar esta, volver
     * a actualizar la interfaz gráfica con los datos recogidos por el servicio.
     */
    private class DownloadContacts extends AsyncTask<Void, Void, Integer> {

        /**
         * Estas dos constantes indican los posibles escenarios al llamar al servicio y parsear
         * su resultado (KO y OK)
         */
        public static final int SERVICE_ERROR = -1;
        public static final int SERVICE_SUCCESS = 1;

        private Activity mActivity;
        private JSONArray mJsonArray = null;

        public DownloadContacts(Activity mActivity) {
            this.mActivity = mActivity;
        }

        /**
         * Este método se ejecutará en el hilo principal de la aplicación y se usa para
         * realizar las tareas de preparación previas a la llamada asíncrona
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Mostramos el spinner de progreso
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * Aquí realizamos las tareas que queremos que se realicen de forma asíncrona (en otro
         * hilo) para no bloquear así el hilo principal de ejecución de la aplicación)
         */
        @Override
        protected Integer doInBackground(Void... params) {
            int result = SERVICE_SUCCESS;

            HttpURLConnection urlConnection = null;
            try {
                // Se establece la conexión con el servicio
                URL url = new URL(APIConstants.SERVICE_ENDPOINT);
                urlConnection = (HttpURLConnection) url.openConnection();

                // Se realiza la llamada y se obtiene su respuesta
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                // Convertimos la respuesta, en una cadena de texto
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                // La respuesta cruda (como String) se tranforma a un array de objetos Json
                // Cada elemento del array es un objeto "Contacto"
                mJsonArray = new JSONArray(responseStrBuilder.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                result = SERVICE_ERROR;
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            //Aquí termina el trabajo del hilo asíncrono, ahora se ejecuta el método onPostExecute()
            return result;
        }

        /**
         * Este método se invoca una vez ha terminado de ejecutarse el hilo asíncrono y lo que
         * escribamos dentro se volverá a ejecutar en el hilo principal. Normalmente lo usamos
         * para actualizar la interfaz de usuario.
         */
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            //Ocultamos el spinner de progreso
            progressBar.setVisibility(View.GONE);

            //Si la llamada ha salido bien, mostramos la lista, si no, un error y
            // volvemos a la actividad anterior
            switch (result) {
                case SERVICE_SUCCESS:
                    populateListView(mJsonArray);
                    break;
                case SERVICE_ERROR:
                    Toast.makeText(mActivity, R.string.error_calling_service, Toast.LENGTH_SHORT).show();
                    mActivity.finish();
                    break;
            }
        }
    }
}
