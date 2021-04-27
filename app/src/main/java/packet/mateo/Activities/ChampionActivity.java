package packet.mateo.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import packet.mateo.R;
import packet.mateo.bd.ChampionDAO;
import packet.mateo.bd.NotificacionesDAO;

public class ChampionActivity extends AppCompatActivity {

    private Cursor cursor;

    private TextView nombre, titulo, lore, info_ataque, info_magia, info_dif, info_defensa;
    private ImageView imagen;
    private ChampionDAO championDAO;

    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.champion_activity_layout);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();


        nombre = findViewById(R.id.ca_nombre_campeon);
        titulo = findViewById(R.id.ca_campeon_titulo);
        lore = findViewById(R.id.ca_campeon_lore);
        info_ataque = findViewById(R.id.ca_info_attack);
        info_magia = findViewById(R.id.ca_info_magic);
        info_defensa = findViewById(R.id.ca_info_defense);
        info_dif = findViewById(R.id.ca_info_difficulty);

        imagen = findViewById(R.id.ca_imagen);


        championDAO = new ChampionDAO(this);

        try {
            championDAO.abrir();

        } catch (Exception e) {
            e.printStackTrace();
        }

        id = extra.getString(ChampionDAO._id);
        consultar(id);


    }


    // Consultar y mostrar la info

    private void consultar(String id) {
        cursor = championDAO.getRegistro(id);

        nombre.setText(cursor.getString(cursor.getColumnIndex(ChampionDAO.data_properties_name)));
        titulo.setText(cursor.getString(cursor.getColumnIndex(ChampionDAO.data_properties_title)));
        info_ataque.setText("Ataque :" + cursor.getInt(cursor.getColumnIndex(ChampionDAO.data_properties_info_attack)));
        info_defensa.setText("Defensa :" + cursor.getInt(cursor.getColumnIndex(ChampionDAO.data_properties_info_defense)));
        info_magia.setText("Magia :" + cursor.getInt(cursor.getColumnIndex(ChampionDAO.data_properties_info_magic)));
        info_dif.setText("Dificultad :" + cursor.getInt(cursor.getColumnIndex(ChampionDAO.data_properties_info_difficulty)));

        lore.setText(cursor.getString(cursor.getColumnIndex(ChampionDAO.data_properties_blurb)));

        Picasso.with(this.getApplicationContext()).load("https://ddragon.leagueoflegends.com/cdn/img/champion/loading/" +
                id
                + "_0.jpg").into(imagen);

    }


}
