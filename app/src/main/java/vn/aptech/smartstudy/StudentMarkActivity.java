package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import vn.aptech.smartstudy.adapter.StudentApdapter;
import vn.aptech.smartstudy.entity.ClassName;
import vn.aptech.smartstudy.entity.ScoreDetail;
import vn.aptech.smartstudy.entity.TestType;
import vn.aptech.smartstudy.entity.User;
import vn.aptech.smartstudy.service.FcmNotifySender;

public class StudentMarkActivity extends AppCompatActivity {
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";

    private RecyclerView rvStudentMark;
    private Spinner spFilterClass , spFilterType;
    private Button btnSave, btnNotify;
    private EditText edMark ;
    private StudentApdapter studentApdapter;
    private List<User> users = new ArrayList<>();
    private List<String> classes = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private List<ScoreDetail> addScores = new ArrayList<>();
    private List<EditText> edMarks = new ArrayList<EditText>();
    private int semester =1;
    private String selected_test;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mark);

        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        subject = sharedPreferences.getString("subject","");

        rvStudentMark = findViewById(R.id.rvStudentMark);
        spFilterClass = findViewById(R.id.spFilterClass);
        spFilterType  = findViewById(R.id.spFilterTestType);
        btnSave = findViewById(R.id.btnAddMark);
        btnNotify = findViewById(R.id.btnNotiMarks);
        studentApdapter = new StudentApdapter(users,this);

        rvStudentMark.setAdapter(studentApdapter);
        rvStudentMark.setLayoutManager( new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvStudentMark.addItemDecoration(itemDecoration);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,classes);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFilterClass.setAdapter(classAdapter);

        ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,types);
        testAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFilterType.setAdapter(testAdapter);

        fillClassAdapter(classAdapter);
        fillTestAdapter(testAdapter);

        spFilterClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedClass = spFilterClass.getSelectedItem().toString();
                if(users !=null){
                    users.clear();
                }
                fillStudentByClass(selectedClass);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addScore();
            }
        });

        spFilterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                semester = spFilterType.getSelectedItem().toString().contains("2") ? 2 : 1;
                selected_test = spFilterType.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnNotify.setOnClickListener(v->{
            String topic = spFilterClass.getSelectedItem().toString();
            String message="Update "+subject+"'s mark of "+topic;
            FcmNotifySender notifySender = new FcmNotifySender("/topics/"+topic,"Marks Notification",message, getApplicationContext(), this);
            notifySender.sendNotifycation();
        });

    }

    private void addScore() {

        for(int i =0 ; i<rvStudentMark.getChildCount();i++){
            StudentApdapter.StudentHolder holder = (StudentApdapter.StudentHolder) rvStudentMark.findViewHolderForAdapterPosition(i);
            if(holder!=null){
                addScores.add(holder.getScore(semester,selected_test,subject));
                edMark = holder.itemView.findViewById(R.id.edStudentMark);
                edMarks.add(edMark);
            }

        }
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference scoreRef = database.getReference("score_detail");
        addScores.forEach(x->scoreRef.push().setValue(x).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(StudentMarkActivity.this, "Success", Toast.LENGTH_SHORT).show();
                edMarks.forEach(x->x.setText(""));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentMarkActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                edMarks.forEach(x->x.setText(""));
            }
        }));
    }

    private void fillStudentByClass(String selectedClass) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference studentRef = database.getReference("users");
        studentRef.orderByChild("studentData/className").equalTo(selectedClass).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    users.add(user);
                }
                studentApdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fillTestAdapter(ArrayAdapter<String> testAdapter) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference testRef = database.getReference("test_type");

        testRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    types.add(dataSnapshot.getValue(String.class));
                }
                testAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fillClassAdapter(ArrayAdapter<String> classAdapter) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference classRef = database.getReference("class");

        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    classes.add(dataSnapshot.getValue(ClassName.class).getName());
                }
                classAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}