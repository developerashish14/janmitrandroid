package com.example.janmitr;

import static com.example.janmitr.R.layout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    Spinner spinner;
    Button add_services;
    EditText Addremark;
    AutoCompleteTextView Addflag,AddService_type;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    List<MemberList> memberList;
    RequestQueue requestQueue;
    Point memberDetails = new Point(0,0);
    private HashMap<String, String> flagKeyValue = new HashMap<String, String>();
    private HashMap<String, String> serviecTypeKeyValue = new HashMap<String, String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_member);

        headerNavtigation();
        //----------------bottom sheet ----------//

        //----------------bottom sheet ----------//


        memberListData();
    }

    private void addService(String Addflag,String AddService_type,String Addremark) {





        SharedPreferences getShared = getSharedPreferences("login",MODE_PRIVATE);
        Integer janmitr_id = getShared.getInt("janmitr_id",0);
        Integer coordinator_id = memberDetails.x;
        Integer member_id = memberDetails.y;
        String flag =flagKeyValue.get(Addflag);
        String service_type = serviecTypeKeyValue.get(AddService_type);
        String remark = Addremark;
        System.out.println("flag value "+janmitr_id+"te"+coordinator_id+"te"+member_id+"te"+flag+"te"+service_type+"te"+remark);
        String apikey = "JANMITR@API-ANDROID";
        String password = "456123789";
        String url = "https://www.janmitr.com/android_api/add_service.html";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Res : ", response);
                        // https://www.youtube.com/watch?v=7I2jep4kgys Help Video
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean service = jsonObject.getBoolean("success");
                            if (service) {
                                Toast.makeText(MemberActivity.this, "Successfully Update Service", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MemberActivity.this, CoordinatorActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MemberActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> Toast.makeText(MemberActivity.this, "Error", Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("apikey",apikey);
                params.put("password",password);
                params.put("janmitr_id",janmitr_id.toString());
                params.put("coordinator_id",coordinator_id.toString());

                params.put("member_id",member_id.toString());
                params.put("flag",flag);
                params.put("service_type",service_type);
                params.put("remark",remark);

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(MemberActivity.this);
        requestQueue.add(stringRequest);





    }



    public void memberListData(){
        recyclerView = findViewById(R.id.recyclerView);
        memberList = new ArrayList<>();

        Intent startingIntent = getIntent();

        SharedPreferences getShared = getSharedPreferences("login",MODE_PRIVATE);


        String apikey = "JANMITR@API-ANDROID";
        String password = "456123789";
        Integer janmitr_id = getShared.getInt("janmitr_id",0);
        String coordinator_id = startingIntent.getStringExtra("coordinatorId");
        String url = "https://www.janmitr.com/android_api/member_activity.html";
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
                                JSONArray members = janmitr.getJSONArray("members");
                                JSONArray service_type = janmitr.getJSONArray("service_type");
                                JSONArray getFlag = janmitr.getJSONArray("flag");
                                if (members.length() > 0) {
                                    for(int i=0;i<members.length();i++){
                                        JSONObject jsonObject1 = members.getJSONObject(i);
                                        String memberName = jsonObject1.getString("name");
                                        String memberId = jsonObject1.getString("id");
                                        memberList.add(new MemberList(memberName, memberId,coordinator_id));

                                    }

                                    //-----------------------flag value adapter ----------------
                                    LayoutInflater inflater = LayoutInflater.from(MemberActivity.this); // 1
                                    View theInflatedView = inflater.inflate(layout.layout_member_bottom, null);

                                    ArrayList<String> flag_value = new ArrayList<String>();
                                    for(int i=0;i<getFlag.length();i++){
                                        JSONObject jsonObjectFlag = getFlag.getJSONObject(i);
                                        String flag_name = jsonObjectFlag.getString("type");
                                        String flag_id = jsonObjectFlag.getString("id");
                                        flag_value.add(flag_name);
                                        flagKeyValue.put(flag_name,flag_id);
                                    }

                                    AutoCompleteTextView flags = theInflatedView.findViewById(R.id.flag);
                                    ArrayAdapter<String> adapter_lists = new ArrayAdapter<String>(MemberActivity.this,
                                            android.R.layout.simple_list_item_1, flag_value);
                                    flags.setAdapter(adapter_lists);
                                    //-----------------------flag value adapter ----------------
                                    //-----------------------serviec value adapter ---------
                                    ArrayList<String> service_value = new ArrayList<String>();
                                    for(int i=0;i<service_type.length();i++){
                                        JSONObject jsonObject2 = service_type.getJSONObject(i);
                                        String service_name = jsonObject2.getString("service");
                                        String service_id = jsonObject2.getString("id");
                                        service_value.add(service_name);
                                        serviecTypeKeyValue.put(service_name,service_id);
                                    }
                                    AutoCompleteTextView types = theInflatedView.findViewById(R.id.type);
                                    ArrayAdapter<String> adapter_types = new ArrayAdapter<String>(MemberActivity.this,
                                            android.R.layout.simple_list_item_1, service_value);
                                    types.setAdapter(adapter_types);
                                    //-----------------------serviec value adapter ---------


                                    ///--------------------------recyclerView adapter----
                                    layoutManager = new LinearLayoutManager(MemberActivity.this,
                                            RecyclerView.VERTICAL,
                                            false);
                                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                                    recyclerView.setLayoutManager(layoutManager);
                                    MemberListAdapter adapter = new MemberListAdapter(memberList);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    adapter.OnRecyclerViewClickListener(new MemberListAdapter.OnRecyclerViewClickListener() {
                                        @Override
                                        public void OnItemClick(int Member_id,int Coordinator_id) {

                                            String coordinatorId = String.valueOf(Coordinator_id);
                                            String memberId = String.valueOf(Member_id);
                                            memberDetails.x = Coordinator_id;
                                            memberDetails.y = Member_id;
                                            BottomSheetDialog buttonSheetDialog = new BottomSheetDialog(
                                                    MemberActivity.this,R.style.BottomSheetDialogTheme
                                            );
                                            if(theInflatedView.getParent() != null) {
                                                ((ViewGroup)theInflatedView.getParent()).removeView(theInflatedView); // <- fix
                                            }
                                            //-----adapter create outside of this adapter

                                            buttonSheetDialog.setContentView(theInflatedView);
                                            buttonSheetDialog.show();

                                            //--------------------Add Service  ----------------

                                            add_services = theInflatedView.findViewById(R.id.addServices);

                                            Addflag = theInflatedView.findViewById(R.id.flag);
                                            AddService_type = theInflatedView.findViewById(R.id.type);
                                            Addremark = theInflatedView.findViewById(R.id.remark);

                                            TextInputLayout flag_list = theInflatedView.findViewById(R.id.flag_list);
                                            TextInputLayout type_list = theInflatedView.findViewById(R.id.type_list);
                                            TextInputLayout remark_list = theInflatedView.findViewById(R.id.remark_list);

                                            add_services.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    //System.out.println("namesjfddsjfdsfljl");
                                                    if(Addflag.getText().toString().isEmpty()){
                                                        flag_list.setError("Please Select Flag");
                                                    }else if(AddService_type.getText().toString().isEmpty()){
                                                        type_list.setError("Please Select Type");
                                                    }else if(Addremark.getText().toString().isEmpty()){
                                                        remark_list.setError("Please Fill Remark");
                                                    }
                                                    else{
                                                        addService(Addflag.getText().toString(), AddService_type.getText().toString(), Addremark.getText().toString());
                                                    }
                                                }
                                            });



                                        }
                                    });

                                    ///--------------------------get data memeber id ----
                                }
                            } else {
                                Toast.makeText(MemberActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> Toast.makeText(MemberActivity.this, "Error", Toast.LENGTH_LONG).show()) {
            // Add Parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("apikey",apikey);
                params.put("password",password);
                params.put("janmitr_id",janmitr_id.toString());
                params.put("coordinator_id",coordinator_id);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(MemberActivity.this);
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
            }
            return true;
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String text = parent.getItemAtPosition(i).toString();
       Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}