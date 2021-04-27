package packet.mateo.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChampionDAO {

    public static final String C_TABLA = "champion";
    public static final String type = "type";
    public static final String format = "format";
    public static final String version = "version";
    public static final String data_key = "data_key";
    public static final String data_properties_version = "data_properties_version";
    public static final String _id = "_id";
    public static final String data_properties_key = "data_properties_key";
    public static final String data_properties_name = "data_properties_name";
    public static final String data_properties_title = "data_properties_title";
    public static final String data_properties_blurb = "data_properties_blurb";
    public static final String data_properties_info_attack = "data_properties_info_attack";
    public static final String data_properties_info_defense = "data_properties_info_defense";
    public static final String data_properties_info_magic = "data_properties_info_magic";
    public static final String data_properties_info_difficulty = "data_properties_info_difficulty";
    public static final String data_properties_image_full = "data_properties_image_full";
    public static final String data_properties_image_sprite = "data_properties_image_sprite";
    public static final String data_properties_image_group = "data_properties_image_group";
    public static final String data_properties_image_x = "data_properties_image_x";
    public static final String data_properties_image_y = "data_properties_image_y";
    public static final String data_properties_image_w = "data_properties_image_w";
    public static final String data_properties_image_h = "data_properties_image_h";
    public static final String data_properties_tags = "data_properties_tags";
    public static final String data_properties_partype = "data_properties_partype";
    public static final String data_properties_stats_hp = "data_properties_stats_hp";
    public static final String data_properties_stats_hpperlevel = "data_properties_stats_hpperlevel";
    public static final String data_properties_stats_mp = "data_properties_stats_mp";
    public static final String data_properties_stats_mpperlevel = "data_properties_stats_mpperlevel";
    public static final String data_properties_stats_movespeed = "data_properties_stats_movespeed";
    public static final String data_properties_stats_armor = "data_properties_stats_armor";
    public static final String data_properties_stats_armorperlevel = "data_properties_stats_armorperlevel";
    public static final String data_properties_stats_spellblock = "data_properties_stats_spellblock";
    public static final String data_properties_stats_spellblockperlevel = "data_properties_stats_spellblockperlevel";
    public static final String data_properties_stats_attackrange = "data_properties_stats_attackrange";
    public static final String data_properties_stats_hpregen = "data_properties_stats_hpregen";
    public static final String data_properties_stats_hpregenperlevel = "data_properties_stats_hpregenperlevel";
    public static final String data_properties_stats_mpregen = "data_properties_stats_mpregen";
    public static final String data_properties_stats_mpregenperlevel = "data_properties_stats_mpregenperlevel";
    public static final String data_properties_stats_crit = "data_properties_stats_crit";
    public static final String data_properties_stats_critperlevel = "data_properties_stats_critperlevel";
    public static final String data_properties_stats_attackdamage = "data_properties_stats_attackdamage";
    public static final String data_properties_stats_attackdamageperlevel = "data_properties_stats_attackdamageperlevel";
    public static final String data_properties_stats_attackspeedoffset = "data_properties_stats_attackspeedoffset";
    public static final String data_properties_stats_attackspeedperlevel = "data_properties_stats_attackspeedperlevel";


    private String[] columnas = new String[]{
            type,
            format,
            version,
            data_key,
            data_properties_version,
            _id,
            data_properties_key,
            data_properties_name,
            data_properties_title,
            data_properties_blurb,
            data_properties_info_attack,
            data_properties_info_defense,
            data_properties_info_magic,
            data_properties_info_difficulty,
            data_properties_image_full,
            data_properties_image_sprite,
            data_properties_image_group,
            data_properties_image_x,
            data_properties_image_y,
            data_properties_image_w,
            data_properties_image_h,
            data_properties_tags,
            data_properties_partype,
            data_properties_stats_hp,
            data_properties_stats_hpperlevel,
            data_properties_stats_mp,
            data_properties_stats_mpperlevel,
            data_properties_stats_movespeed,
            data_properties_stats_armor,
            data_properties_stats_armorperlevel,
            data_properties_stats_spellblock,
            data_properties_stats_spellblockperlevel,
            data_properties_stats_attackrange,
            data_properties_stats_hpregen,
            data_properties_stats_hpregenperlevel,
            data_properties_stats_mpregen,
            data_properties_stats_mpregenperlevel,
            data_properties_stats_crit,
            data_properties_stats_critperlevel,
            data_properties_stats_attackdamage,
            data_properties_stats_attackdamageperlevel,
            data_properties_stats_attackspeedoffset,
            data_properties_stats_attackspeedperlevel};




    private Context contexto;
    private BDSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    public ChampionDAO(Context context){
        this.contexto = context;
    }

    public ChampionDAO abrir(){
        dbHelper = new BDSQLiteHelper(contexto);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbHelper.close();
    }

    public Cursor getCursor() {
        Cursor c = db.query(true, C_TABLA, columnas,
                null, null, null, null, null, null);
        return c;
    }

    public Cursor getRegistroByKey(int id) {
        String condicion = data_properties_key + "="+id ;
        Cursor c = db.query(true, C_TABLA, columnas, condicion, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor getRegistro(String id) {
        String condicion = _id + "= '" + id +"'";
        Cursor c = db.query(true, C_TABLA, columnas, condicion, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
}
