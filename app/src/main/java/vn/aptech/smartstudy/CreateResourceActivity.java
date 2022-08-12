package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.aptech.smartstudy.entity.ClassName;
import vn.aptech.smartstudy.entity.Resource;
import vn.aptech.smartstudy.entity.ReviewClass;
import vn.aptech.smartstudy.entity.Subject;

public class CreateResourceActivity extends AppCompatActivity {

    private EditText edRsContent , edTeacherName ;
    private Spinner spUrl, spSubject;
    private Button btnAddResource;
    List<String> subjects = new ArrayList<String>();
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    private String subject;
    Subject selectedSubject = new Subject();
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_resource);

        edRsContent = findViewById(R.id.edRsContent);
        edTeacherName = findViewById(R.id.edTeacherName);
        spSubject = findViewById(R.id.spSubject);
        spUrl = findViewById(R.id.spUrl);
        btnAddResource = findViewById(R.id.btnAddResource);

        getItemCount();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubject.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,subjects);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUrl.setAdapter(adapter1);

        spSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subject = spSubject.getSelectedItem().toString();
                getSubject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spUrl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                subject = spSubject.getSelectedItem().toString();
//                getSubject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fillSubjectIntoSpinner(adapter);

        btnAddResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResource(subject);
            }
        });




    }
    private void getSubject(){
        FirebaseDatabase database1 = FirebaseDatabase.getInstance(URL);
        DatabaseReference classNameRef = database1.getReference("class");

        classNameRef.orderByChild("subject").equalTo(subject).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                selectedSubject.setId(snapshot.getValue(Subject.class).getId());
                selectedSubject.setSubject(snapshot.getValue(Subject.class).getSubject());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getItemCount(){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference classRef = database.getReference("subject");
        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    count = (int) snapshot.getChildrenCount();
                    //Toast.makeText(CreateReviewClassActivity.this, Integer.toString(count), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addResource(String className) {

        Resource rs = new Resource();
        rs.setId(count+1);
        rs.setSubject(selectedSubject);
        rs.setContent(edRsContent.getText().toString());
        rs.setTeacher_name(edTeacherName.getText().toString());
//        rs.setUrl(selectedUrl);
        rs.setUrl("test");

        FirebaseDatabase database2 = FirebaseDatabase.getInstance(URL);
        DatabaseReference rvClassNameRef = database2.getReference("subject/"+Integer.toString(count+1));

        rvClassNameRef.setValue(rs);

        Intent it = new Intent(CreateResourceActivity.this,ResourceActivity.class);
        startActivity(it);

        //Toast.makeText(this, selectedClass.getName(), Toast.LENGTH_SHORT).show();

    }



    private void fillSubjectIntoSpinner(ArrayAdapter<String> adapter){
        //load Class
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference classRef = database.getReference("class");

        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Subject subject = dataSnapshot.getValue(Subject.class);

                    subjects.add(subject.getSubject());

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}