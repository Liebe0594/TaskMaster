package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TaskReviewActivity extends AppCompatActivity {

    private TextView tvTaskTitle;
    private RatingBar ratingBar;
    private Button btnSubmitRating;
    private int taskPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        tvTaskTitle = findViewById(R.id.tvTaskTitle);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmitRating = findViewById(R.id.btnSubmitRating);

        Intent intent = getIntent();
        String taskTitle = intent.getStringExtra("TASK_TITLE");
        taskPosition = intent.getIntExtra("TASK_POSITION", -1);

        if (taskTitle != null) {
            tvTaskTitle.setText("Califica la tarea: " + taskTitle);
        }

        btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("RATING_VALUE", rating);
                resultIntent.putExtra("TASK_POSITION", taskPosition);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}