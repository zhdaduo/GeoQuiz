package com.example.navie.geoquiz;

/**
 * Created by navie on 2016/11/28.
 */

public class Question {

  private int mTextRestId;
  private boolean mAnswerTrue;
  public Question(int textRestId,boolean answerTrue)
  {
    mTextRestId = textRestId;
    mAnswerTrue = answerTrue;
  }

  public int getmTextRestId() {
    return mTextRestId;
  }

  public void setmTextRestId(int mTextRestId) {
    this.mTextRestId = mTextRestId;
  }

  public boolean ismAnswerTrue() {
    return mAnswerTrue;
  }

  public void setmAnswerTrue(boolean mAnswerTrue) {
    this.mAnswerTrue = mAnswerTrue;
  }

}
