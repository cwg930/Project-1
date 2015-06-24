package com.ucfsurveys.ucfsurveys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cwg93_000 on 6/23/2015.
 */
public class Submit extends Activity implements View.OnClickListener{
    ProgressDialog pDialog;
    Button submitButton;
    ArrayList<String> completedList;
    final String urlString = "http://www.ucfsurveys.com/JSON_encode/submit.php";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        submitButton = (Button)findViewById(R.id.submit_button);
        completedList = getIntent().getStringArrayListExtra("completedList");
        submitButton.setOnClickListener(this);
    }

    public void onClick(View v){
        SubmitTask task = new SubmitTask();
        task.execute(urlString);
    }

    class SubmitTask extends AsyncTask<String, Void, Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            int statusCode;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setDoOutput(true);
                String query = createQuery(completedList);
                Log.d("query", query);
                OutputStream out = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                out.close();
                statusCode = connection.getResponseCode();
                if(statusCode == 200) {

                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while((line = reader.readLine()) != null){
                        sb.append(line);
                    }
                    Log.d("RESPONSE: ", sb.toString());
                }else{
                    Log.d("bad connection, code: ", Integer.toString(statusCode));
                }
            }catch (Exception e){
                Log.d("Error: ", e.toString());
                statusCode = -1;
            }

            return new Integer(statusCode);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Submit.this);
            pDialog.setMessage("Sending...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if(result.intValue() != 200){
                Toast toast = Toast.makeText(Submit.this, "Error "+ result.toString(),Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        private String createQuery(ArrayList<String> list) throws UnsupportedEncodingException{
            StringBuilder builder = new StringBuilder();
            boolean first = true;
            int i = 0;
            for(String str: list){
                if(first){
                    first = false;
                }else builder.append("&");

                builder.append(URLEncoder.encode("answer"+i,"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(str,"UTF-8"));
                i++;
            }
            return builder.toString();
        }
    }
}
