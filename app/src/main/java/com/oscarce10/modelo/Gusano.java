package com.oscarce10.modelo;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Gusano {
    public static final int DERECHA = 0;
    public static final int ABAJO = 1;
    public static final int IZQUIERDA = 2;
    public static final int ARRIBA = 3;
    private ArrayList<Coordenada> gusano;

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

    public void crearGusano(){
        // Obtener una posicion random fila, columna donde va a iniciar el gusano junto a una direccion
        // En la que se empezara a mover
        int fila = ThreadLocalRandom.current().nextInt(4, 14 + 1);
        int columna = ThreadLocalRandom.current().nextInt(4, 17 + 1);
        this.gusano.add(new Coordenada(fila, columna, ThreadLocalRandom.current().nextInt(3)));
        Coordenada cabeza = this.gusano.get(0);
        switch (cabeza.getDireccion()){
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
}
