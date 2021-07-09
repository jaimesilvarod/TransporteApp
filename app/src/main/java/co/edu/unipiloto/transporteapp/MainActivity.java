package co.edu.unipiloto.transporteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText meditTextUsuario, meditTextContrasena;
    Button mBtnValidarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meditTextUsuario = findViewById(R.id.usernameEditText);
        meditTextContrasena = findViewById(R.id.passwordEditText);
        mBtnValidarUsuario = findViewById(R.id.btnValidarAutenticacion);

    }

    public void validarUsuario (View view){
        String usuario = meditTextUsuario.getText().toString();
        String contrasena = meditTextContrasena.getText().toString();

        if(("jaimesilvarod@gmail.com").equals(usuario) && ("123456").equals(contrasena)){
            Intent intent =  new Intent(this, MenuPrincipalAdministrador.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Usuario y contraseña no coinciden", Toast.LENGTH_SHORT).show();
        }
    }

    public void registrarUsuario (View view){
        Intent intent =  new Intent(this, SeleccionTipoUsuario.class);
        startActivity(intent);
    }

}

