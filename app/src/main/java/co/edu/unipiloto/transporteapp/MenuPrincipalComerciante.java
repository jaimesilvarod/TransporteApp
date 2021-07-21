package co.edu.unipiloto.transporteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MenuPrincipalComerciante extends AppCompatActivity {

    TextView view;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_comerciante);

        myToolbar = (Toolbar) findViewById(R.id.toolbarComerciante);
        setSupportActionBar(myToolbar);
        String correoUsuario = getIntent().getStringExtra("correoLogueado");
        view = findViewById(R.id.tituloLogueadoTextView);
        String textoInicial = view.getText().toString();
        textoInicial = "Bienvenido: " + correoUsuario;
        view.setText(textoInicial);


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