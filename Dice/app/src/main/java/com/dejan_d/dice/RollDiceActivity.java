package com.dejan_d.dice;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dejan_d.dice.SQL.DBAdapter;

import java.util.Random;

import static com.dejan_d.dice.R.drawable.animation;
import static com.dejan_d.dice.R.drawable.animation2;

/**
 * Created by Dejan on 24-2-2016.
 *
 * Roll activity that gives 2 random numbers and makes the dice "roll" and sets the random numbers to the dice
 */
public class RollDiceActivity extends Activity {
    DBAdapter myDb;
    Boolean hasRolled = false;
    public Integer amountRolled;
    String task;
    TextView rollingText;
    Animation fadeIn;
    Button goToNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_rolldice);

        rollingText = (TextView) findViewById(R.id.rollDiceText);

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        goToNext = (Button) findViewById(R.id.btnRollDice);
    }

    //The button is reused to do both the rolling and the passing to the next window with the value
    public void rollDice(View view) {
        // Once the random numbers are created and roll animation finishes
        if(hasRolled == true){
            if(view.getId() == R.id.btnRollDice) {
                Intent bordAnimation = new Intent(RollDiceActivity.this, BordActivity.class);

                myDb = new DBAdapter(getApplicationContext());
                myDb.fillDatabase();

                Cursor cursor = myDb.getRow(amountRolled-1);

                if(cursor.moveToFirst()){
                    task = cursor.getString(1);
                }

                myDb.addScoreToDatabase(task);
                bordAnimation.putExtra("assignment", task);
                startActivity(bordAnimation);
            }
            //Rolls the dice and sets the random numbers
        }else{
            goToNext.startAnimation(fadeIn);
            rollingText.setText("Rolling...");

            Random rand = new Random();
            Random rand2 = new Random();
            int numberDice1 = rand.nextInt(6) + 1;
            final String stringDice1 = String.valueOf(numberDice1);

            int numberDice2 = rand2.nextInt(6) + 1;
            final String stringDice2 = String.valueOf(numberDice2);

            amountRolled = numberDice1 + numberDice2;

            String dice1Number = "dice".concat(stringDice1);
            final int imageID1 = getResources().getIdentifier(dice1Number, "drawable", this.getPackageName());

            String dice2Number = "dice".concat(stringDice2);
            final int imageID2 = getResources().getIdentifier(dice2Number, "drawable", this.getPackageName());

            final ImageView myImage1 = (ImageView) findViewById(R.id.imageView1);
            final ImageView myImage2 = (ImageView) findViewById(R.id.imageView2);

            myImage1.setBackgroundResource(animation);
            myImage2.setBackgroundResource(animation2);

            // Get the background, which has been compiled to an AnimationDrawable object.
            final AnimationDrawable frameAnimation = (AnimationDrawable) myImage1.getBackground();
            final AnimationDrawable frameAnimation2 = (AnimationDrawable) myImage2.getBackground();

            // Start the animation
            frameAnimation.start();
            frameAnimation2.start();

            //Wait 2,5 seconds
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    //Stop animation
                    frameAnimation.stop();
                    frameAnimation2.stop();

                    //Set final roll number
                    myImage1.setBackgroundResource(imageID1);
                    myImage2.setBackgroundResource(imageID2);

                    rollingText.setText("Done!");
                    goToNext.setText("See assignment!");

                    hasRolled = true;
                }
            }, 2500);
        }
    }
}
