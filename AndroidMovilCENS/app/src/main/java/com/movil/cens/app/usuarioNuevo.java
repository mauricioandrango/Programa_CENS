package com.movil.cens.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.movil.cens.app.data.usuario.LoginRequestDTO;
import com.movil.cens.app.data.usuario.LoginResponseDTO;
import com.movil.cens.app.data.usuario.ResetPasswordResponseDTO;
import com.movil.cens.app.data.usuario.UsuarioDTO;
import com.movil.cens.app.data.usuario.nuevoUsuarioResponseDTO;
import com.movil.cens.app.utils.ConstantUtils;
import com.movil.cens.app.utils.FunctionUtils;
import com.movil.cens.app.utils.SharedPrefencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class usuarioNuevo extends AppCompatActivity {
    Button btn_ir_inicioSesion,crear_cuenta;
    EditText et_nombre,et_apellido,et_correo,et_contrasena,et_nomUsuario;
    Gson gson;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_nuevo);

        gson = new Gson();

        btn_ir_inicioSesion=findViewById(R.id.btn_ir_inicioSesion);
        crear_cuenta=findViewById(R.id.crear_cuenta);
        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        et_correo = findViewById(R.id.et_correo);
        et_contrasena = findViewById(R.id.et_contrasena);
        et_nomUsuario = findViewById(R.id.et_nomUsuario);


        btn_ir_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(usuarioNuevo.this,LoginActivity.class));
                finish();
            }
        });

        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(usuarioNuevo.this, "Registrar Usuario", Toast.LENGTH_SHORT).show();
                //Aquí codigo
                registrarNuevoUsuario();
            }
        });
    }
    private void registrarNuevoUsuario() {
        dialog = ProgressDialog.show(usuarioNuevo.this, "",
                "Cargando. Por Favor Espere...", true);
        //Obtener username y password
        String nombre  = et_nombre.getText().toString();
        String apellido  = et_apellido.getText().toString();
        String correo  = et_correo.getText().toString();
        String username  = et_nomUsuario.getText().toString();
        String password  = et_contrasena.getText().toString();

        final UsuarioDTO userRequest = new UsuarioDTO();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setNombres(nombre);
        userRequest.setApellidos(apellido);
        userRequest.setMail(correo);

        JSONObject js = new JSONObject();
        String jsonString = gson.toJson(userRequest);
        try {
            js = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, ConstantUtils.REST_API_REST_nuevoUsuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    LoginResponseDTO loginResponse = gson.fromJson(obj.toString(), LoginResponseDTO.class);
                    if (loginResponse.getCode().equals(ConstantUtils.CODE_200)) {
                        FunctionUtils.mostrarToast("Registro Correcto porfavor inicie sesion con su nuevo usuario", getApplicationContext());
                        goToLogin();


                    } else {

                        FunctionUtils.mostrarToast("Error: "+ loginResponse.getMessage() , getApplicationContext());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FunctionUtils.mostrarToast("Existio un error en el registro", getApplicationContext());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                FunctionUtils.mostrarToast("Existio un error en el registro", getApplicationContext());
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

    private void goToLogin() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}

