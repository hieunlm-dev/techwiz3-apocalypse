package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PageStudentActivity extends AppCompatActivity {
    private Button btnLogout;
    private TextView tvWelcome;
    private RelativeLayout rltHelp,rltContact, rltFeedback, rltMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_student);

        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);
        rltHelp = findViewById(R.id.rltAcademic);
        rltContact = findViewById(R.id.rltContactPa);
        rltFeedback = findViewById(R.id.rltFeedbackPa);
        rltMark = findViewById(R.id.relativeRvClass);

        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        String full_name = sharedPreferences.getString("full_name","");

        tvWelcome.setText("Welcome "+ full_name );

        rltHelp.setOnClickListener(v->{
            Intent it = new Intent(this, HelplineActivity.class);
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
        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
        });
    }
}