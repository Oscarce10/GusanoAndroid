package com.oscarce10.controlador;

import android.widget.TextView;

import com.oscarce10.gusano.Juego;
import com.oscarce10.gusano.R;

import java.util.Observable;
import java.util.Observer;

public class Tiempo {
    private int segundos;
    private  int minutos;
    private int horas;


    public Tiempo(){
        this.segundos = 0;
        this.minutos = 0;
        this.horas = 0;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public void avanza(){
        if (this.segundos == 59){
            this.segundos = 0;
            this.minutos++;
        } else {
            this.segundos++;
        }
        if (this.minutos == 59){
            this.horas++;
            this.minutos = 0;
        }
    }

}
