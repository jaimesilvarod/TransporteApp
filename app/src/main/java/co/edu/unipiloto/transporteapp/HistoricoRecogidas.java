package co.edu.unipiloto.transporteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

public class HistoricoRecogidas extends AppCompatActivity {

    ListView historicoServicios;
    String botonesServicio [];
    String correoUsuarioReg, idUsuario, documentoPorMapa;
    Integer posicionArreglo = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_recogidas);
        historicoServicios = findViewById(R.id.listViewHistoricoServicios);
        correoUsuarioReg = getIntent().getStringExtra("correoUsuario");

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

        //Los consultamos de FireBase
        db.collection("recogidas")
                .whereEqualTo(FieldPath.documentId(), idUsuario)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                documentoPorMapa += document.getData().get("fechaHora") + "\n";
                                documentoPorMapa += document.getData().get("sitioInicial") + "\n";
                                documentoPorMapa += document.getData().get("sitioDestino") + "\n";
                                botonesServicio[posicionArreglo] = documentoPorMapa;
                                posicionArreglo++;
                                Log.d(TAG, document.getId() + " => " + document.getData());

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        ArrayAdapter <String> adaptador = new ArrayAdapter<>(this, R.layout.listview_items_historico, botonesServicio);
        historicoServicios.setAdapter(adaptador);
        historicoServicios.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}