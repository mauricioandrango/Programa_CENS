package com.movil.cens.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.movil.cens.app.data.encuestacompleta.EncuestaCompletaConRespuestasDTO;
import com.movil.cens.app.data.encuestacompleta.EncuestaCompletaDTO;
import com.movil.cens.app.data.encuestacompleta.EncuestaCompletaResponseDTO;
import com.movil.cens.app.data.encuestacompleta.PreguntaDTO;
import com.movil.cens.app.data.encuestacompleta.PreguntaEncuestaViewDTO;
import com.movil.cens.app.data.respuestas.EnviarRespuestaRequestDTO;
import com.movil.cens.app.data.respuestas.RespuestaDTO;
import com.movil.cens.app.data.usuario.LoginRequestDTO;
import com.movil.cens.app.data.usuario.LoginResponseDTO;
import com.movil.cens.app.utils.ConstantUtils;
import com.movil.cens.app.utils.FunctionUtils;
import com.movil.cens.app.utils.SharedPrefencesUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.movil.cens.app.utils.ConstantUtils.TAG_APP;

/*
 * Activity para Renderizar la Encuesta con las preguntas y enviar las respuestas
 * preguntas: abiertas, si/no opciones, numericas
 * */

public class EncuestaActivity extends AppCompatActivity {

    private int ID_ENCUESTA_SELECCIONADA = -1;
    private EncuestaCompletaDTO encuestaCompletaDTO = null;
    //Componentes graficos
    LinearLayout linearLayoutContenedorEncuesta;
    Button button_guardar;
    //Objeo par almacenar la encuesta con respuestas
    EncuestaCompletaConRespuestasDTO encuestaCompletaConRespuestasDTO = new EncuestaCompletaConRespuestasDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encusta);
        linearLayoutContenedorEncuesta = (LinearLayout) findViewById(R.id.contenedor_encuesta);
        button_guardar = findViewById(R.id.button_guardar);
        cargarDatosScreenAnterior();
        button_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG_APP, "Encuesta contestada");
                guardarEncuesta();
            }
        });
    }


    private void cargarDatosScreenAnterior() {
        try {
            ID_ENCUESTA_SELECCIONADA = getIntent().getExtras().getInt(ConstantUtils.KEY_ID_ENCUESTA);
            cargarEncuesta();
        } catch (Exception e) {
            FunctionUtils.mostrarToast("No se pudo cargar la encuesta Seleccionada", getApplicationContext());
        }

    }

    private void cargarEncuesta() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ConstantUtils.REST_API_ENCUESTA_COMPLETA_BY_ID + ID_ENCUESTA_SELECCIONADA;
        final ModelMapper modelMapper = new ModelMapper();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //obtener respuesta como string y parsearla a un objet  java
                        FunctionUtils.mostrarToast(response, getApplicationContext());
                        Log.i(TAG_APP, response);
                        //parseo
                        try {
                            Gson gson = new Gson();
                            JSONObject obj = new JSONObject(response);
                            //Se transforma  a un DTO Java
                            EncuestaCompletaResponseDTO genericResponse = gson.fromJson(obj.toString(), EncuestaCompletaResponseDTO.class);
                            //obtener la lista de encuestas y guaardarla como variable global
                            encuestaCompletaDTO = genericResponse.getData();
                            //copiar a  oBJETO DE ENCUESTA CON RESPUESTAS
                            encuestaCompletaConRespuestasDTO.setId(encuestaCompletaDTO.getId());
                            encuestaCompletaConRespuestasDTO.setNombre(encuestaCompletaDTO.getNombre());
                            encuestaCompletaConRespuestasDTO.setDescripcion(encuestaCompletaDTO.getDescripcion());
                            encuestaCompletaConRespuestasDTO.setPreguntas(new ArrayList<PreguntaEncuestaViewDTO>());
                            encuestaCompletaConRespuestasDTO.setRespuestas(new ArrayList<RespuestaDTO>());

                            cargarEncuestaGraficamente(encuestaCompletaDTO);
                            Log.i(TAG_APP, genericResponse.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG_APP, "Error parseando reespuesta: " + e.getMessage());
                            FunctionUtils.mostrarToast("Existio un error obteniendo la encuesta", getApplicationContext());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FunctionUtils.mostrarToast("Existio un error obteniendo la encuesta" + error.getLocalizedMessage(), getApplicationContext());
                Log.e(TAG_APP, error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        ;


    }

    private void cargarEncuestaGraficamente(EncuestaCompletaDTO encuestaCompletaDTO) {
        //cambiar linearlayout
        linearLayoutContenedorEncuesta.setBackgroundColor(Color.TRANSPARENT);
        //Setear el titulo de encuesta
        setTitle(encuestaCompletaDTO.getNombre());
        //Agregar la descripcion de la encuesta
        renderizarTextView(encuestaCompletaDTO.getDescripcion());
        //Recorrer preguntas y renderizar
        for (PreguntaDTO preguntaDTO : encuestaCompletaDTO.getPreguntas()) {
            if (ConstantUtils.TIPO_PREGUNTA_ABIERTA.equals(preguntaDTO.getTipo())) {
                //titulo de la pregunta  un textview
                renderizarTextView(preguntaDTO.getTitulo());
                //la respuesta una caja de texto para todo tipo de carateres
                Integer codigo_id_android_view = renderizarEditTextGeneral(preguntaDTO);
                ///agregar pregunta a objeto de encuesta con id de de android y respuesta
                PreguntaEncuestaViewDTO preguntaEncuestaViewDTO = new PreguntaEncuestaViewDTO();
                preguntaEncuestaViewDTO.setPreguntaDTO(preguntaDTO);
                preguntaEncuestaViewDTO.setIdAndroidView(codigo_id_android_view);
                encuestaCompletaConRespuestasDTO.getPreguntas().add(preguntaEncuestaViewDTO);

            } else if (ConstantUtils.TIPO_PREGUNTA_NUMERICA.equals(preguntaDTO.getTipo())) {
                //titulo de la pregunta  un textview
                renderizarTextView(preguntaDTO.getTitulo());
                //la respuesta una caja de texto para numeros
                Integer codigo_id_android_view = renderizarTextViewNumerico(preguntaDTO);
                ///agregar pregunta a objeto de encuesta con id de de android y respuesta
                PreguntaEncuestaViewDTO preguntaEncuestaViewDTO = new PreguntaEncuestaViewDTO();
                preguntaEncuestaViewDTO.setPreguntaDTO(preguntaDTO);
                preguntaEncuestaViewDTO.setIdAndroidView(codigo_id_android_view);
                encuestaCompletaConRespuestasDTO.getPreguntas().add(preguntaEncuestaViewDTO);

            } else if (ConstantUtils.TIPO_PREGUNTA_OPCIONES.equals(preguntaDTO.getTipo())) {
                //titulo de la pregunta un textview
                renderizarTextView(preguntaDTO.getTitulo());
                //la respuesta un radiogrup o grupo de radiobuttons
                Integer codigo_id_android_view = renderizarRadioGroupOpcionUnica(preguntaDTO);
                ///agregar pregunta a objeto de encuesta con id de de android y respuesta
                PreguntaEncuestaViewDTO preguntaEncuestaViewDTO = new PreguntaEncuestaViewDTO();
                preguntaEncuestaViewDTO.setPreguntaDTO(preguntaDTO);
                preguntaEncuestaViewDTO.setIdAndroidView(codigo_id_android_view);
                encuestaCompletaConRespuestasDTO.getPreguntas().add(preguntaEncuestaViewDTO);

            } else {
                Log.e(TAG_APP, "Tipo de pegunta no definido: " + preguntaDTO.getTipo());
            }


        }


    }

    private Integer renderizarTextViewNumerico(PreguntaDTO preguntaDTO) {
        EditText editText = new EditText(getApplicationContext());
        editText.setHint("Ingrese su respuesta");
        Integer codigo_id_android_view = ViewCompat.generateViewId();
        editText.setId(codigo_id_android_view);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        linearLayoutContenedorEncuesta.addView(editText);
        return codigo_id_android_view;

    }

    private void renderizarTextView(String contenido) {
        TextView textView = new TextView(getApplicationContext());
        textView.setText(contenido);
        linearLayoutContenedorEncuesta.addView(textView);
    }

    private Integer renderizarEditTextGeneral(PreguntaDTO preguntaDTO) {
        EditText editText = new EditText(getApplicationContext());
        editText.setHint("Ingrese su respuesta");
        Integer codigo_id_android_view = ViewCompat.generateViewId();
        editText.setId(codigo_id_android_view);
        linearLayoutContenedorEncuesta.addView(editText);
        return codigo_id_android_view;
    }


    private Integer renderizarRadioGroupOpcionUnica(PreguntaDTO preguntaDTO) {
        Integer codigo_id_android_view = ViewCompat.generateViewId();

        //cargar opciones
        List<String> opciones = FunctionUtils.convertStringToArrayStringOpciones(preguntaDTO.getOpciones());


        ///radiogroup
        RadioGroup radioGroup = new RadioGroup(getApplicationContext());
        radioGroup.setId(codigo_id_android_view);
        //opciones
        for (String opcion : opciones) {
            RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText(opcion);
            radioGroup.addView(radioButton);
        }

        if (radioGroup.getChildCount() > 0)
            radioGroup.check(radioGroup.getChildAt(0).getId());

        //agregar al contenedor
        linearLayoutContenedorEncuesta.addView(radioGroup);
        return codigo_id_android_view;
    }

    private void renderizarPreguntaOpciones(PreguntaDTO preguntaDTO) {

        List<String> opciones = FunctionUtils.convertStringToArrayStringOpciones(preguntaDTO.getOpciones());

        final RadioButton[] rb = new RadioButton[opciones.size()];
        LinearLayout layoutRadioGroup = new LinearLayout(this); //create the RadioGroup layour
        layoutRadioGroup.setOrientation(LinearLayout.VERTICAL);
        layoutRadioGroup.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
        Integer codigo_id_android_view = ViewCompat.generateViewId();
        layoutRadioGroup.setId(codigo_id_android_view);
        //rg.setId();
        // Create botones
        for (int i = 0; i < opciones.size(); i++) {

            rb[i] = new RadioButton(this);
            rb[i].setText(opciones.get(i));

            rb[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (((RadioButton) v).isSelected()) {
                        ((RadioButton) v).setChecked(false);
                        ((RadioButton) v).setSelected(false);
                    } else {
                        ((RadioButton) v).setChecked(true);
                        ((RadioButton) v).setSelected(true);
                    }


                }
            });
            layoutRadioGroup.addView(rb[i]);
        }

        //Add button to LinearLayout defined in XML
        linearLayoutContenedorEncuesta.addView(layoutRadioGroup);

    }


    private void guardarEncuesta() {
        encuestaCompletaConRespuestasDTO.setRespuestas(new ArrayList<RespuestaDTO>());
        //recorrer preguntas y obtener la respuesta
        for (PreguntaEncuestaViewDTO preguntaView : encuestaCompletaConRespuestasDTO.getPreguntas()) {
            if (preguntaView.getPreguntaDTO().getTipo().equals(ConstantUtils.TIPO_PREGUNTA_ABIERTA)) {

                EditText editTextRespuesta = findViewById(preguntaView.getIdAndroidView());
                RespuestaDTO respuestaDTO = new RespuestaDTO();
                respuestaDTO.setPreguntaId(preguntaView.getPreguntaDTO().getId());
                respuestaDTO.setRespuesta(editTextRespuesta.getText().toString());
                //Agregar respuestas
                encuestaCompletaConRespuestasDTO.getRespuestas().add(respuestaDTO);

            } else if (preguntaView.getPreguntaDTO().getTipo().equals(ConstantUtils.TIPO_PREGUNTA_NUMERICA)) {
                EditText editTextRespuesta = findViewById(preguntaView.getIdAndroidView());
                RespuestaDTO respuestaDTO = new RespuestaDTO();
                respuestaDTO.setPreguntaId(preguntaView.getPreguntaDTO().getId());
                respuestaDTO.setRespuesta(editTextRespuesta.getText().toString());
                //Agregar respuestas
                encuestaCompletaConRespuestasDTO.getRespuestas().add(respuestaDTO);

            } else if (preguntaView.getPreguntaDTO().getTipo().equals(ConstantUtils.TIPO_PREGUNTA_OPCIONES)) {
                RadioGroup radioGroupRespuesta = findViewById(preguntaView.getIdAndroidView());
                // Get the checked Radio Button ID from Radio Grou[
                int selectedRadioButtonID = radioGroupRespuesta.getCheckedRadioButtonId();

                // If nothing is selected from Radio Group, then it return -1
                RespuestaDTO respuestaDTO = new RespuestaDTO();
                respuestaDTO.setPreguntaId(preguntaView.getPreguntaDTO().getId());
                if (selectedRadioButtonID != -1) {
                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    String selectedRadioButtonText = selectedRadioButton.getText().toString();
                    respuestaDTO.setRespuesta(selectedRadioButtonText);
                }
                //Agregar respuestas
                encuestaCompletaConRespuestasDTO.getRespuestas().add(respuestaDTO);
            }

        }
        //Imprimir respuestas
        Log.i(TAG_APP,"******RESPUESTAS DE ENCUESTA****");
        Integer encuestaId = encuestaCompletaConRespuestasDTO.getId();
        Integer usuarioId = SharedPrefencesUtils.obtenerUsuarioLogeado(getApplicationContext()).getId();
        Log.i(TAG_APP,  "encuestaId: " + encuestaId);
        Log.i(TAG_APP,  "usuarioId: " + usuarioId);

        for (RespuestaDTO respuestaDTO : encuestaCompletaConRespuestasDTO.getRespuestas()) {
            Log.i(TAG_APP, respuestaDTO.getPreguntaId()+"/"+ respuestaDTO.getRespuesta());
        }

        //Armar OBJETO para enviarlo al Servicio de Encio de Respuestas
        EnviarRespuestaRequestDTO enviarRespuestaRequestDTO = new EnviarRespuestaRequestDTO();
        enviarRespuestaRequestDTO.setEncuestaId(encuestaId);
        enviarRespuestaRequestDTO.setUsuarioId(usuarioId);
        enviarRespuestaRequestDTO.setRespuestas(encuestaCompletaConRespuestasDTO.getRespuestas());

        enviarRespuestasRESTAPI(enviarRespuestaRequestDTO);
    }

    public void enviarRespuestasRESTAPI(EnviarRespuestaRequestDTO enviarRespuestaRequestDTO ){
        //iniciar GSON
        final Gson gson = new Gson();
        //parsear Objeto request a JSON string
        JSONObject js = new JSONObject();
        String jsonString = gson.toJson(enviarRespuestaRequestDTO);
        try {
            js = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, ConstantUtils.REST_API_ENVIAR_RESPUESTAS_ENCUESTA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    LoginResponseDTO loginResponse = gson.fromJson(obj.toString(), LoginResponseDTO.class);
                    if (loginResponse.getCode().equals(ConstantUtils.CODE_200)) {
                        FunctionUtils.mostrarToast(loginResponse.getMessage(), getApplicationContext());
                        abrirPantallPrincipal();
                    } else {
                        FunctionUtils.mostrarToast("Error: "+ loginResponse.getMessage() , getApplicationContext());
                    }
                } catch (JSONException e) {
                    FunctionUtils.mostrarToast("Existio un error al almacenar la encuesta", getApplicationContext());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FunctionUtils.mostrarToast("Existio un error al almacenar la encuesta", getApplicationContext());
            }
        }) {

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    private void abrirPantallPrincipal() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}
