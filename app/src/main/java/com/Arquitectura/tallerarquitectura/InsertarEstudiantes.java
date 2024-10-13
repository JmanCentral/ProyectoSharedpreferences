package com.Arquitectura.tallerarquitectura;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertarEstudiantes extends AppCompatActivity {

    EditText txt_nombre;
    Spinner spinner_asignatura;
    EditText txt_nota1;
    EditText txt_nota2;
    EditText txt_nota3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insertar_estudiantes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        spinner_asignatura = (Spinner) findViewById(R.id.spinner);
        txt_nota1 = (EditText) findViewById(R.id.txt_nota1);
        txt_nota2 =  (EditText)  findViewById(R.id.txt_nota2);
        txt_nota3 = (EditText) findViewById(R.id.txt_nota3);


        String [] opciones = {
                // Primer Nivel
                "Matemáticas I",
                "Pensamiento Sistémico",
                "Pensamiento Algorítmico",
                "Lógica Matemática",

                // Segundo Nivel
                "Expresión y Desarrollo del Pensamiento",
                "Matemáticas II",
                "Constitución Nacional",
                "Programación Orientada a Objetos",
                "Práctica de Ingeniería I",
                "Álgebra Lineal",

                // Tercer Nivel
                "Matemáticas III",
                "Física I",
                "Cultura y Lengua Extranjera",
                "Estructuras de Datos",
                "Matemáticas Discretas",
                "Práctica de Ingeniería II",

                // Cuarto Nivel
                "Física II",
                "Arquitectura de Computadores",
                "Bases de Datos",
                "Introducción a la Teoría de la Computación",
                "Matemáticas IV",
                "Electiva de Facultad",

                // Quinto Nivel
                "Economía",
                "Estilos y Lenguajes de Programación",
                "Ingeniería de Software I",
                "Sistemas Operativos",
                "Matemáticas Especiales",
                "Física III",

                // Sexto Nivel
                "Análisis de Algoritmos",
                "Ingeniería de Software II",
                "Métodos Numéricos",
                "Señales y Comunicaciones",
                "Probabilidad y Estadística",
                "Electiva de Universidad",

                // Séptimo Nivel
                "Inteligencia Artificial",
                "Sistemas Organizacionales y Legislación",
                "Arquitectura de Sistemas I",
                "Práctica de Ingeniería III",
                "Redes de Computadores",
                "Electiva en Lengua Extranjera",

                // Octavo Nivel
                "Formulación y Evaluación de Proyectos",
                "Arquitectura de Sistemas II",
                "Data Analytics",
                "Práctica de Ingeniería IV",
                "Sistemas Distribuidos",
                "Contexto I",

                // Noveno Nivel
                "Ciberseguridad",
                "Gestión de Tecnologías de la Información",
                "Práctica de Ingeniería V",
                "Optativa de Profundización I: Computación Gráfica",
                "Optativa de Profundización II: Programación con Lenguaje PL/SQL",
                "Contexto II",

                // Décimo Nivel
                "Opción de Grado",
                "Optativa de Profundización III: Modelado 3D y Videojuegos"
        };


        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, opciones);
        spinner_asignatura.setAdapter(adapter);


    }

    public void guardar1(View view) {

        try {

            String nombre = txt_nombre.getText().toString();

            if (nombre.isEmpty()) {
                Toast.makeText(this, "Error: los campos estan vacios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!nombre.matches("^[a-zA-Z\\s]+$") ) {
                Toast.makeText(this, "Ingreses caracteres validos para el nombre", Toast.LENGTH_SHORT).show();
                return;
            }

            String asignatura = spinner_asignatura.getSelectedItem().toString();


            Date date = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaactual = dateFormat.format(date);


            String nota1 = txt_nota1.getText().toString();
            String nota2 = txt_nota2.getText().toString();
            String nota3 = txt_nota3.getText().toString();

            Float notauna = Float.parseFloat(nota1);
            Float notados = Float.parseFloat(nota2);
            Float notatres = Float.parseFloat(nota3);
            Float not;

            if (notauna < 0 || notauna > 5 || notados < 0 || notados > 5 || notatres < 0 || notatres > 5) {
                Toast.makeText(this, "Ingrese notas validas", Toast.LENGTH_SHORT).show();
                return;
            }

            Float notaFinal = (notauna * 0.3f) + (notados * 0.3f) + (notatres * 0.4f);

            String estado;

            if (notaFinal <  3.0) {
                estado = "No Aprobó";
            } else {
                estado = "Aprobó";
            }

            Float notaFinalFormateada = Float.valueOf(String.format("%.1f", notaFinal));


            SharedPreferences preferences = getSharedPreferences("estudiantes", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            int estudianteContador = preferences.getInt("estudiante", 0);

            String Estudiante = "estudiante_" + estudianteContador + "_";

            editor.putString(Estudiante + "nombre", nombre);
            editor.putString(Estudiante + "asignatura", asignatura);
            editor.putString(Estudiante + "fecha", fechaactual);
            editor.putFloat(Estudiante + "nota1", notauna);
            editor.putFloat(Estudiante + "nota2", notados);
            editor.putFloat(Estudiante + "nota3", notatres);
            editor.putFloat(Estudiante + "notaFinal", notaFinalFormateada);
            editor.putString(Estudiante + "estado", estado);
            editor.putInt("estudiante", estudianteContador + 1);
            editor.apply();

            Toast.makeText(this, "Registro guardado con exito", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e ) {
            Toast.makeText(this, "Error: una o más notas están vacías", Toast.LENGTH_SHORT).show();
        }
    }
    public void Mostrar1(View view) {
        Intent intent = new Intent(this, MostrarEstudiantes.class);
        startActivity(intent);
    }

    public void saludar (View view)
        {
        Toast.makeText(this, "Bienvenido al registro de notas", Toast.LENGTH_SHORT).show();
    }


}