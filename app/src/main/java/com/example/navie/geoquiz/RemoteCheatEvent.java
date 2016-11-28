package com.example.navie.geoquiz;

/**
 * Created by navie on 2016/11/28.
 */

public class RemoteCheatEvent {
  public boolean getIsCheat() {
    return isCheat;
  }
  public RemoteCheatEvent(boolean cheat)
  {
    this.isCheat = cheat;
  }
  boolean isCheat;
}
