package vn.aptech.smartstudy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import vn.aptech.smartstudy.entity.ScoreDetail;
import vn.aptech.smartstudy.entity.User;

public class MarkActivity extends AppCompatActivity {

    private Spinner spinnerSemeter;
    private String[] semeters = new String[]{"Semester 1","Semester 2"};
    private List<ScoreDetail> allScores= new ArrayList<ScoreDetail>();
    private Button btnSendEmail;
    private ImageView imvSubcribe;

    private List<ScoreDetail> maths;
    private List<ScoreDetail> englishes= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> chems= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> physics= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> histories= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> biologies= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> literatures= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> civics= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> geos= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> pes= new ArrayList<ScoreDetail>();

    private TextView tvMath15 , tvMath45 , tvMathMid ,tvMathFinal ,tvMathAverage;
    private TextView tvEng15 , tvEng45 , tvEngMid , tvEngFinal,tvEngAverage;
    private TextView tvChem15 , tvChem45 , tvChemMid , tvChemFinal,tvChemAverage;
    private TextView tvPhysics15 , tvPhysics45,tvPhysicsMid , tvPhysicsFinal,tvPhysicsAverage;
    private TextView tvHistory15 , tvHistory45,tvHistoryMid , tvHistoryFinal,tvHistoryAverage;
    private TextView tvBiology15 , tvBiology45,tvBiologyMid , tvBiologyFinal,tvBiologyAverage;
    private TextView tvLiterature15 , tvLiterature45 , tvLiteratureMid , tvLiteratureFinal,tvLiteratureAverage;
    private TextView tvCivic15 ,tvCivic45,tvCivicMid , tvCivicFinal ,tvCivicAverage;
    private TextView tvGeo15 , tvGeo45,tvGeoMid , tvGeoFinal,tvGeoAverage;
    private TextView tvPE15 , tvPE45 , tvPEMid , tvPEFinal , tvPEAverage;

    private Float avgMath= 0f;
    private Float avgEng= 0f;private Float avgPhy= 0f;
    private Float avgGeo= 0f;private Float avgCivic= 0f;
    private Float avgPe= 0f;private Float avgHis= 0f;
    private Float avgBio= 0f;private Float avgChem= 0f;
    private Float avgLit= 0f;
    private User user = new User();

    private String studentName ;
    private String studentClass ;

    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    private int selectedCount =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        studentName = sharedPreferences.getString("student_name","");
        //getDataFromFirebase(selectedCount);
        //Toast.makeText(this, studentName, Toast.LENGTH_SHORT).show();
        Log.i("data",studentName);
        //fillData();
        initUi();
        initMathTv();
        initEngTv();
        initChemTv();
        initPhysicsTv();
        initHistoryTv();
        initBiologyTv();
        initLiteratureTv();
        initCivicTv();
        initGeoTv();
        initPETv();

        
        getStudentClass();
//        className =
        imvSubcribe.setOnClickListener(v->{
            subscribeTopics();
        });
        btnSendEmail.setOnClickListener(v->{
            sendMail(sharedPreferences.getString("email",""));
        });
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
//        borderUi();
    }

    private void sendMail(String receiverEmail){
        try {
            String stringSenderEmail = "sluuthanh.demo.send@gmail.com";
            String stringReceiverEmail = receiverEmail;
            String stringPasswordSenderMail ="nppwzhwzwlapivlk";

            String stringHost ="smtp.gmail.com";
            String stringPort ="465";

            Properties properties = System.getProperties();
            properties.put("mail.smtp.host",stringHost);
            properties.put("mail.smtp.port",stringPort);
            properties.put("mail.smtp.ssl.enable","true");
            properties.put("mail.smtp.auth","true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail,stringPasswordSenderMail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(stringSenderEmail,"Smart Study"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Automail from Smart Study: Mark report");
            mimeMessage.setContent("<center><h2>Welcome to SmartStudy</h2></br>\n" +
                    "    <img src=\"https://www.it-ausschreibung.de/storage/logos/logo_smart-study_11833.png\" width=\"10%\"></center>"+"<br/>"+contentMail(),"text/html");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();



        }catch (AddressException e){
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String contentMail(){

        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);



        String studentName = sharedPreferences.getString("student_name","");

        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);

        DatabaseReference userDetailRef = database.getReference("users");

        userDetailRef.orderByChild("studentData/fullName").equalTo(studentName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.setEmail(snapshot.getValue(User.class).getEmail());
                user.setFull_name(snapshot.getValue(User.class).getFull_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append("Mark report of student: "+user.getFull_name()+"\n");
        if(maths.size() >0){
            sb.append("Math: ");
            if(!tvMath15.getText().toString().isEmpty()){
                sb.append("15 minutes Test: "+tvMath15.getText().toString()+"\n");
            }
            if(!tvMath45.getText().toString().isEmpty()){
                sb.append("45 minutes Test: "+tvMath45.getText().toString()+"\n");
            }
            if(!tvMathMid.getText().toString().isEmpty()){
                sb.append("Middle semester Test: "+tvMathMid.getText().toString()+"\n");
            }
            if(!tvMathFinal.getText().toString().isEmpty()){
                sb.append("Final semester Test: "+tvMathFinal.getText().toString()+"\n");
            }
        }

        if(chems.size() >0){
            sb.append("Chemistry: ");
            if(!tvChem15.getText().toString().isEmpty()){
                sb.append("15 minutes Test: "+tvChem15.getText().toString()+"\n");
            }
            if(!tvChem45.getText().toString().isEmpty()){
                sb.append("45 minutes Test: "+tvChem45.getText().toString()+"\n");
            }
            if(!tvChemMid.getText().toString().isEmpty()){
                sb.append("Middle semester Test: "+tvChemMid.getText().toString()+"\n");
            }
            if(!tvChemFinal.getText().toString().isEmpty()){
                sb.append("Final semester Test: "+tvChemFinal.getText().toString()+"\n");
            }
        }


        if(englishes.size() >0){
            sb.append("English: ");
            if(!tvEng15.getText().toString().isEmpty()){
                sb.append("15 minutes Test: "+tvEng15.getText().toString()+"\n");
            }
            if(!tvEng45.getText().toString().isEmpty()){
                sb.append("45 minutes Test: "+tvEng45.getText().toString()+"\n");
            }
            if(!tvEngMid.getText().toString().isEmpty()){
                sb.append("Middle semester Test: "+tvEngMid.getText().toString()+"\n");
            }
            if(!tvEngFinal.getText().toString().isEmpty()){
                sb.append("Final semester Test: "+tvEngFinal.getText().toString()+"\n");
            }
        }

        if(physics.size() >0){
            sb.append("Physics: ");
            if(!tvPhysics15.getText().toString().isEmpty()){
                sb.append("15 minutes Test: "+tvPhysics15.getText().toString()+"\n");
            }
            if(!tvPhysics45.getText().toString().isEmpty()){
                sb.append("45 minutes Test: "+tvPhysics15.getText().toString()+"\n");
            }
            if(!tvPhysicsMid.getText().toString().isEmpty()){
                sb.append("Middle semester Test: "+tvPhysicsMid.getText().toString()+"\n");
            }
            if(!tvPhysicsFinal.getText().toString().isEmpty()){
                sb.append("Final semester Test: "+tvPhysicsFinal.getText().toString()+"\n");
            }
        }

        if(literatures.size() >0){
            sb.append("Literature: ");
            if(!tvLiterature15.getText().toString().isEmpty()){
                sb.append("15 minutes Test: "+tvLiterature15.getText().toString()+"\n");
            }
            if(!tvLiterature45.getText().toString().isEmpty()){
                sb.append("45 minutes Test: "+tvLiterature45.getText().toString()+"\n");
            }
            if(!tvLiteratureMid.getText().toString().isEmpty()){
                sb.append("Middle semester Test: "+tvLiteratureMid.getText().toString()+"\n");
            }
            if(!tvLiteratureFinal.getText().toString().isEmpty()){
                sb.append("Final semester Test: "+tvLiteratureFinal.getText().toString()+"\n");
            }
        }

        if(histories.size() >0){
            sb.append("History: ");
            if(!tvHistory15.getText().toString().isEmpty()){
                sb.append("15 minutes Test: "+tvHistory15.getText().toString()+"\n");
            }
            if(!tvHistory45.getText().toString().isEmpty()){
                sb.append("45 minutes Test: "+tvHistory45.getText().toString()+"\n");
            }
            if(!tvHistoryMid.getText().toString().isEmpty()){
                sb.append("Middle semester Test: "+tvHistoryMid.getText().toString()+"\n");
            }
            if(!tvHistoryFinal.getText().toString().isEmpty()){
                sb.append("Final semester Test: "+tvHistoryFinal.getText().toString()+"\n");
            }
        }

        if(geos.size() >0){
            sb.append("Geography: ");
            if(!tvGeo15.getText().toString().isEmpty()){
                sb.append("15 minutes Test: "+tvGeo15.getText().toString()+"\n");
            }
            if(!tvGeo45.getText().toString().isEmpty()){
                sb.append("45 minutes Test: "+tvGeo45.getText().toString()+"\n");
            }
            if(!tvGeoMid.getText().toString().isEmpty()){
                sb.append("Middle semester Test: "+tvGeoMid.getText().toString()+"\n");
            }
            if(!tvGeoFinal.getText().toString().isEmpty()){
                sb.append("Final semester Test: "+tvGeoFinal.getText().toString()+"\n");
            }
        }

        if(civics.size() >0){
            sb.append("Civic Education: ");
            if(!tvCivic15.getText().toString().isEmpty()){
                sb.append("15 minutes Test: "+tvCivic15.getText().toString()+"\n");
            }
            if(!tvCivic45.getText().toString().isEmpty()){
                sb.append("45 minutes Test: "+tvCivic45.getText().toString()+"\n");
            }
            if(!tvCivicMid.getText().toString().isEmpty()){
                sb.append("Middle semester Test: "+tvCivicMid.getText().toString()+"\n");
            }
            if(!tvCivicFinal.getText().toString().isEmpty()){
                sb.append("Final semester Test: "+tvCivicFinal.getText().toString()+"\n");
            }
        }

        if(pes.size() >0){
            sb.append("Physical Education: ");
            if(!tvPE15.getText().toString().isEmpty()){
                sb.append("15 minutes Test: "+tvPE15.getText().toString()+"\n");
            }
            if(!tvPE45.getText().toString().isEmpty()){
                sb.append("45 minutes Test: "+tvPE45.getText().toString()+"\n");
            }
            if(!tvPEMid.getText().toString().isEmpty()){
                sb.append("Middle semester Test: "+tvPEMid.getText().toString()+"\n");
            }
            if(!tvPEFinal.getText().toString().isEmpty()){
                sb.append("Final semester Test: "+tvPEFinal.getText().toString()+"\n");
            }
        }

        return sb.toString();
    }
    private void getStudentClass() {
        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        studentClass = sharedPreferences.getString("student_class","");


        String studentName = sharedPreferences.getString("student_name","");

        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);

        DatabaseReference userDetailRef = database.getReference("users");



    }

    private void borderUi() {
        List<TextView> listMark = Arrays.asList(
        tvMath15 , tvMath45 , tvMathMid ,tvMathFinal ,tvMathAverage,
        tvEng15 , tvEng45 , tvEngMid , tvEngFinal,tvEngAverage,
        tvChem15 , tvChem45 , tvChemMid , tvChemFinal,tvChemAverage,
        tvPhysics15 , tvPhysics45,tvPhysicsMid , tvPhysicsFinal,tvPhysicsAverage,
        tvHistory15 , tvHistory45,tvHistoryMid , tvHistoryFinal,tvHistoryAverage,
        tvBiology15 , tvBiology45,tvBiologyMid , tvBiologyFinal,tvBiologyAverage,
        tvLiterature15 , tvLiterature45 , tvLiteratureMid , tvLiteratureFinal,tvLiteratureAverage,
        tvCivic15 ,tvCivic45,tvCivicMid , tvCivicFinal ,tvCivicAverage,
        tvGeo15 , tvGeo45,tvGeoMid , tvGeoFinal,tvGeoAverage,
        tvPE15 , tvPE45 , tvPEMid , tvPEFinal , tvPEAverage
        );
        for (TextView tv:listMark) {
            if(tv.getText().toString()==""){
                tv.setText("");
                tv.setVisibility(View.VISIBLE);
            }
        }
        tvPhysics45.setVisibility(View.VISIBLE);
        tvPhysics45.setText("test");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void subscribeTopics() {
        FirebaseMessaging.getInstance().subscribeToTopic(studentClass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed "+ studentClass;
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(MarkActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDataFromFirebase(int selectedCount) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference scoreDetailRef = database.getReference("score_detail");
        scoreDetailRef.orderByChild("student_email").equalTo(studentName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    allScores.add(dataSnapshot.getValue(ScoreDetail.class));
                    //Toast.makeText(MarkActivity.this,dataSnapshot.getValue(ScoreDetail.class).getSubject_name().toString() , Toast.LENGTH_SHORT).show();
                }
                allScores = allScores.stream().filter(x->x.getSemester()==selectedCount).collect(Collectors.toList());
                maths = allScores.stream().filter(x->x.getSubject_name().equals("Math")).collect(Collectors.toList());

                List<ScoreDetail> math15Details = maths.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());

                if(math15Details.size()!=0){
                    tvMath15.setVisibility(View.VISIBLE);

                }
                if(math15Details.size()>1){
                    math15Details.forEach(x ->tvMath15.setText(tvMath15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    math15Details.forEach(x ->tvMath15.setText(Integer.toString(Math.round(x.getMark()))));
                }


                List<ScoreDetail> math45Details = maths.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());
                if(math45Details.size()>0){
                    tvMath45.setVisibility(View.VISIBLE);
                }

                if(math45Details.size()>1){
                    math45Details.forEach(x ->tvMath45.setText(tvMath45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    math45Details.forEach(x ->tvMath45.setText(Integer.toString(Math.round(x.getMark()))));
                }


                List<ScoreDetail> mathMidDetails = maths.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());
                if(mathMidDetails.size()>0){
                    tvMathMid.setVisibility(View.VISIBLE);
                }

                if(mathMidDetails.size()>1){
                    math45Details.forEach(x ->tvMathMid.setText(tvMathMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    math45Details.forEach(x ->tvMathMid.setText(Integer.toString(Math.round(x.getMark()))));
                }


                List<ScoreDetail> mathFinalDetails = maths.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());
                if(mathFinalDetails.size()>0){
                    tvMathFinal.setVisibility(View.VISIBLE);
                }

                if(mathFinalDetails.size()>1){
                    mathFinalDetails.forEach(x ->tvMathFinal.setText(tvMathFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    mathFinalDetails.forEach(x ->tvMathFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }

                maths.forEach(x->{
                    if(x.getMark()>0) {
                        avgMath += x.getMark();
                    }
                });
                avgMath = (avgMath/maths.size());
                if(avgMath>0){
                    tvMathAverage.setText(avgMath.toString());
                }



                chems = allScores.stream().filter(x->x.getSubject_name().equals("Chemistry")).collect(Collectors.toList());

                List<ScoreDetail> chemsFinalDetail = chems.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());
                if(chemsFinalDetail.size()>0){
                    tvChemFinal.setVisibility(View.VISIBLE);
                }

                if(chemsFinalDetail.size()>1){
                    chemsFinalDetail.forEach(x ->tvChemFinal.setText(tvChemFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    chemsFinalDetail.forEach(x ->tvChemFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> chemsMidDetail = chems.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());
                if(chemsFinalDetail.size()>0){
                    tvChemMid.setVisibility(View.VISIBLE);
                }

                if(chemsMidDetail.size()>1){
                    chemsFinalDetail.forEach(x ->tvChemMid.setText(tvChemMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    chemsMidDetail.forEach(x ->tvChemMid.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> chems45Detail = chems.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());
                if(chems45Detail.size()>0){
                    tvChem45.setVisibility(View.VISIBLE);
                }

                if(chems45Detail.size()>1){
                    chemsFinalDetail.forEach(x ->tvChem45.setText(tvChem45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    chemsMidDetail.forEach(x ->tvChem45.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> chems15Detail = chems.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());
                if(chems15Detail.size()>0){
                    tvChem15.setVisibility(View.VISIBLE);
                }

                if(chems15Detail.size()>1){
                    chemsFinalDetail.forEach(x ->tvChem15.setText(tvChem15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    chemsMidDetail.forEach(x ->tvChem15.setText(Integer.toString(Math.round(x.getMark()))));
                }

                chems.forEach(x->{
                    if(x.getMark()>0) {
                        avgChem += x.getMark();
                    }
                });
                avgChem = (avgChem/chems.size());
                if(avgChem>0){
                    tvChemAverage.setText(avgChem.toString());
                }

                physics = allScores.stream().filter(x->x.getSubject_name().equals("Physics")).collect(Collectors.toList());

                List<ScoreDetail> phy15Detail = physics.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());
                if(phy15Detail.size()>0){
                    tvPhysics15.setVisibility(View.VISIBLE);
                }

                if(phy15Detail.size()>1){
                    phy15Detail.forEach(x ->tvPhysics15.setText(tvPhysics15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    phy15Detail.forEach(x ->tvPhysics15.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> phy45Detail = physics.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());
                if(phy45Detail.size()>0){
                    tvPhysics45.setVisibility(View.VISIBLE);
                }

                if(phy45Detail.size()>1){
                    phy45Detail.forEach(x ->tvPhysics45.setText(tvPhysics45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    phy45Detail.forEach(x ->tvPhysics45.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> phyMidDetail = physics.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());
                if(phyMidDetail.size()>0){
                    tvPhysicsMid.setVisibility(View.VISIBLE);
                }

                if(phyMidDetail.size()>1){
                    phyMidDetail.forEach(x ->tvPhysicsMid.setText(tvPhysicsMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    phyMidDetail.forEach(x ->tvPhysicsMid.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> phyFinalDetail = physics.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());
                if(phyFinalDetail.size()>0){
                    tvPhysicsFinal.setVisibility(View.VISIBLE);
                }

                if(phyFinalDetail.size()>1){
                    phyFinalDetail.forEach(x ->tvPhysicsFinal.setText(tvPhysicsFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    phyFinalDetail.forEach(x ->tvPhysicsFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }
                physics.forEach(x->{
                    if(x.getMark()>0) {
                        avgPhy += x.getMark();
                    }
                });
                avgPhy = (avgPhy/physics.size());
                if(avgPhy>0){
                    tvPhysicsAverage.setText(avgPhy.toString());
                }

                histories = allScores.stream().filter(x->x.getSubject_name().equals("History")).collect(Collectors.toList());

                List<ScoreDetail> history15Detail = histories.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());

                if(history15Detail.size()>0){
                    tvHistory15.setVisibility(View.VISIBLE);
                }

                if(history15Detail.size()>1){
                    history15Detail.forEach(x ->tvHistory15.setText(tvHistory15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    history15Detail.forEach(x ->tvHistory15.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> history45Detail = histories.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());

                if(history45Detail.size()>0){
                    tvHistory45.setVisibility(View.VISIBLE);
                }

                if(history45Detail.size()>1){
                    history45Detail.forEach(x ->tvHistory45.setText(tvHistory45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    history45Detail.forEach(x ->tvHistory45.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> historyMidDetail = histories.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());

                if(historyMidDetail.size()>0){
                    tvHistoryMid.setVisibility(View.VISIBLE);
                }

                if(historyMidDetail.size()>1){
                    historyMidDetail.forEach(x ->tvHistoryMid.setText(tvHistoryMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    historyMidDetail.forEach(x ->tvHistoryMid.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> historyFinalDetail = histories.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());

                if(historyFinalDetail.size()>0){
                    tvHistoryFinal.setVisibility(View.VISIBLE);
                }

                if(historyFinalDetail.size()>1){
                    historyFinalDetail.forEach(x ->tvHistoryFinal.setText(tvHistoryMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    historyFinalDetail.forEach(x ->tvHistoryFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }
                histories.forEach(x->{
                    if(x.getMark()>0) {
                        avgHis += x.getMark();
                    }
                });
                avgHis = (avgHis/histories.size());
                if(avgHis>0){
                    tvHistoryAverage.setText(avgHis.toString());
                }
                englishes = allScores.stream().filter(x->x.getSubject_name().equals("English")).collect(Collectors.toList());

                List<ScoreDetail> englishFinalDetail = englishes.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());

                if(englishFinalDetail.size()>0){
                    tvEngFinal.setVisibility(View.VISIBLE);
                }

                if(englishFinalDetail.size()>1){
                    englishFinalDetail.forEach(x ->tvEngFinal.setText(tvEngFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    englishFinalDetail.forEach(x ->tvEngFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> englishMiddleDetail = englishes.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());

                if(englishMiddleDetail.size()>0){
                    tvEngMid.setVisibility(View.VISIBLE);
                }

                if(englishMiddleDetail.size()>1){
                    englishMiddleDetail.forEach(x ->tvEngMid.setText(tvEngMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    englishMiddleDetail.forEach(x ->tvEngMid.setText(Integer.toString(Math.round(x.getMark()))));
                }
                englishes.forEach(x->{
                    if(x.getMark()>0) {
                        avgEng += x.getMark();
                    }
                });
                avgEng = (avgEng/englishes.size());
                if(avgEng>0){
                    tvEngAverage.setText(avgEng.toString());
                }


                List<ScoreDetail> english45Detail = englishes.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());

                if(english45Detail.size()>0){
                    tvEng45.setVisibility(View.VISIBLE);
                }

                if(english45Detail.size()>1){
                    english45Detail.forEach(x ->tvEng45.setText(tvEng45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    english45Detail.forEach(x ->tvEng45.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> english15Detail = englishes.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());

                if(english15Detail.size()>0){
                    tvEng15.setVisibility(View.VISIBLE);
                }

                if(english15Detail.size()>1){
                    english15Detail.forEach(x ->tvEng15.setText(tvEng15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    english15Detail.forEach(x ->tvEng15.setText(Integer.toString(Math.round(x.getMark()))));
                }

                biologies = allScores.stream().filter(x->x.getSubject_name().equals("Biology")).collect(Collectors.toList());

                List<ScoreDetail> biologies15Detail = biologies.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());

                if(biologies15Detail.size()>0){
                    tvBiology15.setVisibility(View.VISIBLE);
                }

                if(biologies15Detail.size()>1){
                    biologies15Detail.forEach(x ->tvBiology15.setText(tvBiology15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    biologies15Detail.forEach(x ->tvBiology15.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> biologies45Detail = biologies.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());

                if(biologies45Detail.size()>0){
                    tvBiology45.setVisibility(View.VISIBLE);
                }

                if(biologies45Detail.size()>1){
                    biologies45Detail.forEach(x ->tvBiology45.setText(tvBiology45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    biologies45Detail.forEach(x ->tvBiology45.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> biologiesMidDetail = biologies.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());

                if(biologiesMidDetail.size()>0){
                    tvBiologyMid.setVisibility(View.VISIBLE);
                }

                if(biologiesMidDetail.size()>1){
                    biologiesMidDetail.forEach(x ->tvBiologyMid.setText(tvBiologyMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    biologiesMidDetail.forEach(x ->tvBiologyMid.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> biologiesFinalDetail = biologies.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());

                if(biologiesFinalDetail.size()>0){
                    tvBiologyFinal.setVisibility(View.VISIBLE);
                }

                if(biologiesFinalDetail.size()>1){
                    biologiesFinalDetail.forEach(x ->tvBiologyFinal.setText(tvBiologyFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    biologiesFinalDetail.forEach(x ->tvBiologyFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }
                biologies.forEach(x->{
                    if(x.getMark()>0) {
                        avgBio += x.getMark();
                    }
                });
                avgBio = (avgBio/biologies.size());
                if(avgBio>0){
                    tvBiologyAverage.setText(avgBio.toString());
                }

                literatures = allScores.stream().filter(x->x.getSubject_name().equals("Literature")).collect(Collectors.toList());

                List<ScoreDetail> literaturesFinalDetail = literatures.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());

                if(literaturesFinalDetail.size()>0){
                    tvLiteratureFinal.setVisibility(View.VISIBLE);
                }

                if(literaturesFinalDetail.size()>1){
                    literaturesFinalDetail.forEach(x ->tvLiteratureFinal.setText(tvLiteratureFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    literaturesFinalDetail.forEach(x ->tvLiteratureFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> literaturesMidDetail = literatures.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());

                if(literaturesMidDetail.size()>0){
                    tvLiteratureMid.setVisibility(View.VISIBLE);
                }

                if(literaturesMidDetail.size()>1){
                    literaturesMidDetail.forEach(x ->tvLiteratureFinal.setText(tvLiteratureFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    literaturesMidDetail.forEach(x ->tvLiteratureFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> literatures45Detail = literatures.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());

                if(literatures45Detail.size()>0){
                    tvLiterature45.setVisibility(View.VISIBLE);
                }

                if(literatures45Detail.size()>1){
                    literatures45Detail.forEach(x ->tvLiterature45.setText(tvLiterature45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    literatures45Detail.forEach(x ->tvLiterature45.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> literatures15Detail = literatures.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());

                if(literatures15Detail.size()>0){
                    tvLiterature15.setVisibility(View.VISIBLE);
                }

                if(literatures15Detail.size()>1){
                    literatures15Detail.forEach(x ->tvLiterature15.setText(tvLiterature15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    literatures15Detail.forEach(x ->tvLiterature15.setText(Integer.toString(Math.round(x.getMark()))));
                }
                literatures.forEach(x->{
                    if(x.getMark()>0) {
                        avgLit += x.getMark();
                    }
                });
                avgLit = (avgLit/literatures.size());
                if(avgLit>0){
                    tvLiteratureAverage.setText(avgLit.toString());
                }


                civics = allScores.stream().filter(x->x.getSubject_name().equals("CivicEducation")).collect(Collectors.toList());

                List<ScoreDetail> civic15Detail = civics.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());

                if(civic15Detail.size()>0){
                    tvCivic15.setVisibility(View.VISIBLE);
                }

                if(civic15Detail.size()>1){
                    civic15Detail.forEach(x ->tvCivic15.setText(tvCivic15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    civic15Detail.forEach(x ->tvCivic15.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> civic45Detail = civics.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());

                if(civic45Detail.size()>0){
                    tvCivic45.setVisibility(View.VISIBLE);
                }

                if(civic45Detail.size()>1){
                    civic45Detail.forEach(x ->tvCivic45.setText(tvCivic45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    civic45Detail.forEach(x ->tvCivic45.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> civicMidDetail = civics.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());

                if(civicMidDetail.size()>0){
                    tvCivicMid.setVisibility(View.VISIBLE);
                }

                if(civicMidDetail.size()>1){
                    civicMidDetail.forEach(x ->tvCivicMid.setText(tvCivicMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    civicMidDetail.forEach(x ->tvCivicMid.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> civicFinalDetail = civics.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());

                if(civicFinalDetail.size()>0){
                    tvCivicFinal.setVisibility(View.VISIBLE);
                }

                if(civicFinalDetail.size()>1){
                    civicFinalDetail.forEach(x ->tvCivicFinal.setText(tvCivicFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    civicFinalDetail.forEach(x ->tvCivicFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }
                civics.forEach(x->{
                    if(x.getMark()>0) {
                        avgCivic += x.getMark();
                    }
                });
                avgCivic = (avgCivic/civics.size());

                if(avgCivic>0){
                    tvCivicAverage.setText(avgCivic.toString());
                }

                geos = allScores.stream().filter(x->x.getSubject_name().equals("Geography")).collect(Collectors.toList());

                List<ScoreDetail> geoFinalDetail = civics.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());

                if(geoFinalDetail.size()>0){
                    tvGeoFinal.setVisibility(View.VISIBLE);
                }

                if(geoFinalDetail.size()>1){
                    geoFinalDetail.forEach(x ->tvGeoFinal.setText(tvGeoFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    geoFinalDetail.forEach(x ->tvGeoFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> geoMiddleDetail = civics.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());

                if(geoMiddleDetail.size()>0){
                    tvGeoMid.setVisibility(View.VISIBLE);
                }

                if(geoMiddleDetail.size()>1){
                    geoMiddleDetail.forEach(x ->tvGeoMid.setText(tvGeoMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    geoMiddleDetail.forEach(x ->tvGeoMid.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> geo45Detail = civics.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());

                if(geo45Detail.size()>0){
                    tvGeo45.setVisibility(View.VISIBLE);
                }

                if(geo45Detail.size()>1){
                    geo45Detail.forEach(x ->tvGeo45.setText(tvGeo45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    geo45Detail.forEach(x ->tvGeo45.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> geo15Detail = civics.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());

                if(geo15Detail.size()>0){
                    tvGeo15.setVisibility(View.VISIBLE);
                }

                if(geo15Detail.size()>1){
                    geo15Detail.forEach(x ->tvGeo15.setText(tvGeo15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    geo15Detail.forEach(x ->tvGeo15.setText(Integer.toString(Math.round(x.getMark()))));
                }
                geos.forEach(x->{
                    if(x.getMark()>0) {
                        avgGeo += x.getMark();
                    }
                });
                avgGeo = (avgGeo/geos.size());

                if(avgGeo >0){
                    tvGeoAverage.setText(avgGeo.toString());
                }


                pes = allScores.stream().filter(x->x.getSubject_name().equals("P.E")).collect(Collectors.toList());

                List<ScoreDetail> pe15Detail = pes.stream().filter(x->x.getType_test().contains("15")).collect(Collectors.toList());

                if(pe15Detail.size()>0){
                    tvPE15.setVisibility(View.VISIBLE);
                }

                if(pe15Detail.size()>1){
                    pe15Detail.forEach(x ->tvPE15.setText(tvPE15.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    pe15Detail.forEach(x ->tvPE15.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> pe45Detail = pes.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());

                if(pe45Detail.size()>0){
                    tvPE45.setVisibility(View.VISIBLE);
                }

                if(pe45Detail.size()>1){
                    pe45Detail.forEach(x ->tvPE45.setText(tvPE45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    pe45Detail.forEach(x ->tvPE45.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> peMidDetail = pes.stream().filter(x->x.getType_test().contains("Middle")).collect(Collectors.toList());

                if(peMidDetail.size()>0){
                    tvPEMid.setVisibility(View.VISIBLE);
                }

                if(peMidDetail.size()>1){
                    peMidDetail.forEach(x ->tvPEMid.setText(tvPEMid.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    peMidDetail.forEach(x ->tvPEMid.setText(Integer.toString(Math.round(x.getMark()))));
                }

                List<ScoreDetail> peFinalDetail = pes.stream().filter(x->x.getType_test().contains("Final")).collect(Collectors.toList());

                if(peFinalDetail.size()>0){
                    tvPEFinal.setVisibility(View.VISIBLE);
                }

                if(peFinalDetail.size()>1){
                    peFinalDetail.forEach(x ->tvPEFinal.setText(tvPEFinal.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));
                }else{
                    peFinalDetail.forEach(x ->tvPEFinal.setText(Integer.toString(Math.round(x.getMark()))));
                }
                pes.forEach(x->{
                    if(x.getMark()>0) {
                        avgPe += x.getMark();
                    }
                });
                avgPe = (avgPe/pes.size());
                if(avgPe >0){
                    tvPEAverage.setText(avgPe.toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        scoreDetailRef.orderByChild("student_email").equalTo(studentName).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void setInvi(){
        tvPE15.setText("");
        tvPE45.setText("");
        tvPEMid.setText("");
        tvPEFinal.setText("");
        tvPEAverage.setText("");

        tvGeo15.setText("");
        tvGeo45.setText("");
        tvGeoMid.setText("");
        tvGeoFinal.setText("");
        tvGeoAverage.setText("");

        tvCivic15.setText("");
        tvCivic45.setText("");
        tvCivicMid.setText("");
        tvCivicFinal.setText("");
        tvCivicAverage.setText("");

        tvLiterature15.setText("");
        tvLiterature45.setText("");
        tvLiteratureMid.setText("");
        tvLiteratureFinal.setText("");
        tvLiteratureAverage.setText("");

        tvBiology15.setText("");
        tvBiology45.setText("");
        tvBiologyMid.setText("");
        tvBiologyFinal.setText("");
        tvBiologyAverage.setText("");

        tvHistory15.setText("");
        tvHistory45.setText("");
        tvHistoryMid.setText("");
        tvHistoryFinal.setText("");
        tvHistoryAverage.setText("");

        tvPhysics15.setText("");
        tvPhysics45.setText("");
        tvPhysicsMid.setText("");
        tvPhysicsFinal.setText("");
        tvPhysicsAverage.setText("");

        tvChem15.setText("");
        tvChem45.setText("");
        tvChemMid.setText("");
        tvChemFinal.setText("");
        tvChemAverage.setText("");

        tvEng15.setText("");
        tvEng45.setText("");
        tvEngMid.setText("");
        tvEngFinal.setText("");
        tvEngAverage.setText("");

        tvMath15.setText("");
        tvMath45.setText("");
        tvMathMid.setText("");
        tvMathFinal.setText("");
        tvMathAverage.setText("");
    }

    private void fillMathTv() {


        List<ScoreDetail> math45Details = maths.stream().filter(x->x.getType_test().contains("45")).collect(Collectors.toList());

        if(math45Details.size()!=0){
            tvMath45.setVisibility(View.VISIBLE);
            math45Details.forEach(x ->tvMath45.setText(tvMath45.getText().toString()+" "+Integer.toString(Math.round(x.getMark()))));

            for(ScoreDetail scoreDetail : math45Details){
                Toast.makeText(this, Float.toString(scoreDetail.getMark()), Toast.LENGTH_SHORT).show();
            }

        }


    }

    private void fillData() {
      maths = allScores.stream().filter(x->x.getSubject_name().equals("Math")).collect(Collectors.toList());
      for(ScoreDetail scoreDetail : maths){
          Log.i("test" , scoreDetail.getType_test());
      }
    }

    private void initPETv() {
        tvPE15 = findViewById(R.id.tvPE15);
        tvPE45 = findViewById(R.id.tvPE45);
        tvPEMid = findViewById(R.id.tvPEMid);
        tvPEFinal = findViewById(R.id.tvPEFinal);
        tvPEAverage = findViewById(R.id.tvPEAverage);

        tvPE15.setText("");
        tvPE45.setText("");
        tvPEMid.setText("");
        tvPEFinal.setText("");
        tvPEAverage.setText("");
    }

    private void initGeoTv() {
        tvGeo15 = findViewById(R.id.tvGeo15);
        tvGeo45 = findViewById(R.id.tvGeo45);
        tvGeoMid = findViewById(R.id.tvGeoMid);
        tvGeoFinal = findViewById(R.id.tvGeoFinal);
        tvGeoAverage = findViewById(R.id.tvGeoAverage);

        tvGeo15.setText("");
        tvGeo45.setText("");
        tvGeoMid.setText("");
        tvGeoFinal.setText("");
        tvGeoAverage.setText("");
    }

    private void initCivicTv() {
        tvCivic15 = findViewById(R.id.tvCivic15);
        tvCivic45 = findViewById(R.id.tvCivic45);
        tvCivicMid = findViewById(R.id.tvCivicMid);
        tvCivicFinal = findViewById(R.id.tvCivicFinal);
        tvCivicAverage = findViewById(R.id.tvCivicAverage);

        tvCivic15.setText("");
        tvCivic45.setText("");
        tvCivicMid.setText("");
        tvCivicFinal.setText("");
        tvCivicAverage.setText("");
    }

    private void initLiteratureTv() {
        tvLiterature15 = findViewById(R.id.tvLiterature15);
        tvLiterature45 = findViewById(R.id.tvLiterature45);
        tvLiteratureMid = findViewById(R.id.tvLiteratureMid);
        tvLiteratureFinal = findViewById(R.id.tvLiteratureFinal);
        tvLiteratureAverage = findViewById(R.id.tvLiteratureAverage);

        tvLiterature15.setText("");
        tvLiterature45.setText("");
        tvLiteratureMid.setText("");
        tvLiteratureFinal.setText("");
        tvLiteratureAverage.setText("");
    }

    private void initBiologyTv() {
        tvBiology15 = findViewById(R.id.tvBiology15);
        tvBiology45 = findViewById(R.id.tvBiology45);
        tvBiologyMid = findViewById(R.id.tvBiologyMid);
        tvBiologyFinal = findViewById(R.id.tvBiologyFinal);
        tvBiologyAverage = findViewById(R.id.tvBiologyAverage);

        tvBiology15.setText("");
        tvBiology45.setText("");
        tvBiologyMid.setText("");
        tvBiologyFinal.setText("");
        tvBiologyAverage.setText("");
    }

    private void initHistoryTv() {
        tvHistory15 = findViewById(R.id.tvHistory15);
        tvHistory45 = findViewById(R.id.tvHistory45);
        tvHistoryMid = findViewById(R.id.tvHistoryMid);
        tvHistoryFinal = findViewById(R.id.tvHistoryFinal);
        tvHistoryAverage = findViewById(R.id.tvHistoryAverage);

        tvHistory15.setText("");
        tvHistory45.setText("");
        tvHistoryMid.setText("");
        tvHistoryFinal.setText("");
        tvHistoryAverage.setText("");
    }

    private void initPhysicsTv() {
        tvPhysics15 = findViewById(R.id.tvPhysics15);
        tvPhysics45 = findViewById(R.id.tvPhysics45);
        tvPhysicsMid = findViewById(R.id.tvPhysicsMid);
        tvPhysicsFinal = findViewById(R.id.tvPhysicsFinal);
        tvPhysicsAverage = findViewById(R.id.tvPhysicsAverage);

        tvPhysics15.setText("");
        tvPhysics45.setText("");
        tvPhysicsMid.setText("");
        tvPhysicsFinal.setText("");
        tvPhysicsAverage.setText("");
    }

    private void initChemTv() {
        tvChem15 = findViewById(R.id.tvChemistry15);
        tvChem45 = findViewById(R.id.tvChemistry45);
        tvChemMid = findViewById(R.id.tvChemistryMid);
        tvChemFinal = findViewById(R.id.tvChemistryFinal);
        tvChemAverage = findViewById(R.id.tvChemistryAverage);

        tvChem15.setText("");
        tvChem45.setText("");
        tvChemMid.setText("");
        tvChemFinal.setText("");
        tvChemAverage.setText("");
    }

    private void initEngTv() {
        tvEng15 = findViewById(R.id.tvEnglish15);
        tvEng45 = findViewById(R.id.tvEnglish45);
        tvEngMid = findViewById(R.id.tvEnglishMid);
        tvEngFinal = findViewById(R.id.tvEnglishFinal);
        tvEngAverage = findViewById(R.id.tvEnglishAverage);

        tvEng15.setText("");
        tvEng45.setText("");
        tvEngMid.setText("");
        tvEngFinal.setText("");
        tvEngAverage.setText("");
    }

    private void initMathTv() {
        tvMath15 = findViewById(R.id.tvMath15);
        tvMath45 = findViewById(R.id.tvMath45);
        tvMathMid = findViewById(R.id.tvMathMid);
        tvMathFinal = findViewById(R.id.tvMathFinal);
        tvMathAverage = findViewById(R.id.tvMathAverage);

        tvMath15.setText("");
        tvMath45.setText("");
        tvMathMid.setText("");
        tvMathFinal.setText("");
        tvMathAverage.setText("");
    }

    private void initUi() {
        imvSubcribe = findViewById(R.id.imvSubcribe);
        btnSendEmail = findViewById(R.id.btnSendEmail);
        spinnerSemeter = findViewById(R.id.spinnerSemeter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, semeters);
        spinnerSemeter.setAdapter(adapter);
        spinnerSemeter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSem = spinnerSemeter.getSelectedItem().toString();
                selectedCount = selectedSem.contains("2")?2:1;
                //Toast.makeText(MarkActivity.this, Integer.toString(selectedCount), Toast.LENGTH_SHORT).show();
                if(allScores.size()>0){
                    allScores.clear();
                }
                if(maths != null){
                    maths.clear();
                }
                if(englishes != null){
                    englishes.clear();
                }
                if(chems != null){
                    chems.clear();
                }
                if(physics != null){
                    physics.clear();
                }
                if(literatures!= null){
                    literatures.clear();
                }
                if(histories!= null){
                    histories.clear();
                }
                if(geos!= null){
                    geos.clear();
                }
                if(civics!= null){
                    civics.clear();
                }
                if(pes!= null){
                    pes.clear();
                }
                if(biologies!= null){
                    biologies.clear();
                }
                setInvi();
                getDataFromFirebase(selectedCount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}