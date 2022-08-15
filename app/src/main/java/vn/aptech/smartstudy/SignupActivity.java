package vn.aptech.smartstudy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import vn.aptech.smartstudy.entity.ClassName;
import vn.aptech.smartstudy.entity.ParentData;
import vn.aptech.smartstudy.entity.StudentData;
import vn.aptech.smartstudy.entity.Subject;
import vn.aptech.smartstudy.entity.TeacherData;
import vn.aptech.smartstudy.entity.User;

public class SignupActivity extends AppCompatActivity {
    private EditText edName, edEmail, edPass, edAddress, edPhone;
    private Spinner spinnerRole, spinnerClass, spinnerSubject, spinnerStudent;
    private TextView tvSubject, tvStudent,tvClass;
    private Button btnRegis;
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    ClassName selectedClass = new ClassName();
    List<String> classNames = new ArrayList<String>();
    List<String> subjects = new ArrayList<String>();
    List<String> students = new ArrayList<String>();
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
        spinnerSubject = findViewById(R.id.spinnerSubject);
        spinnerStudent = findViewById(R.id.spinnerStudentRegis);

        tvSubject = findViewById(R.id.tvSubjectRegis);
        tvStudent = findViewById(R.id.tvStudentRegis);
        tvClass = findViewById(R.id.tvClassRegis);
        btnRegis = findViewById(R.id.btnRegis);
        //fill spinner roles
        String[] roles = new String[]{"Student", "Teacher", "Parent"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        spinnerRole.setAdapter(adapter);
        //classnames
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,classNames);
        fillDataIntoSpinner(adapter2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(adapter2);
        //subjects
        ArrayAdapter<String> adapterSubject = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,subjects);
        fillDataSubjectIntoSpinner(adapterSubject);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(adapterSubject);

        //students
        ArrayAdapter<String> adapterStudent = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,students);
        //hide some field
        tvSubject.setVisibility(View.GONE);
        spinnerSubject.setVisibility(View.GONE);
        spinnerStudent.setVisibility(View.GONE);
        tvStudent.setVisibility(View.GONE);

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerRole.getSelectedItem().toString()=="Teacher"){
                    Toast.makeText(SignupActivity.this, "Teach Role", Toast.LENGTH_SHORT).show();
                    spinnerClass.setVisibility(View.GONE);
                    tvClass.setVisibility(View.GONE);
                    tvSubject.setVisibility(View.VISIBLE);
                    spinnerSubject.setVisibility(View.VISIBLE);
                }
                if(spinnerRole.getSelectedItem().toString()=="Parent"){
                    Toast.makeText(SignupActivity.this, "Please choose Class and Student Name", Toast.LENGTH_SHORT).show();
                    //fill spinner student for parent select
                    tvStudent.setVisibility(View.VISIBLE);
                    spinnerStudent.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                students.clear();
//                Toast.makeText(SignupActivity.this, "Fill data to spinner student", Toast.LENGTH_SHORT).show();
                fillDataStudentByClassIntoSpinner(adapterStudent, spinnerClass.getSelectedItem().toString());
                adapterStudent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerStudent.setAdapter(adapterStudent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //regis
        btnRegis.setOnClickListener(v-> {
            String name = edName.getText().toString();
            String email = edEmail.getText().toString().trim();
            String pass = edPass.getText().toString().trim();
            String address = edAddress.getText().toString().trim();
            String phone = edPhone.getText().toString().trim();
            Boolean valid =  validateRegis(name, email, pass, address, phone);

            String className = spinnerClass.getSelectedItem().toString().trim();
//            String subject = spinnerSubject.getSelectedItem().toString().trim();
            String role = spinnerRole.getSelectedItem().toString().trim();
            FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
            DatabaseReference myRef = database.getReference("users");
            if(valid){
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String , Object> user = new HashMap<String , Object>();
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        if(role=="Student"){
                            User newUser = new User(user.size()+1,name, phone, email, address,pass, role, true, new StudentData("("+name.replaceAll(" ", "").toLowerCase()+")"+email, className,currentDate, email));
                            myRef.push().setValue(newUser);
                            subcribeTopic(className);

                        }else if(role=="Parent"){
                            User newUser = new User(user.size()+1,name, phone, email, address,pass, role, true, new ParentData(spinnerStudent.getSelectedItem().toString().replaceAll(" ", "").toLowerCase(), className,currentDate, email));
                            myRef.push().setValue(newUser);
                            subcribeTopic(className);
                        }else if(role=="Teacher"){
                            String subject = spinnerSubject.getSelectedItem().toString().trim();
                            User newUser = new User(user.size()+1,name, phone, email, address,pass, role, true, new TeacherData(name, subject));
                            myRef.push().setValue(newUser);
                        }
                        sendMail(email);
                        Toast.makeText(SignupActivity.this, "Create Successfull", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(it);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


        });
        //end regist


    }

    private boolean validateRegis(String name, String email, String pass, String address, String phone) {
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //mail
        if(email.length()==0){
            edEmail.requestFocus();
            edEmail.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
//        else if(!email.matches(emailPattern)){
//            edEmail.requestFocus();
//            edEmail.setError("Invalid email address");
//            return false;
//        }
        //phone
        if(phone.length()<10 || phone.length()>13 || !phone.matches("[0-9]+")){
            edPhone.requestFocus();
            edPhone.setError("Invalid phone number");
            return false;
        }
        //password
        if(pass.length()==0){
            edPass.requestFocus();
            edPass.setError("FIELD CANNOT BE EMPTY");
            return false;
        }else if(pass.length() < 8){
            edPass.requestFocus();
            edPass.setError("AT LEAST 8 CHARACTER");
            return false;
        }
        //address
        if(address.length()==0){
            edAddress.requestFocus();
            edAddress.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        //name
        if(name.length()==0){
            edName.requestFocus();
            edName.setError("FIELD CANNOT BE EMPTY");
            return false;
        }else if(!name.matches("[a-zA-Z ]+"))
        {
            edName.requestFocus();
            edName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        }




        return true;
    }


    //subcribeTopic
    private void subcribeTopic(String className) {
        FirebaseMessaging.getInstance().subscribeToTopic(className)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed to receive marks in class "+ className;
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //fill data subjects
    private void fillDataSubjectIntoSpinner(ArrayAdapter<String> adapterSubject) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference classRef = database.getReference("subject");

        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

//                    ClassName className = dataSnapshot.getValue(ClassName.class);
                    Subject subject = dataSnapshot.getValue(Subject.class);
                    subjects.add(subject.getSubject());

                    adapterSubject.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //fill data classnames
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
    //fill data students
    private void fillDataStudentByClassIntoSpinner(ArrayAdapter<String> adapter, String CLASSNAME){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference studentRef = database.getReference("users");

        studentRef.orderByChild("studentData/className").equalTo(CLASSNAME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String studentName = "("+dataSnapshot.getValue(User.class).getFull_name()+")"+dataSnapshot.getValue(User.class).getEmail();
                    students.add(studentName);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void sendMail(String receiverEmail){
        try {


            String stringSenderEmail = "sluuthanh.demo.send@gmail.com";
            String stringReceiverEmail = receiverEmail;
            String stringPasswordSenderMail ="nppwzhwzwlapivlk";

            String stringHost ="smtp.gmail.com";
            String stringPort ="465";

            Properties properties = System.getProperties();
            properties.put("mail.smtp.host",stringHost);
            properties.put("mail.smtp.port",stringPort);
            properties.put("mail.smtp.ssl.enable","true");
            properties.put("mail.smtp.auth","true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail,stringPasswordSenderMail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(stringSenderEmail,"Smart Study"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Welcome to SmartStudy");
            mimeMessage.setContent("<center><h2>Welcome to SmartStudy</h2></br>\n" +
                    "    <img src=\"https://www.it-ausschreibung.de/storage/logos/logo_smart-study_11833.png\" width=\"30%\"></center>\n" +
                    "\n" +
                    "    <p style=\"margin-top:20px;font-family:arial,'helvetica neue',helvetica,sans-serif;color:#333333;line-height:150%;font-size:16px\">You have SmartStudy installed on your Android phone, just one more step to get a better SmartStudy experience</p></br>\n" +
                    "    <p style=\"Margin:0;font-family:arial,'helvetica neue',helvetica,sans-serif;color:#333333;line-height:150%;font-size:16px\">Sign in to your SmartStudy account on your phone to explore all features like view marks, academy progress, study resource, revision classes and so on<br><br>SmartStudy\n" +
                    "        also lets you receive notification when marks update.<br><br>SmartStudy Team</p>", "text/html");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();



        }catch (AddressException e){
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}