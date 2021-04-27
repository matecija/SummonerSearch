package packet.mateo.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import packet.mateo.Activities.GestionFormularioActivity;
import packet.mateo.Adaptadores.AdaptadorNotificaciones;
import packet.mateo.Auxiliar.Constantes;
import packet.mateo.R;
import packet.mateo.bd.NotificacionesDAO;


public class Notificaciones_Fragment extends Fragment {

    private ListView lista;
    private NotificacionesDAO notificacionesDAO;
    private AdaptadorNotificaciones adaptadorNotificaciones;
    private Cursor c;
    private TextView tv;


    public Notificaciones_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.layout_lista_notificaciones, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_notificaciones, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (item.getItemId()) {
            case R.id.menu_crear_notificacion:
                i = new Intent(this.getActivity(), GestionFormularioActivity.class);
                i.putExtra(Constantes.C_MODO, Constantes.C_CREAR);
                startActivityForResult(i, Constantes.C_CREAR);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lista = getView().findViewById(R.id.listado_notifs);

        notificacionesDAO = new NotificacionesDAO(this.getActivity());
        notificacionesDAO.abrir();
        c = notificacionesDAO.getCursor();
        getActivity().startManagingCursor(c);

        adaptadorNotificaciones = new AdaptadorNotificaciones(this.getActivity(), c);

        lista.setAdapter(adaptadorNotificaciones);


        try {
            notificacionesDAO.abrir();
            c = notificacionesDAO.getCursor();
            this.getActivity().startManagingCursor(c);
            adaptadorNotificaciones = new AdaptadorNotificaciones(this.getContext(), c);

            lista.setAdapter(adaptadorNotificaciones);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getActivity(), GestionFormularioActivity.class);
                    i.putExtra(Constantes.C_MODO, Constantes.C_VISUALIZAR);
                    i.putExtra(NotificacionesDAO.C_COLUMNA_ID, id);
                    startActivityForResult(i, Constantes.C_VISUALIZAR);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void recargar_lista() {
        NotificacionesDAO notificacionesDAO = new NotificacionesDAO(getContext());
        notificacionesDAO.abrir();
        AdaptadorNotificaciones adaptadorNotificaciones = new AdaptadorNotificaciones(this.getActivity(), notificacionesDAO.getCursor());
        lista.setAdapter(adaptadorNotificaciones);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case Constantes.C_CREAR:
                if (resultCode == Activity.RESULT_OK)
                    recargar_lista();
                break;
            case Constantes.C_VISUALIZAR:
                if (resultCode == Activity.RESULT_OK)
                    recargar_lista();
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}















