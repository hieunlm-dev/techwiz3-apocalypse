package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import vn.aptech.smartstudy.service.FcmNotifySender;

public class NotifycationActivity extends AppCompatActivity {
    private EditText edMess;
    private Button btnSendNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifycation);
        edMess = findViewById(R.id.edMessNoti);
        btnSendNoti = findViewById(R.id.btnSendNoti);
        

        btnSendNoti.setOnClickListener(v->{
            FcmNotifySender notifySender = new FcmNotifySender("/topics/12A1-2022","Thong bao",edMess.getText().toString(), getApplicationContext(), this);
            notifySender.sendNotifycation();
        });
    }
}