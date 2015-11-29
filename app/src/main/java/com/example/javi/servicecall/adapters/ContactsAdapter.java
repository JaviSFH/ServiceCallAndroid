package com.example.javi.servicecall.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javi.servicecall.R;
import com.example.javi.servicecall.models.Contact;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Javier Martínez
 *         https://play.google.com/store/apps/developer?id=JavNez
 *         https://es.linkedin.com/in/jamafu
 * @since 28/11/15
 *
 * Con este adaptador gestionamos la información que debe contner la lista de contactos y le
 * indicamos a la misma cómo debe mostrar dicha información.
 */
public class ContactsAdapter extends BaseAdapter {

    private List<Contact> contactsList;

    private Context mContext;

    public ContactsAdapter(Context context, List<Contact> contactsList) {
        this.contactsList = contactsList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return contactsList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Reutilizamos la vista gracias al patrón ViewHolder
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.contact_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //Rellenamos dichas vistas con la información del objeto Contact de la posición actual
        holder.name.setText(contactsList.get(position).getName());
        holder.phone.setText(contactsList.get(position).getPhone());

        //Iniciamos el AsyncTask para que descargue la imagen del contacto
        new DownloadPicture(holder.contactImage).execute(contactsList.get(position).getContactPictureURL());

        //Ya tenemos la celda construida, por lo que sólo queda retornarla para que sea pintada en
        //la posición correspondiente del ListView, mientras que de en paralelo se estará descargando
        //la imagen de este contacto

        return convertView;
    }

    /**
     * Clase estática con los elementos del ViewHolder que nos permitirá reutilizar la vista
     */
    static class ViewHolder {
        @Bind(R.id.imageView) ImageView contactImage;
        @Bind(R.id.textViewName) TextView name;
        @Bind(R.id.textViewPhone) TextView phone;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Con este hilo asíncrono cargaremos la imagen de cada contacto recibida como una URL en
     * el primer servicio
     */
    public class DownloadPicture extends AsyncTask<String, Void, Bitmap> {

        /**
         * PlaceHolder donde colocaremos la imagen del contacto una vez cargada
         */
        private ImageView mImageView;

        public DownloadPicture(ImageView imageView) {
            this.mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String pictureURL = params[0];
            Bitmap picture = null;

            HttpURLConnection urlConnection = null;
            try {
                // Se establece la conexión con el servicio
                URL url = new URL(pictureURL);
                urlConnection = (HttpURLConnection) url.openConnection();

                // Se realiza la llamada y se obtiene su respuesta
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                //Convertimos la respuesta del servicio en un BitMap
                picture = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            return picture;
        }

        @Override
        protected void onPostExecute(Bitmap picture) {
            super.onPostExecute(picture);

            //La imagen se muestra en su place holder
            if (picture != null && mImageView != null){
                mImageView.setImageBitmap(picture);
            }
        }
    }
}
