package com.google.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import java.util.Scanner;

public class Game_play extends AppCompatActivity {
    private static String level_str;
    private String username; //The name of the player
    private int counter,points;  //Counting the question to know when finish
    private String questions;

    //Api array
    private static ArrayList<exportQues> qus_ans_list = new ArrayList<exportQues>();
    private RadioGroup radio_group;
    private RadioButton[] radio_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        this.getSupportActionBar().hide();

        points = 0;
        counter = 0;

        ///////////////////////////////////////////////////////////////////////////////////////

        // Get from welcome screen the name and the level
        Intent recive_intent = getIntent();
        username = recive_intent.getStringExtra("username");
        level_str = recive_intent.getStringExtra("level_str");
        questions = recive_intent.getStringExtra("questions");

        //Convert Gson to ArrayList of questions
        Gson gson = new Gson();
        TypeToken<ArrayList<exportQues>> token = new TypeToken<ArrayList<exportQues>>() {};
        qus_ans_list = gson.fromJson(questions, token.getType());


        //First configure the radio group as Array ! (Important!)
        radio_group = (RadioGroup)findViewById(R.id.answers); //Find the radio group
        radio_button = new RadioButton[4];
        radio_button[0] = (RadioButton)findViewById(R.id.answer1);
        radio_button[1] = (RadioButton)findViewById(R.id.answer2);
        radio_button[2] = (RadioButton)findViewById(R.id.answer3);
        radio_button[3] = (RadioButton)findViewById(R.id.answer4);

        change_question(); //Start the game
    }
    

    private void change_question(){
        //This function only change text !!

        //put question+answers from array
        //All areas using HTML decoder to avoid strange charters
        ((TextView)findViewById(R.id.question_view)).setText(Html.fromHtml(qus_ans_list.get(counter).getQuestion())); //Handle the text view
        radio_button[0].setText(Html.fromHtml(qus_ans_list.get(counter).getCorrect_answer()));
        radio_button[1].setText(Html.fromHtml(qus_ans_list.get(counter).getIncorrect_answer1()));
        radio_button[2].setText(Html.fromHtml(qus_ans_list.get(counter).getIncorrect_answer2()));
        radio_button[3].setText(Html.fromHtml(qus_ans_list.get(counter).getIncorrect_answer3()));

        //this loop is randomly changing values 4 times
        for (int i = 0; i < 4; i++) {
            int swap_ind1 = ((int) (Math.random() * 10) % 4);
            int swap_ind2 = ((int) (Math.random() * 10) % 4);
            RadioButton temp = radio_button[swap_ind1];
            radio_button[swap_ind1] = radio_button[swap_ind2];
            radio_button[swap_ind2] = temp;
        }

        //counter (String) in the button
        String count = Integer.toString(counter+1)+" from "+Integer.toString(qus_ans_list.size());
        ((TextView)findViewById(R.id.counter_view)).setText(count);

    }
    public void check_the_answer(View view) {

        if (radio_group.getCheckedRadioButtonId() == -1)
            pop_up("No answer was selected");
        //Not selected

        else {
            RadioButton temp = (RadioButton)findViewById(radio_group.getCheckedRadioButtonId());
            if(temp.getText().toString().equals(qus_ans_list.get(counter).getCorrect_answer())) {
                points++;
            }
            //Right Answer

            //Any case - Count to next in Array or Finish
            counter++;
            if(counter == qus_ans_list.size()) //When we got the max question - >EXIT
                finish_the_game();
            else {
                radio_group.clearCheck();
                change_question();      //Not in the end --> Next question
            }
        }
    }
    private void finish_the_game() {
        Intent move_to_result = new Intent(getBaseContext(),Results.class);
        move_to_result.putExtra("username",username);
        move_to_result.putExtra("points",Integer.toString(points));
        move_to_result.putExtra("level_str",level_str);
        startActivity(move_to_result);
        finish();
    }

    //Just an add-on to use easily with pop-up
    private void pop_up(String str){ //Only get popup without writing all this again
        Toast.makeText(this,str, Toast.LENGTH_LONG).show();
    }
    }


