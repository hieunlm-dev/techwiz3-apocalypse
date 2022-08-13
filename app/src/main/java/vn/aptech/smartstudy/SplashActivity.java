package vn.aptech.smartstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        String full_name = sharedPreferences.getString("full_name","");
        String role = sharedPreferences.getString("role","");
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!full_name.equalsIgnoreCase("")){
                    Toast.makeText(SplashActivity.this, "Hello "+full_name, Toast.LENGTH_SHORT).show();
                    navigatePage(role);
                }else{
                    Intent it = new Intent(SplashActivity.this, MainActivity.class );
                    startActivity(it);
                }
            }
        }, 500);


    }
    private void navigatePage(String role){

        switch (role){
            case "Student":
                Intent it = new Intent(this, PageStudentActivity.class );
                startActivity(it);
                break;
            case "Teacher":
                Intent it2 = new Intent(this, PageTeacherActivity.class );
                startActivity(it2);
                break;
            case "Parent":
                Intent it3 = new Intent(this, PageParentActivity.class );
                startActivity(it3);
                break;
            default:break;
        }
    }
}