package packet.mateo.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import packet.mateo.Activities.MainActivity;
import packet.mateo.Modelo.Champion;
import packet.mateo.R;
import packet.mateo.bd.ChampionDAO;

public class AdaptadorChampions extends CursorAdapter {

    private ChampionDAO championDAO;

    public AdaptadorChampions(Context context, Cursor c) {
        super(context, c);
        championDAO = new ChampionDAO(context);
        championDAO.abrir();

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_lista_campeones, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView iconochamp = view.findViewById(R.id.campeon_icono);
        TextView textochamp = view.findViewById(R.id.campeon_nombre);

        // Asignar datos del campeon

        // Las ids no tienen los mismos caracteres que los nombres, Wukong = MonkeyKing, Lee Sin = Leesin, Kha'Zix = Khazix
        String name = "";

        name = cursor.getString(cursor.getColumnIndex(ChampionDAO._id));

        switch (name){
            case "FiddleSticks":
                name = "Fiddlesticks";
                break;
        }

        Picasso.with(context.getApplicationContext()).load("https://ddragon.leagueoflegends.com/cdn/11.3.1/img/champion/" +
                name
                + ".png").into(iconochamp);

        textochamp.setText(cursor.getString(cursor.getColumnIndex(ChampionDAO.data_properties_name)));


    }
}
