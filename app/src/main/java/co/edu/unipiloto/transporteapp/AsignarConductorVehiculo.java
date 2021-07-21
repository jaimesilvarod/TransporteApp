package co.edu.unipiloto.transporteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AsignarConductorVehiculo extends AppCompatActivity {

    String correoUsuarioReg = "";
    TextView view;
    Spinner conductoresPool;
    public ArrayList<String> lenguajes = new ArrayList<>();
    Integer posicionArreglo = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_conductor_vehiculo);
        conductoresPool = findViewById(R.id.spinnerConductores);
        correoUsuarioReg = getIntent().getStringExtra("correoUsuario");
        view = findViewById(R.id.textViewTituloAsignarCVehiculo);
        String textoInicial = "Bienvenido: " + correoUsuarioReg;
        view.setText(textoInicial);

        //Vamos a buscar los conductores y asignarlos a la posición en el arreglo
        db.collection("usuarios")
                .whereEqualTo("idTipoUsuario", 4)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                lenguajes.set(posicionArreglo, document.getString("nombres") + " " + document.getString("apellidos"));
                                //Log.d(TAG, document.getId() + " => " + document.getData() + " " + lenguajes.get(posicionArreglo));
                                posicionArreglo++;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        conductoresPool.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,lenguajes));


    }
}