package com.dejan_d.dice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dejan_d.dice.SQL.DBAdapter;

/**
 * Created by Dejan on 24-2-2016.
 * This shows the rolled task and has the option to navigate to different other views
 */
public class BordActivity extends Activity {
    String rolledAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bord);
        rolledAssignment = getIntent().getExtras().getString("assignment");

        TextView textViewAssignment = (TextView) findViewById(R.id.assignmentText);
        textViewAssignment.setText(rolledAssignment);
    }

    public void goBackToRoll(View view){
        if(view.getId() == R.id.doneBtn) {
            Intent rollDice = new Intent(this, RollDiceActivity.class);
            startActivity(rollDice);
        }
    }

    public void alternativeToRoll(View view) {
        if(view.getId() == R.id.alternativeBtn) {
            Intent alternativeRoll = new Intent(this, AlternativeActivity.class);
            startActivity(alternativeRoll);
        }
    }

    public void viewScore(View view) {
        if(view.getId() == R.id.viewScoreBtn) {
            Intent viewScore = new Intent(this, ScoreActivity.class);
            startActivity(viewScore);
        }
    }

}
