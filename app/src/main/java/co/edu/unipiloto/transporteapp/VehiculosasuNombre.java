package co.edu.unipiloto.transporteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;


public class VehiculosasuNombre extends AppCompatActivity {

    TextView view, mListadoVehiculos;
    String correoUsuarioReg = "No cargó el usuario de la anterior actividad", datosxVehiculo="";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference vehiculos = db.collection("vehiculos");
    Integer numeroVehiculo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculosasu_nombre);
        correoUsuarioReg = getIntent().getStringExtra("correoUsuario");
        view = findViewById(R.id.tituloUsuarioTextView);
        String textoInicial = "Bienvenido: " + correoUsuarioReg;
        view.setText(textoInicial);
        mListadoVehiculos = findViewById(R.id.cajaResultadosTextView);

        //Los consultamos de FireBase
        db.collection("vehiculos")
                .whereEqualTo("propietario", correoUsuarioReg)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                numeroVehiculo++;
                                datosxVehiculo = datosxVehiculo + "\nNo. " + (numeroVehiculo) + "\nMarca: " + document.getString("marca") + "\nPlaca: " + document.getString("placa") + "\n";
                                Log.d(TAG, document.getId() + " => " + document.getData() + datosxVehiculo);
                                mListadoVehiculos.setText(datosxVehiculo);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}