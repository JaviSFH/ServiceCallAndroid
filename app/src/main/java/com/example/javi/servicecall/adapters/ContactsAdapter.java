package com.example.javi.servicecall.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javi.servicecall.R;
import com.example.javi.servicecall.models.Contact;
import com.squareup.picasso.Picasso;

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

        //Cargamos la imagen a través de su URL en su vista correspondiente
        Picasso.with(mContext)
                .load(contactsList.get(position).getThumbnail())
                .into(holder.contactImage);

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
}
