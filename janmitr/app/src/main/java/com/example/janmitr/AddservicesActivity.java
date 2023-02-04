package com.example.janmitr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AddservicesActivity extends AppCompatActivity {
    AutoCompleteTextView autocomplete;
    String[] arr = { "Paries,France", "PA,United States","Parana,Brazil",
            "Padua,Italy", "Pasadena,CA,United States"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addservices);

        autocomplete = (AutoCompleteTextView)
                findViewById(R.id.members_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);
        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);
    }
}