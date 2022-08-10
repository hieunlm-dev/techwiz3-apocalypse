package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EdgeEffect;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText edEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smartstudy-2180f-default-rtdb-firebaseio.com");             // Get the database instance and store into object
        DatabaseReference myRef = database.getReference();

      


    }
}