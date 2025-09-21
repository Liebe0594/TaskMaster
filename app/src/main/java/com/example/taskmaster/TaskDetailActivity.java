package com.example.taskmaster;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class TaskDetailActivity extends AppCompatActivity {

    private EditText etTaskTitle;
    private Spinner spPriority;
    private TextView tvDueDate;
    private Button btnPickDate;
    private CheckBox cbIsCompleted;
    private Button btnSave;
    private Button btnCancel;

    private String selectedDueDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        etTaskTitle = findViewById(R.id.etTaskTitle);
        spPriority = findViewById(R.id.spPriority);
        tvDueDate = findViewById(R.id.tvDueDate);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // 1. Configurar el Spinner de prioridad
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_options, R.layout.spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(priorityAdapter);
        spPriority.setSelection(1); // Selecciona "Media" por defecto

        // 2. Configurar el botón para seleccionar la fecha
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskDetailActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            selectedDueDate = String.format(Locale.US, "%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                            tvDueDate.setText(selectedDueDate);
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        // 3. Configurar el botón Guardar
        btnSave.setOnClickListener(v -> {
            String taskTitle = etTaskTitle.getText().toString();
            int priority = spPriority.getSelectedItemPosition();
            boolean isCompleted = false; // Asume que una nueva tarea no está completada

            Intent resultIntent = new Intent();
            resultIntent.putExtra("TASK_TITLE", taskTitle);
            resultIntent.putExtra("IS_COMPLETED", isCompleted);
            resultIntent.putExtra("PRIORITY", priority);
            resultIntent.putExtra("DUE_DATE", selectedDueDate);

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // 4. Configurar el botón Cancelar
        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}