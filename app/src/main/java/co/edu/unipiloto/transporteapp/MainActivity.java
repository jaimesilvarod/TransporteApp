package co.edu.unipiloto.transporteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    EditText meditTextUsuario, meditTextContrasena;
    Button mBtnValidarUsuario;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meditTextUsuario = findViewById(R.id.usernameEditText);
        meditTextContrasena = findViewById(R.id.passwordEditText);
        mBtnValidarUsuario = findViewById(R.id.btnValidarAutenticacion);

    }

    public void validarUsuario(View view){

        DocumentReference docRef = db.collection("cities").document("SF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

            /*if(dato.exists()){
                if(Objects.requireNonNull(documentSnapshot.getString("correo")).equals(meditTextUsuario.toString()))
                {
                    if(Objects.requireNonNull(documentSnapshot.getString("contrasena")).equals(meditTextContrasena.toString())){
                        /* Superó la autenticación
                        Intent intent = new Intent(this, MenuPrincipalAdministrador.class);
                        startActivity(intent);
                    }else{
                        /* No superó la autenticación
                        Context context = getApplicationContext();
                        CharSequence text = "Usuario y/o contraseña incorrectos";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }else{
                    /* No superó la autenticación
                    Context context = getApplicationContext();
                    CharSequence text = "Usuario y/o contraseña incorrectos";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        }); */
    }

}