package com.oscarce10.gusano;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.oscarce10.modelo.Gusano;
import com.oscarce10.modelo.Partida;

public class Juego extends AppCompatActivity {
    Gusano G = new Gusano();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        Partida partida = new Partida(this);
        partida.iniciarPartida();
    }
}
