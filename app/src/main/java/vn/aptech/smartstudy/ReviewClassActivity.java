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

import vn.aptech.smartstudy.adapter.ResourceAdapter;
import vn.aptech.smartstudy.adapter.ReviewClassAdapter;
import vn.aptech.smartstudy.entity.Resource;
import vn.aptech.smartstudy.entity.ReviewClass;

public class ReviewClassActivity extends AppCompatActivity {
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";

    private ReviewClassAdapter adapter;
    private RecyclerView rvReviewClass;
    FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
    DatabaseReference myRef = database.getReference("reviewclass");
    private List<ReviewClass> reviewClasses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_class);
        rvReviewClass = findViewById(R.id.rvReviewClass);
        adapter = new ReviewClassAdapter(reviewClasses,this);
        rvReviewClass.setAdapter(adapter);
        rvReviewClass.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rvReviewClass.addItemDecoration(itemDecoration);


//        myRef.orderByChild("className/name").equalTo("10A1").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    ReviewClass rvc = dataSnapshot.getValue(ReviewClass.class);
//                    reviewClasses.add(rvc);
//                    adapter.notifyDataSetChanged();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
        //Teacher dung ifelse de check role
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ReviewClass rvc = dataSnapshot.getValue(ReviewClass.class);
                    reviewClasses.add(rvc);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}