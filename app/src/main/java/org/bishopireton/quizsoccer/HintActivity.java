package org.bishopireton.quizsoccer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HintActivity extends AppCompatActivity {

//    private TextView mAnswerTextView;

    private int penalty = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
            }

    public void displayHint(View view) {
        boolean hint = getIntent().getBooleanExtra("hint",false);
        TextView mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mAnswerTextView.setText(""+hint);
        penalty = (int)(Math.random()*3)+1;
    }

    public void onBackPressed() {
        Intent result;
        result = new Intent();
        result.putExtra("penalty",penalty);
        setResult(RESULT_OK,result);
        this.finish();
    }
}
