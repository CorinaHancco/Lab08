package com.example.lab02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_PostulanteRegistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postulante_registro);

        EditText edtDni = findViewById(R.id.dni);
        EditText edtApellidoMaterno = findViewById(R.id.editTextApellidoMaterno);
        EditText edtNombres = findViewById(R.id.editTextNombres);
        EditText edtFechaNacimiento = findViewById(R.id.editTextFechaNacimiento);
        EditText edtColegio = findViewById(R.id.editTextColegio);
        EditText edtCarrera = findViewById(R.id.editTextCarrera);

        Button btnEnviar = findViewById(R.id.buttonEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = edtDni.getText().toString();
                String apellidoMaterno = edtApellidoMaterno.getText().toString();
                String nombres = edtNombres.getText().toString();
                String fechaNacimiento = edtFechaNacimiento.getText().toString();
                String colegio = edtColegio.getText().toString();
                String carrera = edtCarrera.getText().toString();

                // Crea un nuevo postulante
                Postulante postulante = new Postulante(dni, apellidoMaterno, nombres, fechaNacimiento, colegio, carrera);

                // Convierte el postulante a una cadena (aquí necesitarás implementar tu propio método para hacer esto)
                String postulanteString = postulanteToString(postulante);

                // Escribe la cadena en un archivo
                FileHelper fileHelper = new FileHelper(Activity_PostulanteRegistro.this);
                fileHelper.writeToFile("postulante.txt", postulanteString);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("postulante", postulante);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
    public String postulanteToString(Postulante postulante) {
        return postulante.getDni() + "," +
                postulante.getApellidoMaterno() + "," +
                postulante.getNombres() + "," +
                postulante.getFechaNacimiento() + "," +
                postulante.getColegio() + "," +
                postulante.getCarrera() + "\n";
    }
}