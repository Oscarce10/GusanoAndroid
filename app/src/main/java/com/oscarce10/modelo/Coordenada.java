package com.oscarce10.modelo;

public class Coordenada {
    private int fila;
    private int columna;

    public Coordenada(int fila, int columna, int direccion) {
        this.fila = fila;
        this.columna = columna;
    }

    public Coordenada() {
        this.fila = 0;
        this.columna = 0;
    }

    public Coordenada(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
