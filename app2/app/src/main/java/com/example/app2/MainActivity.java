package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAddEvent = findViewById(R.id.btn_add_event_to_app1);
        Button btnViewEvents = findViewById(R.id.btn_view_events_app1);
        Button btnAddToCalendar = findViewById(R.id.btn_add_to_calendar);

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Ajouter1Activity
                Intent intent = new Intent(MainActivity.this, Formulaire1.class);
                startActivity(intent);
            }
        });

        btnViewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Consulter1Activity
                Intent intent = new Intent(MainActivity.this, consulter_events.class);
                startActivity(intent);
            }
        });

        btnAddToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainActivity
                Intent intent = new Intent(MainActivity.this, Formulaire_Android.class);
                startActivity(intent);
            }
        });
    }
}
