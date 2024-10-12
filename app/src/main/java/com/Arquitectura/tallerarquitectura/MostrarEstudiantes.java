package com.Arquitectura.tallerarquitectura;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MostrarEstudiantes extends AppCompatActivity {

    TextView datos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mostrar_estudiantes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        datos = findViewById(R.id.tv_datos);

        SharedPreferences preferences = getSharedPreferences("estudiantes", Context.MODE_PRIVATE);
        int estudianteContador = preferences.getInt("estudiante", 0);

        StringBuilder estudiantesInformacion = new StringBuilder();
        for (int i = 0; i < estudianteContador; i++) {
            String id = "estudiante_" + i;
            String nombre = preferences.getString(id + "_nombre", "N/A");
            String asignatura = preferences.getString(id + "_asignatura", "N/A");
            String fecha = preferences.getString(id + "_fecha", "N/A");
            Float nota1 = preferences.getFloat(id + "_nota1", 0);
            Float nota2 = preferences.getFloat(id + "_nota2", 0);
            Float nota3 = preferences.getFloat(id + "_nota3", 0);
            Float notaFinal = preferences.getFloat(id + "_notaFinal", 0);
            String estado = preferences.getString(id + "_estado", "N/A");

            estudiantesInformacion.append("Nombre del Estudiante: ").append(nombre)
                    .append("\nNombre de la Asignatura: ").append(asignatura)
                    .append("\nFecha: ").append(fecha)
                    .append("\nCorte 1: ").append("                              ").append(nota1)
                    .append("\nCorte 2: ").append("                              ").append(nota2)
                    .append("\nCorte 3: ").append("                              ").append(nota3)
                    .append("\nNota Final: ").append(notaFinal).append("                 ").append(estado)
                    .append("\n\n");
        }

        datos.setText(estudiantesInformacion.toString());

    }
    public void regresar1(View view) {
        Intent intent = new Intent(this, InsertarEstudiantes.class);
        startActivity(intent);
    }
}