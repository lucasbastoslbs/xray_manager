package com.project.x_raycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    CheckBox cValorPadrao;
    RadioButton rCabeca, rTorax, rPerna, rFrontal, rLateral;
    RadioGroup rgRegiao;
    EditText eBsf, eEspessura, eRendimento;
    SeekBar sKv, sMa, sTempo;
    TextView tESAK,tkv,tma,ttempo,tFormula;
    Map<String,Double> valores = new HashMap<>();
    List<String> calculos;
    ListView lvLogs;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    FileManager fm = new FileManager();

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
        tkv.setText(String.format("Kv: %d",sKv.getProgress()));
        tma = findViewById(R.id.tma);
        tma.setText(String.format("mA: %d",sMa.getProgress()));
        ttempo = findViewById(R.id.ttempo);
        ttempo.setText(String.format("Tempo (ms): %d",sTempo.getProgress()));
        tFormula = findViewById(R.id.formula);
        lvLogs = findViewById(R.id.logs);
        calculos = fm.readFromFile(this);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                calculos);
        lvLogs.setAdapter(adapter);
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
        double tempo = (double) sTempo.getProgress()/1000;
        double rendimento = Double.parseDouble(eRendimento.getText().toString())/(mA*tempo);
        ESAK esak = new ESAK(rendimento,espessura,mA,tempo,bsf,rTorax.isChecked());

        tESAK.setText(String.format("%.3f",esak.getEsak()));
        //tFormula.setText(String.format("ESAK(%.20f) = %.2f * (80/%.2f)^2 * (%.2f*%.2f) * %.2f",esak.getEsak(),esak.getRendimento(), esak.getEspessura(), esak.getMa(),esak.getS(),esak.getBsf()));
        tFormula.setText(esak.getData());
        listItems.add(esak.getData());
        adapter.notifyDataSetChanged();
        calculos.add(esak.getData());
    }

    public void salvalogs(View view){
        fm.writeToFile(calculos,this);
    }
}
