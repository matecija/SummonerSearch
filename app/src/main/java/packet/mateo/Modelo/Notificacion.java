package packet.mateo.Modelo;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import packet.mateo.R;

public class Notificacion{

    private Boolean leido;
    private String titulo,contenido;
    private int tipo;

    public Notificacion(){
    }

    public Notificacion(String titulo, String contenido, Drawable icono) {
        this.leido = false;
        this.titulo = titulo;
        this.contenido = contenido;
        this.tipo = tipo;
    }

    public Notificacion(String titulo, String contenido) {
        this.leido = false;
        this.titulo = titulo;
        this.contenido = contenido;
    }


    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
