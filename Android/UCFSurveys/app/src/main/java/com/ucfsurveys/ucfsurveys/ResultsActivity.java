package com.ucfsurveys.ucfsurveys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.w3c.dom.Text;

/**
 * Created by cwg93_000 on 6/14/2015.
 */
public class ResultsActivity extends Activity implements View.OnClickListener{
    TextView titleText;
    TextView questionText;
    GraphView graph;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        graph = (GraphView)findViewById(R.id.resultGraph);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"one","two","three"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, 4),
                new DataPoint(2, 2),
                new DataPoint(3, 0)
        });
        graph.addSeries(series);
        series.setSpacing(50);
    }

    public void onClick(View v){

    }
}
