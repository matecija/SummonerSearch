package packet.mateo.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import packet.mateo.Adaptadores.AdaptadorChampions;
import packet.mateo.Auxiliar.Constantes;
import packet.mateo.Modelo.Champion;
import packet.mateo.R;
import packet.mateo.bd.ChampionDAO;
import packet.mateo.bd.NotificacionesDAO;

public class ChampionListActivity extends AppCompatActivity {

    private ListView lista;
    private  ChampionDAO championDAO;
    private Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista_campeones);


        championDAO = new ChampionDAO(this);
        championDAO.abrir();
        cursor = championDAO.getCursor();
        startManagingCursor(cursor);


        lista = findViewById(R.id.lista_campeones_activity_listview);
        lista.setAdapter(new AdaptadorChampions(this,cursor));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idstr = cursor.getString(cursor.getColumnIndex(ChampionDAO._id));
                Intent i = new Intent(ChampionListActivity.this, ChampionActivity.class);
                i.putExtra(ChampionDAO._id, idstr);
                startActivity(i);
            }
        });


    }
}
