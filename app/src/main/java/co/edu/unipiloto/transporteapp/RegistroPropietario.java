package co.edu.unipiloto.transporteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistroPropietario extends AppCompatActivity {

    EditText nombresRegistrados, apellidosRegistrados, correoRegistrado, contrasenaRegistrada, confirmaContrasena;
    Button mregistroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_propietario);

        nombresRegistrados = findViewById(R.id.nombreUsuarioEditText);
        apellidosRegistrados = findViewById(R.id.apellidosUsuarioEditText);
        correoRegistrado = findViewById(R.id.correoElectronicoEditText);
        contrasenaRegistrada = findViewById(R.id.contrasenaUsuarioEditText);
        confirmaContrasena = findViewById(R.id.confirmaContrasenaUsuarioEditText);
        mregistroUsuario = findViewById(R.id.btnRegistroUsuario);

    }

    private void validacionDatosRegistrados(View view) {

    }

}