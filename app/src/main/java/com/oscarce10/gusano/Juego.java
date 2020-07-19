package com.oscarce10.gusano;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.VibrationEffect;
import android.os.Vibrator;
import com.oscarce10.controlador.Controlador;
import com.oscarce10.modelo.Partida;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Juego extends AppCompatActivity {
    private Partida partida;
    private PartidaVTA partidaVTA;
    private Controlador controlTouch;
    private SharedPreferences record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        record = getSharedPreferences("PrefencesRecord", Context.MODE_PRIVATE);
        this.partida = new Partida();
        this.partidaVTA = new PartidaVTA(this, this.partida.getRecord());
        this.controlTouch = new Controlador(this);
        this.partida.addObserver(this.partidaVTA);
        this.partida.iniciarPartida();
    }

    public  SharedPreferences getPreferences(){
        return record;
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
