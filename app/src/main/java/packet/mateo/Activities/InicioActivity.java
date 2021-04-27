package packet.mateo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.preference.PreferenceManager;

import com.squareup.picasso.Picasso;

import packet.mateo.Auxiliar.LocaleHelper;
import packet.mateo.R;

public class InicioActivity extends AppCompatActivity {

    private ImageView img;
    MediaPlayer mediaPlayer;
    private Boolean musica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.inicio_activity);

        img = findViewById( R.id.imagen);
        Picasso.with(this.getApplicationContext()).load(R.drawable.lol_icon).into(img);



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idiomaset = prefs.getString("idioma", "es");

        LocaleHelper.setLocale(this, idiomaset);




    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        musica = prefs.getBoolean("reproducirMusica",false);

        if (musica){
            mediaPlayer= MediaPlayer.create(this, R.raw.introsound);
            mediaPlayer.start();
            Log.i("asd","La musica ha sonado");
        }else
            Log.i("asd","La musica NO ha sonado");

    }


    public void irLogin(View view) {

     /*   ActivityOptionsCompat activityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this, img, "imageMain");

        Intent in = new Intent(this, LoginActivity.class);
        startActivity(in, activityOptionsCompat.toBundle());*/

     Intent intent = new Intent(this, MainActivity.class);
     intent.putExtra("IsLogin", false);
     startActivity(intent);


    }

}