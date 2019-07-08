package com.google.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Welcome_page extends AppCompatActivity {
    private String username;
    private RadioGroup radio_group;
    private RadioButton radio_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        this.getSupportActionBar().hide();
    }

    public void on_click_start(View view){
        //Collect the values
        username = ((EditText)findViewById(R.id.user_name)).getText().toString(); //Save the name
        radio_group = (RadioGroup)findViewById(R.id.radio_level_group); //Find the radio group
        int selectedId = radio_group.getCheckedRadioButtonId();         //GetID
        radio_button = (RadioButton)findViewById(selectedId);           //find who was marked


        //Move the values the Server connection
        Intent game_intent = new Intent(getBaseContext(),ServerConnection.class);
        game_intent.putExtra("username",username);
        game_intent.putExtra("level_str",radio_button.getText().toString());

        startActivity(game_intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.out.close();
    }

}
