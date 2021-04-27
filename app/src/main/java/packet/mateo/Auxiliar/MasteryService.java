package packet.mateo.Auxiliar;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import packet.mateo.Modelo.Champion;
import packet.mateo.Modelo.Mastery;
import packet.mateo.Modelo.Summoner;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MasteryService {

     @GET("/lol/champion-mastery/v4/champion-masteries/by-summoner/{encryptedSummonerId}")
      Call<ArrayList<Mastery>> getMastery(@Path("encryptedSummonerId") String encryptedSummonerId, @Query("api_key") String key);
}
