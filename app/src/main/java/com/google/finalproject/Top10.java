package com.google.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Top10 extends AppCompatActivity {
    ArrayList<leaderboard> lead_arr = new ArrayList<leaderboard>();
    ArrayList<String> lead_arr_str = new ArrayList<String>();
    String top_ten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);
        this.getSupportActionBar().hide();

        Intent from_result = getIntent();
        top_ten = from_result.getStringExtra("top_10"); //top_10 is json

        Gson gson = new Gson();
        TypeToken<ArrayList<leaderboard>> token = new TypeToken<ArrayList<leaderboard>>() {};
        lead_arr = gson.fromJson(top_ten,token.getType()); //resolve json

        for(int i=0;i<lead_arr.size();i++) //using as "toString" for the array to string array
            lead_arr_str.add(lead_arr.get(i).getUsername()+": Earn "+lead_arr.get(i).getScore()+
                    " in the same level !");

        ListView top_10 = (ListView)findViewById(R.id.top_10);
        ListAdapter bulkAdapter = new CustomAdapter(this,lead_arr_str.toArray(new String[lead_arr_str.size()]));
        top_10.setAdapter(bulkAdapter);
        }
    }

