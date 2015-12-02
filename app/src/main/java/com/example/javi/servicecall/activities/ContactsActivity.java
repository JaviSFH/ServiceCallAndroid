package com.example.javi.servicecall.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.javi.servicecall.R;
import com.example.javi.servicecall.adapters.ContactsAdapter;
import com.example.javi.servicecall.data.APIConstants;
import com.example.javi.servicecall.data.APIServices;
import com.example.javi.servicecall.models.Contact;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    @Bind(R.id.listViewContacts)
    ListView listViewContacts;

    /**
     * Barra de progresso (que se mostrará como un spinner circular) para indicar al usuario que se
     * está ejecutando una tarea y que debe esperar su resultado (UX)
     */
    @Bind(R.id.progressBar)
        ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //Inyectamos las vistas con ButterKnife
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Instanciamos el adapatador Rest
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(APIConstants.SERVICE_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();


        APIServices service = restAdapter.create(APIServices.class);

        //Invocamos al servicio, recuperando en el callback la respuesta del mismo
        service.getContacts(new Callback<List<Contact>>() {
            //Si el servicio se invoca y éste responde (éxito) se invoca este método
            @Override
            public void success(List<Contact> contacts, Response response) {
                //Instanciamos el adapter con la lista de contactos ya creada y rellena
                contactsAdapter = new ContactsAdapter(getApplicationContext(), contacts);

                //Bindeamos el adapter a la lista
                listViewContacts.setAdapter(contactsAdapter);
            }

            //Si el servicio falla por cualquier motivo, se invoca este otro método
            @Override
            public void failure(RetrofitError error) {
                Log.e("Retrofit", error.toString());
            }
        });
    }
}
