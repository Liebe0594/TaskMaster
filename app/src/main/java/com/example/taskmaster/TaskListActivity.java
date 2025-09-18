package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private FloatingActionButton fabAddTask;
    private FloatingActionButton fabViewStats;

    private final int ADD_TASK_REQUEST_CODE = 1;
    private final int REVIEW_TASK_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();
        loadSampleTasks();

        taskAdapter = new TaskAdapter(taskList);
        taskAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(taskAdapter);

        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
            }
        });

        fabViewStats = findViewById(R.id.fabViewStats);
        fabViewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalTasks = taskList.size();
                int completedTasks = 0;
                for (Task task : taskList) {
                    if (task.isCompleted()) {
                        completedTasks++;
                    }
                }

                Intent intent = new Intent(TaskListActivity.this, StatsActivity.class);
                intent.putExtra("TOTAL_TASKS", totalTasks);
                intent.putExtra("COMPLETED_TASKS", completedTasks);
                startActivity(intent);
            }
        });
    }

    private void loadSampleTasks() {
        taskList.add(new Task("Completar informe", false, 2));
        taskList.add(new Task("Responder correos", true, 0));
        taskList.add(new Task("Comprar víveres", false, 1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_TASK_REQUEST_CODE) {
                String taskTitle = data.getStringExtra("TASK_TITLE");
                boolean isCompleted = data.getBooleanExtra("IS_COMPLETED", false);
                int priority = data.getIntExtra("PRIORITY", 0);

                Task newTask = new Task(taskTitle, isCompleted, priority);
                taskList.add(newTask);
                taskAdapter.notifyItemInserted(taskList.size() - 1);

            } else if (requestCode == REVIEW_TASK_REQUEST_CODE) {
                float rating = data.getFloatExtra("RATING_VALUE", 0.0f);
                int position = data.getIntExtra("TASK_POSITION", -1);

                if (position != -1) {
                    Task task = taskList.get(position);
                    Toast.makeText(this, "Calificación de '" + task.getTitle() + "': " + rating + " estrellas", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onDeleteClick(int position) {
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskCompletedClick(int position, boolean isChecked) {
        Task task = taskList.get(position);
        task.setCompleted(isChecked);
        if (isChecked) {
            Intent intent = new Intent(TaskListActivity.this, TaskReviewActivity.class);
            intent.putExtra("TASK_TITLE", task.getTitle());
            intent.putExtra("TASK_POSITION", position);
            startActivityForResult(intent, REVIEW_TASK_REQUEST_CODE);
        }
    }
}