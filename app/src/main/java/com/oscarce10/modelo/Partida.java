package com.oscarce10.modelo;

import com.oscarce10.controlador.Tiempo;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class Partida extends Observable {
    private int score;
    private int record;
    private Tablero obT;
    private Gusano gusano;
    private Tiempo tiempo;
    public static final int AGREGAR = 1;
    public static final int REMOVER = 2;
    public static final int PERDER = -1;
    public static final int SUMAR = 3;
    public static final int TIME = 4;


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
        this.tiempo = new Tiempo();
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


        final TimerTask elapsedTime = new TimerTask() {
            @Override
            public void run() {
                avanzaTiempo();
            }
        };
        Timer timerTiempo = new Timer();
        timerTiempo.schedule(elapsedTime, 0, 1000);

        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(!moverGusano()){
                    this.cancel();
                    elapsedTime.cancel();
                }

            }
        };
        timer.schedule(timerTask, 1000, 300);

    }

    public boolean moverGusano(){
        ArrayList <Object> args = new ArrayList<>();
        // Se toma la cola del gusano
        Coordenada cola =  new Coordenada(this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila(), this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna());
        // Se toma la cabeza del gusano para despues ser cambiada por cuerpo
        Coordenada cabeza = new Coordenada(this.gusano.getGusano().get(0).getFila(), this.gusano.getGusano().get(0).getColumna());

        // Cada coordenada del cuerpo la hace igual a la anterior tanto en las coordenadas como en el tablero y todo se vuelve cuerpo
        for (int i = this.gusano.getGusano().size() - 1; i >= 1 ; i--){
            this.gusano.getGusano().get(i).setFila(this.gusano.getGusano().get(i - 1).getFila());
            this.gusano.getGusano().get(i).setColumna(this.gusano.getGusano().get(i - 1).getColumna());
            this.obT.getTablero()[this.gusano.getGusano().get(i).getFila()][this.gusano.getGusano().get(i).getColumna()] = Tablero.CUERPO;
        }

        // SE UBICA LA NUEVA CABEZA DEPENDIENDO DE LA DIRECCION EN EL TABLERO Y SE ENVIA A LA VISTA
        // SI SE SALE DEL TABLERO O TOCA EL CUEPO SE NOTIFICA QUE PERDIO
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

        // Si la cabeza toca al cuerpo del gusano
        if(this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] == Tablero.CUERPO){
            args.add(PERDER);
            this.setChanged();
            this.notifyObservers(args);
            return false;
        }

        if (this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] == 0){
            // Se borra la actual cola del tablero
            this.obT.getTablero()[cola.getFila()][cola.getColumna()] = 0;
            // 0. Se marca directriz de REMOVER
            args.add(REMOVER);
        }
        // Si agarra la fruta
        else {
            // 0. Se marca directriz de SUMAR
            args.add(SUMAR);
            gusano.getGusano().add(cola);
        }

        // En tablero se hace la nueva cabeza
        this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] = Tablero.CABEZA;
        // 2. Se envia la cola antigua para ser removida o no
        args.add(cola);
        // 3. Se envia solo la cabeza para ser agregada a la vista
        args.add(this.gusano.getGusano().get(0));
        // 4. Se envia solo la cabeza antigua para ser cambiada por cuerpo
        args.add(cabeza);
        // 5. Se envia la direccion del gusano
        args.add(this.gusano.getDireccion());

        if (Integer.parseInt(args.get(0).toString()) == SUMAR){
            args.add(this.obT.agregarFruta());
            args.add(cola);
            this.score++;
            args.add(this.score);
        }


        this.setChanged();
        this.notifyObservers(args);
        return true;
    }

    public void cambiarDireccion(int direccion) {
        if (this.gusano.getDireccion() != direccion && this.gusano.getDireccion() % 2 != direccion % 2){
            this.gusano.setDireccion(direccion);
        }

    }

    public void avanzaTiempo(){
        this.tiempo.avanza();
        ArrayList <Object> args = new ArrayList<>();
        args.add(TIME);
        args.add(this.tiempo.getSegundos());
        args.add(this.tiempo.getMinutos());
        args.add(this.tiempo.getHoras());
        this.setChanged();
        this.notifyObservers(args);
    }

}
