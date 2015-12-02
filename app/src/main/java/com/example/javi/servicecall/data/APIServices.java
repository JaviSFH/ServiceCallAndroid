package com.example.javi.servicecall.data;

import com.example.javi.servicecall.models.Contact;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * @author Javier Martínez
 *         https://play.google.com/store/apps/developer?id=JavNez
 *         https://es.linkedin.com/in/jamafu
 * @since 28/11/15
 *
 * En esta interfaz se definen los servicios que tendremos disponibles a través de la
 * librería Retrofit.
 */
public interface APIServices {

    /**
     * Con este servicio obtendremos la lista de contactos
     *
     * @param callBack Respuesta del servidor ya parseada en una lista de contactos
     */
    @GET(APIConstants.CONTACTS_SERVICE)
    void getContacts(Callback<List<Contact>> callBack);
}
