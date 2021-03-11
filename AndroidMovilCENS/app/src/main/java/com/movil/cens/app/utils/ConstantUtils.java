package com.movil.cens.app.utils;

public class ConstantUtils {

    public static String TAG_APP= "CENSAPP";
    public static String HOST_BACKEND = "http://192.168.1.5:80";
    //response crear usuario
    public static String REST_API_REST_nuevoUsuario = HOST_BACKEND + "/registrarUsuarioMovil";
    public static String REST_API_LOGIN = HOST_BACKEND + "/login";
    public static String REST_API_REST_PASSWORD= HOST_BACKEND + "/resetPassword/";


    public static String REST_API_ALL_ENCUESTAS = HOST_BACKEND + "/allEncuestas";
    public static String REST_API_ALL_ENCUESTAS_BY_USER = HOST_BACKEND + "/allEncuestasByUser/";

    public static String REST_API_ENCUESTA_COMPLETA_BY_ID = HOST_BACKEND + "/findEncuestaConPreguntasById/";
    public static String REST_API_ENVIAR_RESPUESTAS_ENCUESTA = HOST_BACKEND + "/enviarRespuestasEncuesta";

    //Errores de codigo
    public static String CODE_200= "200";

    ///Nombre de archivo shared preferences
    public  static  String SHARED_PREFERENCES_APP = "preferences_cens_app";

    ///caMPOS DEL SHARED Preferences
    public static final String FIELD_PREFERENCES_ID = "id";
    public static final String FIELD_PREFERENCES_APELLIDOS = "apellidos";
    public static final String FIELD_PREFERENCES_MAIL = "mail";
    public static final String FIELD_PREFERENCES_USERNAME =  "username";
    public static final String FIELD_PREFERENCES_NOMBRES = "nombres";

    //Key para pasar datos entre pantallas
    public static final String KEY_ID_ENCUESTA = "KEY_ID_ENCUESTA";
    //tipos de pregunta
    public static final String TIPO_PREGUNTA_ABIERTA= "ABIERTA";
    public static final String TIPO_PREGUNTA_NUMERICA= "NUMERICA";
    public static final String TIPO_PREGUNTA_OPCIONES= "OPCIONES";

    public static final String DELIMITADOR_OPCIONES= ",";

}
