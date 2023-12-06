package com.project.x_raycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    CheckBox cValorPadrao;
    RadioButton rCabeca, rTorax, rPerna, rFrontal, rLateral;
    RadioGroup rgRegiao;
    EditText eBsf, eEspessura, eRendimento;
    SeekBar sKv, sMa, sTempo;
    TextView tESAK,tkv,tma,ttempo;

    ESAK esak = new ESAK();

    Map<String,Double> valores = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cValorPadrao = findViewById(R.id.valorpadrao);
        rCabeca = findViewById(R.id.cabeca);
        rTorax = findViewById(R.id.torax);
        rPerna = findViewById(R.id.perna);
        rFrontal = findViewById(R.id.frontal);
        rLateral = findViewById(R.id.lateral);
        rgRegiao = findViewById(R.id.regiao);
        eBsf = findViewById(R.id.bsf);
        eEspessura = findViewById(R.id.espessura);
        eRendimento = findViewById(R.id.rendimento);
        sKv = findViewById(R.id.kV);
        sMa = findViewById(R.id.mA);
        sTempo = findViewById(R.id.tempo);
        tESAK = findViewById(R.id.esak);
        tkv = findViewById(R.id.tkv);
        tma = findViewById(R.id.tma);
        ttempo = findViewById(R.id.ttempo);
        rgRegiao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null) {
                    updateValuesForFrontalOrLateral();
                }
            }
        });

        // Add click listeners for rFrontal and rLateral
        rFrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValuesForFrontalOrLateral();
            }
        });

        rLateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValuesForFrontalOrLateral();
            }
        });
        final int stepSize = 5;
        sTempo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Calculate the value based on the step size
                int adjustedProgress = Math.round(progress / stepSize) * stepSize;
                seekBar.setProgress(adjustedProgress);
                ttempo.setText(String.format("Tempo (ms): %d", adjustedProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sKv.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Calculate the value based on the step size
                tkv.setText(String.format("kV: %d", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sMa.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Calculate the value based on the step size
                tma.setText(String.format("mA: %d", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void updateValuesForFrontalOrLateral() {
        if(!cValorPadrao.isChecked())
            return;
        boolean isFrontal = rFrontal.isChecked();

        // Use the currently selected radio button in rgRegiao to determine the tag
        int checkedRadioButtonId = rgRegiao.getCheckedRadioButtonId();
        RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);
        String tag = (checkedRadioButton != null) ? (String) checkedRadioButton.getTag() : null;

        if (tag != null) {
            switch (tag) {
                case "cabeca":
                    valores.put("espessura", isFrontal ? 19.0 : 15.0);
                    valores.put("rendimento", isFrontal ? 5.0 : 3.0);
                    break;
                case "torax":
                    valores.put("espessura", isFrontal ? 23.0 : 32.0);
                    valores.put("rendimento", isFrontal ? 0.4 : 1.5);
                    break;
                case "perna":
                    valores.put("espessura", isFrontal ? 100.0 : 200.0);
                    valores.put("rendimento", isFrontal ? 100.0 : 200.0);
                    break;
            }
            setar_valores();
        }
    }
    public void setar_valores(){
        eEspessura.setText(valores.get("espessura")+"");
        eRendimento.setText(valores.get("rendimento")+"");
    }

    public void calcular(View view){
        double espessura = Double.parseDouble(eEspessura.getText().toString());
        double bsf = Double.parseDouble(eBsf.getText().toString());
        double mA = (double) sMa.getProgress();
        double kV = (double) sKv.getProgress();
        double tempo = (double) sTempo.getProgress();
        double rendimento = Double.parseDouble(eRendimento.getText().toString())/(mA*tempo);


        esak = new ESAK(rendimento,espessura,mA,kV,bsf,rTorax.isChecked());
        tESAK.setText(String.format("%.3f",esak.calculaESAK()));

    }
}