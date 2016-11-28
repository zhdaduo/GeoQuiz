package com.example.navie.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;

public class CheatActivity extends AppCompatActivity {

  @BindView(R.id.answer_text_view) TextView answerTextView;
  @BindView(R.id.show_answer_button) Button showAnswerButton;
  private boolean mAnswerIsTrue;

  private static final String EXTRA_ANSWER_IS_TRUE = "com.example.navie.geoquiz.answer_is_true";

  public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
    Intent i = new Intent(packageContext, CheatActivity.class);
    i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    return i;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    ButterKnife.bind(this);
    mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @OnClick(R.id.show_answer_button) public void onShowAnswerButtonClick() {
    if (mAnswerIsTrue) {
      answerTextView.setText(R.string.true_button);
    } else {
      answerTextView.setText(R.string.false_button);
    }
    EventBus.getDefault().post(new RemoteCheatEvent(true));
    //animation limit
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
    {
      int cx = showAnswerButton.getWidth()/2;
      int cy = showAnswerButton.getHeight()/2;
      float radius = showAnswerButton.getWidth();
      Animator anim = ViewAnimationUtils.createCircularReveal(showAnswerButton,cx,cy,radius,0);
      anim.addListener(new AnimatorListenerAdapter() {
        @Override public void onAnimationEnd(Animator animation) {
          super.onAnimationEnd(animation);
          showAnswerButton.setVisibility(View.INVISIBLE);
        }
      });
      anim.start();
    }else{
          showAnswerButton.setVisibility(View.INVISIBLE);
    }
  }
}
