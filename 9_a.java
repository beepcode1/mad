package com.example.meetingschedule;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MeetingScheduleActivity extends AppCompatActivity {

    private EditText dateInput, timeInput, agendaInput;
    private Button saveMeetingButton;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_schedule);

        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        agendaInput = findViewById(R.id.agendaInput);
        saveMeetingButton = findViewById(R.id.saveMeetingButton);
        dbHelper = new SQLiteHelper(this);

        saveMeetingButton.setOnClickListener(v -> {
            String date = dateInput.getText().toString();
            String time = timeInput.getText().toString();
            String agenda = agendaInput.getText().toString();

            if (date.isEmpty() || time.isEmpty() || agenda.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                long id = dbHelper.insertMeeting(date, time, agenda);
                if (id > 0) {
                    Toast.makeText(this, "Meeting saved!", Toast.LENGTH_SHORT).show();
                    dateInput.setText("");
                    timeInput.setText("");
                    agendaInput.setText("");
                } else {
                    Toast.makeText(this, "Error saving meeting", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
