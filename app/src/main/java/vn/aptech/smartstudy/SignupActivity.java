package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.aptech.smartstudy.entity.ClassName;
import vn.aptech.smartstudy.entity.StudentData;
import vn.aptech.smartstudy.entity.User;

public class SignupActivity extends AppCompatActivity {
    private EditText edName, edEmail, edPass, edAddress, edPhone;
    private Spinner spinnerRole, spinnerClass;
    private Button btnRegis;
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    ClassName selectedClass = new ClassName();
    List<String> classNames = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edName = findViewById(R.id.edNameRegis);
        edEmail = findViewById(R.id.edEmailRegis);
        edPass = findViewById(R.id.edPassRegis);
        edAddress = findViewById(R.id.edAddressRegis);
        edPhone = findViewById(R.id.edPhoneRegis);
        spinnerRole= findViewById(R.id.spinnerRole);
        spinnerClass = findViewById(R.id.spinnerClassRegis);
        btnRegis = findViewById(R.id.btnRegis);
        //fill spinner
        String[] roles = new String[]{"Student", "Teacher", "Parent"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        spinnerRole.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,classNames);
        fillDataIntoSpinner(adapter2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(adapter2);
        //regis
        btnRegis.setOnClickListener(v-> {
            String name = edName.getText().toString().trim();
            String email = edEmail.getText().toString().trim();
            String pass = edPass.getText().toString().trim();
            String address = edAddress.getText().toString().trim();
            String phone = edPhone.getText().toString().trim();
            String role = spinnerRole.getSelectedItem().toString().trim();
            String className = spinnerClass.getSelectedItem().toString().trim();
            FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
            DatabaseReference myRef = database.getReference("users");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Map<String , Object> user = new HashMap<String , Object>();
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    User newUser = new User(user.size()+1,name, phone, email, address,pass, role, true, new StudentData(name, className,currentDate, email));
                    myRef.push().setValue(newUser);
                    Toast.makeText(SignupActivity.this, "Create Successfull", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(it);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });
        //end regist


    }
    private void fillDataIntoSpinner(ArrayAdapter<String> adapter){
        //load Class
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference classRef = database.getReference("class");

        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ClassName className = dataSnapshot.getValue(ClassName.class);

                    classNames.add(className.getName());

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}