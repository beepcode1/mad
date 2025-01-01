package com.example.medicinealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText medicineName, medicineDate;
    private Spinner timeOfDaySpinner;
    private Button saveButton;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineName = findViewById(R.id.medicineName);
        medicineDate = findViewById(R.id.medicineDate);
        timeOfDaySpinner = findViewById(R.id.timeOfDaySpinner);
        saveButton = findViewById(R.id.saveButton);

        dbHelper = new SQLiteHelper(this);

        // Populate the Spinner with Time of Day options
        String[] timesOfDay = {"Morning", "Afternoon", "Evening", "Night"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timesOfDay);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeOfDaySpinner.setAdapter(adapter);

        saveButton.setOnClickListener(v -> {
            String name = medicineName.getText().toString();
            String date = medicineDate.getText().toString();
            String timeOfDay = timeOfDaySpinner.getSelectedItem().toString();

            if (name.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                long id = dbHelper.insertMedicine(name, date, timeOfDay);
                if (id > 0) {
                    setAlarm(name, date, timeOfDay);
                    Toast.makeText(this, "Medicine saved and alarm set!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setAlarm(String name, String date, String timeOfDay) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        try {
            switch (timeOfDay) {
                case "Morning":
                    date += " 08:00";
                    break;
                case "Afternoon":
                    date += " 12:00";
                    break;
                case "Evening":
                    date += " 18:00";
                    break;
                case "Night":
                    date += " 22:00";
                    break;
            }
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("MEDICINE_NAME", name);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
