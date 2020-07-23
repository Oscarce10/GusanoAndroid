package com.oscarce10.gusano;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.view.Display;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.oscarce10.controlador.Tiempo;
import com.oscarce10.modelo.Coordenada;
import com.oscarce10.modelo.Gusano;
import com.oscarce10.modelo.Partida;
import com.oscarce10.modelo.Tablero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class PartidaVTA implements Observer   {
    private TextView scoreNum;
    private TextView recordNum;
    private GridLayout grilla;
    private TextView txtSegundos;
    private TextView txtMinutos;
    private TextView txtHoras;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PartidaVTA(Juego juego, int record) {
        this.juego = juego;
        this.scoreNum = juego.findViewById(R.id.scoreNum);
        this.recordNum = juego.findViewById(R.id.recordNum);
        String puntuacion_maxima;
        puntuacion_maxima = juego.getPreferences().getString("record","");
        System.out.println(".................................."+puntuacion_maxima);
        if(puntuacion_maxima.length()>0){
            System.out.println("Entro trueeee");
            recordNum.setText(puntuacion_maxima);
        }else{
            SharedPreferences.Editor edit = juego.getPreferences().edit();
            edit.putString("record", "0");
            edit.commit();
            puntuacion_maxima = juego.getPreferences().getString("record","");
            recordNum.setText(puntuacion_maxima);
        }
        this.grilla = juego.findViewById(R.id.grilla);
        Display display = juego.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.width = size.x;
        this.height = size.y;
        this.main = juego.findViewById(R.id.main);
        main.setBackgroundColor(Color.parseColor("#273238"));

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
        this.txtSegundos = juego.findViewById(R.id.segundosTxt);
        this.txtMinutos = juego.findViewById(R.id.minutosTxt);
        this.txtHoras = juego.findViewById(R.id.horasTxt);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        }else if(accion == Partida.REMOVER || accion == Partida.SUMAR){
            Coordenada aux;

            if (accion == Partida.REMOVER){
                aux = (Coordenada) args.get(1);
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
            }

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
            campo.setBackgroundResource(R.drawable.tile);
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
            campo.setBackgroundResource(R.drawable.tile);
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

            // Agregar nueva fruta al tablero
            if (accion == Partida.SUMAR){
                final int score = Integer.parseInt(args.get(7).toString());
                aux = (Coordenada) args.get(5);
                final Coordenada auxFruta = aux;
                final ConstraintLayout fieldFruta = new ConstraintLayout(this.juego);
                fieldFruta.setBackgroundResource(R.drawable.tile);
                fieldFruta.setMinHeight((int) (height * 0.65 / Tablero.ALTO));
                fieldFruta.setMinWidth((int) (width * 0.90 / Tablero.ANCHO));
                fieldFruta.setMaxHeight((int) (height * 0.65 / Tablero.ALTO));
                fieldFruta.setMaxWidth((int) (width * 0.90 / Tablero.ANCHO));
                ImageView apple = new ImageView(this.juego);
                apple.setBackgroundResource(R.drawable.ic_appletiny);
                apple.setMaxHeight(tiles[0][0].getMaxHeight());
                apple.setMinimumHeight(tiles[0][0].getMaxHeight());
                apple.setMinimumWidth(tiles[0][0].getMinWidth());
                apple.setMaxWidth(tiles[0][0].getMaxWidth());
                fieldFruta.addView(apple);
                // Only the original thread that created a view hierarchy can touch its views
                // https://es.stackoverflow.com/questions/250763/only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-views
                this.juego.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scoreNum.setText("" + score);
                        grilla.removeViewAt((auxFruta.getFila() * Tablero.ANCHO) + auxFruta.getColumna());
                        grilla.addView(fieldFruta, (auxFruta.getFila() * Tablero.ANCHO) + auxFruta.getColumna());
                    }
                });
            }

        }else if (accion == Partida.PERDER){
            // Toca para que muestre toast
            // https://es.stackoverflow.com/questions/250763/only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-views
            if(Integer.parseInt(String.valueOf(scoreNum.getText()))>Integer.parseInt(String.valueOf(recordNum.getText()))){
                    SharedPreferences.Editor edit = juego.getPreferences().edit();
                    edit.putString("record", String.valueOf(scoreNum.getText()));
                    edit.commit();
            }
            this.juego.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(juego, "Ha perdido", Toast.LENGTH_SHORT).show();
                }
            });
            this.juego.perder();
        } else if (accion == Partida.TIME){
            final int segundos = Integer.parseInt(args.get(1).toString());
            final int minutos = Integer.parseInt(args.get(2).toString());
            final int horas = Integer.parseInt(args.get(3).toString());
            // Only the original thread that created a view hierarchy can touch its views
            // https://es.stackoverflow.com/questions/250763/only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-views
            this.juego.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtSegundos.setText( ((segundos < 10)?"0":"") + segundos);
                    txtMinutos.setText(((minutos < 10)?"0":"") + minutos);
                    txtHoras.setText(((horas < 10)?"0":"") + horas);
                }
            });
        }

    }
}
