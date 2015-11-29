package com.example.javi.servicecall.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.javi.servicecall.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Javier Martínez
 *         https://play.google.com/store/apps/developer?id=JavNez
 *         https://es.linkedin.com/in/jamafu
 * @since 28/11/15
 *
 * Esta es la actividad principal y en ella únicamente aparecerá un botón para navegar a la
 * ventana de contactos
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.buttonGetPersons)
    public void onGetPersonsButtonPressed(Button button){
        //Se inicia la navegación a la Activity de contactos
        Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
        startActivity(intent);
    }
}
