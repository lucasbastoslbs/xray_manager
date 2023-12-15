package com.project.x_raycalc;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileManager {
    JSONObject json = new JSONObject();

    public FileManager(){
    }
    public void salvaJSON(Map<String,Double> data){

    }
    public ArrayList<String> readFromFile(Context context) {

        ArrayList<String> calculos = new ArrayList<String>();

        try {
            InputStream inputStream = context.openFileInput("data.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    calculos.add(receiveString);
                }
                inputStream.close();
            } else {
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("data.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    Log.e("Exception (readFromFile)", "File write failed: " + e.toString());
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return calculos;
    }

    public void writeToFile(ArrayList<String> calculos, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("data.txt", Context.MODE_PRIVATE));
            for(String item : calculos)
                outputStreamWriter.write(item+"\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception (writeToFile)", "File write failed: " + e.toString());
        }
    }

}
