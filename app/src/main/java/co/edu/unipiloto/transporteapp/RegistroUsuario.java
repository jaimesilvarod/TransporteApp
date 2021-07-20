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

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RegistroUsuario extends AppCompatActivity {

    private final static String emailAccount= "transportecuenta848@gmail.com";
    private final static String emailPassword= "fasdjkl123";

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
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error writing document", e);
                    Toast.makeText(RegistroUsuario.this, "No fue posible crear el usuario", Toast.LENGTH_LONG).show();
                });

                new Thread(() -> {
                    try {
                        String emailFrom = emailAccount;
                        String emailTo = correoRegistrado.getText().toString();
                        String emailSubj = "Creación de cuenta exitosa";
                        String emailBody = "Tu cuenta ha sido creada satisfactoriamente";
                        GMailSender sender = new GMailSender(emailAccount, emailPassword);
                        sender.sendMail(emailSubj, emailBody, emailFrom, emailTo);
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }).start();

        Intent intent = new Intent(RegistroUsuario.this, MainActivity.class);
        intent.putExtra("origen", "usuarioComercianteCreado");
        startActivity(intent);
    }

}