package com.example.healthtrackerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class ExerciseActivity extends AppCompatActivity {

    private EditText etExerciseDate, etExerciseType, etExerciseDuration;
    private Button btnSaveExercise, btnViewExerciseData, btnBackToMain;
    private final String FILE_NAME = "exercise_data.txt";
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        etExerciseDate = findViewById(R.id.etExerciseDate);
        etExerciseType = findViewById(R.id.etExerciseType);
        etExerciseDuration = findViewById(R.id.etExerciseDuration);
        btnSaveExercise = findViewById(R.id.btnSaveExercise);
        btnViewExerciseData = findViewById(R.id.btnViewExerciseData);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        etExerciseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnSaveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exerciseType = etExerciseType.getText().toString();
                String exerciseDuration = etExerciseDuration.getText().toString();

                if (!selectedDate.isEmpty() && !exerciseType.isEmpty() && !exerciseDuration.isEmpty()) {
                    saveData(selectedDate, exerciseType, exerciseDuration);
                    Toast.makeText(ExerciseActivity.this, "운동 기록이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExerciseActivity.this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewExerciseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseActivity.this, ViewDataActivity.class);
                intent.putExtra("fileName", FILE_NAME);
                startActivity(intent);
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExerciseActivity.this, MainActivity.class));
            }
        });

        loadData();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                etExerciseDate.setText(selectedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void saveData(String date, String type, String duration) {
        String data = date + "," + type + "," + duration + "분\n";
        FileOutputStream fos = null;
        BufferedWriter writer = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND); // MODE_APPEND로 변경하여 기존 데이터 유지
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void loadData() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String data = sb.toString();
            String[] entries = data.split("\n");
            if (entries.length > 0) {
                String lastEntry = entries[entries.length - 1];
                String[] parts = lastEntry.split(",");
                if (parts.length == 3) {
                    selectedDate = parts[0];
                    etExerciseDate.setText(parts[0]);
                    etExerciseType.setText(parts[1]);
                    etExerciseDuration.setText(parts[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
