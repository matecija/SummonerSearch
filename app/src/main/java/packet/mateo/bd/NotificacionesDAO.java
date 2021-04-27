package packet.mateo.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import packet.mateo.Modelo.Notificacion;

public class NotificacionesDAO {

    public static final String C_TABLA = "notificaciones";
    public static final String C_COLUMNA_ID = "_id";
    public static final String C_COLUMNA_TITULO = "titulo";
    public static final String C_COLUMNA_CONTENIDO = "contenido";
    public static final String C_COLUMNA_TIPO = "tipo";
    public static final String C_COLUMNA_LEIDO = "leido";

    private Context contexto;
    private BDSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    private String[] columnas = new String[]{C_COLUMNA_ID,C_COLUMNA_TITULO,C_COLUMNA_CONTENIDO,C_COLUMNA_TIPO,C_COLUMNA_LEIDO};

    public NotificacionesDAO(Context context){
        this.contexto = context;
    }

    public NotificacionesDAO abrir(){
        dbHelper = new BDSQLiteHelper(contexto);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar(){
        dbHelper.close();
    }

    public Cursor getCursor(){
        Cursor c = db.query(true, C_TABLA, columnas,
                null,null,null,null,null,null);
        return c;
    }


    public Cursor getRegistro(long id) {
        String condicion = C_COLUMNA_ID + "=" + id;
        Cursor c = db.query( true, C_TABLA, columnas, condicion, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAllRegistro() {
        Cursor c = db.query( true, C_TABLA, columnas, null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public long insert(ContentValues reg){
        if (db == null)
            abrir();

        return db.insert(C_TABLA, null,reg);


    }

    public long delete(long id) {
        if (db == null)
            abrir();
        return db.delete(C_TABLA, "_id=" + id, null);
    }

    public long update(ContentValues reg) {
        long result = 0;
        if (db == null)
            abrir();
        if (reg.containsKey(C_COLUMNA_ID)) {
      long id = reg.getAsLong(C_COLUMNA_ID);
            reg.remove(C_COLUMNA_ID);

            result = db.update(C_TABLA, reg, "_id=" + id, null);
        }
        return result;
    }

}
