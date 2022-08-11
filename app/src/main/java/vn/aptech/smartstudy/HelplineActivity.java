package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class HelplineActivity extends AppCompatActivity {
    private TextView tvHealthPhone, tvSecuPhone, tvOfficePhone;
    private DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);
        tvHealthPhone= findViewById(R.id.tvPhoneHealth2);
        tvOfficePhone= findViewById(R.id.tvPhonePrinci);
        tvSecuPhone= findViewById(R.id.tvPhoneSecu);
        tvHealthPhone.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvHealthPhone.getText().toString()));// Initiates the Intent
            startActivity(intent);
        });
        tvOfficePhone.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvOfficePhone.getText().toString()));// Initiates the Intent
            startActivity(intent);
        });
        tvSecuPhone.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvSecuPhone.getText().toString()));// Initiates the Intent
            startActivity(intent);
        });


    }
}