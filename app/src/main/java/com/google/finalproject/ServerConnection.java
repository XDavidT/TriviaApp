package com.google.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection extends AppCompatActivity {
    private String username,level_str;
    private static ArrayList<exportQues> questions = new ArrayList<exportQues>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_connection);
        this.getSupportActionBar().hide();
        ///////////////////////////////////////////////////////


        Intent recive_intent = getIntent();
        username = recive_intent.getStringExtra("username");
        level_str = recive_intent.getStringExtra("level_str");
        try {
            new get_q().execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public class get_q extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground (Void...voids){
            String json_str = null;
            Socket clientSocket = null;
            DataOutputStream outToServer = null;
            BufferedReader inFromServer = null;
            try {
                clientSocket = new Socket("192.168.43.108", 10000); //Connect to server
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outToServer = new DataOutputStream(clientSocket.getOutputStream()); //Stream for selection
                outToServer.writeBytes("1\n"); //Select server menu - get questions
                outToServer.writeBytes(level_str + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Get server output
                json_str = inFromServer.readLine();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return json_str;
        }

        @Override
        protected void onPostExecute (String s){
            if (s!=null) {
                Intent game_intent = new Intent(getBaseContext(),Game_play.class);
                game_intent.putExtra("username",username);
                game_intent.putExtra("level_str",level_str);
                game_intent.putExtra("questions",s); //json
                startActivity(game_intent);
                finish();


            }

        }
    }
}
