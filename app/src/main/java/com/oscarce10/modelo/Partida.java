package com.oscarce10.modelo;

import android.os.Looper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Partida extends Observable {
    private int score;
    private int record;
    private Tablero obT;
    private Gusano gusano;
    public static final int AGREGAR = 1;
    public static final int REMOVER = 2;
    public static final int PERDER = -1;


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
        obG.add(Partida.AGREGAR);
        obG.add(this.gusano);
        obG.add(this.gusano.getDireccion());
        obG.add(this.obT.agregarFruta());
        this.setChanged();
        this.notifyObservers(obG);
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(!moverGusano())
                    this.cancel();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 500);
    }

    public boolean moverGusano(){
        ArrayList <Object> args = new ArrayList<>();
        Coordenada cola = null;
        // Tablero[][] cola = 0
        this.obT.getTablero()[this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila()]
                [this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna()] = 0;
        // En tablero se cambia la actual cabeza por cuerpo
        this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] = Tablero.CUERPO;
        // Se crea la coordenada de la cola a remover de la vista
        cola = new Coordenada(this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila(),
                this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna());
        // Cada coordenada del cuerpo la hace igual a la anterior
        for (int i = 1; i < this.gusano.getGusano().size(); i++){
            this.gusano.getGusano().get(i).setFila(this.gusano.getGusano().get(i - 1).getFila());
            this.gusano.getGusano().get(i).setColumna(this.gusano.getGusano().get(i - 1).getColumna());
        }
        switch (this.gusano.getDireccion()){
            case Gusano.DERECHA:
                if (this.gusano.getGusano().get(0).getColumna() == Tablero.ANCHO - 1){
                    args.add(PERDER);
                    this.setChanged();
                    this.notifyObservers(args);
                    return false;
                }
                // La coordenada de la cabeza la cambia a [][+1]
                this.gusano.getGusano().get(0).setColumna(this.gusano.getGusano().get(0).getColumna() + 1);
                break;

            case Gusano.ABAJO:
                if (this.gusano.getGusano().get(0).getFila() == Tablero.ALTO - 1){
                    args.add(PERDER);
                    this.setChanged();
                    this.notifyObservers(args);
                    return false;
                }
                // La coordenada de la cabeza la cambia a [+1][]
                this.gusano.getGusano().get(0).setFila(this.gusano.getGusano().get(0).getFila() + 1);
                break;

            case Gusano.IZQUIERDA:
                if (this.gusano.getGusano().get(0).getColumna() == 0){
                    args.add(PERDER);
                    this.setChanged();
                    this.notifyObservers(args);
                    return false;
                }
                // La coordenada de la cabeza la cambia a [][-1]
                this.gusano.getGusano().get(0).setColumna(this.gusano.getGusano().get(0).getColumna() - 1);
                break;

            case Gusano.ARRIBA:
                if (this.gusano.getGusano().get(0).getFila() == 0){
                    args.add(PERDER);
                    this.setChanged();
                    this.notifyObservers(args);
                    return false;
                }
                // La coordenada de la cabeza la cambia a [-1][]
                this.gusano.getGusano().get(0).setFila(this.gusano.getGusano().get(0).getFila() - 1);
                break;
        }
        // En tablero se hace la nueva cabeza
        this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] = Tablero.CABEZA;
        // 1. Se envia el argumento de remover a la vista
        args.add(Partida.REMOVER);
        // 2. Se envia solo la cola para ser removida
        args.add(cola);
        // 3. Se envia solo la cabeza para ser agregada a la vista
        args.add(this.gusano.getGusano().get(0));
        // 4. Se envia solo la cola nueva para ser dibujada
        args.add(this.gusano.getGusano().get(this.gusano.getGusano().size() - 1));
        // 5. Se envia la direccion del gusano
        args.add(this.gusano.getDireccion());
        this.setChanged();
        this.notifyObservers(args);
        return true;
    }

    public void cambiarDireccion(int direccion) {
        if (this.gusano.getDireccion() != direccion && this.gusano.getDireccion() % 2 != direccion % 2){
            this.gusano.setDireccion(direccion);
        }

    }
}
