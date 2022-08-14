package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.aptech.smartstudy.entity.ScoreDetail;
import vn.aptech.smartstudy.entity.TestType;
import vn.aptech.smartstudy.entity.User;

public class FillMarkActivity extends AppCompatActivity {
    private List<String> studentNames = new ArrayList<>();
    private List<String> test_types = new ArrayList<>();
    private List<String> removeTypes = new ArrayList<>();

    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    private Spinner spStudent,spTestType;
    private EditText edMark;
    private Button btnInsertMark;

    private String studentName;

    private int count = 0 ;
    private String email;
    private String test_type;
    private String className;
    private String subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_mark);
        getItemCount();


        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        subject = sharedPreferences.getString("subject","");

        spStudent = findViewById(R.id.spStudent);
        spTestType = findViewById(R.id.spTestStyle);
        edMark = findViewById(R.id.edMark);
        btnInsertMark = findViewById(R.id.btnInsertMark);


        className = getIntent().getStringExtra("class");
        Log.i("className",className);

        ArrayAdapter<String> testAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,test_types);
        testAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTestType.setAdapter(testAdapter);




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,studentNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStudent.setAdapter(adapter);


        spStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                email = spStudent.getSelectedItem().toString().trim().toLowerCase();
                //check if teacher have filled student's mark for mid/final term allready yet , if already => remove();


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

    private void checkTypes(String stuName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference scoreRef = database.getReference("score_detail");
        scoreRef.orderByChild("student_email").equalTo(stuName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.getValue(ScoreDetail.class).getType_test().equals("Final semester test Sem 1")||dataSnapshot.getValue(ScoreDetail.class).getType_test().equals("Middle semester test Sem 1")){
                            String s = dataSnapshot.getValue(ScoreDetail.class).getType_test();
                            //Log.i("Tag :" ,s);
                            removeTypes.add(s);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addNewScore() {
        int year = Year.now().getValue();
        int semester = 1;
        if(test_type.contains("2")){
            semester=2;
        }
        ScoreDetail scoreDetail = new ScoreDetail(test_type,subject,email,Float.parseFloat(edMark.getText().toString()),semester,year,"123");
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference scoreRef = database.getReference("score_detail");
        scoreRef.push().setValue(scoreDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(FillMarkActivity.this, "Success", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FillMarkActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillTestDataIntoSpinner(ArrayAdapter<String> adapter){
        /*FirebaseDatabase database1 = FirebaseDatabase.getInstance(URL);
        DatabaseReference scoreRef = database1.getReference("score_detail");
        scoreRef.orderByChild("student_email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Toast.makeText(FillMarkActivity.this, dataSnapshot.getValue(ScoreDetail.class).getType_test(), Toast.LENGTH_SHORT).show();
                        if(dataSnapshot.getValue(ScoreDetail.class).getType_test().equals("Final semester test Sem 1")||dataSnapshot.getValue(ScoreDetail.class).getType_test().equals("Middle semester test Sem 1")){
                            String s = dataSnapshot.getValue(ScoreDetail.class).getType_test();
                            //Log.i("Tag :" ,s);
                            Toast.makeText(FillMarkActivity.this, "Add "+s, Toast.LENGTH_SHORT).show();
                            removeTypes.add(s);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
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

        studentRef.orderByChild("studentData/className").equalTo(className).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String studentName = "(" + dataSnapshot.getValue(User.class).getFull_name() + ")" + dataSnapshot.getValue(User.class).getEmail();
                        studentNames.add(dataSnapshot.getValue(User.class).getStudentData().getFullName());

                        adapter.notifyDataSetChanged();
                    }
                    email = studentNames.get(0);
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