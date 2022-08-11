package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {
    private TextView tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        tvEmail = findViewById(R.id.tvEmail);
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_SENDTO);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{"xyz@gmail.com"});
                it.putExtra(Intent.EXTRA_SUBJECT, "Feedback from SmartStudy App");
                it.putExtra(Intent.EXTRA_TEXT, " Message: ");
//            it.setType("message/rfc822");
                it.setData(Uri.parse("mailto:"));
                startActivity(Intent.createChooser(it,"Please select Email app"));
                try {
                    startActivity(Intent.createChooser(it,"Please select Email "));
                }catch(android.content.ActivityNotFoundException ex){
                    Toast.makeText(ContactActivity.this, "There are no Email Clients", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}