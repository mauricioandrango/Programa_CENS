package com.movil.cens.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.movil.cens.app.data.encuesta.Encuesta;
import com.movil.cens.app.data.encuesta.EncuestaListResponseDTO;
import com.movil.cens.app.data.usuario.UsuarioDTO;
import com.movil.cens.app.utils.ConstantUtils;
import com.movil.cens.app.utils.EncuestaListAdapter;
import com.movil.cens.app.utils.FunctionUtils;
import com.movil.cens.app.utils.SharedPrefencesUtils;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.movil.cens.app.utils.ConstantUtils.SHARED_PREFERENCES_APP;
import static com.movil.cens.app.utils.ConstantUtils.TAG_APP;

public class MainActivity extends AppCompatActivity {

    ListView listviewEncuestas;
    List<Encuesta> encuestasList = new ArrayList<>();
    TextView textViewNombreUsuario, textViewMensajeListaEncuestas;
    Button buttonLogout;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicializar elementos GUI
        textViewNombreUsuario = findViewById(R.id.textViewNombreUsuario);
        textViewMensajeListaEncuestas = findViewById(R.id.textViewMensajeListaEncuestas);
        buttonLogout = findViewById(R.id.buttonLogout);

        listviewEncuestas = findViewById(R.id.listviewEncuestas);
        //verificar si el usuario esta logeado o si noe nviarle a la pantalla de login
        suscribirTopic();
        boolean usuarioLogeado = FunctionUtils.existeUsuarioActivo(getApplicationContext());
        if (!usuarioLogeado) {
            abrirLogin();
        }
        if (usuarioLogeado) {
            cargarDatosUsuario();
            listarEncuestas();
        }
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        textViewMensajeListaEncuestas.setVisibility(View.GONE);
    }

    private void cargarDatosUsuario() {
        UsuarioDTO usuarioDTO = SharedPrefencesUtils.obtenerUsuarioLogeado(getApplicationContext());
        textViewNombreUsuario.setText(usuarioDTO.getUsername());
        textViewNombreUsuario.append(" ("+usuarioDTO.getNombres() + " "+usuarioDTO.getApellidos()+")");
    }

    private void logout() {
        try {
            SharedPrefencesUtils.logOutUsuario(getApplicationContext());
            FunctionUtils.mostrarToast("Se cerro sesion correctamente", getApplicationContext());
            //recargar pantalla
            finish();
            startActivity(getIntent());
        }catch (Exception e){
            FunctionUtils.mostrarToast("Error cerrando sesión", getApplicationContext());
        }
    }

    private void abrirLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void listarEncuestas() {

        dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading. Please wait...", true);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ConstantUtils.REST_API_ALL_ENCUESTAS_BY_USER + SharedPrefencesUtils.obtenerUsuarioLogeado(getApplicationContext()).getId();
        final ModelMapper modelMapper = new ModelMapper();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        //obtener respuesta como string y parsearla a un objet  java
                        FunctionUtils.mostrarToast(response, getApplicationContext());
                        Log.i(TAG_APP, response);
                        //parseo
                        try {
                            Gson gson = new Gson();
                            JSONObject obj = new JSONObject(response);
                            //Se transforma  a un DTO Java
                            EncuestaListResponseDTO genericResponse = gson.fromJson(obj.toString(), EncuestaListResponseDTO.class);
                            //obtener la lista de encuestas y guaardarla como variable global
                            encuestasList = genericResponse.getData();
                            poblarListViewEncuestasAdapter();
                            Log.i(TAG_APP, genericResponse.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG_APP, "Error parseando reespuesta: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                FunctionUtils.mostrarToast("Existio un error obteniendo las encuestas" + error.getLocalizedMessage(), getApplicationContext());
                if (error.getLocalizedMessage() != null) {
                    Log.e(TAG_APP, error.getLocalizedMessage());
                }
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void poblarListview(List<String> stringArrayList) {


        ArrayAdapter<String> favefoodadapter = new ArrayAdapter<String>(
                getApplicationContext(), //some context for the activity
                android.R.layout.simple_list_item_1, //layout to be displayed(create)
                stringArrayList); //strings to be diplayed


        listviewEncuestas.setAdapter(favefoodadapter);
        listviewEncuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.e(TAG_APP, "Selecciono la encuesta: " + encuestasList.get(position).getId() + "/" + encuestasList.get(position).getNombre());
                    abrirEncuestaCompleta(encuestasList.get(position).getId());
                } catch (Exception e) {
                    Log.e(TAG_APP, "Error seleccionando la encuesta con indice: " + position);
                }
            }
        });
    }

    private  void poblarListViewEncuestasAdapter(){


        EncuestaListAdapter adapter = new EncuestaListAdapter(this, encuestasList );
        if(encuestasList.isEmpty()){
            //show message no existen encuestas asignadas a su usuario
            textViewMensajeListaEncuestas.setText("Todavia no existen Encuestas asignadas a su usuario");
            textViewMensajeListaEncuestas.setVisibility(View.VISIBLE);

        }

        listviewEncuestas.setAdapter(adapter);
        listviewEncuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.e(TAG_APP, "Selecciono la encuesta: " + encuestasList.get(position).getId() + "/" + encuestasList.get(position).getNombre());
                    abrirEncuestaCompleta(encuestasList.get(position).getId());
                } catch (Exception e) {
                    Log.e(TAG_APP, "Error seleccionando la encuesta con indice: " + position);
                }
            }
        });

    }

    private void abrirEncuestaCompleta(Integer idEncuesta) {
        Intent intent = new Intent(this, EncuestaActivity.class);
        intent.putExtra(ConstantUtils.KEY_ID_ENCUESTA, idEncuesta);
        startActivity(intent);
        //finish();
    }

    private List<String> encuestasToArrayString(List<Encuesta> encuestas) {
        List<String> list = new ArrayList<>();
        for (Encuesta encuesta : encuestas) {
            list.add(encuesta.toString());

        }
        return list;
    }

    public  void suscribirTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic("CENS_APP")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "suscrito";
                        if (!task.isSuccessful()) {
                            msg = "fail";
                        }
                        Log.d(TAG_APP, msg);
                       // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
