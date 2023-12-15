package com.project.x_raycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LogList extends AppCompatActivity {
    FileManager fm = new FileManager();
    ListView lvLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);
        lvLogs = findViewById(R.id.listview);
        ArrayList<String> calculos = fm.readFromFile(this);
        CalculosAdapter adapter = new CalculosAdapter(this,calculos);
        lvLogs.setAdapter(adapter);
    }

    public void voltar(View view) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }
}