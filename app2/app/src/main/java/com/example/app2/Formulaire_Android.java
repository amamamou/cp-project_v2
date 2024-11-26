package com.example.app2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Formulaire_Android extends AppCompatActivity {

    EditText title, location, description;
    CheckBox checkAllDay;
    LinearLayout linearStartTime, linearEndTime;
    TimePicker timePickerStart, timePickerEnd;
    Button addEvent;

    private static final int REQUEST_CODE_WRITE_CALENDAR = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_android);

        // Bind UI elements
        title = findViewById(R.id.etTitle);
        location = findViewById(R.id.etLocation);
        description = findViewById(R.id.etDescription);
        checkAllDay = findViewById(R.id.checkAllDay);
        linearStartTime = findViewById(R.id.linearStartTime);
        linearEndTime = findViewById(R.id.linearEndTime);
        addEvent = findViewById(R.id.btnAdd);

        timePickerStart = findViewById(R.id.timePickerStart);
        timePickerEnd = findViewById(R.id.timePickerEnd);

        // Check if permissions are granted at runtime
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_CALENDAR}, REQUEST_CODE_WRITE_CALENDAR);
        }

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
            // Ensure fields are not empty
            if (!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty()
                    && !description.getText().toString().isEmpty()) {

                // Create an Intent to add an event
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());

                String message = checkAllDay.isChecked()
                        ? "All-day event added!"
                        : "Event with specific times added!";

                if (!checkAllDay.isChecked()) {
                    int startHour = timePickerStart.getCurrentHour(); // Use correct method
                    int startMinute = timePickerStart.getCurrentMinute();
                    int endHour = timePickerEnd.getCurrentHour(); // Use correct method
                    int endMinute = timePickerEnd.getCurrentMinute();

                    long startTime = getTimeInMillis(startHour, startMinute);
                    long endTime = getTimeInMillis(endHour, endMinute);

                    // Check if start time is before end time
                    if (startTime >= endTime) {
                        Toast.makeText(Formulaire_Android.this, "Start time must be before end time", Toast.LENGTH_LONG).show();
                        return;
                    }

                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);

                    message += "\nStart Time: " + startHour + ":" + startMinute;
                    message += "\nEnd Time: " + endHour + ":" + endMinute;
                }

                Toast.makeText(Formulaire_Android.this, message, Toast.LENGTH_LONG).show();
                startActivity(intent);
                // Check if a calendar app is available to handle the intent
            } else {
                Toast.makeText(Formulaire_Android.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Helper function to convert time to milliseconds
    private long getTimeInMillis(int hour, int minute) {
        // Create a Calendar object to get the timestamp
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR_OF_DAY, hour);
        calendar.set(java.util.Calendar.MINUTE, minute);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    // Handle permission result (if needed)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_WRITE_CALENDAR) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
