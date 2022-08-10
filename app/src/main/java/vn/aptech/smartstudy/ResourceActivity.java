package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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

public class ResourceActivity extends AppCompatActivity {
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";

    private ResourceAdapter adapter;
    private RecyclerView rvResource;
    FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
    DatabaseReference myRef = database.getReference("resource");
    private List<Resource> resources = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        rvResource = findViewById(R.id.rvResource);
        adapter = new ResourceAdapter(resources,this);
        rvResource.setAdapter(adapter);
        rvResource.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rvResource.addItemDecoration(itemDecoration);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Resource r = dataSnapshot.getValue(Resource.class);
                    resources.add(r);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}