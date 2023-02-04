package com.example.janmitr;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ServiceActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    Toolbar toolbarr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        headerNavtigation();

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