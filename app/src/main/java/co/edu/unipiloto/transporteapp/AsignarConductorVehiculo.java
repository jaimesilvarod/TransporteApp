package co.edu.unipiloto.transporteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.LazyStringArrayList;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AsignarConductorVehiculo extends AppCompatActivity {

    String correoUsuarioReg = "";
    TextView view;
    Spinner conductoresPool, vehiculosPool;
    ArrayAdapter<String> conductoresAdapter;
    ArrayAdapter<String> vehiculosAdapter;
    ArrayList<String> conductores = new ArrayList<>();
    ArrayList<String> vehiculos = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_conductor_vehiculo);
        conductoresPool = findViewById(R.id.spinnerConductores);
        vehiculosPool = findViewById(R.id.spinnerVehiculos);
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
                                conductores.add(document.getString("nombres") + " " + document.getString("apellidos"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        conductoresPool.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,conductores));

        conductoresPool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Vamos a buscar los conductores y asignarlos a la posición en el arreglo
        db.collection("vehiculos")
                .whereEqualTo("propietario", correoUsuarioReg)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                vehiculos.add(document.getString("marca") + " " + document.getString("placa"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        vehiculosPool.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,vehiculos));

        vehiculosPool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}