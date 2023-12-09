package com.example.lab02;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileHelper {
    private Context context;

    public FileHelper(Context context) {
        this.context = context;
    }

    public void writeToFile(String filename, String data) {
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFromFile(String filename) {
        String result = "";
        try {
            FileInputStream inputStream = context.openFileInput(filename);
            int c;
            while ((c = inputStream.read()) != -1) {
                result = result + Character.toString((char) c);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
