package com.example.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import static com.example.widget.ReminderAppWidgetProvider.EXTRA_BUTTON;

/**
 * This Activity should resemble a Reminder App.
 * It is possible to give a specific time and set the reminder.
 * Setting the reminder shows a Toast message to the user.
 * There is a home screen widget that has the option to set a reminder 10 minutes from the current time.
 *
 * The documentation focuses on the ReminderAppWidgetProvider and the Widget itself.
 * For Javadoc information about the TimePicker check out the specific project.
 *
 * Look into the Android.Manifest.xml File for important Widget information.
 *
 * Layout File: activity_main.xml
 *
 * @author Lukas Plenk
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText time;
    private Button button;

    Calendar calendar;
    int hour;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = findViewById(R.id.edit_time);
        time.setOnClickListener(this);
        time.setFocusable(false);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    /**
     * This method handles the Intent from the Button of the Widget.
     * If the Intent is not equal to null and the extra is true, the setReminderFromWidget method gets called.
     * @param intent is the Intent from the Widget to the MainActivity.
     */
    @Override
    protected void onNewIntent(Intent intent) {

        if (intent != null) {

            super.onNewIntent(intent);

            if (intent.getBooleanExtra(EXTRA_BUTTON, false)) {

                setReminderFromWidget();
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.edit_time:
                setTime();
                break;

            case R.id.button:
                setReminder();
                break;
        }
    }

    /**
     * Method for setting the Calendar time to 10 minutes in the future.
     * Once the time is set, the setReminder method gets called.
     */
    private void setReminderFromWidget() {

        // Getting the current time
        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        // Minute 60 should be converted to the next hour and minute 0
        if (minute + 10 == 60) {

            hour += 1;
            minute = 0;
        }
        // Minute 61 and higher should be converted to the next hour and the suited minute
        else if (minute + 10 > 60) {

            hour += 1;
            minute -= 50;
        }
        // 10 minutes get added
        else {

            minute += 10;
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        String timeInTxt = hour + ":" + minute;
        time.setText(timeInTxt);
        setReminder();
    }

    private void setTime() {

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                String timeInTxt = hour + ":" + minute;
                time.setText(timeInTxt);
            }
        }, hour, minute, DateFormat.is24HourFormat(MainActivity.this));
        timePickerDialog.show();
    }

    private void setReminder() {

        if (time.getText().toString().trim().isEmpty()) {

            Toast.makeText(MainActivity.this, "Please insert all information", Toast.LENGTH_LONG).show();
        }
        else {

            String reminderInTxt = "Reminder set for " + time.getText().toString();
            Toast.makeText(MainActivity.this, reminderInTxt, Toast.LENGTH_LONG).show();
        }
    }
}