package com.oscarce10.modelo;

public class Coordenada {
    private int fila;
    private int columna;
    private int direccion;

    public Coordenada(int fila, int columna, int direccion) {
        this.fila = fila;
        this.columna = columna;
        this.direccion = direccion;
    }

    public Coordenada() {
        this.fila = 0;
        this.columna = 0;
        this.direccion = 0;
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

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
}
