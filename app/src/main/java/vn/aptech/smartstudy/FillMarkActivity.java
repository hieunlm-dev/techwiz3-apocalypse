package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.aptech.smartstudy.entity.ScoreDetail;
import vn.aptech.smartstudy.entity.TestType;
import vn.aptech.smartstudy.entity.User;

public class FillMarkActivity extends AppCompatActivity {
    private List<String> studentNames = new ArrayList<>();
    private List<String> test_types = new ArrayList<>();

    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    private Spinner spStudent,spTestType;
    private EditText edMark;
    private Button btnInsertMark;

    private String studentName;
    private final String CLASSNAME="12A1";
    private int count = 0 ;
    private String email;
    private String test_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_mark);
        getItemCount();
        spStudent = findViewById(R.id.spStudent);
        spTestType = findViewById(R.id.spTestStyle);
        edMark = findViewById(R.id.edMark);
        btnInsertMark = findViewById(R.id.btnInsertMark);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,studentNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStudent.setAdapter(adapter);
        ArrayAdapter<String> testAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,test_types);
        testAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTestType.setAdapter(testAdapter);
        spStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                email = spStudent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spTestType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                test_type = spTestType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fillDataIntoSpinner(adapter);
        fillTestDataIntoSpinner(testAdapter);
        btnInsertMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewScore();
            }
        });
    }

    private void addNewScore() {
        ScoreDetail scoreDetail = new ScoreDetail(count+1,test_type,"Math",email,Float.parseFloat(edMark.getText().toString()));
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference scoreRef = database.getReference("score_detail/"+Integer.toString(count+1));
        scoreRef.setValue(scoreDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(FillMarkActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FillMarkActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillTestDataIntoSpinner(ArrayAdapter<String> adapter){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference testRef = database.getReference("test_type");
        testRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String type = dataSnapshot.getValue(String.class);
                    test_types.add(type);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fillDataIntoSpinner(ArrayAdapter<String> adapter){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference studentRef = database.getReference("users");

        studentRef.orderByChild("studentData/className").equalTo(CLASSNAME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String studentName = "("+dataSnapshot.getValue(User.class).getFull_name()+")"+dataSnapshot.getValue(User.class).getEmail();
                    studentNames.add(studentName);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getItemCount(){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference scoreRef = database.getReference("score_detail");
        scoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}