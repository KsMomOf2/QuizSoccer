package org.bishopireton.quizsoccer;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuizOver extends AppCompatActivity implements LeaderAdapter.ItemClickListener {

    private List<Long> highScores = new ArrayList<Long>(10);
    private RecyclerView mRecLeaders;
    private Adapter adapLeaders;
    private TextView mMessage;
    private int score;

    // access the firebase database
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz_over);
        mMessage = findViewById(R.id.message);

        // retrieve the score this user received
        score = getIntent().getIntExtra("score",0);
        mMessage.setText("Thanks for playing, your score is " + score);

        //Access the database and my scores
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("scores");
        // GET THE SCORE LIST
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            //This reads in the current high scores
            highScores = (List<Long>)snapshot.getValue();

            Collections.sort(highScores,Collections.reverseOrder());
            adapLeaders. = highScores;
            Log.i("MMMMM", highScores.toString());
            checkHighScores();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });

/*  If you want to have multiple keys
        myRef.child("score1").setValue(score);
*/

/*
        SharedPreferences pref = getApplicationContext().getSharedPreferences("HighScores", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        int currentHighScore = pref.getInt("HighScore",-1);

        if (score > currentHighScore) {
            editor.putInt("HighScore",score);
            editor.commit();
            mMessage.setText(mMessage.getText() + "\n You have the NEW high score");
        }
*/
        ImageView ball = findViewById(R.id.usaball);
        ObjectAnimator animation = ObjectAnimator.ofFloat(ball, "translationX", 500f);
        animation.setDuration(2000);
        animation.start();
    }

    private void checkHighScores() {
        Log.i("MMMMM", ""+(highScores.size()-1));
        Log.i("MMMMM", "" + highScores.get(highScores.size()-1));

         Long lowestHighScore = highScores.get(highScores.size()-1);


 //       Log.i("MMMMM",""+lowestHighScore);
/*        if (score > lowestHighScore) {
            highScores.remove(highScores.size()-1); // Remove last entry
            highScores.add(0,score);
            myRef.setValue(highScores);
            mMessage.setText(mMessage.getText() + "\n You have a NEW high score");
        }
        */
    }
}
