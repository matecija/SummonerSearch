package packet.mateo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import packet.mateo.Auxiliar.Constantes;
import packet.mateo.Fragments.Notificaciones_Fragment;
import packet.mateo.Modelo.Notificacion;
import packet.mateo.R;
import packet.mateo.bd.NotificacionesDAO;

public class NotificacionesActivity extends AppCompatActivity {

    private Notificaciones_Fragment fragment_noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        fragment_noti = new Notificaciones_Fragment();
       getSupportFragmentManager().beginTransaction().add(R.id.cl_notificacionesActivity,fragment_noti).commit();

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

    }
}