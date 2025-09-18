package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    private EditText etTaskTitle;
    private RadioGroup rgPriority;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Inicializar vistas
        etTaskTitle = findViewById(R.id.etTaskTitle);
        rgPriority = findViewById(R.id.rgPriority);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            // Recopilar datos de los widgets
            String taskTitle = etTaskTitle.getText().toString();
            boolean isCompleted = false; // Asumimos que la nueva tarea no está completada
            int priority = 0; // Asumimos prioridad baja por defecto

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

            // Establecer el resultado y cerrar la actividad
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}