package co.edu.unipiloto.transporteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class RegistroUsuario extends AppCompatActivity {

    EditText nombresRegistrados, apellidosRegistrados, correoRegistrado, contrasenaRegistrada, confirmaContrasena;
    Button mregistroUsuario;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        nombresRegistrados = findViewById(R.id.nombreUsuarioEditText);
        apellidosRegistrados = findViewById(R.id.apellidosUsuarioEditText);
        correoRegistrado = findViewById(R.id.correoElectronicoEditText);
        contrasenaRegistrada = findViewById(R.id.contrasenaUsuarioEditText);
        confirmaContrasena = findViewById(R.id.confirmaContrasenaUsuarioEditText);
        mregistroUsuario = findViewById(R.id.btnRegistroUsuario);
    }

    public void validacionDatosRegistrados(View view) {

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombres", nombresRegistrados.getText().toString());
        usuario.put("apellidos", apellidosRegistrados.getText().toString());
        usuario.put("correo", correoRegistrado.getText().toString());
        usuario.put("contrasena", contrasenaRegistrada.getText().toString());
        usuario.put("idTipoUsuario", 2);

        db.collection("usuarios").document()
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(RegistroUsuario.this, "No fue posible crear el usuario", Toast.LENGTH_LONG).show();
                    }
                });

        //Pausa para mostrar los mensajes
        Toast.makeText(RegistroUsuario.this, "Se ha registrado con éxito", Toast.LENGTH_LONG).show();
        onPause();
        onResume();
        Intent intent = new Intent(RegistroUsuario.this, MainActivity.class);
        startActivity(intent);

    }

}