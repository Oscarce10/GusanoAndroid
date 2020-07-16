package com.oscarce10.controlador;


import android.view.MotionEvent;
import android.view.View;

import com.oscarce10.gusano.Juego;
import com.oscarce10.modelo.Gusano;

public class Controlador {
    private Juego mainActivity;

    public Controlador(Juego mainActivity){
        this.mainActivity = mainActivity;
        View fs = this.mainActivity.getWindow().getDecorView();
        fs.setOnTouchListener(new View.OnTouchListener(){
            float x1;
            float x2;
            float y1;
            float y2;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    x1 = event.getX();
                    y1 = event.getY();
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    x2 = event.getX();
                    y2 = event.getY();
                    movimiento(x1, x2, y1, y2);
                }
                return false;
            }
        });
    }

    private void movimiento(float x1, float x2, float y1, float y2) {
        float difX = x2-x1;
        float difY = y2-y1;
        int movimiento;
        if(Math.abs(difX) > Math.abs(difY)){
            if(difX > 0){
                movimiento = Gusano.DERECHA;
            }else{
                movimiento = Gusano.IZQUIERDA;
            }
        }else{
            if(difY > 0){
                movimiento = Gusano.ABAJO;
            }else{
                movimiento = Gusano.ARRIBA;
            }
        }
        System.out.println("Movimiento: " + movimiento);
        this.mainActivity.cambiarDireccion(movimiento);
    }

}
