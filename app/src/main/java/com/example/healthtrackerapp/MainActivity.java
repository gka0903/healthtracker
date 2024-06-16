package com.example.healthtrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnExercise, btnDiet, btnSleep, btnWater;
    private Button btnViewExerciseData, btnViewDietData, btnViewSleepData, btnViewWaterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExercise = findViewById(R.id.btnExercise);
        btnDiet = findViewById(R.id.btnDiet);
        btnSleep = findViewById(R.id.btnSleep);
        btnWater = findViewById(R.id.btnWater);

        btnViewExerciseData = findViewById(R.id.btnViewExerciseData);
        btnViewDietData = findViewById(R.id.btnViewDietData);
        btnViewSleepData = findViewById(R.id.btnViewSleepData);
        btnViewWaterData = findViewById(R.id.btnViewWaterData);

        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExerciseActivity.class));
            }
        });

        btnDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DietActivity.class));
            }
        });

        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SleepActivity.class));
            }
        });

        btnWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WaterActivity.class));
            }
        });

        btnViewExerciseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);
                intent.putExtra("fileName", "exercise_data.txt");
                startActivity(intent);
            }
        });

        btnViewDietData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);
                intent.putExtra("fileName", "diet_data.txt");
                startActivity(intent);
            }
        });

        btnViewSleepData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);
                intent.putExtra("fileName", "sleep_data.txt");
                startActivity(intent);
            }
        });

        btnViewWaterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);
                intent.putExtra("fileName", "water_data.txt");
                startActivity(intent);
            }
        });
    }
}
