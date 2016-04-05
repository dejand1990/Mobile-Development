package com.dejan_d.dice;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.dejan_d.dice.SQL.DBAdapter;

/**
 * Created by Dejan on 31-3-2016.
 * Here the list is filled with the rolled scores so far
 * It fills the listview and has buttons to go back and delete the listview
 */
public class ScoreActivity extends Activity {
    DBAdapter myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_score);

        populateListViewFromDB();

    }
    public void goBackToRoll(View view){
        if(view.getId() == R.id.doneBtn) {
            Intent rollDice = new Intent(this, RollDiceActivity.class);
            startActivity(rollDice);
        }
    }

    //To delete the listview
    public void deleteAllScore(View view){
        if(view.getId() == R.id.deleteAllBtn) {
            myDb = new DBAdapter(getApplicationContext());
            myDb.deleteAll();
            Intent rollDice = new Intent(this, RollDiceActivity.class);
            startActivity(rollDice);
        }
    }

    //Fill the listview with the rolled so far scores
    private void populateListViewFromDB(){
        myDb = new DBAdapter(getApplicationContext());

        Cursor cursor = myDb.getAllRows();

        String[] fromFieldNumbers = new String[]
                {DBAdapter.SCORE_ROWID, DBAdapter.SCORE_AMOUNT};
        int[] toViewIDs = new int[]
                {R.id.item_name, R.id.item_value};


        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_layout, cursor, fromFieldNumbers, toViewIDs, 0);

        ListView myList = (ListView) findViewById(R.id.listViewFromDB);
        myList.setAdapter(myCursorAdapter);

    }

}