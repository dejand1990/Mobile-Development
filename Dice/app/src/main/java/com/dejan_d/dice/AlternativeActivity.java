package com.dejan_d.dice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Dejan on 24-2-2016.
 */
public class AlternativeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_alternative);
    }

    public void goBackToRoll(View view){
        if(view.getId() == R.id.endAlternative) {
            Intent rollDice = new Intent(this, RollDiceActivity.class);
            startActivity(rollDice);
        }
    }
}
