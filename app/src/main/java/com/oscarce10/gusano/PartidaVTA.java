package com.oscarce10.gusano;

import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.oscarce10.modelo.Coordenada;
import com.oscarce10.modelo.Gusano;
import com.oscarce10.modelo.Partida;
import com.oscarce10.modelo.Tablero;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PartidaVTA implements Observer {
    private TextView scoreNum;
    private TextView recordNum;
    private GridLayout grilla;
    private ConstraintLayout main;
    private TextView tiles[][];

    public PartidaVTA(TextView scoreNum, TextView recordNum, GridLayout grilla) {
        this.scoreNum = scoreNum;
        this.recordNum = recordNum;
        this.grilla = grilla;
    }

    public PartidaVTA(Juego juego, int record) {
        this.scoreNum = juego.findViewById(R.id.scoreNum);
        this.recordNum = juego.findViewById(R.id.recordNum);
        this.grilla = juego.findViewById(R.id.grilla);
        Display display = juego.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        this.main = juego.findViewById(R.id.main);
        main.setBackgroundColor(Color.parseColor("#273238"));

        this.recordNum.setText("" + record);

        tiles = new TextView[Tablero.ALTO][Tablero.ANCHO];
        this.grilla.setRowCount(Tablero.ALTO);
        grilla.setColumnCount(Tablero.ANCHO);
        int c = 1;
        for (int i = 0; i < Tablero.ALTO; i++){
            for (int j = 0; j < Tablero.ANCHO; j++){
                tiles[i][j] = new TextView(juego);
                tiles[i][j].setText("" + (c++));
                tiles[i][j].setTextSize(10);
                tiles[i][j].setBackgroundResource(R.drawable.tile);
                tiles[i][j].setHeight((int) (height * 0.65 / Tablero.ALTO));
                tiles[i][j].setWidth((int) (width * 0.90 / Tablero.ANCHO));
                grilla.addView(tiles[i][j]);
            }
        }
    }

//    public void iniciarPartida(){
//        try {
//            BufferedReader re = new BufferedReader(new FileReader("src/record"));
//            this.record = Integer.parseInt(re.readLine());
//        } catch (IOException e) {
//            this.record = 150;
//        }
//        // Score
//        TextView score = this.juego.findViewById(R.id.scoreNum);
//        score.setText(String.valueOf(this.score));
//        // Record
//        TextView record = this.juego.findViewById(R.id.recordNum);
//        record.setText(String.valueOf(this.record));
//        //this.obTablero.crearGusano();
//        //this.obTablero.agregarFruta();
//        crearTablero();
//    }
//
//
//    public void actualizarCuadro(int fila, int columna){
//        LinearLayout tab = (LinearLayout) this.juego.findViewById(R.id.board);
//        ConstraintLayout campo = tab.findViewWithTag("" + fila + "" + columna);
//        if (tablero[fila][columna] == Tablero.CABEZA){
//            ImageView cab = new ImageView(this.juego);
//            switch (obTablero.getGusano().getGusano().get(0).getDireccion()){
//                case Gusano.DERECHA:
//                    cab.setBackgroundResource(R.drawable.ic_headgameright);
//                    break;
//
//                case Gusano.ABAJO:
//                    cab.setBackgroundResource(R.drawable.ic_headgamedown);
//                    break;
//
//                case Gusano.IZQUIERDA:
//                    cab.setBackgroundResource(R.drawable.ic_headgameleft);
//                    break;
//
//                case Gusano.ARRIBA:
//                    cab.setBackgroundResource(R.drawable.ic_headgameup);
//                    break;
//            }
//            campo.addView(cab);
//        }else if(tablero[fila][columna] == Tablero.CUERPO){
//            ImageView cuerpo = new ImageView(this.juego);
//            cuerpo.setBackgroundResource(R.drawable.ic_bodygame);
//            campo.addView(cuerpo);
//        } else if (tablero[fila][columna] == Tablero.FRUTA){
//            ImageView fruta = new ImageView(this.juego);
//            fruta.setBackgroundResource(R.drawable.ic_apple);
//            campo.addView(fruta);
//        }
//    }
//
//    public void run(){
//        int movida;
//        while ( (movida = this.obTablero.moverGusano()) != Gusano.PERDER) {
//            Coordenada cabeza, cola;
//            cabeza = this.obTablero.getGusano().getGusano().get(0);
//            cola = this.obTablero.getGusano().getGusano().get(this.obTablero.getGusano().getGusano().size()-1);
//            switch (movida){
//                case Gusano.DERECHA:
//                    actualizarCuadro(cabeza.getFila(), cabeza.getColumna());
//                    actualizarCuadro(cabeza.getFila(), cabeza.getColumna() - 1);
//                    actualizarCuadro(cola.getFila(), cola.getColumna());
//                    actualizarCuadro(cola.getFila(), cola.getColumna() - 1);
//                    break;
//                case Gusano.ABAJO:
//                    actualizarCuadro(cabeza.getFila(), cabeza.getColumna());
//                    actualizarCuadro(cabeza.getFila() + 1, cabeza.getColumna());
//                    actualizarCuadro(cola.getFila(), cola.getColumna());
//                    actualizarCuadro(cola.getFila() - 1, cola.getColumna());
//                    break;
//
//                case Gusano.IZQUIERDA:
//                    actualizarCuadro(cabeza.getFila(), cabeza.getColumna());
//                    actualizarCuadro(cabeza.getFila(), cabeza.getColumna() + 1);
//                    actualizarCuadro(cola.getFila(), cola.getColumna());
//                    actualizarCuadro(cola.getFila(), cola.getColumna() + 1);
//                    break;
//
//                case Gusano.ARRIBA:
//                    actualizarCuadro(cabeza.getFila(), cabeza.getColumna());
//                    actualizarCuadro(cabeza.getFila() - 1, cabeza.getColumna());
//                    actualizarCuadro(cola.getFila(), cola.getColumna());
//                    actualizarCuadro(cola.getFila() + 1, cola.getColumna());
//                    break;
//            }
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        System.exit(0);
//    }
//
    @Override
    public void update(Observable o, Object arg) {
        ArrayList <Object> args = (ArrayList<Object>) arg;
        Gusano gusano = (Gusano) args.get(0);
        int direccion = Integer.parseInt(args.get(1).toString());
        Coordenada fruta = (Coordenada) args.get(2);
        for (int i = 0; i < gusano.getGusano().size(); i++){
            if(i == 0){
                switch (direccion){
                    case Gusano.ABAJO:
                        tiles[gusano.getGusano().get(0).getFila()][gusano.getGusano().get(0).getColumna()].setBackgroundResource(R.drawable.ic_headgamedown);
                        break;

                    case Gusano.ARRIBA:
                        tiles[gusano.getGusano().get(0).getFila()][gusano.getGusano().get(0).getColumna()].setBackgroundResource(R.drawable.ic_headgameup);
                        break;

                    case Gusano.DERECHA:
                        tiles[gusano.getGusano().get(0).getFila()][gusano.getGusano().get(0).getColumna()].setBackgroundResource(R.drawable.ic_headgameright);
                        break;

                    case Gusano.IZQUIERDA:
                        tiles[gusano.getGusano().get(0).getFila()][gusano.getGusano().get(0).getColumna()].setBackgroundResource(R.drawable.ic_headgameleft);
                        break;
                }
            }else {
                tiles[gusano.getGusano().get(i).getFila()][gusano.getGusano().get(i).getColumna()].setBackgroundResource(R.drawable.ic_bodygame);
            }
        }
        tiles[fruta.getFila()][fruta.getColumna()].setBackgroundResource(R.drawable.ic_apple);
    }
}
