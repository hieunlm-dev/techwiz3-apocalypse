package vn.aptech.smartstudy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class PageTeacherActivity extends AppCompatActivity {
    private Button btnLogout;
    private TextView tvWelcome;
    private RelativeLayout rltContact, rltFeedback,rltMark ,rltRvClass,rltExamSchedule,rltstudyResource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_teacher);

        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);
        rltFeedback = findViewById(R.id.rltReviewClassTeacher);
        rltContact = findViewById(R.id.rltContactTeacher);
        rltMark = findViewById(R.id.rltMarksTeacher);
        rltRvClass = findViewById(R.id.rltRvClass);
        rltExamSchedule = findViewById(R.id.rltExamSchedule);
        rltstudyResource = findViewById(R.id.rltResourceTeacher);

        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        String full_name = sharedPreferences.getString("full_name","");
//        String role = sharedPreferences.getString("role","");
        tvWelcome.setText("Welcome "+ full_name );

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finishAffinity();
        });
        rltContact.setOnClickListener(v->{
            Intent it = new Intent(this, ContactActivity.class);
            startActivity(it);
        });
        rltFeedback.setOnClickListener(v->{
            Intent it = new Intent(this, FeedbackActivity.class);
            startActivity(it);
        });
        rltMark.setOnClickListener(v->{
            Intent it = new Intent(this, StudentMarkActivity.class);
            startActivity(it);
        });
        rltRvClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PageTeacherActivity.this, CreateReviewClassActivity.class);
                startActivity(it);
            }
        });
        rltExamSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PageTeacherActivity.this, CreateExamScheduleActivity.class);
                startActivity(it);
            }
        });
        rltstudyResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PageTeacherActivity.this,CreateResourceActivity.class);
                startActivity(it);
            }
        });
    }
}