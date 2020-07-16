package com.oscarce10.gusano;

import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ConstraintLayout tiles[][];
    private Juego juego;
    private int width;
    private int height;

    public PartidaVTA(TextView scoreNum, TextView recordNum, GridLayout grilla) {
        this.scoreNum = scoreNum;
        this.recordNum = recordNum;
        this.grilla = grilla;
    }

    public PartidaVTA(Juego juego, int record) {
        this.juego = juego;
        this.scoreNum = juego.findViewById(R.id.scoreNum);
        this.recordNum = juego.findViewById(R.id.recordNum);
        this.grilla = juego.findViewById(R.id.grilla);
        Display display = juego.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.width = size.x;
        this.height = size.y;
        this.main = juego.findViewById(R.id.main);
        main.setBackgroundColor(Color.parseColor("#273238"));

        this.recordNum.setText("" + record);
        this.scoreNum.setText("" + 0);

        tiles = new ConstraintLayout[Tablero.ALTO][Tablero.ANCHO];
        this.grilla.setRowCount(Tablero.ALTO);
        grilla.setColumnCount(Tablero.ANCHO);
        int c = 1;
        for (int i = 0; i < Tablero.ALTO; i++){
            for (int j = 0; j < Tablero.ANCHO; j++){
                tiles[i][j] = new ConstraintLayout(juego);
                tiles[i][j].setBackgroundResource(R.drawable.tile);
                tiles[i][j].setMinHeight((int) (height * 0.65 / Tablero.ALTO));
                tiles[i][j].setMinWidth((int) (width * 0.90 / Tablero.ANCHO));
                tiles[i][j].setMaxHeight((int) (height * 0.65 / Tablero.ALTO));
                tiles[i][j].setMaxWidth((int) (width * 0.90 / Tablero.ANCHO));
                grilla.addView(tiles[i][j]);
            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        ArrayList <Object> args = (ArrayList<Object>) arg;
        int accion = Integer.parseInt(args.get(0).toString());
        if(accion == Partida.AGREGAR) {
            Gusano gusano = (Gusano) args.get(1);
            int direccion = Integer.parseInt(args.get(2).toString());
            Coordenada fruta = (Coordenada) args.get(3);
            for (int i = 0; i < gusano.getGusano().size(); i++){
                ImageView gus = new ImageView(this.juego);
                if(i == 0){
                    switch (direccion){
                        case Gusano.ABAJO:
                            gus.setBackgroundResource(R.drawable.ic_headgametinydown);
                            break;

                        case Gusano.ARRIBA:
                            gus.setBackgroundResource(R.drawable.ic_headgametinyup);
                            break;

                        case Gusano.DERECHA:
                            gus.setBackgroundResource(R.drawable.ic_headgametinyright);
                            break;

                        case Gusano.IZQUIERDA:
                            gus.setBackgroundResource(R.drawable.ic_headgametinyleft);
                            break;
                    }

                }else {
                    gus.setBackgroundResource(R.drawable.ic_bodygametiny);
                }
                gus.setMaxHeight(tiles[0][0].getMaxHeight());
                gus.setMinimumHeight(tiles[0][0].getMaxHeight());
                gus.setMinimumWidth(tiles[0][0].getMinWidth());
                gus.setMaxWidth(tiles[0][0].getMaxWidth());
                tiles[gusano.getGusano().get(i).getFila()][gusano.getGusano().get(i).getColumna()].addView(gus);
            }
            ImageView apple = new ImageView(this.juego);
            apple.setBackgroundResource(R.drawable.ic_appletiny);
            apple.setMaxHeight(tiles[0][0].getMaxHeight());
            apple.setMinimumHeight(tiles[0][0].getMaxHeight());
            apple.setMinimumWidth(tiles[0][0].getMinWidth());
            apple.setMaxWidth(tiles[0][0].getMaxWidth());
            tiles[fruta.getFila()][fruta.getColumna()].addView(apple);
        }else if(accion == Partida.REMOVER){
            Coordenada aux = (Coordenada) args.get(1);
            // Se elimina la cola del tablero
            final Coordenada finalAux2 = aux;
            final ConstraintLayout field = new ConstraintLayout(this.juego);
            field.setBackgroundResource(R.drawable.tile);
            field.setMinHeight((int) (height * 0.65 / Tablero.ALTO));
            field.setMinWidth((int) (width * 0.90 / Tablero.ANCHO));
            field.setMaxHeight((int) (height * 0.65 / Tablero.ALTO));
            field.setMaxWidth((int) (width * 0.90 / Tablero.ANCHO));
            // Only the original thread that created a view hierarchy can touch its views
            // https://es.stackoverflow.com/questions/250763/only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-views
            this.juego.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    grilla.removeViewAt((finalAux2.getFila() * Tablero.ANCHO) + finalAux2.getColumna());
                    grilla.addView(field, (finalAux2.getFila() * Tablero.ANCHO) + finalAux2.getColumna());
                }
            });

            // Agregar nueva cabeza a la vista dependiendo de la direccion
            aux = (Coordenada) args.get(2);
            ImageView gus = new ImageView(this.juego);
            switch (Integer.parseInt(args.get(4).toString())){
                case Gusano.ABAJO:
                    gus.setBackgroundResource(R.drawable.ic_headgametinydown);
                    break;

                case Gusano.ARRIBA:
                    gus.setBackgroundResource(R.drawable.ic_headgametinyup);
                    break;

                case Gusano.DERECHA:
                    gus.setBackgroundResource(R.drawable.ic_headgametinyright);
                    break;

                case Gusano.IZQUIERDA:
                    gus.setBackgroundResource(R.drawable.ic_headgametinyleft);
                    break;
            }
            gus.setMaxHeight(tiles[0][0].getMaxHeight());
            gus.setMinimumHeight(tiles[0][0].getMaxHeight());
            gus.setMinimumWidth(tiles[0][0].getMinWidth());
            gus.setMaxWidth(tiles[0][0].getMaxWidth());
            ConstraintLayout campo = new ConstraintLayout(this.juego);
            campo.setBackgroundResource(R.drawable.ic_rec);
            campo.addView(gus);

            // Only the original thread that created a view hierarchy can touch its views
            // https://es.stackoverflow.com/questions/250763/only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-views
            final Coordenada finalAux = aux; // Final porque asi lo exige la inner
            final ConstraintLayout finalCampo = campo;
            this.juego.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    grilla.removeViewAt((finalAux.getFila() * Tablero.ANCHO) + finalAux.getColumna());
                    grilla.addView(finalCampo, (finalAux.getFila() * Tablero.ANCHO) + finalAux.getColumna());
                }
            });


            // Eliminar la antigua cabeza y reemplazar por el cuerpo
            aux = (Coordenada) args.get(3);
            campo = new ConstraintLayout(this.juego);
            campo.setBackgroundResource(R.drawable.ic_rec);
            gus = new ImageView(this.juego);
            gus.setBackgroundResource(R.drawable.ic_bodygametiny);
            gus.setMaxHeight(tiles[0][0].getMaxHeight());
            gus.setMinimumHeight(tiles[0][0].getMaxHeight());
            gus.setMinimumWidth(tiles[0][0].getMinWidth());
            gus.setMaxWidth(tiles[0][0].getMaxWidth());
            campo.addView(gus);
            // Only the original thread that created a view hierarchy can touch its views
            // https://es.stackoverflow.com/questions/250763/only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-views
            final Coordenada finalAux1 = aux;
            final ConstraintLayout finalCampo1 = campo;
            this.juego.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    grilla.removeViewAt((finalAux1.getFila() * Tablero.ANCHO) + finalAux1.getColumna());
                    grilla.addView(finalCampo1, (finalAux1.getFila() * Tablero.ANCHO) + finalAux1.getColumna());
                }
            });

        }else if (accion == Partida.PERDER){
            // Toca para que muestre toast
            // https://es.stackoverflow.com/questions/250763/only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-views
            this.juego.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(juego, "Ha perdido", Toast.LENGTH_SHORT).show();
                }
            });
            this.juego.finish();
        }


    }
}
