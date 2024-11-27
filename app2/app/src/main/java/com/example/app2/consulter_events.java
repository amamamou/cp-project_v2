package com.example.app2;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class consulter_events extends AppCompatActivity {
    public static final String AUTH = "com.example.app1";
    public static final Uri BASE = Uri.parse("content://" + AUTH);
    public static final String PathTable = "table_Evenements";
    public static final Uri uri = BASE.buildUpon().appendPath(PathTable).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_events);

        Button btnBack = findViewById(R.id.btn_back);
        TableLayout tb = findViewById(R.id.tbl_events);
        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                Log.d("fakhri", "Rows retrieved: " + cursor.getCount());
                while (cursor.moveToNext()) {
                    TableRow row = new TableRow(this);
                    String id = cursor.getString(0);
                    String titre = cursor.getString(1);
                    String lieu = cursor.getString(2);
                    String date = cursor.getString(3);
                    String heured = cursor.getString(4);
                    String heuref = cursor.getString(5);

                    TextView idTextView = new TextView(this);
                    idTextView.setText(id);
                    idTextView.setPadding(8, 8, 4, 8);

                    TextView titreTextView = new TextView(this);
                    titreTextView.setText(titre);
                    titreTextView.setPadding(8, 8, 4, 8);

                    TextView lieuTextView = new TextView(this);
                    lieuTextView.setText(lieu);
                    lieuTextView.setPadding(8, 8, 4, 8);

                    TextView dateTextView = new TextView(this);
                    dateTextView.setText(date);
                    dateTextView.setPadding(8, 8, 4, 8);

                    TextView heureDebutTextView = new TextView(this);
                    heureDebutTextView.setText(heured);
                    heureDebutTextView.setPadding(8, 8, 4, 8);

                    TextView heureFinTextView = new TextView(this);
                    heureFinTextView.setText(heuref);
                    heureFinTextView.setPadding(8, 8, 4, 8);

                    // Add TextViews to the TableRow
                    row.addView(idTextView);
                    row.addView(titreTextView);
                    row.addView(lieuTextView);
                    row.addView(dateTextView);
                    row.addView(heureDebutTextView);
                    row.addView(heureFinTextView);

                    // Add the TableRow to the TableLayout
                    tb.addView(row);
                }
            } else {
                Log.d("fakhri", "No data found.");
            }
        } catch (Exception e) {
            Log.e("fakhri", "Error querying ContentProvider", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
