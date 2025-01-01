package com.example.meetingschedule;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MeetingInfoActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button searchMeetingButton;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_info);

        datePicker = findViewById(R.id.datePicker);
        searchMeetingButton = findViewById(R.id.searchMeetingButton);
        dbHelper = new SQLiteHelper(this);

        searchMeetingButton.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String selectedDate = sdf.format(calendar.getTime());

            String agenda = dbHelper.getMeetingAgenda(selectedDate);

            if (agenda != null) {
                Toast.makeText(this, "Meeting Agenda: " + agenda, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No Meeting on this Date", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
