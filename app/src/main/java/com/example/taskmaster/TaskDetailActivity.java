package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    private EditText etTaskTitle;
    private ImageView ivTaskIcon;
    private RadioGroup rgPriority;
    private ProgressBar pbTaskProgress;
    private RatingBar rbTaskRating;
    private Button btnSave;

    private float taskRating = 0.0f; // Variable para guardar la calificación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Inicializar vistas
        etTaskTitle = findViewById(R.id.etTaskTitle);
        ivTaskIcon = findViewById(R.id.ivTaskIcon);
        rgPriority = findViewById(R.id.rgPriority);
        pbTaskProgress = findViewById(R.id.pbTaskProgress);
        rbTaskRating = findViewById(R.id.rbTaskRating);
        btnSave = findViewById(R.id.btnSave);

        // Agregar el listener para la RatingBar
        rbTaskRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    // Guarda la calificación solo si la cambia el usuario
                    taskRating = rating;
                }
            }
        });

        btnSave.setOnClickListener(v -> {
            // Recopilar datos de los widgets
            String taskTitle = etTaskTitle.getText().toString();
            boolean isCompleted = false;
            int priority = 0;

            int selectedPriorityId = rgPriority.getCheckedRadioButtonId();
            if (selectedPriorityId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedPriorityId);
                if (selectedRadioButton.getText().toString().equals("Media")) {
                    priority = 1;
                } else if (selectedRadioButton.getText().toString().equals("Alta")) {
                    priority = 2;
                }
            }

            // Crear un Intent para enviar los datos de regreso
            Intent resultIntent = new Intent();
            resultIntent.putExtra("TASK_TITLE", taskTitle);
            resultIntent.putExtra("IS_COMPLETED", isCompleted);
            resultIntent.putExtra("PRIORITY", priority);
            resultIntent.putExtra("RATING", taskRating); // ¡Aquí se envía la calificación!

            // Establecer el resultado y cerrar la actividad
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}