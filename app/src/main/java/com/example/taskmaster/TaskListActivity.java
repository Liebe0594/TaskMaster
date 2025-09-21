package com.example.taskmaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskmaster.TaskAdapter.OnTaskClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity implements OnTaskClickListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private TextView tvEmptyList;
    private FloatingActionButton fabViewStats;
    private FloatingActionButton fabAddTask;

    private ActivityResultLauncher<Intent> addTaskLauncher;
    private ActivityResultLauncher<Intent> reviewTaskLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        recyclerView = findViewById(R.id.recyclerView);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        fabAddTask = findViewById(R.id.fabAddTask);
        fabViewStats = findViewById(R.id.fabViewStats);

        taskList = new ArrayList<>();
        loadSampleTasks();
        taskAdapter = new TaskAdapter(taskList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
            addTaskLauncher.launch(intent);
        });

        fabViewStats.setOnClickListener(v -> {
            Intent intent = new Intent(this, StatsActivity.class);
            int totalTasks = taskList.size();
            int completedTasks = 0;
            for (Task task : taskList) {
                if (task.isCompleted()) {
                    completedTasks++;
                }
            }
            intent.putExtra("TOTAL_TASKS", totalTasks);
            intent.putExtra("COMPLETED_TASKS", completedTasks);
            startActivity(intent);
        });

        addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String title = data.getStringExtra("TASK_TITLE");
                            boolean isCompleted = data.getBooleanExtra("IS_COMPLETED", false);
                            int priority = data.getIntExtra("PRIORITY", 0);
                            String dueDate = data.getStringExtra("DUE_DATE");

                            Task newTask = new Task(title, isCompleted, priority, dueDate);
                            taskList.add(newTask);
                            taskAdapter.notifyItemInserted(taskList.size() - 1);
                            updateEmptyListVisibility();
                        }
                    }
                });

        reviewTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            float rating = data.getFloatExtra("RATING_VALUE", 0.0f);
                            int position = data.getIntExtra("TASK_POSITION", -1);
                            if (position != -1 && position < taskList.size()) {
                                Task task = taskList.get(position);
                                Toast.makeText(this, "Calificación de '" + task.getTitle() + "': " + rating + " estrellas", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        updateEmptyListVisibility();
    }

    private void loadSampleTasks() {
        taskList.add(new Task("Completar informe", false, 2, "2025-09-25"));
        taskList.add(new Task("Responder correos", true, 0, "2025-09-18"));
        taskList.add(new Task("Comprar víveres", false, 1, "2025-09-20"));
    }

    @Override
    public void onTaskCompleted(Task task, boolean isCompleted) {
        task.setCompleted(isCompleted);

        if (isCompleted) {
            Intent intent = new Intent(TaskListActivity.this, TaskReviewActivity.class);
            int position = taskList.indexOf(task);
            intent.putExtra("TASK_TITLE", task.getTitle());
            intent.putExtra("TASK_POSITION", position);
            reviewTaskLauncher.launch(intent);
        }

        taskList.remove(task);
        if (isCompleted) {
            taskList.add(task);
        } else {
            taskList.add(0, task);
        }
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTaskClick(Task task) {
        // Lógica para ver o editar detalles de la tarea si la necesitas
    }

    @Override
    public void onTaskDelete(Task task) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar esta tarea?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    taskList.remove(task);
                    taskAdapter.notifyDataSetChanged();
                    updateEmptyListVisibility();
                    Toast.makeText(TaskListActivity.this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void updateEmptyListVisibility() {
        if (taskList.isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
        } else {
            tvEmptyList.setVisibility(View.GONE);
        }
    }
}