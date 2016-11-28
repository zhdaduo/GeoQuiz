package com.example.navie.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class QuziActivity extends AppCompatActivity {

  private static final String TAG  = "QuziActivity";
  private static final String KEY_INDEX = "index";
  @BindView(R.id.question_text_view) TextView mQuestionTextView;

  private Question[] mQuestionBank = new Question[]{
      new Question(R.string.question_oceans,true),
      new Question(R.string.question_mideast,false),
      new Question(R.string.question_africa,false),
      new Question(R.string.question_americas,true),
      new Question(R.string.question_asia,true),
  };

  private int mCurrentIndex = 0;
  private boolean message;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quzi);
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);
    if(savedInstanceState != null)
    {
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
    }
    updateQuestion();
  }

  private void updateQuestion()
  {
    int question = mQuestionBank[mCurrentIndex].getmTextRestId();
    mQuestionTextView.setText(question);
  }

  @OnClick({ R.id.true_button, R.id.false_button, R.id.next_button, R.id.cheat_button}) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.true_button:
        PredictAnswer(true);
        break;
      case R.id.false_button:
        PredictAnswer(false);
        break;
      case R.id.next_button:
        mCurrentIndex = (mCurrentIndex + 1)%mQuestionBank.length;
        updateQuestion();
        message = false;
        break;
      case R.id.cheat_button:
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
        Intent i = CheatActivity.newIntent(getApplicationContext(),answerIsTrue);
        startActivity(i);
        break;

    }
  }

  public void PredictAnswer(boolean userPressedTrue)
  {
    int Toastmessage = 0;
    boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
    if(message){
      Toastmessage = R.string.judgment_toast;
    }else{
      if(answerIsTrue == userPressedTrue) {
        Toastmessage = R.string.correct_toast;
      }else {
        Toastmessage = R.string.incorrect_toast;
      }
    }
    Toast.makeText(QuziActivity.this,Toastmessage,Toast.LENGTH_SHORT).show();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEventMainThread(RemoteCheatEvent rce)
  {
      message = rce.getIsCheat();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.i(TAG,"onSaveInstanceState");
    outState.putInt(KEY_INDEX,mCurrentIndex);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

}
