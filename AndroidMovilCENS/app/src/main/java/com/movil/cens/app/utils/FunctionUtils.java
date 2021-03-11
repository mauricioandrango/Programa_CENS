package com.movil.cens.app.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.movil.cens.app.LoginActivity;
import com.movil.cens.app.MainActivity;
import com.movil.cens.app.data.usuario.UsuarioDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class FunctionUtils {

    public static void mostrarToast(String mensaje, Context context) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }

    public static boolean existeUsuarioActivo(Context context) {
        UsuarioDTO usuario = SharedPrefencesUtils.obtenerUsuarioLogeado(context);
        if (usuario == null) {
            return false;
        }
        Log.i(ConstantUtils.TAG_APP, "Usuario logeado: " + usuario.toString());
        return true;

    }

    public static List <String> convertStringToArrayStringOpciones(String opcionesString){
        List<String> listaOpciones= new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(opcionesString, ConstantUtils.DELIMITADOR_OPCIONES);
        while (tokenizer.hasMoreElements()){
            listaOpciones.add(tokenizer.nextToken());

        }
        return listaOpciones;

    }


    // Generic function to convert set to list
    public static <T> List<T> convertToList(Set<T> set)
    {
        return new ArrayList<>(set);
    }

}
