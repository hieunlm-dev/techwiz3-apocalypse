package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        edEmail = findViewById(R.id.edEmail);
        edPasword = findViewById(R.id.edPass);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin(edEmail.getText().toString(),edPasword.getText().toString());
            }
        });

    }

    private void seedingData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    Map<String,Object> users = new HashMap<>();
                    User user = new User();
                    user.setId(1);
                    user.setFull_name("An");
                    user.setEmail("nhta151202@gmail.com");
                    user.setPhone_number("123123123");
                    user.setAddress("590 CMT8");
                    user.setRole("Teacher");
                    user.setPassword("123123");
                    users.put(Integer.toString(user.getId()),user);
                    myRef.setValue(users);
                }
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
                        editor.apply();
                        Toast.makeText(MainActivity.this, "Welcome "+user.getFull_name(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}