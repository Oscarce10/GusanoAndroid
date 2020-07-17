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

}
