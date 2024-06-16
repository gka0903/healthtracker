package com.example.healthtrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class ViewDataActivity extends AppCompatActivity {

    private CustomCalendarView calendarView;
    private TextView tvData;
    private Button btnClose;
    private String fileName;
    private HashSet<String> eventDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        calendarView = findViewById(R.id.calendarView);
        tvData = findViewById(R.id.tvData);
        btnClose = findViewById(R.id.btnClose);

        Intent intent = getIntent();
        fileName = intent.getStringExtra("fileName");
        eventDays = new HashSet<>();

        loadEventDays();

        calendarView.setEventDays(eventDays);
        calendarView.setOnDateChangeListener(new CustomCalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CustomCalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = year + "-" + (month + 1) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                loadData(selectedDate);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadEventDays() {
        FileInputStream fis = null;
        BufferedReader reader = null;
        try {
            fis = openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                String date = line.split(",")[0];
                eventDays.add(date);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadData(String date) {
        FileInputStream fis = null;
        BufferedReader reader = null;
        try {
            fis = openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(date)) {
                    String record = line.substring(date.length() + 1);
                    sb.append(record).append("\n");
                }
            }
            String data = sb.toString();
            if (!data.isEmpty()) {
                tvData.setText(data);
            } else {
                tvData.setText("저장된 데이터가 없습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            tvData.setText("데이터를 불러오는 중 오류가 발생했습니다.");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
