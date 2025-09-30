package com.example.sumaslocas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class Nivel1_Activity extends AppCompatActivity {

    private TextView textoNumero1, textoNumero2;
    private EditText editTextRespuesta;
    private Button botonRevisar;
    private int numero1, numero2;
    private int respuestaCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_1);

        // Inicializar vistas
        textoNumero1 = findViewById(R.id.textView4);
        textoNumero2 = findViewById(R.id.textView6);
        editTextRespuesta = findViewById(R.id.editTextRespuesta);
        botonRevisar = findViewById(R.id.button2);

        // Generar números aleatorios para nivel 1 (0-9)
        generarNumerosAleatorios();

        botonRevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revisarRespuesta();
            }
        });
    }

    private void generarNumerosAleatorios() {
        Random random = new Random();
        numero1 = random.nextInt(10); // 0-9
        numero2 = random.nextInt(10); // 0-9
        respuestaCorrecta = numero1 + numero2;

        // Mostrar números en los TextView
        textoNumero1.setText(String.valueOf(numero1));
        textoNumero2.setText(String.valueOf(numero2));

        // Limpiar respuesta anterior
        editTextRespuesta.setText("");
    }

    private void revisarRespuesta() {
        String respuestaStr = editTextRespuesta.getText().toString().trim();

        // Validar que se haya ingresado una respuesta
        if (respuestaStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa tu respuesta", Toast.LENGTH_SHORT).show();
            editTextRespuesta.requestFocus();
            return;
        }

        try {
            int respuestaUsuario = Integer.parseInt(respuestaStr);

            if (respuestaUsuario == respuestaCorrecta) {
                // Respuesta correcta - mostrar palomita
                mostrarResultado(true);
            } else {
                // Respuesta incorrecta - mostrar tache
                mostrarResultado(false);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor ingresa un número válido", Toast.LENGTH_SHORT).show();
            editTextRespuesta.setText("");
            editTextRespuesta.requestFocus();
        }
    }

    private void mostrarResultado(boolean esCorrecto) {
        if (esCorrecto) {
            // Cambiar a layout con palomita
            setContentView(R.layout.level_success);

            TextView textoResultado = findViewById(R.id.textViewExito);
            textoResultado.setText("¡Correcto!\n" + numero1 + " + " + numero2 + " = " + respuestaCorrecta);

            Button botonSiguiente = findViewById(R.id.botonSiguiente);
            botonSiguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ir al nivel 2
                    irSiguienteNivel();
                }
            });
        } else {
            // Cambiar a layout con tache
            setContentView(R.layout.level_fail);

            TextView textoResultado = findViewById(R.id.textViewFallo);
            textoResultado.setText("Incorrecto\nLa respuesta era: " + respuestaCorrecta);

            Button botonReintentar = findViewById(R.id.botonReintentar);
            botonReintentar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reiniciar nivel 1
                    recreate();
                }
            });
        }
    }

    private void irSiguienteNivel() {
        Intent intent = new Intent(this, Nivel2_Activity.class);
        startActivity(intent);
        finish();
    }
}