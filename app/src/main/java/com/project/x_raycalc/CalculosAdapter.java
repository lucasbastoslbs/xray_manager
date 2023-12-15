package com.project.x_raycalc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CalculosAdapter extends ArrayAdapter<String> {
    public CalculosAdapter(@NonNull Context context, ArrayList<String> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        String currentNumberPosition = getItem(position);
        String[] linha = currentNumberPosition.split(";");

        // then according to the position of the view assign the desired image for the same
        TextView titulo = currentItemView.findViewById(R.id.titulo);
        assert currentNumberPosition != null;
        titulo.setText(linha[0]);

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView calculo = currentItemView.findViewById(R.id.texto);
        String temp = "";
        for(int i = 1; i < linha.length; i++)
            temp += linha[i];
        calculo.setText(temp);

        // then return the recyclable view
        return currentItemView;
    }
}
