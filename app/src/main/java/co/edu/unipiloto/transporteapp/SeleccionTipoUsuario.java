package co.edu.unipiloto.transporteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SeleccionTipoUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_tipo_usuario);
    }

    public void registrarUsuarios(View view){
        Intent intent =  new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }

    public void registrarPropietarios(View view){
        Intent intent =  new Intent(this, RegistroPropietario.class);
        startActivity(intent);
    }
}