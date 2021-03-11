package com.movil.cens.app.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.movil.cens.app.R;
import com.movil.cens.app.data.encuesta.Encuesta;

import java.util.List;

public class EncuestaListAdapter extends ArrayAdapter<Encuesta> {


    private final Activity context;

    private final List<Encuesta> encuestaList;

    public EncuestaListAdapter(Activity context, List<Encuesta> encuestaList) {
        super(context, R.layout.item_encuesta, encuestaList);

        this.context = context;
        this.encuestaList = encuestaList;

    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_encuesta, null, true);

        TextView textview_nombre_encuesta = rowView.findViewById(R.id.textview_nombre_encuesta);
        TextView textview_descripcion_encuesta = rowView.findViewById(R.id.textview_descripcion_encuesta);



        textview_nombre_encuesta.setText(encuestaList.get(position).getNombre());
        textview_descripcion_encuesta.setText(encuestaList.get(position).getDescripcion());

        return rowView;

    }


}
