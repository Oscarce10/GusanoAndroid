package com.oscarce10.modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

public class Tablero extends Observable {
    public static final int ALTO = 15;
    public static final int ANCHO = 13;
    private int [][] tablero;
    public static final int CABEZA = 1;
    public static final int CUERPO = 2;
    public static final int FRUTA = 3;


    public Tablero() {
        this.tablero = new int[ALTO][ANCHO];
    }

    public int[][] getTablero() {
        return tablero;
    }


    public Coordenada agregarFruta(){
        int fila;
        int columna;
        do{
            fila = ThreadLocalRandom.current().nextInt(ALTO - 1);
            columna = ThreadLocalRandom.current().nextInt(ANCHO - 1);
        } while(this.tablero[fila][columna] != 0);
        tablero[fila][columna] = FRUTA;
        return new Coordenada(fila, columna);
    }

//    public int moverGusano(){
//        while( (this.gusano.getGusano().get(0).getFila() > -1 && this.gusano.getGusano().get(0).getFila() < ALTO) ||
//                (this.gusano.getGusano().get(0).getColumna() > -1 && this.gusano.getGusano().get(0).getColumna() < ANCHO )){
//            switch (this.gusano.getGusano().get(0).getDireccion()){
//                case Gusano.DERECHA:
//                    this.gusano.avanzaDerecha();
//                    break;
//
//                case Gusano.ABAJO:
//                    this.gusano.avanzaAbajo();
//                    break;
//
//                case Gusano.IZQUIERDA:
//                    this.gusano.avanzaIzquierda();
//                    break;
//
//                case Gusano.ARRIBA:
//                    this.gusano.avanzaArriba();
//                    break;
//            }
//            this.tablero[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] = 1;
//            this.tablero[this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila()]
//                    [this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna()] = 0;
//            return this.gusano.getGusano().get(0).getDireccion();
//
//        }
//        return Gusano.PERDER;
//    }

}
