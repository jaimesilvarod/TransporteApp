package co.edu.unipiloto.transporteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RegistrarVehiculo extends AppCompatActivity {

    String correoUsuarioReg = "";
    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_vehiculo);
        setContentView(R.layout.activity_vehiculosasu_nombre);
        correoUsuarioReg = getIntent().getStringExtra("correoUsuario");
        view = findViewById(R.id.tituloUsuarioRegVehiTextView);
        String textoInicial = "Bienvenido: " + correoUsuarioReg;
        view.setText(textoInicial);
        


    }
}