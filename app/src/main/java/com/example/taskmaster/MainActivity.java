package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botón para ir a la lista de tareas
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad de la lista de tareas
                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

        // Botones de redes sociales
        ImageButton btnX = findViewById(R.id.btnX);
        ImageButton btnInstagram = findViewById(R.id.btnInstagram);
        ImageButton btnFacebook = findViewById(R.id.btnFacebook);

        // Aquí puedes añadir la lógica para cada botón si lo necesitas
        // Por ejemplo: btnX.setOnClickListener(...)
    }
}