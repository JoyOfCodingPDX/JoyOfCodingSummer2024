package edu.pdx.cs.joy.whitlock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int GET_SUM = 42;
    private ArrayAdapter<Integer> sums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.sums = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        ListView listView = findViewById(R.id.sums);
        listView.setAdapter(this.sums);

        try {
            readSumsFromFile();
        } catch (IOException e) {
            toast("While reading sums file: " + e);
        }
    }

    private void readSumsFromFile() throws IOException {
        File sumsFile = getSumsFile();
        try (BufferedReader br = new BufferedReader(new FileReader(sumsFile))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                int sum = Integer.parseInt(line);
                this.sums.add(sum);
            }
        }
    }

    public void launchCalculator(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivityForResult(intent, GET_SUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_SUM) {
            if (resultCode == RESULT_OK && data != null) {
                int sum = data.getIntExtra(CalculatorActivity.SUM_VALUE, 0);
                toast("The sum was " + sum);
                this.sums.add(sum);
                try {
                    writeSumsToFile();
                } catch (IOException e) {
                    toast("While writing sums: " + e.getMessage());
                }
            }
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void writeSumsToFile() throws IOException {
        File sumsFiles = getSumsFile();
        try (PrintWriter pw = new PrintWriter(new FileWriter(sumsFiles))) {
            for (int i = 0; i < this.sums.getCount(); i++) {
                Integer sum = this.sums.getItem(i);
                pw.println(sum);
            }
            pw.flush();
        }
    }

    private File getSumsFile() {
        return new File(this.getDataDir(), "sums.txt");
    }
}