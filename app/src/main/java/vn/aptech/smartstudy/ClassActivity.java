package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.aptech.smartstudy.adapter.ClassAdapter;
import vn.aptech.smartstudy.entity.ClassName;

/**
 *
 */
public class ClassActivity extends AppCompatActivity {

    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";

    private ClassAdapter adapter;
    private RecyclerView rvClass;
    FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
    DatabaseReference myRef = database.getReference("class");
    private List<ClassName> classNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_class);
        adapter = new ClassAdapter(classNames,this);

        rvClass = findViewById(R.id.rvClassLists);

        rvClass.setAdapter(adapter);

        rvClass.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvClass.addItemDecoration(itemDecoration);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ClassName rvc = dataSnapshot.getValue(ClassName.class);
                    classNames.add(rvc);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}