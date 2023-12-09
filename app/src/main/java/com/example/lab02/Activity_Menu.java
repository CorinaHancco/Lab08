package com.example.lab02;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Activity_Menu extends AppCompatActivity {
    private List<Postulante> listaPostulantes = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostulanteAdapter postulanteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postulanteAdapter = new PostulanteAdapter(listaPostulantes);
        recyclerView.setAdapter(postulanteAdapter);

        cargarDatosDesdeArchivo();  // Cargar datos al iniciar la actividad

        Button nuevoButton = findViewById(R.id.butonNuevo);
        Button butonInfo = findViewById(R.id.buttonInfo);

        nuevoButton.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Menu.this, Activity_PostulanteRegistro.class);
            startActivityForResult(intent, 1);
        });

        butonInfo.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Menu.this, Activity_PostulanteInfo.class);
            intent.putExtra("lista", (Serializable) listaPostulantes);
            startActivity(intent);
        });
    }

    private void cargarDatosDesdeArchivo() {
        // Lee la cadena del archivo
        FileHelper fileHelper = new FileHelper(this);
        String postulanteString = fileHelper.readFromFile("postulante.txt");

        // Convierte la cadena a una lista de postulantes
        listaPostulantes = stringToPostulantes(postulanteString);

        // Agregar registro de log para verificar la carga de datos
        Log.d(TAG, "Tamaño de la lista al cargar: " + listaPostulantes.size());

        // Actualiza el RecyclerView
        actualizarRecyclerView();
    }

    private void actualizarRecyclerView() {
        if (postulanteAdapter != null) {
            postulanteAdapter.setPostulantes(listaPostulantes);
            postulanteAdapter.notifyDataSetChanged();
        }
    }

    private List<Postulante> stringToPostulantes(String data) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult called");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Postulante post = (Postulante) data.getSerializableExtra("postulante");
            listaPostulantes.add(post);

            // Actualiza el RecyclerView
            actualizarRecyclerView();

            // Imprime el tamaño de la lista para verificar
            Log.d(TAG, "Tamaño de la lista después de agregar: " + listaPostulantes.size());

            // Guarda la lista actualizada en el archivo
            guardarListaEnArchivo();
        }
    }


    private void guardarListaEnArchivo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Postulante postulante : listaPostulantes) {
            stringBuilder.append(postulanteToString(postulante)).append("\n");
        }
        // Escribe la cadena en el archivo
        FileHelper fileHelper = new FileHelper(this);
        fileHelper.writeToFile("postulante.txt", stringBuilder.toString());
    }

    private String postulanteToString(Postulante postulante) {
        return postulante.getDni() + "," +
                postulante.getApellidoMaterno() + "," +
                postulante.getNombres() + "," +
                postulante.getFechaNacimiento() + "," +
                postulante.getColegio() + "," +
                postulante.getCarrera();
    }
}