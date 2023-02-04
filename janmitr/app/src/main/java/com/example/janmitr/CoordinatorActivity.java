package com.example.janmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CoordinatorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    List<CoordinatorList> userList;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        headerNavtigation();
        initData();

    }



    public void initData(){
        recyclerView = findViewById(R.id.recyclerView);
        userList = new ArrayList<>();


        String apikey = "JANMITR@API-ANDROID";
        String password = "456123789";
        SharedPreferences getShared = getSharedPreferences("login",MODE_PRIVATE);
        String email_id = getShared.getString("email","Please login again");
        Integer janmitr_id = getShared.getInt("janmitr_id",0);
        String url = "https://www.janmitr.com/android_api/coordinator_activity.html";

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
                                JSONArray coordinator = janmitr.getJSONArray("coordinator");

                                if (coordinator.length() > 0) {
                                    for(int i=0;i<coordinator.length();i++){
                                        JSONObject jsonObject1 = coordinator.getJSONObject(i);
                                        String coName = jsonObject1.getString("name");
                                        String coMobile = jsonObject1.getString("contact");
                                        String coAddress = jsonObject1.getString("address");
                                        String id = jsonObject1.getString("id");
                                        userList.add(new CoordinatorList(coName, coMobile, coAddress,id));

                                    }
                                    ///--------------------------recyclerView adapter----
                                    layoutManager = new LinearLayoutManager(CoordinatorActivity.this,
                                            RecyclerView.VERTICAL,
                                            false);
                                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                                    recyclerView.setLayoutManager(layoutManager);
                                    CoordinatorListAdapter adapter = new CoordinatorListAdapter(userList);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    ///--------------------------recyclerView adapter----


                                    ///--------------------------get data memeber id ----
                                    adapter.OnRecyclerViewClickListener(new CoordinatorListAdapter.OnRecyclerViewClickListener() {
                                        @Override
                                        public void OnItemClick(int value) {
                                            String coordinatorId = String.valueOf(value);
                                            Intent intent = new Intent(CoordinatorActivity.this,MemberActivity.class);
                                            intent.putExtra("coordinatorId", coordinatorId);
                                            startActivity(intent);
                                            //Toast.makeText(CoordinatorActivity.this, value, Toast.LENGTH_LONG).show();

                                        }
                                    });

                                }
                            } else {
                                Toast.makeText(CoordinatorActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> Toast.makeText(CoordinatorActivity.this, "Error", Toast.LENGTH_LONG).show()) {
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
        requestQueue = Volley.newRequestQueue(CoordinatorActivity.this);
        requestQueue.add(stringRequest);
    }

    public void redirectMember(String buttonId){
        Intent intent = new Intent(this, CoordinatorActivity.class);
        startActivity(intent);
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
            }
            return true;
        });




    }
}