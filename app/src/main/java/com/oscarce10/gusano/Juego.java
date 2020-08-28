package com.oscarce10.gusano;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oscarce10.controlador.Controlador;
import com.oscarce10.modelo.Partida;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Juego extends AppCompatActivity {
    private Partida partida;
    private PartidaVTA partidaVTA;
    private SharedPreferences record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        record = getSharedPreferences("PrefencesRecord", Context.MODE_PRIVATE);
        this.partida = new Partida();
        this.partidaVTA = new PartidaVTA(this, this.partida.getRecord());
        Controlador controlTouch = new Controlador(this);
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
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setCancelable(false);
        dialogo.setView(R.layout.restart);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog titulo = dialogo.create();
                titulo.show();
                TextView score = titulo.findViewById(R.id.txtScore);
                TextView time = titulo.findViewById(R.id.txtTime);
                score.setText("" + partida.getScore());
                time.setText(partida.getTiempo().getHoras() + " : " + partida.getTiempo().getMinutos() + " : " + partida.getTiempo().getSegundos());
                titulo.findViewById(R.id.btnRetry).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        startActivity(getIntent());
                    }
                });
            }
        });

        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // Start without a delay
        // Each element then alternates between vibrate, sleep, vibrate, sleep...
        long[] pattern = {200, 100, 200, 100, 200};

        // The '-1' here means to vibrate once, as '-1' is out of bounds in the pattern array
        v.vibrate(pattern, -1);
    }

}
