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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.aptech.smartstudy.entity.ClassName;
import vn.aptech.smartstudy.entity.ExamSchedule;
import vn.aptech.smartstudy.entity.Resource;
import vn.aptech.smartstudy.entity.ResourceURL;
import vn.aptech.smartstudy.entity.ReviewClass;
import vn.aptech.smartstudy.entity.ScoreDetail;
import vn.aptech.smartstudy.entity.StudentData;
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
        seedingData();

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
        DatabaseReference myRef = database.getReference("resource_url");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                 /*   Map<String , String> test_types = new HashMap<String , String>();
                    test_types.put(Integer.toString(test_types.size()+1),"15 minutes test Sem 1");
                    test_types.put(Integer.toString(test_types.size()+1),"45 minutes test Sem 1");
                    test_types.put(Integer.toString(test_types.size()+1),"Middle semester test Sem 1");
                    test_types.put(Integer.toString(test_types.size()+1),"Final semester test Sem 1");
                    test_types.put(Integer.toString(test_types.size()+1),"15 minutes test Sem 2");
                    test_types.put(Integer.toString(test_types.size()+1),"45 minutes test Sem 2");
                    test_types.put(Integer.toString(test_types.size()+1),"Middle semester test Sem 2");
                    test_types.put(Integer.toString(test_types.size()+1),"Final semester test Sem 2");*/


                 /*   ExamSchedule schedule = new ExamSchedule();
                    schedule.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
                    schedule.setExam_content("45 minutes Test on 13/8/2022");
                    schedule.setSubject("Math");
                    schedule.setType_test("45 minutes test Sem 1");
                    schedule.setClass_name("12A1");
                    schedule.setHour("8:00");

                  */
                    List<ResourceURL> resources = new ArrayList<ResourceURL>();

                    resources.add(new ResourceURL("https://hoctructuyen.violet.vn/present/ham-so-bac-hai-13248817.html","Quadratic function","Math"));
                    resources.add(new ResourceURL("https://hoctructuyen.violet.vn/present/tong-va-hieu-cua-hai-vecto-13199350.html","Summation and Subtraction of two vectors","Math"));
                    resources.add(new ResourceURL("https://hoctructuyen.violet.vn/present/cap-so-nhan-12094605.html","Level multiplier in Math","Math"));
                    resources.add(new ResourceURL("https://hoctructuyen.violet.vn/lesson/relationships-lesson-7-communication-and-culture-8507277-15.html","Unit 2. Relationships. Lesson 7. Communication and culture , English 11","English"));
                    resources.add(new ResourceURL("https://hoctructuyen.violet.vn/present/technology-and-you-13337243.html","Unit 5. Technology and you","English"));
                    resources.add(new ResourceURL("https://hoctructuyen.violet.vn/present/clo-13248318.html","Chlorine","Chemistry"));
                    resources.add(new ResourceURL("https://123docz.net/document/1282365-petroleum-refining-iii-conversion-processes.htm","Petroleum refining","Chemistry"));
                    resources.add(new ResourceURL("https://hoctructuyen.violet.vn/present/nhom-va-hop-chat-cua-nhom-13440211.html","Aluminum and its compounds","Chemistry"));
                    resources.add(new ResourceURL("https://hoctructuyen.violet.vn/present/cac-luc-co-hoc-13285777.html","Mechanical Force","Physics"));
                    resources.add(new ResourceURL("https://hoctructuyen.violet.vn/present/ba-dinh-luat-niu-ton-13261997.html","Newton's laws of motion","Physics"));
                    resources.add(new ResourceURL("https://toploigiai.vn/phan-tich-truyen-kieu-cua-nguyen-du","Analysis of the Tale of Kieu","Literature"));
                    resources.add(new ResourceURL("https://toploigiai.vn/trac-nghiem-ngu-van-10","Literature Quiz 10","Literature"));
                    resources.add(new ResourceURL("https://toploigiai.vn/giai-tap-ban-do-dia-li-10","Geographic Atlas exercise","Geography"));
                    resources.add(new ResourceURL("https://toploigiai.vn/ly-thuyet-chay-cu-ly-ngan","Theory of running short distances\n","P.E"));
                    resources.add(new ResourceURL("https://toploigiai.vn/soan-sinh-10-ngan-nhat","Biology Quiz","Biology"));
                    resources.add(new ResourceURL("https://toploigiai.vn/trac-nghiem-giao-duc-quoc-phong-10-co-dap-an","Civic Education Quiz","CivicEducation"));

                    resources.forEach(x-> myRef.push().setValue(x));

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
        //studentData/username
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

                        if(user.getRole().equals("Student")){
                            editor.putString("student_name",user.getStudentData().getFullName());
                            editor.putString("student_class",user.getStudentData().getClassName());
                            Log.i("name :",user.getStudentData().getFullName());
                        }

                        if(user.getRole().equals("Parent")){
                            editor.putString("student_name",user.getParentData().getFullName());
                        }

                        if(user.getRole().equals("Teacher")){
                            editor.putString("subject",user.getTeacherData().getSubject());
                        }

                        editor.putString("role",user.getRole());
                        editor.apply();
                        Toast.makeText(MainActivity.this, "Welcome "+user.getFull_name(), Toast.LENGTH_SHORT).show();
                        String role = sharedPreferences.getString("role","");
                        navigatePage(role);
                        break;
                    }

                }
                if(result==false){
                    edEmail.setText("");
                    edPasword.setText("");
                    Toast.makeText(MainActivity.this, "Wrong Email or Password!", Toast.LENGTH_SHORT).show();

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