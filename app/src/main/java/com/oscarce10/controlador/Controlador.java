package com.oscarce10.controlador;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.oscarce10.gusano.R;

import java.util.Observable;
import java.util.Observer;

public class Controlador extends AppCompatActivity implements Observer {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generarTablero(){
        LinearLayout main = findViewById(R.id.main);
        TextView txt = new TextView(this);
        txt.setText("asdsadasd");
        main.addView(txt);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
