// Archivo: C:\Users\felip\AndroidStudioProjects\TaskMaster\app\src\main\java\com\example\taskmaster\TaskDetailActivity.java

package com.example.taskmaster;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class TaskDetailActivity extends AppCompatActivity {

    private EditText etTaskTitle;
    private RadioGroup rgPriority;
    private Button btnSave;
    private TextView tvDueDate; // Nuevo
    private Button btnPickDate; // Nuevo

    private String selectedDueDate; // Variable para guardar la fecha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        etTaskTitle = findViewById(R.id.etTaskTitle);
        rgPriority = findViewById(R.id.rgPriority);
        btnSave = findViewById(R.id.btnSave);
        tvDueDate = findViewById(R.id.tvDueDate); // Nuevo
        btnPickDate = findViewById(R.id.btnPickDate); // Nuevo

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });

        // Configurar el Date Picker
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    selectedDueDate = String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    tvDueDate.setText(selectedDueDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveTask() {
        String title = etTaskTitle.getText().toString();
        int priority = getSelectedPriority();

        Intent intent = new Intent();
        intent.putExtra("TASK_TITLE", title);
        intent.putExtra("IS_COMPLETED", false); // Siempre se crea como no completada
        intent.putExtra("PRIORITY", priority);
        intent.putExtra("DUE_DATE", selectedDueDate); // Pasa la fecha límite
        setResult(RESULT_OK, intent);
        finish();
    }

    private int getSelectedPriority() {
        int selectedId = rgPriority.getCheckedRadioButtonId();
        if (selectedId == R.id.rbLow) {
            return 0;
        } else if (selectedId == R.id.rbMedium) {
            return 1;
        } else if (selectedId == R.id.rbHigh) {
            return 2;
        }
        return 0; // Default a baja
    }
}