package packet.mateo.Activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import packet.mateo.Auxiliar.Constantes;
import packet.mateo.Modelo.Notificacion;
import packet.mateo.R;
import packet.mateo.bd.NotificacionesDAO;

public class GestionFormularioActivity extends AppCompatActivity {

    private NotificacionesDAO notificacionesDAO;
    private Cursor cursor;
    private int modo;
    private long id;
    private EditText titulo, contenido;
    private CheckBox leido;
    private RadioGroup radioGroup;
    private RadioButton importante, no_importante, recordatorio;
    private Button guardar, cancelar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificaciones_formulario);


        Toolbar toolbar = findViewById(R.id.toolbar_gestion);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra == null) return;

        titulo = (EditText) findViewById(R.id.nombre);
        contenido = (EditText) findViewById(R.id.contenido);
        leido = (CheckBox) findViewById(R.id.leido);
        radioGroup = (RadioGroup) findViewById(R.id.rg_noti);

        importante = findViewById(R.id.tipo_importante);
        no_importante = findViewById(R.id.tipo_no_importante);
        recordatorio = findViewById(R.id.tipo_recordatorio);


        notificacionesDAO = new NotificacionesDAO(this);

        try {
            notificacionesDAO.abrir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /// Modos

        if (extra.containsKey(NotificacionesDAO.C_COLUMNA_ID)) {
            id = extra.getLong(NotificacionesDAO.C_COLUMNA_ID);
            consultar(id);
        }

        establecerModo(extra.getInt(Constantes.C_MODO));

        guardar = findViewById(R.id.boton_guardar);
        cancelar = findViewById(R.id.boton_cancelar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });


    }

    private void guardar() {

        ContentValues reg = new ContentValues();

        reg.put(NotificacionesDAO.C_COLUMNA_TITULO, titulo.getText().toString());
        reg.put(NotificacionesDAO.C_COLUMNA_CONTENIDO, contenido.getText().toString());

        if (leido.isChecked()){
            reg.put(NotificacionesDAO.C_COLUMNA_LEIDO, 1);
        }else{
            reg.put(NotificacionesDAO.C_COLUMNA_LEIDO, 0);
        }

        switch (radioGroup.getCheckedRadioButtonId()){

            case R.id.tipo_importante:
                reg.put(NotificacionesDAO.C_COLUMNA_TIPO, 1);
                break;
            case R.id.tipo_no_importante:
                reg.put(NotificacionesDAO.C_COLUMNA_TIPO, 2);
                break;
            case R.id.tipo_recordatorio:
                reg.put(NotificacionesDAO.C_COLUMNA_TIPO, 3);
                break;
        }

        if (modo == Constantes.C_EDITAR) {
            reg.put(NotificacionesDAO.C_COLUMNA_ID, id);
        }


        if (modo == Constantes.C_CREAR) {
            notificacionesDAO.insert(reg);
        } else if (modo == Constantes.C_EDITAR) {
            notificacionesDAO.update(reg);
        }




        setResult(RESULT_OK);
        finish();
    }


    private void borrar(final long id) {

        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);
        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(getResources().getString(R.string.noti_borrar_seguro));
        dialogEliminar.setMessage(getResources().getString(R.string.noti_borrar_seguro));
        dialogEliminar.setCancelable(false);
        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        notificacionesDAO.delete(id);
                        Toast.makeText(GestionFormularioActivity.this, R.string.noti_eliminar_confirmacion,
                                Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
        dialogEliminar.setNegativeButton(android.R.string.no, null);
        dialogEliminar.show();
    }


    private void cancelar() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    private void establecerModo(int m) {
        this.modo = m;
        if (modo == Constantes.C_VISUALIZAR) {
            this.setTitle(titulo.getText().toString());
            this.setEdicion(false);
        } else if (modo == Constantes.C_CREAR) {
            this.setTitle(R.string.crear_notificaciones_confirmar);
            this.setEdicion(true);
        } else if (modo == Constantes.C_EDITAR) {
            this.setTitle(R.string.notificaciones_editar);
            this.setEdicion(true);
        }
    }


    private void setEdicion(Boolean b) {
        titulo.setEnabled(b);
        contenido.setEnabled(b);
        leido.setEnabled(b);
        no_importante.setEnabled(b);
        importante.setEnabled(b);
        recordatorio.setEnabled(b);
    }


    private void consultar(long id) {
        cursor = notificacionesDAO.getRegistro(id);

        titulo.setText(cursor.getString(cursor.getColumnIndex(NotificacionesDAO.C_COLUMNA_TITULO)));
        contenido.setText(cursor.getString(cursor.getColumnIndex(NotificacionesDAO.C_COLUMNA_CONTENIDO)));

        if (cursor.getInt(cursor.getColumnIndex(NotificacionesDAO.C_COLUMNA_LEIDO)) == 1){
            leido.setChecked(true);
        }else
            leido.setChecked(false);


        switch (cursor.getInt(cursor.getColumnIndex(NotificacionesDAO.C_COLUMNA_TIPO))){

            case 1:
                importante.setChecked(true);
                break;
            case 2:
                no_importante.setChecked(true);
                break;
            case 3:
                recordatorio.setChecked(true);
                break;
        }


    }


    // Menu ----

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gestion_notificaciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar_notificacion:
                establecerModo(Constantes.C_EDITAR);
                return true;

            case R.id.borrar_notificacion:
                borrar(id);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
