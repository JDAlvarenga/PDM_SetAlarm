package com.angryscarf.setalarm;

import android.content.Intent;
import android.graphics.Color;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> days;
    ArrayList<Button> btnDays;

    TimePicker timePicker;

    CheckBox checkVibrate;
    CheckBox checkRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.tp_Time);
        checkRepeat = findViewById(R.id.check_repeat);
        checkVibrate = findViewById(R.id.check_vibrate);


        days = new ArrayList<Integer>();

        btnDays = new ArrayList<Button>();

        btnDays.add((Button)findViewById(R.id.btn_day_sunday));
        btnDays.add((Button)findViewById(R.id.btn_day_monday));
        btnDays.add((Button)findViewById(R.id.btn_day_tuesday));
        btnDays.add((Button)findViewById(R.id.btn_day_wednesday));
        btnDays.add((Button)findViewById(R.id.btn_day_thursday));
        btnDays.add((Button)findViewById(R.id.btn_day_friday));
        btnDays.add((Button)findViewById(R.id.btn_day_saturday));


    }

    public void toggleRepeat(View check) {

        View l_days = findViewById(R.id.layout_days);

        if(((CheckBox) check).isChecked()){

            days.clear();
            days.add(Calendar.SUNDAY);
            days.add(Calendar.MONDAY);
            days.add(Calendar.TUESDAY);
            days.add(Calendar.WEDNESDAY);
            days.add(Calendar.THURSDAY);
            days.add(Calendar.FRIDAY);
            days.add(Calendar.SATURDAY);

            for (Button btnDay : btnDays) {
                btnDay.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            l_days.setVisibility(View.VISIBLE);
        }
        else {
            l_days.setVisibility(View.GONE);
        }
    }

    public void toggleDay(View v){
        int day = Calendar.SUNDAY;

        switch (v.getId()){

            case R.id.btn_day_sunday:
                day = Calendar.SUNDAY;
                break;

            case R.id.btn_day_monday:
                day = Calendar.MONDAY;
                break;

            case R.id.btn_day_tuesday:
                day = Calendar.TUESDAY;
                break;

            case R.id.btn_day_wednesday:
                day = Calendar.WEDNESDAY;
                break;

            case R.id.btn_day_thursday:
                day = Calendar.THURSDAY;
                break;

            case R.id.btn_day_friday:
                day = Calendar.FRIDAY;
                break;

            case R.id.btn_day_saturday:
                day = Calendar.SATURDAY;
                break;
        }

        if(days.contains(day)) {
            days.remove(days.indexOf(day));

            ((Button) v).setTextColor(Color.BLACK);

            if(days.isEmpty()) {
                checkRepeat.setChecked(false);
                toggleRepeat(checkRepeat);
            }

        }
        else {
            days.add(day);
            ((Button) v).setTextColor(getResources().getColor(R.color.colorAccent));
        }

        Log.d("DAYS: ", days.toString());
    }

    public void setAlarm(View v) {
        Intent i = new Intent();
        i.setAction(AlarmClock.ACTION_SET_ALARM);

        i.putExtra(AlarmClock.EXTRA_HOUR, timePicker.getCurrentHour());
        i.putExtra(AlarmClock.EXTRA_MINUTES, timePicker.getCurrentMinute());

        i.putExtra(AlarmClock.EXTRA_VIBRATE, checkVibrate.isChecked());

        if(checkRepeat.isChecked()) {
            i.putExtra(AlarmClock.EXTRA_DAYS, days);
        }
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);


        if(i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

}
