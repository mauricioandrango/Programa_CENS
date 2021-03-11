package com.movil.cens.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.movil.cens.app.utils.ConstantUtils;
import com.movil.cens.app.utils.FunctionUtils;
import com.movil.cens.app.utils.SharedPrefencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsuario;
    EditText editTextClave;
    Button buttonLogin;
    Button buttonRecuperarClave;
    Button btn_ir_crearCuenta;
    ProgressDialog dialog;

    Gson gson;


    //RelativeLayout rellay1, rellay2;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //rellay1.setVisibility(View.VISIBLE);
//            rellay2.setVisibility(View.VISIBLE);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //splash
       // rellay1 = (RelativeLayout) findViewById(R.id.rellay1);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash



        //inicializar  componentes
        editTextUsuario = findViewById(R.id.editText_usuario);
        editTextClave = findViewById(R.id.editText_clave);
        buttonLogin = findViewById(R.id.button_login);
        buttonRecuperarClave = findViewById(R.id.button_recuperarClave);
        btn_ir_crearCuenta=findViewById(R.id.btn_ir_crearCuenta);

        //iniciar GSON
        gson = new Gson();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarLogin();
            }
        });
        buttonRecuperarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarclave();
            }
        });

        //crear cuenta
        btn_ir_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,usuarioNuevo.class));
                finish();
            }
        });


    }

    private void llamarLogin() {
        dialog = ProgressDialog.show(LoginActivity.this, "",
                "Loading. Please wait...", true);
        //Obtener username y password
        String username  = editTextUsuario.getText().toString();
        String password = editTextClave.getText().toString();

        final LoginRequestDTO userRequest = new LoginRequestDTO();
        userRequest.setUsername(username);
        userRequest.setPassword(password);

        JSONObject js = new JSONObject();
        String jsonString = gson.toJson(userRequest);
        try {
            js = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, ConstantUtils.REST_API_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONObject obj = new JSONObject(response);
                    LoginResponseDTO loginResponse = gson.fromJson(obj.toString(), LoginResponseDTO.class);
                    if (loginResponse.getCode().equals(ConstantUtils.CODE_200)) {
                        FunctionUtils.mostrarToast("Login Correcto", getApplicationContext());
                        //Guardar el usuario ogeado en las preferencias para que se quede la sesion abierta
                        SharedPrefencesUtils.guardarUsuario(loginResponse.getData(), getApplicationContext());
                        abrirPantallPrincipal();

                    } else {

                        FunctionUtils.mostrarToast("Error: "+ loginResponse.getMessage() , getApplicationContext());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FunctionUtils.mostrarToast("Existio un error en el login", getApplicationContext());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                FunctionUtils.mostrarToast("Existio un error en el login", getApplicationContext());
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


    private void recuperarclave(){
        dialog = ProgressDialog.show(LoginActivity.this, "",
                "Loading. Please wait...", true);
        //Obtener username y password
        String username  = editTextUsuario.getText().toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, ConstantUtils.REST_API_REST_PASSWORD+username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    ResetPasswordResponseDTO loginResponse = gson.fromJson(obj.toString(), ResetPasswordResponseDTO.class);
                    if (loginResponse.getCode().equals(ConstantUtils.CODE_200)) {
                        FunctionUtils.mostrarToast("Clave recuperada porfavor ingrese con sus nuevas credenciales", getApplicationContext());

                    } else {

                        FunctionUtils.mostrarToast("Error: "+ loginResponse.getMessage() , getApplicationContext());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FunctionUtils.mostrarToast("Existio un error al recuperar la clave", getApplicationContext());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                FunctionUtils.mostrarToast("Existio un error al recuperar clave del usuario", getApplicationContext());
            }
        }) {

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }



            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    private void abrirPantallPrincipal(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
