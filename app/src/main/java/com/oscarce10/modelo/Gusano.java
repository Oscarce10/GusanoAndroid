package com.oscarce10.modelo;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Gusano {
    public static final int DERECHA = 0;
    public static final int ABAJO = 1;
    public static final int IZQUIERDA = 2;
    public static final int ARRIBA = 3;
    public static final int PERDER = -1;
    private ArrayList<Coordenada> gusano;
    private int direccion;

    public Gusano(ArrayList<Coordenada> gusano) {
        this.gusano = gusano;
    }

    public Gusano() {
        this.gusano = new ArrayList<Coordenada>();
    }

    public ArrayList<Coordenada> getGusano() {
        return gusano;
    }

    public void setGusano(ArrayList<Coordenada> gusano) {
        this.gusano = gusano;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public void crearGusano(){
        // Obtener una posicion random fila, columna donde va a iniciar el gusano junto a una direccion
        // En la que se empezara a mover
        int fila = (int)(Math.random() * (((Tablero.ALTO - 3) - 2) + 1)) + 2;
        int columna = (int)(Math.random() * (((Tablero.ANCHO - 3) - 2) + 1)) + 2;
        System.out.println("------x: " + columna + " ----------y: " + fila);
        this.gusano.add(new Coordenada(fila, columna));
        this.direccion = (int) (Math.random() * 3);
        Coordenada cabeza = this.gusano.get(0);
        switch (this.direccion){
            case DERECHA:
                this.gusano.add(new Coordenada(cabeza.getFila(), cabeza.getColumna() - 1));
                break;

            case ABAJO:
                this.gusano.add(new Coordenada(cabeza.getFila() - 1, cabeza.getColumna()));
                break;

            case IZQUIERDA:
                this.gusano.add(new Coordenada(cabeza.getFila(), cabeza.getColumna() + 1));
                break;

            case ARRIBA:
                this.gusano.add(new Coordenada(cabeza.getFila() + 1, cabeza.getColumna()));
                break;
        }

    }

//    public void avanzaDerecha(){
//        this.gusano.get(0).setColumna(this.gusano.get(0).getColumna() + 1);
//        this.gusano.get(0).setDireccion(Gusano.DERECHA);
//        this.gusano.remove(this.gusano.size() - 1);
//    }
//    public void avanzaIzquierda(){
//        this.gusano.get(0).setColumna(this.gusano.get(0).getColumna() - 1);
//        this.gusano.get(0).setDireccion(Gusano.IZQUIERDA);
//        this.gusano.remove(this.gusano.size() - 1);
//    }
//    public void avanzaArriba(){
//        this.gusano.get(0).setFila(this.gusano.get(0).getFila() - 1);
//        this.gusano.get(0).setDireccion(Gusano.ARRIBA);
//        this.gusano.remove(this.gusano.size() - 1);
//    }
//    public void avanzaAbajo(){
//        this.gusano.get(0).setColumna(this.gusano.get(0).getColumna() - 1);
//        this.gusano.get(0).setDireccion(Gusano.ABAJO);
//        this.gusano.remove(this.gusano.size() - 1);
//    }

}
