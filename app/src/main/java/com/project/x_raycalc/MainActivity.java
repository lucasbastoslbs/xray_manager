package com.project.x_raycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {

    RadioButton rCabeca, rTorax, rPerna, rFrontal, rLateral;
    EditText eBsf, eEspessura;
    Slider sKv, sMa, sTempo;
    TextView tESAK;

    ESAK esak = new ESAK();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rCabeca = findViewById(R.id.cabeca);
        rTorax = findViewById(R.id.torax);
        rPerna = findViewById(R.id.perna);
        rFrontal = findViewById(R.id.frontal);
        rLateral = findViewById(R.id.lateral);
        eBsf = findViewById(R.id.bsf);
        eEspessura = findViewById(R.id.espessura);
        sKv = findViewById(R.id.kV);
        sMa = findViewById(R.id.mA);
        sTempo = findViewById(R.id.tempo);
        tESAK = findViewById(R.id.esak);
    }

    public void calcular(View view){
        float espessura = Float.parseFloat(eEspessura.getText().toString());
        float bsf = Float.parseFloat(eBsf.getText().toString());

        esak = new ESAK(10.2,espessura,2.2,0.05,bsf,false);
        tESAK.setText(esak.calculaESAK()+"");

    }
}