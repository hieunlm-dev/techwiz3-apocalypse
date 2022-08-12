package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PageTeacherActivity extends AppCompatActivity {
    private Button btnLogout;
    private TextView tvWelcome;
    private RelativeLayout rltContact, rltFeedback,rltMark ,rltRvClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_teacher);

        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);
        rltFeedback = findViewById(R.id.rltFeedBackTeacher);
        rltContact = findViewById(R.id.rltContactPa);
        rltMark = findViewById(R.id.rltMarkStu);
        rltRvClass = findViewById(R.id.rltRvClass);


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
            Intent it = new Intent(this, MarkActivity.class);
            startActivity(it);
        });
        rltRvClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PageTeacherActivity.this, CreateReviewClassActivity.class);
                startActivity(it);
            }
        });

    }
}