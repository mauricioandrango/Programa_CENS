package com.movil.cens.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.movil.cens.app.data.usuario.UsuarioDTO;

public class SharedPrefencesUtils {

    public static void guardarUsuario(UsuarioDTO usuarioDTO, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.SHARED_PREFERENCES_APP, context.MODE_PRIVATE);
        preferences.edit().putString(ConstantUtils.FIELD_PREFERENCES_USERNAME, usuarioDTO.getUsername()).apply();
        preferences.edit().putInt(ConstantUtils.FIELD_PREFERENCES_ID, usuarioDTO.getId()).apply();
        preferences.edit().putString(ConstantUtils.FIELD_PREFERENCES_APELLIDOS, usuarioDTO.getApellidos()).apply();
        preferences.edit().putString(ConstantUtils.FIELD_PREFERENCES_NOMBRES, usuarioDTO.getNombres()).apply();
        preferences.edit().putString(ConstantUtils.FIELD_PREFERENCES_MAIL, usuarioDTO.getMail()).apply();

    }
    public static void logOutUsuario(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.SHARED_PREFERENCES_APP, context.MODE_PRIVATE);
        preferences.edit().putString(ConstantUtils.FIELD_PREFERENCES_USERNAME, "").apply();
        preferences.edit().putInt(ConstantUtils.FIELD_PREFERENCES_ID, -1).apply();
        preferences.edit().putString(ConstantUtils.FIELD_PREFERENCES_APELLIDOS, "").apply();
        preferences.edit().putString(ConstantUtils.FIELD_PREFERENCES_NOMBRES, "").apply();
        preferences.edit().putString(ConstantUtils.FIELD_PREFERENCES_MAIL, "").apply();

    }
    public static UsuarioDTO obtenerUsuarioLogeado(Context context) {
        try {

            SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.SHARED_PREFERENCES_APP, context.MODE_PRIVATE);

            UsuarioDTO usuarioDTOLogeado = new UsuarioDTO();

            String username = preferences.getString(ConstantUtils.FIELD_PREFERENCES_USERNAME, null);
            Integer idUsuario = preferences.getInt(ConstantUtils.FIELD_PREFERENCES_ID, -1);
            String apellidos = preferences.getString(ConstantUtils.FIELD_PREFERENCES_APELLIDOS, null);
            String nombres = preferences.getString(ConstantUtils.FIELD_PREFERENCES_NOMBRES, null);
            String mail = preferences.getString(ConstantUtils.FIELD_PREFERENCES_MAIL, null);

            usuarioDTOLogeado.setId(idUsuario);
            usuarioDTOLogeado.setUsername(username);
            usuarioDTOLogeado.setNombres(nombres);
            usuarioDTOLogeado.setApellidos(apellidos);
            usuarioDTOLogeado.setMail(mail);
            //verificar si id es nulo
            if(usuarioDTOLogeado.getId()==-1){
                Log.e(ConstantUtils.TAG_APP, "Usuario no logeado");
                return  null;
            }
            return usuarioDTOLogeado;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }


    }

    public void borrarDatosUsuario(Context context){
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.SHARED_PREFERENCES_APP, context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(ConstantUtils.FIELD_PREFERENCES_USERNAME);
        editor.remove(ConstantUtils.FIELD_PREFERENCES_ID);
        editor.remove(ConstantUtils.FIELD_PREFERENCES_APELLIDOS);
        editor.remove(ConstantUtils.FIELD_PREFERENCES_NOMBRES);
        editor.remove(ConstantUtils.FIELD_PREFERENCES_MAIL);
        editor.commit();
    }


}
