package com.oscarce10.modelo;

import java.util.concurrent.ThreadLocalRandom;

public class Tablero {
    private final int ALTO;
    private final int ANCHO;
    private Gusano gusano;
    private int [][] tablero;
    public static final int CABEZA = 1;
    public static final int CUERPO = 2;
    public static final int FRUTA = 3;


    public Tablero(int ALTO, int ANCHO) {
        this.ALTO = ALTO;
        this.ANCHO = ANCHO;
    }

    public Tablero() {
        this.ALTO = 20;
        this.ANCHO = 18;
        this.tablero = new int[this.ALTO][this.ANCHO];
    }

    public int getALTO() {
        return ALTO;
    }

    public int getANCHO() {
        return ANCHO;
    }

    public Gusano getGusano() {
        return gusano;
    }

    public void setGusano(Gusano gusano) {
        this.gusano = gusano;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public void crearGusano(){
        this.gusano = new Gusano();
        this.gusano.crearGusano();
        Coordenada cabeza = this.gusano.getGusano().get(0);
        this.tablero[cabeza.getFila()][cabeza.getColumna()] = CABEZA;
        Coordenada cuerpo = this.gusano.getGusano().get(1);
        this.tablero[cuerpo.getFila()][cuerpo.getColumna()] = CUERPO;
    }

    public void agregarFruta(){
        int fila;
        int columna;
        do{
            fila = ThreadLocalRandom.current().nextInt(this.ALTO - 1);
            columna = ThreadLocalRandom.current().nextInt(this.ANCHO - 1);
        } while(this.tablero[fila][columna] != 0);
        tablero[fila][columna] = FRUTA;

    }

}
