package com.oscarce10.gusano;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.oscarce10.controlador.Controlador;
import com.oscarce10.modelo.Partida;

public class Juego extends AppCompatActivity {
    private Partida partida;
    private PartidaVTA partidaVTA;
    private Controlador controlTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        this.partida = new Partida();
        this.partidaVTA = new PartidaVTA(this, this.partida.getRecord());
        this.controlTouch = new Controlador(this);
        this.partida.addObserver(this.partidaVTA);
        this.partida.iniciarPartida();
    }

    public void cambiarDireccion(int direccion){
        this.partida.cambiarDireccion(direccion);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
