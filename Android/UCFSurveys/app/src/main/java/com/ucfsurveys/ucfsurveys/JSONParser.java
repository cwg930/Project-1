package com.ucfsurveys.ucfsurveys;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by cwg93_000 on 6/14/2015.
 */
public class JSONParser {

    public static ArrayList<Bundle> parseQuestionList(InputStream is) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        ArrayList<Bundle> questionList = new ArrayList<>();
        try{
            reader.beginArray();
            while(reader.hasNext()) {
                questionList.add(parseQuestion(reader));
            }
            reader.endArray();
        }catch (IOException e){
            Log.e("Error: ", e.toString());
        }

        return questionList;
    }

    public static Bundle parseQuestion(JsonReader reader){
        Bundle questionData = new Bundle();
        try{
            reader.beginObject();
            while (reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("question_id")){
                    questionData.putInt(name,reader.nextInt());
                }else if(name.equals("question_type")){
                    questionData.putString(name,reader.nextString());
                }else if(name.equals("question_title")){
                    questionData.putString(name,reader.nextString());
                }else if(name.equals("question_choice")){
                    questionData.putString(name,reader.nextString());
                }else{
                    reader.skipValue();
                }
            }
            reader.endObject();
        }catch (IOException e){
            Log.e("Error: ", e.toString());
        }
        return questionData;
    }
}
