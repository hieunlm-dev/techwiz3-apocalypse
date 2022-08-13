package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
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
// calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}