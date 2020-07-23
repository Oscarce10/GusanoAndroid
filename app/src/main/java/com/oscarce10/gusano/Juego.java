package com.oscarce10.gusano;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oscarce10.controlador.Controlador;
import com.oscarce10.modelo.Partida;

@RequiresApi(api = Build.VERSION_CODES.O)
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

    public void perder(){
        finish();
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // Start without a delay
// Each element then alternates between vibrate, sleep, vibrate, sleep...
        long[] pattern = {200, 100, 200, 100, 200};

// The '-1' here means to vibrate once, as '-1' is out of bounds in the pattern array
        v.vibrate(pattern, -1);
    }

}
