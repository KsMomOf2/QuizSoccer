package org.bishopireton.quizsoccer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mBtnTrue;
    private Button mBtnFalse;
    private Button mBtnNext;
    private Button mBtnHint;
    private TextView mTxtScore;

    private TextView mQuestionTextView;
    private ImageView mImageFlag;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question1, true, R.drawable.groupf),
            new Question(R.string.question2, false, R.drawable.russia),
            new Question(R.string.question3, false,R.drawable.belgium),
            new Question(R.string.question4, true, R.drawable.england),
            new Question(R.string.question5, true, R.drawable.croatia)
    };

    private int mCurrentIndex = 0;
    private int totalScore=0;
    private int numQuestions=0;

    private SoundPool sounds;
    private int sndCorrect;
    private int sndWrong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mImageFlag = (ImageView) findViewById(R.id.imgFlag);
        mBtnTrue = (Button) findViewById(R.id.btnTrue);
        mBtnFalse = (Button) findViewById(R.id.btnFalse);
        mBtnNext = (Button) findViewById(R.id.btnNext);
        mBtnHint = (Button) findViewById(R.id.btnHint);
        mTxtScore = (TextView) findViewById(R.id.txtScore);

        mQuestionTextView = (TextView) findViewById(R.id.txtQuestion);

        setupSounds();
        displayNextQuestion();

        mBtnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mBtnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNextQuestion();
            }
        });
    }
    private void setupSounds() {
        //declare variables
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        sndWrong = sounds.load(this, R.raw.wrong, 1);
        sndCorrect = sounds.load(this, R.raw.correct, 1);

    }
        private void displayNextQuestion () {
            if (numQuestions >= mQuestionBank.length) {
                Intent ending = new Intent(this,QuizOver.class);
                ending.putExtra("score",totalScore);
                startActivity(ending);
            }
            else {
                mBtnFalse.setEnabled(true);
                mBtnTrue.setEnabled(true);
                do {
                    mCurrentIndex = (int) (Math.random() * mQuestionBank.length);
                } while (mQuestionBank[mCurrentIndex].isUsed());
                mQuestionBank[mCurrentIndex].setUsed(true);
                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
                mImageFlag.setImageResource(mQuestionBank[mCurrentIndex].getImgFlagId());
                numQuestions++;
            }
        }

        private void checkAnswer ( boolean userPressedTrue){
            mBtnFalse.setEnabled(false);
            mBtnTrue.setEnabled(false);
            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
            int messageResID = 0;
            if (userPressedTrue == answerIsTrue) {
                sounds.play(sndCorrect, 1.0f, 1.0f, 0, 0, 1.5f);
                messageResID = R.string.correctToast;
               totalScore += mQuestionBank[mCurrentIndex].getPoints();
               mTxtScore.setText(""+totalScore);
            }
            else {
                sounds.play(sndWrong, 1.0f, 1.0f, 0, 0, 1.5f);
                messageResID = R.string.incorrectToast;
            }
            Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();


        }

    public void btnHintOnClick(View view) {
        // create an intent to open the activity
        // Need QuizActivity.this if you are putting this code in a listener
        //   Intent i = new Intent(QuizActivity.this, HintActivity.class);
        Intent i = new Intent(this, HintActivity.class);
        i.putExtra("hint", mQuestionBank[mCurrentIndex].isAnswerTrue());
        startActivityForResult(i,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int penalty =0;
        if (requestCode==1)
            if (resultCode == RESULT_OK)
                penalty = data.getIntExtra("penalty",2);
        totalScore -= penalty;
        mTxtScore.setText(""+totalScore);
    }

}