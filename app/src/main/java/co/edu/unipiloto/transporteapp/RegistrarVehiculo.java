package co.edu.unipiloto.transporteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RegistrarVehiculo extends AppCompatActivity {

    String correoUsuarioReg = "";
    TextView view;
    EditText mMarca, mPlaca;
    Button mRegVeh;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_vehiculo);
        mMarca = findViewById(R.id.editTextMarca);
        mPlaca = findViewById(R.id.editTextPlaca);
        mRegVeh = findViewById(R.id.btnRegistrarVehiculo);
        correoUsuarioReg = getIntent().getStringExtra("correoUsuario");
        view = findViewById(R.id.tituloUsuarioRegVehiTextView);
        String textoInicial = "Bienvenido: " + correoUsuarioReg;
        view.setText(textoInicial);
    }

    public void registrarVehiculo(View view){
        Map<String, Object> vehiculo = new HashMap<>();
        vehiculo.put("marca", mMarca.getText().toString());
        vehiculo.put("placa", mPlaca.getText().toString());
        vehiculo.put("propietario", correoUsuarioReg);

        db.collection("vehiculos").document()
                .set(vehiculo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(RegistrarVehiculo.this, "El vehículo se registró con éxito", Toast.LENGTH_SHORT).show();
                        mMarca.setText("");
                        mPlaca.setText("");
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