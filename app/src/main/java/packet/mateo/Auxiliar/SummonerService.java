package packet.mateo.Auxiliar;

import android.content.Context;

import packet.mateo.Modelo.Summoner;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface SummonerService {
    @GET("/lol/summoner/v4/summoners/by-name/{summonerName}")
    Call<Summoner> getSummoner(@Path("summonerName") String summonerName, @Query("api_key") String key);
}
