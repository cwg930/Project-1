package com.ucfsurveys.ucfsurveys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by cwg93_000 on 6/14/2015.
 */
public abstract class QuestionActivity extends Activity implements View.OnClickListener{
    TextView questionText;
    Button nextButton;
    ArrayList<Bundle> questionList;
    ArrayList<String> completedList;
    int questionNum;
    long surveyID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onClick(View v){
        Intent nextQuestion;

        if(questionNum == (questionList.size()-1)){
            nextQuestion = new Intent(getApplicationContext(),Submit.class);
            nextQuestion.putStringArrayListExtra("completedList", completedList);
            nextQuestion.putExtra("surveyID", surveyID);
            startActivity(nextQuestion);
        }
        else {
            questionNum++;
            String questionType = questionList.get(questionNum).getString("question_type");
            if (questionType.equals("TX")) {
                nextQuestion = new Intent(getApplicationContext(), TextQuestionActivity.class);
                nextQuestion.putParcelableArrayListExtra("questionList", questionList);
                nextQuestion.putExtra("questionNum", questionNum);
                nextQuestion.putExtra("surveyID", surveyID);
                nextQuestion.putStringArrayListExtra("completedList", completedList);
                startActivity(nextQuestion);
            } else if (questionType.equals("MC") || questionType.equals("DD")) {
                nextQuestion = new Intent(getApplicationContext(), MultipleChoiceQuestionActivity.class);
                nextQuestion.putParcelableArrayListExtra("questionList", questionList);
                nextQuestion.putExtra("questionNum", questionNum);
                nextQuestion.putExtra("surveyID", surveyID);
                nextQuestion.putStringArrayListExtra("completedList", completedList);
                startActivity(nextQuestion);
            } else if (questionType.equals("CB")) {
                nextQuestion = new Intent(getApplicationContext(), MultipleSelectionQuestionActivity.class);
                nextQuestion.putParcelableArrayListExtra("questionList", questionList);
                nextQuestion.putExtra("questionNum", questionNum);
                nextQuestion.putExtra("surveyID", surveyID);
                nextQuestion.putStringArrayListExtra("completedList", completedList);
                startActivity(nextQuestion);
            }
        }
    }

}
