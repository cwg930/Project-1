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

    public static ArrayList<Bundle> parseResponse(InputStream is, boolean isViewer) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

        if(isViewer){
            return parseAnswerList(reader);
        }else{
            return parseQuestionList(reader);
        }
    }

    private static ArrayList<Bundle> parseQuestionList(JsonReader reader) throws IOException {
        ArrayList<Bundle> questionList = new ArrayList<>();
        try{
            reader.beginArray();
            while(reader.hasNext()) {
                questionList.add(parseQuestion(reader));
            }
            reader.endArray();
        }catch (IOException e){
            Log.d("Parse failed: ", e.toString());
        }

        return questionList;
    }

    private static Bundle parseQuestion(JsonReader reader){
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
                }else if(name.equals("num_choices")){
                    questionData.putInt(name, reader.nextInt());
                }else if(name.equals("choices")){
                    questionData.putParcelableArrayList(name, parseChoiceList(reader));
                }else{
                    reader.skipValue();
                }
            }
            reader.endObject();
        }catch (IOException e){
            Log.d("Parse failed: ", e.toString());
        }
        return questionData;
    }

    private static ArrayList<Bundle> parseChoiceList(JsonReader reader){
        ArrayList<Bundle> choices = new ArrayList<>();
        try{
            reader.beginArray();
            while(reader.hasNext()){
                choices.add(parseChoice(reader));
            }
            reader.endArray();
        }catch (IOException e){
            Log.d("Parse failed: ", e.toString());
        }
        return choices;
    }

    private static Bundle parseChoice(JsonReader reader){
        Bundle choice = new Bundle();
        try{
            reader.beginObject();
            while (reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("num")){
                    choice.putInt(name, reader.nextInt());
                }else if(name.equals("text")){
                    choice.putString(name, reader.nextString());
                }else{
                    reader.skipValue();
                }
            }
            reader.endObject();
        }catch (IOException e){
            Log.d("Parse failed: ", e.toString());
        }
        return choice;
    }

    private static ArrayList<Bundle> parseAnswerList(JsonReader reader) throws IOException{
        ArrayList<Bundle> answerList = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()){
            answerList.add(parseResult(reader));
        }
        reader.endArray();
        return answerList;
    }

    private static Bundle parseResult(JsonReader reader) throws IOException{
        Bundle result = new Bundle();
        reader.beginObject();
        String type = null;
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("question_type")){
                type = reader.nextString();
                result.putString(name, type);
            }else if(name.equals("total")){
                result.putInt(name,reader.nextInt());
            }else if(name.equals("answers")){
                if(type != null) {
                    if (type.equals("TX")) {
                        result.putStringArrayList(name,parseTextAnswers(reader));
                    } else {
                        result.putIntegerArrayList(name,parseIntAnswers(reader));
                    }
                }
            }
        }
        reader.endObject();

        return result;
    }

    private static ArrayList<String> parseTextAnswers(JsonReader reader) throws IOException{
        ArrayList<String> answerList = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()){
            answerList.add(reader.nextString());
        }
        reader.endArray();
        return answerList;
    }

    private static ArrayList<Integer> parseIntAnswers(JsonReader reader) throws IOException{
        ArrayList<Integer> answerList = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()){
            answerList.add(reader.nextInt());
        }
        reader.endArray();
        return answerList;
    }
}
