
package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {
    private EditText edName, edMessage;
    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        edName = findViewById(R.id.edName);
        edMessage = findViewById(R.id.edMessage);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {
            Intent it = new Intent(Intent.ACTION_SENDTO);
            it.putExtra(Intent.EXTRA_EMAIL, new String[]{"xyz@gmail.com"});
            it.putExtra(Intent.EXTRA_SUBJECT, "Feedback from SmartStudy App");
            it.putExtra(Intent.EXTRA_TEXT, "Name: "+edName.getText()+"\n Message: "+edMessage.getText());
//            it.setType("message/rfc822");
            it.setData(Uri.parse("mailto:"));
            startActivity(Intent.createChooser(it,"Please select Email app"));
            try {
                startActivity(Intent.createChooser(it,"Please select Email "));
            }catch(android.content.ActivityNotFoundException ex){
                Toast.makeText(this, "There are no Email Clients", Toast.LENGTH_SHORT).show();
            }
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