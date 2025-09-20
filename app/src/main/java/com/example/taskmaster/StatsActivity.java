// Archivo: C:\Users\felip\AndroidStudioProjects\TaskMaster\app\src\main\java\com\example\taskmaster\StatsActivity.java

package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ProgressBar; // Nueva importación
import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    private TextView tvTotalTasks;
    private TextView tvCompletedTasks;
    private TextView tvProgressLabel; // Nuevo
    private ProgressBar progressBar; // Nuevo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvTotalTasks = findViewById(R.id.tvTotalTasks);
        tvCompletedTasks = findViewById(R.id.tvCompletedTasks);
        tvProgressLabel = findViewById(R.id.tvProgressLabel); // Nuevo
        progressBar = findViewById(R.id.progressBar); // Nuevo

        Intent intent = getIntent();
        if (intent != null) {
            int totalTasks = intent.getIntExtra("TOTAL_TASKS", 0);
            int completedTasks = intent.getIntExtra("COMPLETED_TASKS", 0);

            tvTotalTasks.setText(String.valueOf(totalTasks));
            tvCompletedTasks.setText(String.valueOf(completedTasks));

            // Lógica para la barra de progreso
            if (totalTasks > 0) {
                int progress = (int) (((double) completedTasks / totalTasks) * 100);
                progressBar.setProgress(progress);
                tvProgressLabel.setText("Progreso de Tareas: " + progress + "%");
            } else {
                progressBar.setProgress(0);
                tvProgressLabel.setText("Progreso de Tareas: 0%");
            }
        }
    }
}