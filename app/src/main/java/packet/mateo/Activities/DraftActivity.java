package packet.mateo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import packet.mateo.R;
import packet.mateo.bd.ChampionDAO;

public class DraftActivity extends AppCompatActivity {

    private ImageView top_blue, top_red, jungle_blue, jungle_red, mid_blue, mid_red, adc_red, adc_blue, sup_blue, sup_red;
    private Button calcular;
    private TextView tb, tr, jb,jr,mb,mr,ar,ab,sb,sr;
    private ChampionDAO championDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        championDAO = new ChampionDAO(getApplicationContext());

        tb = findViewById(R.id.texto_top_blue);
        tr = findViewById(R.id.texto_top_red);
        jb = findViewById(R.id.texto_jungla_blue);
        jr = findViewById(R.id.texto_jungla_red);
        mb = findViewById(R.id.texto_mid_blue);
        mr = findViewById(R.id.texto_mid_red);
        ab = findViewById(R.id.texto_adc_blue);
        ar = findViewById(R.id.texto_adc_red);
        sb = findViewById(R.id.texto_sup_blue);
        sr = findViewById(R.id.texto_sup_red);


        top_blue = findViewById(R.id.top_blue);
        top_red = findViewById(R.id.top_red);
        jungle_blue = findViewById(R.id.jungle_blue);
        jungle_red = findViewById(R.id.jungle_red);
        mid_blue = findViewById(R.id.mid_blue);
        mid_red = findViewById(R.id.mid_red);
        adc_blue = findViewById(R.id.adc_blue);
        adc_red = findViewById(R.id.adc_red);
        sup_blue = findViewById(R.id.sup_blue);
        sup_red = findViewById(R.id.sup_red);

        setPositionLogic(top_blue);
        setPositionLogic(top_red);
        setPositionLogic(jungle_blue);
        setPositionLogic(jungle_red);
        setPositionLogic(mid_blue);
        setPositionLogic(mid_red);
        setPositionLogic(adc_blue);
        setPositionLogic(adc_red);
        setPositionLogic(sup_blue);
        setPositionLogic(sup_red);



        calcular = findViewById(R.id.botoncalcular);
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( !tb.getText().toString().isEmpty() && !tr.getText().toString().isEmpty() &&
                        !jb.getText().toString().isEmpty() && !jr.getText().toString().isEmpty() &&
                        !mb.getText().toString().isEmpty() && !mr.getText().toString().isEmpty() &&
                        !ab.getText().toString().isEmpty() && !ar.getText().toString().isEmpty() &&
                        !sb.getText().toString().isEmpty() && !sr.getText().toString().isEmpty() ){

                    try {

                        new CreateDataDialog().execute();

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(DraftActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Seleccione a todos los campeones primero.");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


            }
        });


    }


    void setPositionLogic(final ImageView item){

        item.setOnClickListener( new AdapterView.OnClickListener(){
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DraftActivity.this);
                builder.setTitle(getString(R.string.selectChamp));


                championDAO.abrir();
                Cursor cursor = championDAO.getCursor();
                startManagingCursor(cursor);

                final ArrayList<String> listado = new ArrayList<String>();

                for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                    listado.add(cursor.getString(cursor.getColumnIndex(ChampionDAO._id)));
                }

                builder.setItems( listado.toArray(new String[listado.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = listado.get(which);
                        switch (name){
                            case "FiddleSticks":
                                name = "Fiddlesticks";
                                break;
                        }

                        Picasso.with(getApplicationContext()).load("https://ddragon.leagueoflegends.com/cdn/11.3.1/img/champion/" +
                                name
                                + ".png").into(item);

                        switch (v.getId()){
                            case R.id.top_blue : tb.setText(name);break;
                            case R.id.top_red : tr.setText(name);break;
                            case R.id.jungle_blue : jb.setText(name);break;
                            case R.id.jungle_red: jr.setText(name);break;
                            case R.id.mid_blue : mb.setText(name);break;
                            case R.id.mid_red : mr.setText(name);break;
                            case R.id.adc_blue : ab.setText(name);break;
                            case R.id.adc_red : ar.setText(name);break;
                            case R.id.sup_blue : sb.setText(name);break;
                            case R.id.sup_red : sr.setText(name);break;

                        }




                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    String sacarDatosOnline (String champ1 , String champ2 , String position) throws IOException{

        //Hay que hacer una conversión de Nombre a ID para hacer la consulta a la página, la url compara el champ 1 nombre con el champ 2 id

        championDAO.abrir();
        Cursor cursor = championDAO.getCursor();
        startManagingCursor(cursor);

        cursor = championDAO.getRegistro(champ2);
        Log.d("key", cursor.toString());

        int idchamp2 = cursor.getInt(cursor.getColumnIndex(ChampionDAO.data_properties_key));

        //Sacar datos de Winrate de internet
        String url = new String("https://euw.op.gg/champion/"+champ1+"/statistics/"+position+"/matchup?targetChampionId="+idchamp2);

        Document document = Jsoup.connect(url).get();
        Elements elemtosWR = document.select("div.champion-matchup-champion__winrate");

        if (elemtosWR.size() == 0){

            String fin =  champ1+ ": -Sin datos- vs "+champ2+": -Sin datos-";
            Log.d("matchup",fin);
            return fin;
        }else{
            String champ1WR = elemtosWR.first().text();
            String champ2WR = elemtosWR.last().text();
            String fin = champ1+ ": "+champ1WR+" vs "+champ2+": "+champ2WR;
            Log.d("matchup",fin);
            return fin;
        }





    }


    class CreateDataDialog extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            String message ="";

            try {
                message += sacarDatosOnline(tb.getText().toString(), tr.getText().toString(), "top") + "\n";
                message += sacarDatosOnline(jb.getText().toString(), jr.getText().toString(), "jungle") + "\n";
                message += sacarDatosOnline(mb.getText().toString(), mr.getText().toString(), "mid") + "\n";
                message += sacarDatosOnline(ab.getText().toString(), ar.getText().toString(), "bot") + "\n";
                message += sacarDatosOnline(sb.getText().toString(), sr.getText().toString(), "support") + "\n";
            } catch (IOException e) {
                e.printStackTrace();
            }


            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            AlertDialog.Builder builder = new AlertDialog.Builder(DraftActivity.this);
            builder.setTitle("Datos");
            builder.setMessage(s);

            AlertDialog dialog = builder.create();
            dialog.show();


        }
    }


}

