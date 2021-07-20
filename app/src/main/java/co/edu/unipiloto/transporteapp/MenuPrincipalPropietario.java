package co.edu.unipiloto.transporteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class MenuPrincipalPropietario extends AppCompatActivity {

    TextView view, botonVehiculos;
    int vehiculosxPropietario = 0;
    Toolbar myToolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onStart() {
        super.onStart();
        //Obtener correoUsuario
        String correoUsuario = "Mother";
        FeedReaderDbHelper insertar = new FeedReaderDbHelper(this);
        SQLiteDatabase BD2 = insertar.getWritableDatabase();
        Cursor c = BD2.rawQuery("SELECT correoUsuarioDB FROM t_usuarios WHERE id = 1", null);
        if (c.moveToFirst()) {
            correoUsuario = c.getString(0);
        }
        Toast.makeText(this, "Texto consultado" + correoUsuario , Toast.LENGTH_LONG).show();
        db.collection("vehiculos")
                .whereEqualTo("propietario", correoUsuario)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
        c.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_propietario);

        myToolbar = findViewById(R.id.toolbarComerciante);
        setSupportActionBar(myToolbar);
        view = findViewById(R.id.tituloLogueadoTextView);
        String textoInicial = view.getText().toString();
        textoInicial = "Bienvenido: ";
        view.setText(textoInicial);


        CollectionReference vehiculosRef = db.collection("vehiculos");

        //Mostrar cantidad de camiones del propietario
        //Consultar camiones cuyo dueño coincida con su correo ejemplo: jrico@gmail.com
        botonVehiculos = findViewById(R.id.btnxVehiculo);
        String nuevoTexto =  "Vehículos registrados: " + vehiculosxPropietario;
        botonVehiculos.setText(nuevoTexto);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cerrarSesion:
                Intent intent =  new Intent(this, MainActivity.class);
                intent.putExtra("origen","cerrarSesion");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}