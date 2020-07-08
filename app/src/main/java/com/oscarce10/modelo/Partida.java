package com.oscarce10.modelo;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.oscarce10.gusano.Juego;
import com.oscarce10.gusano.R;

public class Partida {
    private int score;
    private int record;
    private Tablero obTablero;
    private Juego juego;
    private LinearLayout tableroCont;
    int [][] tablero;

    public Partida(int score, int record, Tablero tablero, Juego juego) {
        this.score = score;
        this.record = record;
        this.obTablero = tablero;
        this.juego = juego;
    }

    public Partida(Tablero tablero, Juego juego) {
        this.score = 0;
        this.record = 0;
        this.obTablero = tablero;
        this.juego = juego;
    }

    public Partida(Juego juego) {
        this.score = 0;
        this.record = 0;
        this.juego = juego;
        this.obTablero = new Tablero();
    }

    public void iniciarPartida(){
        // Score
        TextView score = this.juego.findViewById(R.id.score);
        score.setText(String.valueOf(this.score));
        // Record
        TextView record = this.juego.findViewById(R.id.record);
        record.setText(String.valueOf(this.record));
        this.obTablero.crearGusano();
        this.obTablero.agregarFruta();
        mostrarTablero();
    }

    public void mostrarTablero(){
        // Crear y mostrar tablero
        this.tableroCont = (LinearLayout) this.juego.findViewById(R.id.board);
        this.tablero = this.obTablero.getTablero();
        for (int i = 0; i < tablero.length; i++){
            LinearLayout aux = new LinearLayout(this.juego);
            for (int j = 0; j < tablero[i].length; j++){
                ConstraintLayout campo = new ConstraintLayout(this.juego);
                ImageView cuadro = new ImageView(this.juego);
                cuadro.setBackgroundResource(R.drawable.ic_rec);
                campo.addView(cuadro);
                if (tablero[i][j] == Tablero.CABEZA){
                    ImageView cab = new ImageView(this.juego);
                    switch (obTablero.getGusano().getGusano().get(0).getDireccion()){
                        case Gusano.DERECHA:
                            cab.setBackgroundResource(R.drawable.ic_headgameright);
                            break;

                        case Gusano.ABAJO:
                            cab.setBackgroundResource(R.drawable.ic_headgamedown);
                            break;

                        case Gusano.IZQUIERDA:
                            cab.setBackgroundResource(R.drawable.ic_headgameleft);
                            break;

                        case Gusano.ARRIBA:
                            cab.setBackgroundResource(R.drawable.ic_headgameup);
                            break;
                    }
                    campo.addView(cab);
                }else if(tablero[i][j] == Tablero.CUERPO){
                    ImageView cuerpo = new ImageView(this.juego);
                    cuerpo.setBackgroundResource(R.drawable.ic_bodygame);
                    campo.addView(cuerpo);
                } else if (tablero[i][j] == Tablero.FRUTA){
                    ImageView fruta = new ImageView(this.juego);
                    fruta.setBackgroundResource(R.drawable.ic_apple);
                    campo.addView(fruta);
                }
                aux.addView(campo);
            }
            tableroCont.addView(aux);
        }
    }
}
