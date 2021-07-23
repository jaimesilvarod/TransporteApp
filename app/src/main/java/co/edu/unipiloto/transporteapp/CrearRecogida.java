package co.edu.unipiloto.transporteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CrearRecogida extends AppCompatActivity {

    EditText mTipoDoc, mPeso, mLatOri, mLonOri, mLatDes, mLonDes;
    String correoUsuarioReg = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String idUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_recogida);
        correoUsuarioReg = getIntent().getStringExtra("correoUsuario");
        mTipoDoc = findViewById(R.id.editTextTipoDocumento);
        mPeso = findViewById(R.id.editTextPesoCarga);
        mLatOri = findViewById(R.id.editTextLatitudOrigen);
        mLonOri = findViewById(R.id.editTextLongitudOrigen);
        mLatDes = findViewById(R.id.editTextLatitudDestino);
        mLonDes = findViewById(R.id.editTextLongitudDestino);

    }

    public void programarServicio(View view){

        //Vamos a buscar los conductores y asignarlos a la posición en el arreglo
        db.collection("usuarios")
                .whereEqualTo("correo", correoUsuarioReg)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                idUsuario = document.getId();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        Map<String, Object> recogida = new HashMap<>();
        recogida.put("tipoCarga", mTipoDoc.getText().toString());
        recogida.put("Peso", mPeso.getText().toString());
        double latOrigen = Double.parseDouble(mLatOri.getText().toString());
        double lonOrigen = Double.parseDouble(mLonOri.getText().toString());
        double latDestino = Double.parseDouble(mLatDes.getText().toString());
        double lonDestino = Double.parseDouble(mLonDes.getText().toString());
        recogida.put("sitioInicio", new GeoPoint(latOrigen,lonOrigen));
        recogida.put("sitioDestino", new GeoPoint(latDestino,lonDestino));
        recogida.put("idConductor","sinConductor");
        recogida.put("fechaHora",new Timestamp(new Date()));
        recogida.put("idUsuario", idUsuario);

        db.collection("recogidas").document()
                .set(recogida)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(CrearRecogida.this, "Su solicitud de carga se creó con éxito", Toast.LENGTH_SHORT).show();
                        mTipoDoc.setText("");
                        mPeso.setText("");
                        mLatOri.setText("");
                        mLonOri.setText("");
                        mLatDes.setText("");
                        mLonDes.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }
}