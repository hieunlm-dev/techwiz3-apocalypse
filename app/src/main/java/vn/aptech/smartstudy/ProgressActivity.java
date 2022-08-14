package vn.aptech.smartstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProgressActivity extends AppCompatActivity {

    BarChart barChartSubject;
    PieChart pieChartSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        barChartSubject = findViewById(R.id.bar_chart_subject);
        pieChartSubject = findViewById(R.id.pie_chart_subject);

        ArrayList<BarEntry> barEntries= new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for(int i = 0;i<=10;i++){
            float value = (float) (i*10.0);
            BarEntry barEntry= new BarEntry(i,value);
            PieEntry pieEntry= new PieEntry(i,value);

            barEntries.add(barEntry);
            pieEntries.add(pieEntry);
        }

        //init Barchart
        BarDataSet barDataSet= new BarDataSet(barEntries,"Subject and score's average");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(false);
        barChartSubject.setData(new BarData(barDataSet));
        barChartSubject.animateY(5000);
        barChartSubject.getDescription().setText("Student Chart");
        barChartSubject.getDescription().setTextColor(Color.BLUE);

        //init PieChart
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Student");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChartSubject.setData(new PieData(pieDataSet));
        pieChartSubject.animateXY(5000,5000);
        pieChartSubject.getDescription().setEnabled(false);

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