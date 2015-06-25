package com.ucfsurveys.ucfsurveys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by cwg93_000 on 6/14/2015.
 */
public class ResultsActivity extends Activity implements View.OnClickListener{
    TextView titleText;
    TextView questionText;
    GraphView graph;
    ArrayList<Bundle> resultList;
    Button nextButton;
    int questionNum;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        graph = (GraphView)findViewById(R.id.resultGraph);
        nextButton = (Button)findViewById(R.id.nextButton);
        questionText = (TextView)findViewById(R.id.textView3);
        questionNum = getIntent().getIntExtra("questionNum", -1);
        resultList = getIntent().getParcelableArrayListExtra("resultList");
        Bundle currentQuestion = resultList.get(questionNum);
        questionText.setText("Question: " + (questionNum+1));
        if(currentQuestion.getString("question_type").equals("TX")){

        }else {
            ArrayList<Integer> dataList = currentQuestion.getIntegerArrayList("answers");
            DataPoint[] dataPoints = new DataPoint[dataList.size()];
            for(int i = 0; i < dataList.size(); i++) {
                dataPoints[i] = new DataPoint(i, dataList.get(i).intValue());
            }
            BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(dataPoints);
            graph.addSeries(series);
            series.setSpacing(50);
        }
        nextButton.setOnClickListener(this);

    }

    public void onClick(View v){
        if(questionNum == resultList.size()-1){
            //do nothing
        }else{
            Intent next = new Intent(this, ResultsActivity.class);
            next.putParcelableArrayListExtra("resultList", resultList);
            next.putExtra("questionNum", questionNum + 1);
            startActivity(next);
        }
    }
}
