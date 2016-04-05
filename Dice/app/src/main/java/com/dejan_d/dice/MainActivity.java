package com.dejan_d.dice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.dejan_d.dice.SQL.DBAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.Random;

import static com.dejan_d.dice.R.drawable.animation;
import static com.dejan_d.dice.R.drawable.animation2;

/*
Shared preferences are done in the first window. They count the amount of times someone has played.
 */
public class MainActivity extends AppCompatActivity {
    private final String MY_PREFERENCES = "MyPrefs";
    private final String AMOUNT_PLAYED = "amountPlayed";
    private SharedPreferences sharedpreferences;

    //Sets the value of times played
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        addAmountPlayed(this);
        TextView amountPlayed = (TextView) findViewById(R.id.amountPlayed);
        amountPlayed.setText("You have played "+getAmountPlayed()+" times.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addAmountPlayed(Context context){
        sharedpreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        int amountPlayed = getAmountPlayed()+1;
        setAmountPlayed(amountPlayed);
    }

    public void setAmountPlayed(int a) {
        sharedpreferences.edit().putInt(AMOUNT_PLAYED, a).commit();
    }

    public int getAmountPlayed() {
        return sharedpreferences.getInt(AMOUNT_PLAYED, 0);
    }

    public void goToRollDice(View view) {
        if(view.getId() == R.id.btnLogin) {
            Intent rollDice = new Intent(this, RollDiceActivity.class);
            startActivity(rollDice);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
