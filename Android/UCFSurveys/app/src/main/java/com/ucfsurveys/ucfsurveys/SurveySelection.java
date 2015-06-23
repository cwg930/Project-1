package com.ucfsurveys.ucfsurveys;

import android.app.Activity;
import android.app.ProgressDialog;
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
import java.util.HashMap;


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
    ProgressDialog pDialog;
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
        mNameList.add("Survey 1: Misc Questions");
        mNameList.add("Survey 2: Pictures & Humor");
        mNameList.add("Survey 3: R&B Questionnaire");
        mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mNameList);

        SurveyList.setAdapter(mArrayAdapter);

        selectSurveyButton.setOnClickListener(this);
        SurveyList.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(isViewer){
            Intent nextActivity;
            nextActivity = new Intent(this,ResultsActivity.class);
            startActivity(nextActivity);

        }
        else {
            GetQuestionsTask task = new GetQuestionsTask();
            if(selectedId == 0) {
                task.execute("http://www.ucfsurveys.com/JSON_encode/json_encode1.php");
            }else if(selectedId == 1){
                task.execute("http://www.ucfsurveys.com/JSON_encode/json_encode2.php");
            }
            else{
                task.execute("http://www.ucfsurveys.com/JSON_encode/json_encode3.php");
            }
        }
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

    class GetQuestionsTask extends AsyncTask<String, Void , ArrayList<Bundle> >{

        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(SurveySelection.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ArrayList<Bundle> doInBackground(String... params) {
            InputStream is = null;
            HttpURLConnection httpConnection = null;
            ArrayList<Bundle> questionList = null;
            try {
                URL url = new URL(params[0]);
                httpConnection = (HttpURLConnection)url.openConnection();

                httpConnection.setRequestMethod("POST");
                int statusCode = httpConnection.getResponseCode();

                if(statusCode == 200){
                    is = new BufferedInputStream(httpConnection.getInputStream());
                    questionList = JSONParser.parseQuestionList(is);
                }
            }catch (Exception e){
                Log.d("error", e.toString());
            }
            return questionList;
        }

        @Override
        protected void onPostExecute(ArrayList<Bundle> list) {
            super.onPostExecute(list);
            pDialog.dismiss();
            Intent nextQuestion;
            ArrayList<String> answerList = new ArrayList<>();
            String questionType = list.get(0).getString("question_type");
            if(questionType.equals("TX")){
                nextQuestion = new Intent(getApplicationContext(),TextQuestionActivity.class);
                nextQuestion.putParcelableArrayListExtra("questionList",list);
                nextQuestion.putExtra("questionNum", 0);
                nextQuestion.putStringArrayListExtra("answerList", answerList);
                startActivity(nextQuestion);
            }else if(questionType.equals("MC")||questionType.equals("DD")){
                nextQuestion = new Intent(getApplicationContext(), MultipleChoiceQuestionActivity.class);
                nextQuestion.putParcelableArrayListExtra("questionList", list);
                nextQuestion.putExtra("questionNum", 0);
                nextQuestion.putStringArrayListExtra("answerList", answerList);
                startActivity(nextQuestion);
            }else if(questionType.equals("CB")){
                nextQuestion = new Intent(getApplicationContext(), MultipleSelectionQuestionActivity.class);
                nextQuestion.putParcelableArrayListExtra("questionList", list);
                nextQuestion.putExtra("questionNum", 0);
                nextQuestion.putStringArrayListExtra("answerList",answerList);
                startActivity(nextQuestion);
            }
        }

    }
}
