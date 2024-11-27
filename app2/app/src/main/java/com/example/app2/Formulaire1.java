package com.example.app2;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Formulaire1 extends AppCompatActivity implements View.OnClickListener {
    EditText Et;
    EditText El;
    EditText Ed;
    EditText Ehd;
    EditText Ehf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire1);
        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        Button save = findViewById(R.id.btnSaveEvent);
        save.setOnClickListener(this);
         Et =(EditText)findViewById(R.id.etTitle);
         El =(EditText)findViewById(R.id.etLocation);
         Ed =(EditText)findViewById(R.id.etDate);
         Ehd =(EditText)findViewById(R.id.etStartTime);
         Ehf =(EditText)findViewById(R.id.etEndTime);


    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnSaveEvent){

            ContentValues values = new ContentValues();
            values.put("titre",Et.getText().toString()); // Replace with actual column names and values
            values.put("lieu", El.getText().toString());
            values.put("date", Ed.getText().toString());
            values.put("heure_debut", Ehd.getText().toString());
            values.put("heure_fin", Ehf.getText().toString());

            Uri resultUri = getContentResolver().insert(consulter_events.uri, values);

            if (resultUri != null) {
                Log.d("fakhri", "Inserted successfully at URI: " + resultUri.toString());
            } else {
                Log.d("debug", "Insert failed.");
            }

        }else { finish();}

    }
}
