package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton; // Importa ImageButton
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnX;
    private ImageButton btnInstagram;
    private ImageButton btnFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            return insets;
        });

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

        // Inicializar los botones de redes sociales
        btnX = findViewById(R.id.btnX);
        btnInstagram = findViewById(R.id.btnInstagram);
        btnFacebook = findViewById(R.id.btnFacebook);

        // Puedes agregar listeners para abrir URLs de redes sociales aquí si lo deseas
        // Por ejemplo:
        // btnX.setOnClickListener(v -> { /* Abrir URL de X */ });
        // btnInstagram.setOnClickListener(v -> { /* Abrir URL de Instagram */ });
        // btnFacebook.setOnClickListener(v -> { /* Abrir URL de Facebook */ });
    }
}