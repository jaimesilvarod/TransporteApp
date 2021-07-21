package co.edu.unipiloto.transporteapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.Objects;
import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    String contrasenaNube = "";
    Long idUsuarioNube;
    EditText meditTextUsuario, meditTextContrasena;
    Button mBtnValidarUsuario;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meditTextUsuario = findViewById(R.id.usernameEditText);
        meditTextContrasena = findViewById(R.id.passwordEditText);
        mBtnValidarUsuario = findViewById(R.id.btnValidarAutenticacion);

        String valorOrigen = getIntent().getStringExtra("origen");
        if(valorOrigen != null){
            switch(valorOrigen){
                case "cerrarSesion":
                    Toast.makeText(this, "Su sesión fue cerrada con éxito", Toast.LENGTH_LONG).show();
                    break;
                case "usuarioPropietarioCreado":
                    Toast.makeText(this, "Su cuenta como Propietario fue creada, por favor inicie sesión", Toast.LENGTH_LONG).show();
                    break;
                case "usuarioComercianteCreado":
                    Toast.makeText(this, "Su cuenta como Comerciante fue creada, por favor inicie sesión", Toast.LENGTH_LONG).show();
                    break;
            }

        }

    }

    public void validarUsuario (View view){
        meditTextUsuario = findViewById(R.id.usernameEditText);
        meditTextContrasena = findViewById(R.id.passwordEditText);
        String usuario = meditTextUsuario.getText().toString();
        String contrasena = meditTextContrasena.getText().toString();

        //Consulta de si existe el correo en la base de datos
        db.collection("usuarios")
                .whereEqualTo("correo", usuario)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            contrasenaNube = document.getString("contrasena");
                            idUsuarioNube = document.getLong("idTipoUsuario");
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        //Si existe el correo en la base de datos y la contraseña coincide
        if(contrasenaNube.equals(contrasena)){
            if(idUsuarioNube==2){
                Intent intent =  new Intent(this, MenuPrincipalComerciante.class);
                intent.putExtra("correoLogueado",usuario);
                startActivity(intent);
            }else if(idUsuarioNube==3){
                Intent intent =  new Intent(this, MenuPrincipalPropietario.class);
                //Escribir correo usuario en t_usuarios
                intent.putExtra("correoLogueado",usuario);
                startActivity(intent);
            }else if (idUsuarioNube==4){
                Intent intent =  new Intent(this, MenuPrincipalAdministrador.class);
                intent.putExtra("correoLogueado",usuario);
                startActivity(intent);
            }else{
                Intent intent =  new Intent(this, MenuPrincipalAdministrador.class);
                intent.putExtra("correoLogueado",usuario);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "Usuario y contraseña no coinciden", Toast.LENGTH_SHORT).show();
        }
    }

    public void registrarUsuario (View view){
        Intent intent =  new Intent(this, SeleccionTipoUsuario.class);
        startActivity(intent);
    }

}