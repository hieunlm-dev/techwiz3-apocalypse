package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.aptech.smartstudy.entity.ClassName;
import vn.aptech.smartstudy.entity.ReviewClass;

public class CreateReviewClassActivity extends AppCompatActivity {

    private EditText edRvContent , edDate ;
    private Spinner spClass;
    private DatePickerDialog datePickerDialog;
    private Button btnAdd, btnDatePicker;
    List<String> classNames = new ArrayList<String>();
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    private String className;
    ClassName selectedClass = new ClassName();
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review_class);

        spClass = findViewById(R.id.spClass);
        btnAdd = findViewById(R.id.btnReviewAdd);
        edRvContent = findViewById(R.id.edRvContent);
        edDate = findViewById(R.id.edDate);

        initDatePicker();
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnDatePicker.setText(getTodayDate());

        getItemCount();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,classNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClass.setAdapter(adapter);

        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                className = spClass.getSelectedItem().toString();
                getClassName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        fillDataIntoSpinner(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReviewClass(className);
            }
        });

// calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month +1;
                String date = makeDateString(day, month, year);
                btnDatePicker.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) +" "+day+" "+year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUNE";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
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
    private void getClassName(){
        FirebaseDatabase database1 = FirebaseDatabase.getInstance(URL);
        DatabaseReference classNameRef = database1.getReference("class");

        classNameRef.orderByChild("name").equalTo(className).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                selectedClass.setId(snapshot.getValue(ClassName.class).getId());
                selectedClass.setName(snapshot.getValue(ClassName.class).getName());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getItemCount(){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference classRef = database.getReference("reviewclass");
        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    count = (int) snapshot.getChildrenCount();
                    //Toast.makeText(CreateReviewClassActivity.this, Integer.toString(count), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addReviewClass(String className) {

        ReviewClass rv = new ReviewClass();
        rv.setId(count+1);
        rv.setClassName(selectedClass);
        rv.setContent(edRvContent.getText().toString());
//        rv.setDatetime(edDate.getText().toString());
        rv.setDatetime(btnDatePicker.getText().toString());

        FirebaseDatabase database2 = FirebaseDatabase.getInstance(URL);
        DatabaseReference rvClassNameRef = database2.getReference("reviewclass/"+Integer.toString(count+1));

        rvClassNameRef.setValue(rv);

        Intent it = new Intent(CreateReviewClassActivity.this,ReviewClassActivity.class);

        startActivity(it);
        finish();
        //Toast.makeText(this, selectedClass.getName(), Toast.LENGTH_SHORT).show();

    }



    private void fillDataIntoSpinner(ArrayAdapter<String> adapter){
        //load Class
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference classRef = database.getReference("class");

        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ClassName className = dataSnapshot.getValue(ClassName.class);

                    classNames.add(className.getName());

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}