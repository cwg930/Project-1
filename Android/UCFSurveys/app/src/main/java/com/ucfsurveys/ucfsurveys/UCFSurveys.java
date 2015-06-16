package com.ucfsurveys.ucfsurveys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cwg93_000 on 6/2/2015.
 */
public class UCFSurveys extends Activity implements View.OnClickListener {

    Button getSurveyButton;
    Button viewResultsButton;
    Boolean nextScreenIsViewer = false;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.getSurveyButton) {
            nextScreenIsViewer = false;
        } else if (v.getId() == R.id.getResultsButton) {
            nextScreenIsViewer = true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucfsurveys);

        getSurveyButton = (Button)findViewById(R.id.getSurveyButton);
        viewResultsButton = (Button)findViewById(R.id.getResultsButton);

        getSurveyButton.setOnClickListener(this);
        viewResultsButton.setOnClickListener(this);
    }
    class GetSurveysTask extends AsyncTask<String, Void, Bundle>{

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
            return null;
        }

        @Override
        protected void onPostExecute(Bundle b) {
            super.onPostExecute(b);

        }
    }
}
