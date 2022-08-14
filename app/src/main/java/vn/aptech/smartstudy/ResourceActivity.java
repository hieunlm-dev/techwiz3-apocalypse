package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.aptech.smartstudy.adapter.ResourceAdapter;
import vn.aptech.smartstudy.entity.Resource;
import vn.aptech.smartstudy.entity.Subject;

public class ResourceActivity extends AppCompatActivity {
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";

    private ResourceAdapter adapter;
    private RecyclerView rvResource;
    private Spinner spFilterSubject;

    private List<Resource> resources = new ArrayList<>();
    private List<String> subjects = new ArrayList<>();
    private String selectedSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        spFilterSubject = findViewById(R.id.spFilterSubject);
        rvResource = findViewById(R.id.rvResource);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,subjects);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFilterSubject.setAdapter(adapter1);


        adapter = new ResourceAdapter(resources,this);
        rvResource.setAdapter(adapter);
        rvResource.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rvResource.addItemDecoration(itemDecoration);

        spFilterSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                resources.clear();
                selectedSubject = spFilterSubject.getSelectedItem().toString();
                fecthResources(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       fecthResources(adapter);
       fillSpinnerSubject(adapter1);

    }

    private void fecthResources(ResourceAdapter adapter){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("resource");
        myRef.orderByChild("subject/subject").equalTo(selectedSubject).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Resource r = dataSnapshot.getValue(Resource.class);
                    resources.add(r);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    private void fillSpinnerSubject (ArrayAdapter<String> adapter1){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("subject");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    subjects.add(dataSnapshot.getValue(Subject.class).getSubject());
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        // calling the action bar
        //ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        //actionBar.setDisplayHomeAsUpEnabled(true);
    }


