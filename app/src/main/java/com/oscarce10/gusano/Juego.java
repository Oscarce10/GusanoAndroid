package com.oscarce10.gusano;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.oscarce10.modelo.Partida;

public class Juego extends AppCompatActivity {
    private Partida partida;
    private PartidaVTA partidaVTA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        this.partida = new Partida();
        this.partidaVTA = new PartidaVTA(this, this.partida.getRecord());
        this.partida.addObserver(this.partidaVTA);
       this.partida.iniciarPartida();
    }

    public void nuevaPartida(int rows, int columns){

    }


//    Partida partida = new Partida(this);
//    Gusano G = new Gusano();
//    @SuppressLint("SetTextI18n")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.game);
//        //partida.start();
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        GridLayout tableroCont = this.findViewById(R.id.board);
//        partida.iniciarPartida();
    }
