package com.ucfsurveys.ucfsurveys;

import android.app.ProgressDialog;
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
    int selectionId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_selection_question);
        questionText = (TextView)findViewById(R.id.QuestionText);
        answerListView = (ListView)findViewById(R.id.answerList);
        nextButton = (Button)findViewById(R.id.nextButton);
        questionList = getIntent().getParcelableArrayListExtra("questionList");
        questionNum = getIntent().getIntExtra("questionNum",0);
        completedList = getIntent().getStringArrayListExtra("completedList");
        Bundle questionData = null;
        if(questionList != null){
            questionData = questionList.get(questionNum);
        }
        questionText.setText(questionData.getString("question_title"));
        ArrayList<Bundle> choiceList = questionData.getParcelableArrayList("choices");
        answerTextList = new ArrayList<String>();
        if(choiceList != null){
            for(Bundle b: choiceList){
                answerTextList.add(b.getString("text"));
            }
        }
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,answerTextList);
        answerListView.setAdapter(mArrayAdapter);

        nextButton.setOnClickListener(this);
        answerListView.setOnItemClickListener(this);
    }

    public void onClick(View v){
        completedList.add(Integer.toString(selectionId));
        super.onClick(v);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectionId = position;
        view.setSelected(true);
    }
}
