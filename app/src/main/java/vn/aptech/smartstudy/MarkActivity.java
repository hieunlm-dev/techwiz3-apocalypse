package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class MarkActivity extends AppCompatActivity {
    private Spinner spinnerSemeter;
    private String[] semeters = new String[]{"Semester 1","Semester 2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        initUi();
    }

    private void initUi() {
        spinnerSemeter = findViewById(R.id.spinnerSemeter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, semeters);
        spinnerSemeter.setAdapter(adapter);
    }
}