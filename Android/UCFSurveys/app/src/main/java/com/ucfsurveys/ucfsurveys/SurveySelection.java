package com.ucfsurveys.ucfsurveys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class SurveySelection extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    int i, selectedPosition;
    long selectedId;
    TextView titleView;
    TextView selectedView;
    Button selectSurveyButton;
    ListView SurveyList;
    ArrayAdapter mArrayAdapter;
    ArrayList<String> mNameList = new ArrayList<String>();
    Boolean isViewer;
    Bundle surveyBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_selection);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            isViewer = extras.getBoolean("isViewer");
        }
        titleView = (TextView)findViewById(R.id.survey_available_text);
        selectSurveyButton = (Button)findViewById(R.id.select_survey_btn);
        SurveyList = (ListView)findViewById(R.id.Survey_Listview);
        for(i=0; i < 5; i++){
            mNameList.add(getString(R.string.survey_name) + i);
        }
        mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mNameList);

        SurveyList.setAdapter(mArrayAdapter);

        selectSurveyButton.setOnClickListener(this);
        SurveyList.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent nextActivity;
        if(isViewer){
            nextActivity = new Intent(this,ResultsActivity.class);
        }
        else {
            nextActivity = new Intent(this, MultipleChoiceQuestionActivity.class);
            nextActivity.putExtra(Intent.EXTRA_TEXT, selectedView.getText());

        }
        startActivity(nextActivity);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(selectedView!=null){
            selectedView.setBackgroundColor(Color.WHITE);
        }
        selectedId = id;
        selectedPosition = position;
        selectedView = (TextView)view;
        selectedView.setBackgroundColor(Color.LTGRAY);
    }

    class NextQuestionTask extends AsyncTask<String, Void , Bundle >{

        @Override
        protected Bundle doInBackground(String... params) {
            InputStream is = null;
            HttpURLConnection httpConnection = null;
            Bundle surveyData = null;
            try {
                URL url = new URL(params[0]);
                httpConnection = (HttpURLConnection)url.openConnection();

                httpConnection.setRequestMethod("POST");
                int statusCode = httpConnection.getResponseCode();

                if(statusCode == 200){
                    is = new BufferedInputStream(httpConnection.getInputStream());
                    surveyData = JSONParser.parseSurvey(is);
                }
            }catch (Exception e){
                Log.d("error", e.toString());
            }
            return surveyData;
        }

    }
}
