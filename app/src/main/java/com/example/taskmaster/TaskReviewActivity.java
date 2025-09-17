package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TaskReviewActivity extends AppCompatActivity {

    private RatingBar rbTaskReview;
    private Button btnSubmitReview;
    private String taskTitle;
    private int taskPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_review);

        rbTaskReview = findViewById(R.id.rbTaskReview);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
        TextView tvReviewMessage = findViewById(R.id.tvReviewMessage);

        // Obtiene los datos enviados desde TaskListActivity
        Intent intent = getIntent();
        if (intent != null) {
            taskTitle = intent.getStringExtra("TASK_TITLE");
            taskPosition = intent.getIntExtra("TASK_POSITION", -1);
            if (taskTitle != null) {
                tvReviewMessage.setText("¡Felicidades! ¿Cómo calificarías la tarea '" + taskTitle + "'?");
            }
        }

        btnSubmitReview.setOnClickListener(v -> {
            float rating = rbTaskReview.getRating();

            // Crea un Intent para enviar el resultado de vuelta
            Intent resultIntent = new Intent();
            resultIntent.putExtra("RATING_VALUE", rating);
            resultIntent.putExtra("TASK_POSITION", taskPosition);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}