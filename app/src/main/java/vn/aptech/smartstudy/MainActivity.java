package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.aptech.smartstudy.entity.ClassName;
import vn.aptech.smartstudy.entity.Resource;
import vn.aptech.smartstudy.entity.ReviewClass;
import vn.aptech.smartstudy.entity.Subject;
import vn.aptech.smartstudy.entity.User;


public class MainActivity extends AppCompatActivity {
    private EditText edEmail , edPasword;
    private Button btnLogin;
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seedingData();
        testQuery();

        edEmail = findViewById(R.id.edEmail);
        edPasword = findViewById(R.id.edPass);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin(edEmail.getText().toString(),edPasword.getText().toString());
                SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
                String full_name = sharedPreferences.getString("full_name","");
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        String full_name = sharedPreferences.getString("full_name","");
        String role = sharedPreferences.getString("role","");

        if(!full_name.equalsIgnoreCase("")){
            navigatePage(role);
        }

    }

    private void seedingData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
//        DatabaseReference myRef = database.getReference("resource");
//        DatabaseReference myRef = database.getReference("reviewclass");
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Map<String, Object> resources = new HashMap<>();
//                    resources.put(Integer.toString(resources.size()+1),new Resource(resources.size()+1,"https://vnexpress.net",new Subject(1,"Math"),"Review 1","Nguyen Hoang Thien An"));
//                    resources.put(Integer.toString(resources.size()+1),new Resource(resources.size()+1,"https://vnexpress.net",new Subject(2,"Chemistry"),"Review 2","Nguyen Hoang Thien An"));
//                    resources.put(Integer.toString(resources.size()+1),new Resource(resources.size()+1,"https://vnexpress.net",new Subject(1,"Math"),"Review 3","Nguyen Hoang Thien An"));
//                    resources.put(Integer.toString(resources.size()+1),new Resource(resources.size()+1,"https://vnexpress.net",new Subject(1,"Math"),"Review 4","Nguyen Hoang Thien An"));
//                    resources.put(Integer.toString(resources.size()+1),new Resource(resources.size()+1,"https://vnexpress.net",new Subject(1,"Math"),"Review 5","Nguyen Hoang Thien An"));
//                    resources.put(Integer.toString(resources.size()+1), new ReviewClass(resources.size()+1, "Review 1", new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()),new ClassName(1,"10A1")));
//                    User test = new User(resources.size()+1, "Vo Phan Hien","0973210955","hien@gmail.com","CMT8 HCM","123123","Student");
//                    resources.put(Integer.toString(resources.size()+1),test);
//                    myRef.push().setValue(resources);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    //test query
    private void testQuery(){
        List<Resource> resources = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("resource");
        myRef.orderByChild("subject/subject").equalTo("Math").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Resource r = dataSnapshot.getValue(Resource.class);
                    resources.add(r);
                }
               for(Resource r : resources){
                   Log.i("url",r.getUrl());
               }

                Toast.makeText(MainActivity.this, Integer.toString(resources.size()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void checkLogin(String email , String password){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("users");

        // Đọc data từ Firebase , kiểm tra thông tin đăng nhập có đúng hay k , nếu đúng => save thông tin vào SharedPreferences
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean result = false;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if(user.getPassword().equals(password)&& user.getEmail().equals(email)){
                        result = true;
                        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("full_name",user.getFull_name());
                        editor.putString("role",user.getRole());
                        editor.apply();
                        Toast.makeText(MainActivity.this, "Welcome "+user.getFull_name(), Toast.LENGTH_SHORT).show();
                        String role= user.getRole();
                        navigatePage(role);
                        break;
                    }else{
                        edEmail.setText("");
                        edPasword.setText("");
                        //Toast.makeText(MainActivity.this, "Wrong Email or Password!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void navigatePage(String role){
            switch (role){
                case "Student":
                    Intent it = new Intent(MainActivity.this, PageTeacherActivity.class );
                    startActivity(it);
                    break;
                case "Teacher":
                    Intent it2 = new Intent(MainActivity.this, PageStudentActivity.class );
                    startActivity(it2);
                    break;
                case "Parent":
                    Intent it3 = new Intent(MainActivity.this, PageParentActivity.class );
                    startActivity(it3);
                    break;

            }
    }
}