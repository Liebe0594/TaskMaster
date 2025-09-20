package com.example.taskmaster;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class TaskDetailActivity extends AppCompatActivity {

    private EditText etTaskTitle;
    private Spinner spPriority;
    private Button btnSave;
    private TextView tvDueDate;
    private Button btnPickDate;

    private String selectedDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        etTaskTitle = findViewById(R.id.etTaskTitle);
        spPriority = findViewById(R.id.spPriority);
        btnSave = findViewById(R.id.btnSave);
        tvDueDate = findViewById(R.id.tvDueDate);
        btnPickDate = findViewById(R.id.btnPickDate);

        // Configura el Spinner con los diseños
        String[] priorities = {"Baja", "Media", "Alta"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, priorities); // Usa el diseño personalizado
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item); // Usa el diseño para el menú desplegable
        spPriority.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });

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
        intent.putExtra("IS_COMPLETED", false);
        intent.putExtra("PRIORITY", priority);
        intent.putExtra("DUE_DATE", selectedDueDate);
        setResult(RESULT_OK, intent);
        finish();
    }

    private int getSelectedPriority() {
        int selectedIndex = spPriority.getSelectedItemPosition();
        return selectedIndex;
    }
}