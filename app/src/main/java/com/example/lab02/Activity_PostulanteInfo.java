package com.example.lab02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Activity_PostulanteInfo extends AppCompatActivity {
    private List<Postulante> list ;
    private Button btnSearh;
    private EditText searchDNI;
    private TextView dni,nombres,apellidos,fecha,colegio,carrera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postulante_info);

        btnSearh = findViewById(R.id.buttonBuscar);
        dni = findViewById(R.id.textDni);
        nombres = findViewById(R.id.textNombres);
        apellidos = findViewById(R.id.textApellidos);
        fecha = findViewById(R.id.textDate);
        colegio = findViewById(R.id.textColegio);
        carrera = findViewById(R.id.textCarrera);
        searchDNI = findViewById(R.id.textViewDni);
        btnSearh.setOnClickListener( v ->{
            String dni_text = searchDNI.getText().toString();

            // Lee la cadena del archivo
            FileHelper fileHelper = new FileHelper(this);
            String postulanteString = fileHelper.readFromFile("postulante.txt");

            // Convierte la cadena a una lista de postulantes (aquí necesitarás implementar tu propio método para hacer esto)
            list = stringToPostulantes(postulanteString);

            Postulante postulante = buscarDNI(dni_text);
            if(postulante!=null){
                dni.setText(postulante.getDni());
                nombres.setText(postulante.getNombres());
                apellidos.setText(postulante.getApellidoMaterno());
                fecha.setText(postulante.getFechaNacimiento());
                colegio.setText(postulante.getColegio());
                carrera.setText(postulante.getCarrera());
            } else{
                dni.setText("---");
                nombres.setText("---");
                apellidos.setText("---");
                fecha.setText("---");
                colegio.setText("---");
                carrera.setText("---");
            }
        });
    }

    public Postulante buscarDNI(String dni){
        for(Postulante post : list)
            if(post.getDni().equals(dni)) return post;
        return null;
    }
    public List<Postulante> stringToPostulantes(String data) {
        List<Postulante> postulantes = new ArrayList<>();
        String[] lines = data.split("\n");

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 6) {
                Postulante postulante = new Postulante(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                postulantes.add(postulante);
            }
        }

        return postulantes;
    }
}