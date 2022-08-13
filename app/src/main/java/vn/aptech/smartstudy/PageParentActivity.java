package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PageParentActivity extends AppCompatActivity {
    private Button btnLogout;
    private TextView tvWelcome;
    private RelativeLayout rlvContact, rlvFeed, rlvHelp, rltMark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_parent);

        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);
        rlvContact = findViewById(R.id.rltContactStudent);
        rlvFeed  = findViewById(R.id.rltFeedbackStudent);
        rlvHelp  = findViewById(R.id.rltProgressStudent);
        rltMark  = findViewById(R.id.rltMarksStudent);

        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        String full_name = sharedPreferences.getString("full_name","");
//        String role = sharedPreferences.getString("role","");
        tvWelcome.setText("Welcome "+ full_name );
        rlvHelp.setOnClickListener(v->{
            Intent it = new Intent(this, HelplineActivity.class);
            startActivity(it);
        });
        rlvContact.setOnClickListener(v->{
            Intent it = new Intent(this, ContactActivity.class);
            startActivity(it);
        });
        rlvFeed.setOnClickListener(v->{
            Intent it = new Intent(this, FeedbackActivity.class);
            startActivity(it);
        });
        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
        });
        rltMark.setOnClickListener(v->{
            Intent it = new Intent(this, MarkActivity.class);
            startActivity(it);
        });
    }
}