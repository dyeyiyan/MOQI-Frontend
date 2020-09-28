package com.ardeidei.moqi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ardeidei.moqi.connection.VolleySingleton;
import com.ardeidei.moqi.connection.WebsiteCon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    WebsiteCon websiteCon = new WebsiteCon();
    EditText etUsername, etPass;
    Button btnLogin;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.al_et_username);
        etPass = findViewById(R.id.al_et_password);
        btnLogin = findViewById(R.id.al_btn_login);
        dialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateUsername() | !validatePass()) {
                    dialog.dismiss();
                    return;
                } else {
                    login();
                }
            }
        });

    }

    private boolean validatePass() {
        String passInput = etPass.getText().toString().trim();
        if (passInput.isEmpty()) {
            Toast.makeText(Login.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etPass.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String userInput = etUsername.getText().toString().trim();
        if (userInput.isEmpty()) {
            Toast.makeText(Login.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etUsername.setError(null);
            return true;
        }

    }

    private void login() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in");

        String requestUrl = websiteCon.getURL() + "login";
        final String nUsername = etUsername.getText().toString().trim();
        final String nPass = etPass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(Login.this, obj.getString("response"), Toast.LENGTH_SHORT).show();
                                //FBToast.warningToast(getApplicationContext(),obj.getString("response"),FBToast.LENGTH_LONG);
                            } else {
                                dialog.dismiss();
                                Intent homeIntent = new Intent(Login.this, MainActivity.class);
                                startActivity(homeIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", nUsername);
                params.put("password", nPass);
                return params;
            }
        };
        VolleySingleton.getInstance(Login.this).addToRequestQueue(stringRequest);
    }
}
