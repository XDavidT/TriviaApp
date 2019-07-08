package com.google.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class Results extends AppCompatActivity {
    private String username;
    private String points, level;
    static private ArrayList<LeadB_person> leadb=new ArrayList<LeadB_person>();

    //This page is after user done to play and want to see his result
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        this.getSupportActionBar().hide();

        Intent recive_intent = getIntent();
        username = recive_intent.getStringExtra("username");
        points = recive_intent.getStringExtra("points");
        level = recive_intent.getStringExtra("level_str");
        ((TextView)findViewById(R.id.points_view)).setText(points);
        try{
            new send_data().execute();
        } catch (Exception e){
            e.printStackTrace();
        }

        //need to send the winner to the server

        leadb.add(new LeadB_person(username,level,points));
    }


    public void play_again(View view){
        Intent back_to_start = new Intent(this,Welcome_page.class);
        startActivity(back_to_start);
        finish();
    }

    public void move_to_leaderboard(View view){
        new get_the_top().execute();
    }
    @Override
    public void onBackPressed() {
    }
    private void pop_up(String str){ //Only get popup without writing all this again
        Toast.makeText(this,str, Toast.LENGTH_LONG).show();
    }

    public class send_data extends AsyncTask<Void,Void,Void>{
        //Need to sent points to server
        @Override
        protected Void doInBackground(Void... voids) {
            String json_str = null;
            Socket clientSocket = null;
            DataOutputStream outToServer = null;
            try {
                clientSocket = new Socket("192.168.43.108", 10000); //Connect to server
                outToServer = new DataOutputStream(clientSocket.getOutputStream()); //Stream for selection
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outToServer.writeBytes("2"+"\n");
                outToServer.writeBytes(username + "\n");
                outToServer.writeBytes(level + "\n");
                outToServer.write(Integer.parseInt(points));
                clientSocket.close();
            }
            catch (Exception e){
                pop_up("Error sending data to server");
                e.printStackTrace();
            }


            return null;
        }
    }

    //Task that downloading top from current level and move you to next intent
    public class get_the_top extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... voids) {
            //same as in "serverconnection"
            String json_str = null;
            Socket clientSocket = null;
            DataOutputStream outToServer = null;
            BufferedReader inFromServer = null;
            try{
                clientSocket = new Socket("192.168.43.108", 10000); //Connect to server
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                outToServer = new DataOutputStream(clientSocket.getOutputStream()); //Stream for selection
                outToServer.writeBytes("3\n"); //Select server menu - get questions
                outToServer.writeBytes(level+"\n");
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Get server output
                json_str = inFromServer.readLine();
                clientSocket.close();
                return  json_str;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json_str) {
        Intent move_to_lead = new Intent(getBaseContext(),Top10.class);
        move_to_lead.putExtra("top_10",json_str); //top_10 will be resolve next screen
        startActivity(move_to_lead);
        }
    }
}
