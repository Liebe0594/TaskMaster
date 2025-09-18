package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    private TextView tvTotalTasks;
    private TextView tvCompletedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary); // El nombre de tu archivo XML

        tvTotalTasks = findViewById(R.id.tvTotalTasks);
        tvCompletedTasks = findViewById(R.id.tvCompletedTasks);

        // Obtiene el Intent que inició esta actividad
        Intent intent = getIntent();
        if (intent != null) {
            // Extrae los datos
            int totalTasks = intent.getIntExtra("TOTAL_TASKS", 0);
            int completedTasks = intent.getIntExtra("COMPLETED_TASKS", 0);

            // Actualiza los TextViews
            tvTotalTasks.setText(String.valueOf(totalTasks));
            tvCompletedTasks.setText(String.valueOf(completedTasks));
        }
    }
}