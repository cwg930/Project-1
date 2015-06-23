package com.ucfsurveys.ucfsurveys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cwg93_000 on 6/14/2015.
 */
public class MultipleSelectionQuestionActivity extends QuestionActivity implements AdapterView.OnItemClickListener{

    ListView answerListView;
    ArrayAdapter mArrayAdapter;
    ArrayList<String> answerTextList;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_selection_question);
        questionText = (TextView)findViewById(R.id.QuestionText);
        answerListView = (ListView)findViewById(R.id.answerList);
        nextButton = (Button)findViewById(R.id.nextButton);
        ArrayList<Bundle> questionList = getIntent().getParcelableArrayListExtra("questionList");
        int questionNum = getIntent().getIntExtra("questionNum",0);
        Bundle questionData = null;
        if(questionList != null){
            questionData = questionList.get(questionNum);
        }
        questionText.setText(questionData.getString("question_title"));
        answerTextList = new ArrayList<String>();
        for (int i = 1; i <= 5; i++) {

            answerTextList.add("answer: " + i);
        }
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,answerTextList);
        answerListView.setAdapter(mArrayAdapter);

        nextButton.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent i = new Intent(this,TextQuestionActivity.class);
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
