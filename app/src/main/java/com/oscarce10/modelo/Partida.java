package com.oscarce10.modelo;

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
    public static final int SUMAR = 3;


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
        // Se toma la cola del gusano
        Coordenada cola = this.gusano.getGusano().get(this.gusano.getGusano().size() - 1);
        Coordenada cabeza = this.gusano.getGusano().get(0);

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
        }

        // Si en el tablero donde se para la cabeza esta la fruta
        if (this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] == Tablero.FRUTA){
            // Se añade la cola guardada previamente al gusano y no se cambia en el tablero la coordenada ya que sigue siendo CUERPO
            this.gusano.getGusano().add(cola);
            // Se marca directriz de AGREGAR
            args.add(SUMAR);
            // Se agrega la nueva fruta para mostrar
            Coordenada nuevaFruta = this.obT.agregarFruta();
            this.obT.getTablero()[nuevaFruta.getFila()][nuevaFruta.getColumna()] = Tablero.FRUTA;
            args.add(nuevaFruta);
        }
        // Si en el tablero donde se para la cabeza NO esta la fruta
        else {
            // Se borra la acual cola del tablero
            this.obT.getTablero()[cola.getFila()][cola.getColumna()] = 0;
            // 0. Se marca directriz de REMOVER
            args.add(REMOVER);
            // 1. Se envia la antigua cabeza a ser cambiada por cuerpo
            args.add(cabeza);
        }

        // En el tablero se marca la cabeza
        this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] = Tablero.CABEZA;

        // 2. Se envia solo la cola para ser removida o sumada
        args.add(cola);

        // 3. Se envia solo la cabeza para ser agregada a la vista
        args.add(this.gusano.getGusano().get(0));

        // 4. Se envia la direccion del gusano
        args.add(this.gusano.getDireccion());
        this.setChanged();
        this.notifyObservers(args);
        return true;

//        // SI LA NUEVA CABEZA NO SE PARA SOBRE LA FRUTA
//        if (this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] != Tablero.FRUTA){
//            // Tablero[][] cola = 0
//            this.obT.getTablero()[this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila()]
//                    [this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna()] = 0;
//            // Se crea la coordenada de la cola a remover de la vista
//            cola = new Coordenada(this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila(),
//                    this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna());
//            // Cada coordenada del cuerpo la hace igual a la anterior
//            for (int i = this.gusano.getGusano().size() - 1; i >= 1 ; i++){
//                this.gusano.getGusano().get(i).setFila(this.gusano.getGusano().get(i - 1).getFila());
//                this.gusano.getGusano().get(i).setColumna(this.gusano.getGusano().get(i - 1).getColumna());
//            }
//        }



//        // Si la cabeza agarra una fruta
//        if (obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] == Tablero.FRUTA){
//            // 1. Se envia el argumento de sumar a la vista
//            args.add(SUMAR);
//            Coordenada nuevo = null;
//            switch (this.gusano.getDireccion()){
//                case Gusano.DERECHA:
//                    nuevo = new Coordenada(this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila(), this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna() - 1);
//                    break;
//                case Gusano.IZQUIERDA:
//                    nuevo = new Coordenada(this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila(), this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna() + 1);
//                    break;
//                case Gusano.ABAJO:
//                    nuevo = new Coordenada(this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila() - 1, this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna());
//                    break;
//                case Gusano.ARRIBA:
//                    nuevo = new Coordenada(this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getFila() + 1, this.gusano.getGusano().get(this.gusano.getGusano().size() - 1).getColumna());
//                    break;
//            }
//            this.gusano.getGusano().add(nuevo);
//            this.obT.getTablero()[nuevo.getFila()][nuevo.getColumna()] = Tablero.CUERPO;
//            this.obT.agregarFruta();
//        } else{
//            // 1. SINO, Se envia el argumento de remover a la vista
//            args.add(Partida.REMOVER);
//        }
//        // En tablero se hace la nueva cabeza
//        this.obT.getTablero()[this.gusano.getGusano().get(0).getFila()][this.gusano.getGusano().get(0).getColumna()] = Tablero.CABEZA;
//        // 2. Se envia solo la cola para ser removida o sumada
//        args.add(cola);
//        // 3. Se envia solo la cabeza para ser agregada a la vista
//        args.add(this.gusano.getGusano().get(0));
//        // 4. Se envia solo la cola nueva para ser dibujada
//        args.add(this.gusano.getGusano().get(this.gusano.getGusano().size() - 1));

    }

    public void cambiarDireccion(int direccion) {
        if (this.gusano.getDireccion() != direccion && this.gusano.getDireccion() % 2 != direccion % 2){
            this.gusano.setDireccion(direccion);
        }

    }
}
