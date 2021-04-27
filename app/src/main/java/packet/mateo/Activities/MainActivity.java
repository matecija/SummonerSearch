package packet.mateo.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import packet.mateo.Adaptadores.AdaptadorChampionsMastery;
import packet.mateo.Auxiliar.Constantes;
import packet.mateo.Auxiliar.MasteryService;
import packet.mateo.Auxiliar.SummonerService;
import packet.mateo.Modelo.Champion;
import packet.mateo.Modelo.Mastery;
import packet.mateo.Modelo.Summoner;
import packet.mateo.R;
import packet.mateo.bd.NotificacionesDAO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private SearchView searchView, searchPrimero;
    private ImageView imageMain;
    private Summoner summonerGuardado;
    private TextView summonerName, summonerLevel, textoBuscar;
    private SummonerService summonerService;
    private MasteryService masteryService;
    private ListView campeones_listview;

    private View menu_login, menu_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.layout_main_menu_deslizable);

        // Canal de notificaciones (esto va aparte)
        createNotificationChannel();




        textoBuscar = findViewById(R.id.textoprebusqueda);

        campeones_listview = findViewById(R.id.lista_campeones_mastery);
        imageMain = findViewById(R.id.imagenMain);
        summonerName = findViewById(R.id.txtNombreInvocador);
        summonerLevel = findViewById(R.id.txtNivelInvocador);
        searchView = findViewById(R.id.buscarsummoner);
        searchPrimero = findViewById(R.id.buscarsummoner2);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loaddata(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchPrimero.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loaddata(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // estado sin buscar -- interfaz
        // debe aparecer la barra de busqueda al medio y un textview de busqueda.
        // Al realizar una busqueda se activará la interfaz.

        imageMain.setVisibility(View.GONE);
        summonerName.setVisibility(View.GONE);
        summonerLevel.setVisibility(View.GONE);
        searchView.setVisibility(View.GONE);
        campeones_listview.setVisibility(View.GONE);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        setNavigationViewListener();

        //// Notificaciones


        NotificacionesDAO notificacionesDAO = new NotificacionesDAO(this);
        notificacionesDAO.abrir();
        Cursor c = notificacionesDAO.getCursor();
        startManagingCursor(c);

        c = notificacionesDAO.getAllRegistro();

        if (c.moveToFirst()) {

            while (c.isAfterLast() == false) {

                if (c.getInt(c.getColumnIndex(NotificacionesDAO.C_COLUMNA_LEIDO)) != 1) {

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "NotificacionesCanal")
                            .setSmallIcon(R.drawable.lol_icon)
                            .setContentTitle(c.getString(c.getColumnIndex(NotificacionesDAO.C_COLUMNA_TITULO)))
                            .setContentText(c.getString(c.getColumnIndex(NotificacionesDAO.C_COLUMNA_CONTENIDO)))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                    notificationManagerCompat.notify(c.getPosition(), builder.build());

                }
                c.moveToNext();
            }
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        Menu menu = navigationView.getMenu();

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra != null && extra.getBoolean("IsLogin")){
            menu.findItem(R.id.action_login).setVisible(false);
        }else if (extra != null && !extra.getBoolean("IsLogin")){
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.findItem(R.id.action_draft).setVisible(false);

        }


    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notificationcanal";
            String description = "descripcion notificationcanal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NotificacionesCanal", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void loaddata(String data) {


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        summonerService = retrofit.create(SummonerService.class);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);


        Call<Summoner> call = summonerService.getSummoner(data, prefs.getString("apikey", "Error con api key"));

        call.enqueue(new Callback<Summoner>() {
            @Override
            public void onResponse(Call<Summoner> call, Response<Summoner> response) {
                Summoner summoner = response.body();

                try {
                    if (summoner != null) {
                        textoBuscar.setVisibility(View.GONE);
                        searchPrimero.setVisibility(View.GONE);

                        imageMain.setVisibility(View.VISIBLE);
                        summonerName.setVisibility(View.VISIBLE);
                        summonerLevel.setVisibility(View.VISIBLE);
                        searchView.setVisibility(View.VISIBLE);

                        summonerGuardado = summoner;
                        summonerName.setText(summoner.getName());
                        summonerLevel.setText("Level " + String.valueOf(summoner.getSummonerLevel()));
                        Picasso.with(MainActivity.this).load("https://opgg-static.akamaized.net/images/profile_icons/profileIcon" + summoner.getProfileIconId() + ".jpg").into(imageMain);


                        //// Get champion mastery

                        llamarmastery();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "No se han podido cargar los datos del invocador", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Summoner> call, Throwable t) {

                Toast.makeText(MainActivity.this, "No se ha podido cargar el invocador.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void llamarmastery() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        masteryService = retrofit.create(MasteryService.class);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Call<ArrayList<Mastery>> call_mastery = masteryService.getMastery(summonerGuardado.getId(), prefs.getString("apikey", "Error con api key"));

        call_mastery.enqueue(new Callback<ArrayList<Mastery>>() {
            @Override
            public void onResponse(Call<ArrayList<Mastery>> call, Response<ArrayList<Mastery>> response) {
                ArrayList<Mastery> lista_campeones = response.body();

                try {
                    if (lista_campeones != null) {

                        campeones_listview.setVisibility(View.VISIBLE);

                        AdaptadorChampionsMastery adaptadorChampionsMastery = new AdaptadorChampionsMastery(MainActivity.this, lista_campeones);
                        campeones_listview.setAdapter(adaptadorChampionsMastery);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "No se ha podido cargar los datos de Puntos de maestria del usuario :( ", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<ArrayList<Mastery>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No se ha podido cargar los datos de Puntos de maestria del usuario :( ", Toast.LENGTH_SHORT).show();
            }
        });


    }


    // Menu ----------
    // Listener
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Onclick do, para menu deslizable
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings: {
                Intent intent_settings = new Intent(this, SettingsActivity.class);
                startActivity(intent_settings);
                break;
            }

            case R.id.notificaciones: {
                Intent intent_notificaciones = new Intent(this, NotificacionesActivity.class);
                startActivity(intent_notificaciones);
                break;
            }

            case R.id.naviagion_campeones: {
                Intent intent_champs = new Intent(this, ChampionListActivity.class);
                startActivity(intent_champs);
                break;
            }
            case R.id.tienda: {
                Intent mapaintent = new Intent(this, MapaActivity.class);
                startActivity(mapaintent);
                break;
            }


            case R.id.action_login : {
                Intent intent_login = new Intent(this, LoginActivity.class);
                finish();
                startActivity(intent_login);
                break;
            }
            case R.id.action_logout : {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("IsLogin", false);
                finish();

                startActivity(intent);

                break;
            }

            case R.id.action_draft : {

                Intent intent_draft = new Intent(getApplicationContext(), DraftActivity.class);

                startActivity(intent_draft);

                break;
            }


        }
        //close
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Fin cosas menu ------

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}