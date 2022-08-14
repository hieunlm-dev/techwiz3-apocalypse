package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vn.aptech.smartstudy.entity.ScoreDetail;

public class ProgressActivity extends AppCompatActivity {

    private BarChart barChart;
    private PieChart pieChartSubject;
    private BarDataSet barDataSet1, barDataSet2,barDataSet3,barDataSet4,barDataSet5;
    private PieDataSet pieDataSet;
    private ArrayList barEntries;
    private final String URL ="https://smartstudy-ac389-default-rtdb.firebaseio.com/";
    private int selectedCount =1;
    private List<ScoreDetail> allScores= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> maths= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> englishes= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> chems= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> physics= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> histories= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> biologies= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> literatures= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> civics= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> geos= new ArrayList<ScoreDetail>();
    private List<ScoreDetail> pes= new ArrayList<ScoreDetail>();
    private String studentName;
    private Float avgMath,avgPhysic,avgEng,avgBio,avgHis,avgChem,avgLite,avgCivic,avgGeo,avgPes;
    private Float avgMath15,avgPhysic15,avgEng15,avgBio15,avgHis15,avgChem15,avgLite15,avgCivic15,avgGeo15,avgPes15;
    private Float Math45,Physic45,Eng45,Bio45,His45,Chem45,Lite45,Civic45,Geo45,Pes45;
    private Float MathMid,PhysicMid,EngMid,BioMid,HisMid,ChemMid,LiteMid,CivicMid,GeoMid,PesMid;
    private Float MathFinal,PhysicFinal,EngFinal,BioFinal,HisFinal,ChemFinal,LiteFinal,CivicFinal,GeoFinal,PesFinal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        getStudentName();
        avgMath=avgPhysic=avgEng=avgBio=avgHis=avgChem=avgLite=avgCivic=avgGeo=avgPes=0f;
        avgMath15=avgPhysic15=avgEng15=avgBio15=avgHis15=avgChem15=avgLite15=avgCivic15=avgGeo15=avgPes15=0f;
        Math45=Physic45=Eng45=Bio45=His45=Chem45=Lite45=Civic45=Geo45=Pes45=0f;
        MathMid=PhysicMid=EngMid=BioMid=HisMid=ChemMid=LiteMid=CivicMid=GeoMid=PesMid=0f;
        MathFinal=PhysicFinal=EngFinal=BioFinal=HisFinal=ChemFinal=LiteFinal=CivicFinal=GeoFinal=PesFinal=0f;
        barChart = findViewById(R.id.bar_chart_subject);
        pieChartSubject = findViewById(R.id.pie_chart_subject);
        getDataFromFirebase(1);


//        for(int i = 0;i<=10;i++){
////            float value = (float) (i*10.0);
//            BarEntry barEntry= new BarEntry(i,value);
//            PieEntry pieEntry= new PieEntry(i,value);
//
//            barEntries.add(barEntry);
//            pieEntries.add(pieEntry);
//        }

        //init Barchart
//        BarDataSet barDataSet= new BarDataSet(barEntries,"Subject and score's average");
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        barDataSet.setDrawValues(false);
//        barChartSubject.setData(new BarData(barDataSet));
//        barChartSubject.animateY(5000);
//        barChartSubject.getDescription().setText("Student Chart");
//        barChartSubject.getDescription().setTextColor(Color.BLUE);

//        //init PieChart
//        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Student");
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        pieChartSubject.setData(new PieData(pieDataSet));
//        pieChartSubject.animateXY(5000,5000);
//        pieChartSubject.getDescription().setEnabled(false);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    private List<BarEntry> getBarEntriesTwo() {
        barEntries = new ArrayList<>();
//        getDataFromFirebase(1);
        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, Math45/2));
        barEntries.add(new BarEntry(2f, Eng45/2));
        barEntries.add(new BarEntry(3f, Bio45/2));
        barEntries.add(new BarEntry(4f, Physic45/2));
        barEntries.add(new BarEntry(5f, His45/2));
        barEntries.add(new BarEntry(6f, Geo45/2));
        barEntries.add(new BarEntry(7f, Civic45/2));
        barEntries.add(new BarEntry(8f, Pes45/2));
        barEntries.add(new BarEntry(9f, Lite45/2));
        barEntries.add(new BarEntry(10f, Chem45/2));
        return barEntries;
    }

    private List<BarEntry> getBarEntriesOne() {
        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, avgMath15/2));
        barEntries.add(new BarEntry(2f, avgEng15/2));
        barEntries.add(new BarEntry(3f, avgBio15/2));
        barEntries.add(new BarEntry(4f, avgPhysic15/2));
        barEntries.add(new BarEntry(5f, avgHis15/2));
        barEntries.add(new BarEntry(6f, avgGeo15/2));
        barEntries.add(new BarEntry(7f, avgCivic15/2));
        barEntries.add(new BarEntry(8f, avgPes15/2));
        barEntries.add(new BarEntry(9f, avgLite15/2));
        barEntries.add(new BarEntry(10f, avgChem15/2));
        return barEntries;
    }
    private List<BarEntry> getBarEntriesThree() {
        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, MathMid));
        barEntries.add(new BarEntry(2f, EngMid));
        barEntries.add(new BarEntry(3f, BioMid));
        barEntries.add(new BarEntry(4f, PhysicMid));
        barEntries.add(new BarEntry(5f, HisMid));
        barEntries.add(new BarEntry(6f, GeoMid));
        barEntries.add(new BarEntry(7f, CivicMid));
        barEntries.add(new BarEntry(8f, PesMid));
        barEntries.add(new BarEntry(9f, LiteMid));
        barEntries.add(new BarEntry(10f, ChemMid));
        return barEntries;
    }
    private List<BarEntry> getBarEntriesFour() {
        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, MathFinal));
        barEntries.add(new BarEntry(2f, EngFinal));
        barEntries.add(new BarEntry(3f, BioFinal));
        barEntries.add(new BarEntry(4f, PhysicFinal));
        barEntries.add(new BarEntry(5f, HisFinal));
        barEntries.add(new BarEntry(6f, GeoFinal));
        barEntries.add(new BarEntry(7f, CivicFinal));
        barEntries.add(new BarEntry(8f, PesFinal));
        barEntries.add(new BarEntry(9f, LiteFinal));
        barEntries.add(new BarEntry(10f, ChemFinal));
        return barEntries;
    }
    private List<BarEntry> getBarEntriesFive() {
        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, avgMath));
        barEntries.add(new BarEntry(2f, avgEng));
        barEntries.add(new BarEntry(3f, avgBio));
        barEntries.add(new BarEntry(4f, avgPhysic));
        barEntries.add(new BarEntry(5f, avgHis));
        barEntries.add(new BarEntry(6f, avgGeo));
        barEntries.add(new BarEntry(7f, avgCivic));
        barEntries.add(new BarEntry(8f, avgPes));
        barEntries.add(new BarEntry(9f, avgLite));
        barEntries.add(new BarEntry(10f, avgChem));
        return barEntries;
    }


    private void getStudentName() {
        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        studentName = sharedPreferences.getString("student_name","");
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
                chems = allScores.stream().filter(x->x.getSubject_name().equals("Chemistry")).collect(Collectors.toList());
                physics = allScores.stream().filter(x->x.getSubject_name().equals("Physics")).collect(Collectors.toList());
                histories = allScores.stream().filter(x->x.getSubject_name().equals("History")).collect(Collectors.toList());
                englishes = allScores.stream().filter(x->x.getSubject_name().equals("English")).collect(Collectors.toList());
                biologies = allScores.stream().filter(x->x.getSubject_name().equals("Biology")).collect(Collectors.toList());
                literatures = allScores.stream().filter(x->x.getSubject_name().equals("Literature")).collect(Collectors.toList());
                civics = allScores.stream().filter(x->x.getSubject_name().equals("CivicEducation")).collect(Collectors.toList());
                geos = allScores.stream().filter(x->x.getSubject_name().equals("Geography")).collect(Collectors.toList());
                pes = allScores.stream().filter(x->x.getSubject_name().equals("P.E")).collect(Collectors.toList());

                maths.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgMath += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgMath15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            Math45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            MathMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            MathFinal =x.getMark();
                        }
                    }
                });
                chems.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgChem += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgChem15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            Chem45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            ChemMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            ChemFinal =x.getMark();
                        }
                    }
                });
                physics.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgPhysic += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgPhysic15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            Physic45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            PhysicMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            PhysicFinal =x.getMark();
                        }
                    }
                });
                histories.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgHis += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgHis15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            His45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            HisMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            HisFinal =x.getMark();
                        }
                    }
                });
                englishes.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgEng += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgEng15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            Eng45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            EngMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            EngFinal =x.getMark();
                        }
                    }
                });
                biologies.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgBio += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgBio15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            Bio45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            BioMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            BioFinal =x.getMark();
                        }
                    }
                });
                literatures.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgLite += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgLite15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            Lite45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            LiteMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            LiteFinal =x.getMark();
                        }
                    }
                });
                civics.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgCivic += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgCivic15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            Civic45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            CivicMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            CivicFinal =x.getMark();
                        }
                    }
                });
                geos.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgGeo += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgGeo15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            Geo45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            GeoMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            GeoFinal =x.getMark();
                        }
                    }
                });
                pes.forEach(x->{
                    if(x.getMark()>0) {
                        String test_type= x.getType_test();
                        avgPes += x.getMark();
                        if(test_type.equals("15 minutes test Sem 1")){
                            avgPes15+=x.getMark();
                        }else if(test_type.equals("45 minutes test Sem 1")){
                            Pes45 +=x.getMark();
                        }else if(test_type.equals("Middle semester test Sem 1")){
                            PesMid =x.getMark();
                        }else if(test_type.equals("Final semester test Sem 1")){
                            PesFinal =x.getMark();
                        }
                    }
                });
                //average
                avgMath = (avgMath/maths.size());
                avgPes = (avgPes/pes.size());
                avgGeo =avgGeo/geos.size();
                avgCivic= avgCivic/civics.size();
                avgLite=avgLite/literatures.size();
                avgBio = avgBio/biologies.size();
                avgEng= avgEng/englishes.size();
                avgHis = avgHis/histories.size();
                avgPhysic= avgPhysic/physics.size();
                avgChem = avgChem/chems.size();
                //15'
                Log.i("msg mark math", avgMath.toString());
                createChart();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void createChart() {
        String[] subjects = new String[]{"Math", "English", "Biology","Physics","History","Geography","Civic","P.E","Literature",
                "Chemistry"};
        barDataSet1 = new BarDataSet(getBarEntriesOne(),"15'");
        barDataSet1.setColor(Color.GREEN);
        barDataSet2 = new BarDataSet(getBarEntriesTwo(),"45'");
        barDataSet2.setColor(Color.YELLOW);
        barDataSet3 = new BarDataSet(getBarEntriesThree(),"Mid-term");
        barDataSet3.setColor(Color.MAGENTA);
        barDataSet4 = new BarDataSet(getBarEntriesFour(),"Final");
        barDataSet4.setColor(Color.RED);
        barDataSet5 = new BarDataSet(getBarEntriesFive(),"Avg");
        barDataSet5.setColor(Color.BLUE);
        // below line is to add bar data set to our bar data.
        BarData data = new BarData(barDataSet1, barDataSet2,barDataSet3,barDataSet4,barDataSet5);

        barChart.setData(data);

        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(subjects));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(2);
//        xAxis.setAxisMinimum(2.5f);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        float barSpace = 0.1f;
        float groupSpace = 0.5f;
        data.setBarWidth(0.2f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.animate();
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
//        barChart.getDescription().setText("");
    }
}