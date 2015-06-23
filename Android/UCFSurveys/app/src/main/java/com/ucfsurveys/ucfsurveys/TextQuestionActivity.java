package com.ucfsurveys.ucfsurveys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cwg93_000 on 6/14/2015.
 */
public class TextQuestionActivity extends QuestionActivity {

    EditText answerText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_question);
        ArrayList<Bundle> questionList = getIntent().getParcelableArrayListExtra("questionList");
        int questionNum = getIntent().getIntExtra("questionNum",0);
        Bundle questionData = null;
        if(questionList != null){
            questionData = questionList.get(questionNum);
        }
        questionText = (TextView)findViewById(R.id.QuestionText);
        nextButton = (Button)findViewById(R.id.nextButton);
        answerText = (EditText)findViewById(R.id.text_answer_box);

        if(questionData != null){
            questionText.setText(questionData.getString("question_title"));
        }

    }

    public void onClick(View v){

    }
}
