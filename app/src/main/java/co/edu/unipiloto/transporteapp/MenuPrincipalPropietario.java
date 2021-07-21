package co.edu.unipiloto.transporteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuPrincipalPropietario extends AppCompatActivity {

    TextView view, botonVehiculos;
    Toolbar myToolbar;
    String correoUsuario;
    Button mBtnIraRegistrarVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_propietario);
        mBtnIraRegistrarVehiculo = findViewById(R.id.btnIrRegistrarVehiculo);
        myToolbar = findViewById(R.id.toolbarComerciante);
        setSupportActionBar(myToolbar);
        view = findViewById(R.id.tituloLogueadoTextView);
        correoUsuario = getIntent().getStringExtra("correoLogueado");
        String textoInicial = "Bienvenido: " + correoUsuario;
        view.setText(textoInicial);
        botonVehiculos = findViewById(R.id.btnxVehiculo);

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
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("origen", "cerrarSesion");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mostrarVehiculos(View view) {
        Intent intent = new Intent(this, VehiculosasuNombre.class);
        intent.putExtra("correoUsuario", correoUsuario);
        startActivity(intent);
    }

    public void registrarVehiculos(View view) {
        Intent intent = new Intent(this, RegistrarVehiculo.class);
        intent.putExtra("correoUsuario", correoUsuario);
        startActivity(intent);
    }

    public void irAsignarConductorVehiculo(View view) {
        Intent intent = new Intent(this, AsignarConductorVehiculo.class);
        intent.putExtra("correoUsuario", correoUsuario);
        startActivity(intent);
    }
}