package packet.mateo.Adaptadores;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import packet.mateo.Activities.MainActivity;
import packet.mateo.Modelo.Champion;
import packet.mateo.Modelo.Mastery;
import packet.mateo.R;
import packet.mateo.bd.ChampionDAO;

public class AdaptadorChampionsMastery extends ArrayAdapter<Mastery> {

    private ArrayList<Mastery> masteries;
    private Activity context;
    private ChampionDAO championDAO = null;

    public AdaptadorChampionsMastery(Activity context, ArrayList<Mastery> champions){
        super(context, R.layout.activity_main, champions);
        this.context = context;
        this.masteries = champions;
        championDAO = new ChampionDAO(context);
        championDAO.abrir();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(R.layout.item_campeones_mastery,null);
        // Sacar el campeon



        Cursor cursor = championDAO.getCursor();
        ((MainActivity)context).startManagingCursor(cursor);


        cursor = championDAO.getRegistroByKey( masteries.get(position).getChampionId() );



        // defs
        ImageView iconoChmapion = item.findViewById(R.id.icon_champion);
        ImageView iconoMastery = item.findViewById(R.id.icon_mastery_level);

        TextView nombreChampion = item.findViewById(R.id.nombre_campeon);
        TextView masteryPoints = item.findViewById(R.id.mastery_points);

        // Asignar datos del campeon



        String name = "";
        name = cursor.getString(cursor.getColumnIndex(ChampionDAO._id));

        switch (name){
            case "FiddleSticks":
                name = "Fiddlesticks";
                break;
        }

        Picasso.with(context.getApplicationContext()).
                load("https://ddragon.leagueoflegends.com/cdn/11.3.1/img/champion/"+
               name
                +".png").into(iconoChmapion);

        switch (masteries.get(position).getChampionLevel()){

            case 1:
                Picasso.with(context.getApplicationContext()).load(R.drawable.mastery_1).into(iconoMastery);
                break;
            case 2:
                Picasso.with(context.getApplicationContext()).load(R.drawable.mastery_2).into(iconoMastery);
                break;
            case 3:Picasso.with(context.getApplicationContext()).load(R.drawable.mastery_3).into(iconoMastery);
                break;
            case 4:Picasso.with(context.getApplicationContext()).load(R.drawable.mastery_4).into(iconoMastery);
                break;
            case 5:Picasso.with(context.getApplicationContext()).load(R.drawable.mastery_5).into(iconoMastery);
                break;
            case 6:Picasso.with(context.getApplicationContext()).load(R.drawable.mastery_6).into(iconoMastery);
                break;
            case 7:Picasso.with(context.getApplicationContext()).load(R.drawable.mastery_7).into(iconoMastery);
                break;

        }


        DecimalFormat df = new DecimalFormat("#,###");
        nombreChampion.setText(  cursor.getString(  cursor.getColumnIndex(ChampionDAO.data_properties_name)) );
        masteryPoints.setText( context.getResources().getString(R.string.masterypoints) +": "+ String.valueOf( df.format( masteries.get(position).getChampionPoints())) );

        return item;
    }

}
