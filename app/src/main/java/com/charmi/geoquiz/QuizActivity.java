package com.charmi.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton, mFalseButton,mNextButton, mPrevButton, mCheatButton;
    private TextView mQuestionTextView;
    private static final String KEY_INDEX ="index";

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;

    @Override
    public  void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mTrueButton=(Button) findViewById(R.id.true_button);
        mFalseButton= (Button) findViewById(R.id.false_button);
        mNextButton= (Button) findViewById(R.id.next_button);
        mPrevButton= (Button) findViewById(R.id.prev_button );
        mCheatButton= (Button) findViewById(R.id.cheat_button);
        mQuestionTextView= (TextView) findViewById(R.id.question_text_view);


        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(QuizActivity.this, "Cheating is bad !", Toast.LENGTH_SHORT).show();

                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivity(i);
            }
        });


        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(mCurrentIndex==0){
                    mCurrentIndex=mQuestionBank.length-1;

                }
                else
                    mCurrentIndex--;

                updateQuestion();
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               checkAnswer(true);

            }
        });


        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);

            }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();
    }

    private void updateQuestion()
    {
        int question =mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue= mQuestionBank[mCurrentIndex].isAnswerTrue();


        if(userPressedTrue==answerIsTrue)
        {
            Toast.makeText(QuizActivity.this, "Correct answer", Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(QuizActivity.this,"Incorrect answer", Toast.LENGTH_SHORT).show();
        }
    }
}
