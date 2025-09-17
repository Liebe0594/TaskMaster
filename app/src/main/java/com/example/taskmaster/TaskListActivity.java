package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListener {

    private RecyclerView rvTaskList;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private FloatingActionButton fabAddTask;

    private final int ADD_TASK_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        rvTaskList = findViewById(R.id.rvTaskList);
        rvTaskList.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();
        loadSampleTasks();

        taskAdapter = new TaskAdapter(taskList);
        taskAdapter.setOnItemClickListener(this); // Asigna el listener
        rvTaskList.setAdapter(taskAdapter);

        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
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

        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String taskTitle = data.getStringExtra("TASK_TITLE");
            boolean isCompleted = data.getBooleanExtra("IS_COMPLETED", false);
            int priority = data.getIntExtra("PRIORITY", 0);

            Task newTask = new Task(taskTitle, isCompleted, priority);
            taskList.add(newTask);

            taskAdapter.notifyItemInserted(taskList.size() - 1);
        }
    }

    @Override
    public void onDeleteClick(int position) {
        // Elimina el elemento de la lista y notifica al adaptador
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
}