package com.example.janmitr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends AppCompatActivity{
    DrawerLayout drawerLayout;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    Toolbar toolbarr;
    RequestQueue requestQueue;
    private MenuItem item;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        headerNavtigation();

        SharedPreferences getShared = getSharedPreferences("login",MODE_PRIVATE);
        String email_id = getShared.getString("email","Please login again");
        Integer janmitr_id = getShared.getInt("janmitr_id",0);
        String name = getShared.getString("name","Name");
        Log.i("SharedItem",janmitr_id.toString());

        String apikey = "JANMITR@API-ANDROID";
        String password = "456123789";
        String url = "https://www.janmitr.com/android_api/home_activity.html";

        //Log.i("Info", eTxtEmailAddress.getText().toString() + " and " +eTxtPassword.getText().toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Res : ",response);
                        // https://www.youtube.com/watch?v=7I2jep4kgys Help Video
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success)
                            {
                                JSONObject janmitr = jsonObject.getJSONObject("janmitr");
                                if(janmitr.length()>0)
                                {
                                    TextView lblcredit = findViewById(R.id.lblCredit);
                                    TextView lblcoordinator = findViewById(R.id.lblCoordinator);
                                    TextView lblinvited = findViewById(R.id.lblInvited);
                                    TextView lblsamudaay = findViewById(R.id.lblSamudaay);
                                    TextView lblservice = findViewById(R.id.lblService);
                                    lblcredit.setText(janmitr.getString("credit"));
                                    lblcoordinator.setText(janmitr.getString("cordinator"));
                                    lblinvited.setText(janmitr.getString("invited"));
                                    lblsamudaay.setText(janmitr.getString("samudaay_member"));
                                    lblservice.setText(janmitr.getString("services"));
                                }
                            }
                            else
                            {
                                Toast.makeText(HomeActivity.this, "Connection Error..!", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                error -> Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_LONG).show()) {
            // Add Parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("apikey",apikey);
                params.put("password",password);
                params.put("janmitr_id",janmitr_id.toString());
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(stringRequest);


    }
    public void headerNavtigation(){
        DrawerLayout drawerLayout;
        NavigationView nav;
        ActionBarDrawerToggle toggle;
        Toolbar toolbarr;


        toolbarr = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        nav = findViewById(R.id.navigation_view);
        toolbarr.setTitle("Janmitr");
        setSupportActionBar(toolbarr);
        ActionBar actionBar = getSupportActionBar();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbarr,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        nav.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_menu) {
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
            }else if (item.getItemId() == R.id.coordinato_menu) {
                Intent intent = new Intent(this, CoordinatorActivity.class);
               startActivity(intent);
            }else{
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
            }
            return true;
        });


    }



}