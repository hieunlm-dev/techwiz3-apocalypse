package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NavigateActivity extends AppCompatActivity {
    private Button btnLogout;
    private TextView tvWelcome;
    private RelativeLayout relativeRvClass, rltFeedback, rltContact, rltMark;
    private LinearLayout layoutGuard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);


        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);
        relativeRvClass = findViewById(R.id.rltHelp);
        rltFeedback = findViewById(R.id.rltFeedbackPa);
        rltContact = findViewById(R.id.rltContactPa);
        rltMark = findViewById(R.id.rltMarks);
        layoutGuard = findViewById(R.id.layoutGuard);

        relativeRvClass.setOnClickListener(v->{
            Intent it = new Intent(NavigateActivity.this, ReviewClassActivity.class);
            startActivity(it);
        });
        rltFeedback.setOnClickListener(v->{
            Intent it = new Intent(NavigateActivity.this, FeedbackActivity.class);
            startActivity(it);
        });
        rltContact.setOnClickListener(v->{
            Intent it = new Intent(NavigateActivity.this, ContactActivity.class);
            startActivity(it);
        });

        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        String full_name = sharedPreferences.getString("full_name","");
        String role = sharedPreferences.getString("role","");
        tvWelcome.setText("Welcome "+ full_name + role);

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent it = new Intent(NavigateActivity.this, MainActivity.class);
            startActivity(it);
        });
//        if(role.equalsIgnoreCase("Teacher")){
//            layoutGuard.setVisibility(View.GONE);
//        }

    }
}