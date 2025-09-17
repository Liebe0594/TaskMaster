package com.example.taskmaster;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView rvTaskList;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        rvTaskList = findViewById(R.id.rvTaskList);
        rvTaskList.setLayoutManager(new LinearLayoutManager(this));

        // Datos de ejemplo
        taskList = new ArrayList<>();
        taskList.add(new Task("Completar informe", false, 2)); // Alta
        taskList.add(new Task("Responder correos", true, 0)); // Baja
        taskList.add(new Task("Comprar víveres", false, 1)); // Media

        taskAdapter = new TaskAdapter(taskList);
        rvTaskList.setAdapter(taskAdapter);
    }
}