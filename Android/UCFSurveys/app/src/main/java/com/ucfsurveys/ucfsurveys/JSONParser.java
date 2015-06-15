package com.ucfsurveys.ucfsurveys;

import android.os.Bundle;
import android.util.JsonReader;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by cwg93_000 on 6/14/2015.
 */
public class JSONParser {

    public static Bundle parseSurvey(InputStream is) throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(is,"UTF-8"));
        Bundle surveyData = new Bundle();
        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("id")){
                surveyData.putInt("id", reader.nextInt());
            }else if(name.equals("title")){
                surveyData.putString("title", reader.nextString());
            }else if(name.equals("numQuestions")){
                surveyData.putInt("numQuestions", reader.nextInt());
            }else if(name.equals("questionList")){
                surveyData.putParcelableArrayList("questionList",parseQuestionList(reader));
            }
        }
        reader.endObject();
        return surveyData;
    }
    public static ArrayList<Bundle> parseQuestionList(JsonReader reader) throws IOException{
        ArrayList<Bundle> questionList = new ArrayList<Bundle>();
        reader.beginArray();
        while(reader.hasNext()){
            questionList.add(parseQuestion(reader));
        }
        reader.endArray();
        return questionList;
    }
    public static Bundle parseQuestion(JsonReader reader) throws IOException{
        Boolean textAnswer = false;
        Bundle questionData = new Bundle();
        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("id")){
                questionData.putInt("id",reader.nextInt());
            }else if(name.equals("questionText")){
                questionData.putString("questionText",reader.nextString());
            }else if(name.equals("answerType")){
                String answerType = reader.nextString();
                if(answerType.equals("Text")){
                    textAnswer = true;
                }
                questionData.putString("answerType", answerType);
            }else if(name.equals("answers")&&(!textAnswer)){
                questionData.putParcelableArrayList("answers", parseAnswers(reader));
            }
        }
        reader.endObject();
        return questionData;
    }
    public static ArrayList<Bundle> parseAnswers(JsonReader reader) throws IOException{
        ArrayList<Bundle> answerList = new ArrayList<Bundle>();
        Bundle answerBundle;
        reader.beginArray();
        while(reader.hasNext()){
            answerBundle = new Bundle();
            reader.beginObject();
            while (reader.hasNext()){
                String name = new String();
                if(name.equals("id")){
                    answerBundle.putInt("id",reader.nextInt());
                }else if(name.equals("answerText")){
                    answerBundle.putString("answerText",reader.nextString());
                }
            }
            reader.endObject();
            answerList.add(answerBundle);
        }
        reader.endArray();
        return answerList;
    }

}
