package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private LinearLayout layoutSignup;
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        seedingData();
//        testQuery();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
//                String full_name = sharedPreferences.getString("full_name","");
//                String role = sharedPreferences.getString("role","");
//                if(!full_name.equalsIgnoreCase("")){
//                    navigatePage(role);
//                }
//                navigatePage(role);
//            }
//        },2000);
        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        String full_name = sharedPreferences.getString("full_name","");
        String role = sharedPreferences.getString("role","");
        if(!full_name.equalsIgnoreCase("")){
            navigatePage(role);
        }
        navigatePage(role);
        edEmail = findViewById(R.id.edEmail);
        edPasword = findViewById(R.id.edPass);
        layoutSignup = findViewById(R.id.layoutSignup);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin(edEmail.getText().toString(),edPasword.getText().toString());
//                SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
//                String full_name = sharedPreferences.getString("full_name","");
            }
        });
        layoutSignup.setOnClickListener(v->{
            Intent it = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(it);
        });
    }

    private void seedingData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
//        DatabaseReference myRef = database.getReference("resource");
//        DatabaseReference myRef = database.getReference("reviewclass");
        DatabaseReference myRef = database.getReference("class");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Map<String , String> test_types = new HashMap<String , String>();
                    test_types.put(Integer.toString(test_types.size()+1),"15 minutes test");
                    test_types.put(Integer.toString(test_types.size()+1),"45 minutes test");
                    test_types.put(Integer.toString(test_types.size()+1),"Middle semester test");
                    test_types.put(Integer.toString(test_types.size()+1),"Final semester test");


                    Map<String , Object> classes = new HashMap<String , Object>();
                    classes.put(Integer.toString(classes.size()+1), new ClassName(classes.size()+1,"10A1",test_types));
                    classes.put(Integer.toString(classes.size()+1), new ClassName(classes.size()+1,"10A2",test_types));
                    classes.put(Integer.toString(classes.size()+1), new ClassName(classes.size()+1,"11A1",test_types));
                    classes.put(Integer.toString(classes.size()+1), new ClassName(classes.size()+1,"11A2",test_types));
                    classes.put(Integer.toString(classes.size()+1), new ClassName(classes.size()+1,"12A1",test_types));
                    classes.put(Integer.toString(classes.size()+1), new ClassName(classes.size()+1,"12A2",test_types));

                   myRef.setValue(classes);
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
                        editor.putString("email",user.getEmail());
                        editor.putString("role",user.getRole());
                        editor.apply();
                        Toast.makeText(MainActivity.this, "Welcome "+user.getFull_name(), Toast.LENGTH_SHORT).show();
                        String role = sharedPreferences.getString("role","");
                        navigatePage(role);
                        break;
                    }else{
                        edEmail.setText("");
                        edPasword.setText("");
                        Toast.makeText(MainActivity.this, "Wrong Email or Password!", Toast.LENGTH_SHORT).show();
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
                    Intent it = new Intent(MainActivity.this, PageStudentActivity.class );
                    startActivity(it);
                    break;
                case "Teacher":
                    Intent it2 = new Intent(MainActivity.this, PageTeacherActivity.class );
                    startActivity(it2);
                    break;
                case "Parent":
                    Intent it3 = new Intent(MainActivity.this, PageParentActivity.class );
                    startActivity(it3);
                    break;
                default:break;
            }
    }
}