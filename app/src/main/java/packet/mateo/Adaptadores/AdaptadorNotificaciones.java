package packet.mateo.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import packet.mateo.Modelo.Notificacion;
import packet.mateo.R;
import packet.mateo.bd.NotificacionesDAO;

public class AdaptadorNotificaciones extends CursorAdapter {

    private NotificacionesDAO notificacionesDAO = null;

    public AdaptadorNotificaciones(Context context, Cursor cursor) {
        super(context, cursor, 0);
        notificacionesDAO = new NotificacionesDAO(context);
        notificacionesDAO.abrir();
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
// Obtenemos el inflador
        LayoutInflater inflater = LayoutInflater.from(context);
// Inflamos la vista que vamos a devolver
        View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
// Como hemos inflado simple_dropdown_item_1line solo tenemos un TextView que lo obtenemos
        TextView tv = (TextView) view;
// Obtenemos el indice de la columna
        int i = cursor.getColumnIndex(NotificacionesDAO.C_COLUMNA_TITULO);
// Asignamos el valor
        tv.setText(cursor.getString(i));
    }


}
