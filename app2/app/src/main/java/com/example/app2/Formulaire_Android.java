package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Formulaire_Android extends AppCompatActivity {

    EditText title, location, description;
    CheckBox checkAllDay;
    LinearLayout linearStartTime, linearEndTime;
    TimePicker timePickerStart, timePickerEnd;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_android);

        // Liaison des éléments UI
        title = findViewById(R.id.etTitle);
        location = findViewById(R.id.etLocation);
        description = findViewById(R.id.etDescription);
        checkAllDay = findViewById(R.id.checkAllDay);
        linearStartTime = findViewById(R.id.linearStartTime);
        linearEndTime = findViewById(R.id.linearEndTime);
        addEvent = findViewById(R.id.btnAdd);

        timePickerStart = findViewById(R.id.timePickerStart);
        timePickerEnd = findViewById(R.id.timePickerEnd);


        checkAllDay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                linearStartTime.setVisibility(View.GONE);
                linearEndTime.setVisibility(View.GONE);
            } else {
                linearStartTime.setVisibility(View.VISIBLE);
                linearEndTime.setVisibility(View.VISIBLE);
            }
        });


        addEvent.setOnClickListener(v -> {
            if (!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty()
                    && !description.getText().toString().isEmpty()) {

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());

                String message = checkAllDay.isChecked()
                        ? "Événement sur toute la journée ajouté !"
                        : "Événement avec heures spécifiques ajouté !";

                if (!checkAllDay.isChecked()) {
                    int startHour = timePickerStart.getHour();
                    int startMinute = timePickerStart.getMinute();
                    int endHour = timePickerEnd.getHour();
                    int endMinute = timePickerEnd.getMinute();

                    long startTime = getTimeInMillis(startHour, startMinute);
                    long endTime = getTimeInMillis(endHour, endMinute);

                    // Vérifier si l'heure de début est supérieure à l'heure de fin
                    if (startTime >= endTime) {
                        Toast.makeText(Formulaire_Android.this, "L'heure de début ne peut pas être après l'heure de fin", Toast.LENGTH_LONG).show();
                        return;
                    }

                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);

                    message += "\nHeure de début : " + startHour + ":" + startMinute;
                    message += "\nHeure de fin : " + endHour + ":" + endMinute;
                }

                Toast.makeText(Formulaire_Android.this, message, Toast.LENGTH_LONG).show();

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(Formulaire_Android.this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
            }
        });
    }

    private long getTimeInMillis(int hour, int minute) {
        // Créer un calendrier pour obtenir le timestamp
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR_OF_DAY, hour);
        calendar.set(java.util.Calendar.MINUTE, minute);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
