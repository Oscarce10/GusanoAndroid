package com.oscarce10.modelo;

import java.util.ArrayList;
import java.util.Observable;

public class Partida extends Observable {
    private int score;
    private int record;
    private Tablero obT;
    private Gusano gusano;

    public Partida(int score, int record, int[][] tablero) {
        this.score = score;
        this.record = record;
        this.obT = new Tablero();
    }

    public Partida() {
        this.score = 0;
        this.record = 0;
        this.obT = new Tablero();
        this.gusano = new Gusano();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public void iniciarPartida(){
        this.gusano.crearGusano();
        this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()]= Tablero.CABEZA;
        this.obT.getTablero()[this.gusano.getGusano().get(1).getFila()][this.gusano.getGusano().get(1).getColumna()] = Tablero.CUERPO;
        ArrayList <Object> obG = new ArrayList<>();
        obG.add(this.gusano);
        obG.add(this.gusano.getDireccion());
        obG.add(this.obT.agregarFruta());
        this.setChanged();
        this.notifyObservers(obG);
    }
}
