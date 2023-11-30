package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RadioButton radioPvp, radioPve;
    ImageButton[] imageTab = new ImageButton[9];
    TextView textEnd;
    Button btnStart;

    boolean endgame = false;
    int turn = 0;
    int[] map = new int[9];
    int gamemode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioPve = findViewById(R.id.radioPve);
        radioPvp = findViewById(R.id.radioPvp);
        textEnd = findViewById(R.id.textEnd);
        imageTab[0] = findViewById(R.id.ib00);
        imageTab[1] = findViewById(R.id.ib01);
        imageTab[2] = findViewById(R.id.ib02);
        imageTab[3] = findViewById(R.id.ib10);
        imageTab[4] = findViewById(R.id.ib11);
        imageTab[5] = findViewById(R.id.ib12);
        imageTab[6] = findViewById(R.id.ib20);
        imageTab[7] = findViewById(R.id.ib21);
        imageTab[8] = findViewById(R.id.ib22);
        btnStart = findViewById(R.id.btnStart);

        if (savedInstanceState != null){
            map = savedInstanceState.getIntArray("MAP");
            turn = savedInstanceState.getInt("TURN");
            gamemode = savedInstanceState.getInt("GAMEMODE");
            textEnd.setText(savedInstanceState.getString("TURNTEXT"));
            endgame = savedInstanceState.getBoolean("ENDGAME");
            for(int i = 0; i<9; i++){
                if (map[i] == 1){
                    imageTab[i].setImageResource(android.R.drawable.presence_offline);
                    imageTab[i].setClickable(false);
                }
                else if (map[i] == 2){
                    imageTab[i].setImageResource(android.R.drawable.presence_invisible);
                    imageTab[i].setClickable(false);
                }
                if(endgame)
                    imageTab[i].setClickable(false);
            }
        }
        else{
            int x = 10;
            for(int i = 0; i<9; i++){
                map[i] = x;
                x++;
            }
            textEnd.setVisibility(View.GONE);
            for(int i = 0;i < 9; i++)
                imageTab[i].setClickable(false);
        }
    }

    public void Start(View view) {
        if (radioPvp.isChecked()){
            gamemode = 0;
        }
        else if(radioPve.isChecked()){
            gamemode = 1;
        }
        for(int i = 0; i<9; i++){
            imageTab[i].setClickable(true);
            imageTab[i].setImageResource(android.R.drawable.divider_horizontal_dark);
        }
        int x = 10;
        for(int i = 0; i<9; i++){
            map[i] = x;
            x++;
        }
        turn = 0;
        endgame = false;
        textEnd.setText("Vez do jogador 1");
        textEnd.setVisibility(View.VISIBLE);
    }

    public void mark(int choice){
        if (turn % 2 == 0){
            imageTab[choice].setImageResource(android.R.drawable.presence_offline);
        }
        else if (turn % 2 == 1){
            imageTab[choice].setImageResource(android.R.drawable.presence_invisible);
        }
        imageTab[choice].setClickable(false);
        checkEndGame();
    }

    public void checkEndGame(){
        if(map[0] == map[1] && map[1] == map[2]) // linha 1
            endgame = true;
        else if(map[0] == map[3] && map[3] == map[6]) // coluna 1
            endgame = true;
        else if(map[3] == map[4] && map[4] == map[5]) // linha 2
            endgame = true;
        else if(map[1] == map[4] && map[4] == map[7]) // coluna 2
            endgame = true;
        else if(map[6] == map[7] && map[7] == map[8]) // linha 3
            endgame = true;
        else if(map[2] == map[5] && map[5] == map[8]) // coluna 3
            endgame = true;
        else if(map[0] == map[4] && map[4] == map[8]) // \
            endgame = true;
        else if(map[2] == map[4] && map[4] == map[6]) // /
            endgame = true;

        if(turn == 8 && !endgame){
            textEnd.setText("Deu velha !!");
            textEnd.setVisibility(View.VISIBLE);
        }
        else if(endgame){
            for(int i = 0; i < 9; i++){
                if(map[i] != 1 && map[i] != 2)
                    imageTab[i].setClickable(false);
            }
            textEnd.setText("Jogador "+((turn%2)+1)+" venceu!");
            textEnd.setVisibility(View.VISIBLE);
        }
        else {
            updateTurn();
        }
    }

    public void updateTurn(){
        turn++;
        textEnd.setText("Vez do jogador "+((turn % 2)+1));

        if(gamemode == 1 && turn % 2 == 1){
            pcTurn();
        }
    }

    public void pcTurn(){
        ArrayList<String> list = new ArrayList<>();
        int i;
        for(i = 0; i < 9; i++){
            if(map[i] != 1 && map[i] != 2)
                list.add(i+"");
            }
        Random r = new Random();
        int choice = r.nextInt(list.size());
        i = Character.getNumericValue(list.get(choice).charAt(0));
        map[i] = 2;
        mark(i);

    }

    public void swapBtn(View view) {
        if(radioPvp.isChecked()){
            radioPvp.setText("(X) Jogador vs Jogador");
            radioPve.setText("(  ) Jogador vs Computador");
        }
        if(radioPve.isChecked()){
            radioPvp.setText("(  ) Jogador vs Jogador");
            radioPve.setText("(X) Jogador vs Computador");
        }
    }

    public void ib00(View view) {
        map[0] = (turn % 2) + 1;
        mark(0);
    }

    public void ib01(View view) {
        map[1] = (turn % 2) + 1;
        mark(1);
    }

    public void ib02(View view) {
        map[2] = (turn % 2) + 1;
        mark(2);
    }

    public void ib10(View view) {
        map[3] = (turn % 2) + 1;
        mark(3);
    }

    public void ib11(View view) {
        map[4] = (turn % 2) + 1;
        mark(4);
    }

    public void ib12(View view) {
        map[5] = (turn % 2) + 1;
        mark(5);
    }

    public void ib20(View view) {
        map[6] = (turn % 2) + 1;
        mark(6);
    }

    public void ib21(View view) {
        map[7] = (turn % 2) + 1;
        mark(7);
    }

    public void ib22(View view) {
        map[8] = (turn % 2) + 1;
        mark(8);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putIntArray("MAP", map);
        savedInstanceState.putInt("TURN", turn);
        savedInstanceState.putInt("GAMEMODE", gamemode);
        savedInstanceState.putString("TURNTEXT", textEnd.getText().toString());
        savedInstanceState.putBoolean("ENDGAME", endgame);
        super.onSaveInstanceState(savedInstanceState);
    }

}