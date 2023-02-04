package com.example.janmitr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText inputEmail,inputPassword;
    Button btnLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog processDialog;

    public static boolean isTouchAccessiblityEnabled(Context context) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        boolean isAccessibilityEnabled = am.isEnabled();
        boolean isTouchExplorationEnabled = am.isTouchExplorationEnabled();
        return isAccessibilityEnabled || isTouchExplorationEnabled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickOnLogin(View view) {

        EditText eTxtEmailAddress = findViewById(R.id.email);
        EditText eTxtPassword = findViewById(R.id.password);

        TextInputLayout email_field = findViewById(R.id.email_field);
        TextInputLayout password_field = findViewById(R.id.password_field);
        if(eTxtEmailAddress.getText().toString().isEmpty()){
            email_field.setError("Please Enter Email");
        }else if(eTxtPassword.getText().toString().isEmpty()){
            password_field.setError("Please Enter Password");
        }else {
            processDialog = new ProgressDialog(this);
            processDialog.setMessage("Please Wait While Login");
            processDialog.setTitle("Login");
            processDialog.setCanceledOnTouchOutside(false);
            processDialog.show();
            String apikey = "JANMITR@API-ANDROID";
            String password = "456123789";
            String url = "https://www.janmitr.com/android_api/login_janmitr.html";

            Log.i("Info", eTxtEmailAddress.getText().toString() + " and " + eTxtPassword.getText().toString());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Res : ", response);
                            // https://www.youtube.com/watch?v=7I2jep4kgys Help Video
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean login = jsonObject.getBoolean("success");
                                if (login) {
                                    JSONObject janmitr = jsonObject.getJSONObject("janmitr");
                                    if (janmitr.length() > 0) {
                                        String email = janmitr.getString("email_id");
                                        Integer janmitr_id = janmitr.getInt("id");
                                        String name = janmitr.getString("name");
                                        SharedPreferences shrd = getSharedPreferences("login", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = shrd.edit();
                                        editor.putInt("janmitr_id", janmitr_id);
                                        editor.putString("name", name);
                                        editor.putString("email", email);
                                        editor.apply();
                                        processDialog.dismiss();
                                        sendUserToNextActivity();
                                    }
                                } else {
                                    processDialog.dismiss();
                                    Toast.makeText(MainActivity.this,"Email and Password not match.",Toast.LENGTH_SHORT).show();
                                    //sendUserToNextActivity();
                                    //Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    //startActivity(intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    error -> Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show()) {
                // Add Parameters to the request
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("apikey", apikey);
                    params.put("password", password);
                    params.put("email_address", eTxtEmailAddress.getText().toString());
                    params.put("janmitr_password", eTxtPassword.getText().toString());
                    return params;
                }
            };

            requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}