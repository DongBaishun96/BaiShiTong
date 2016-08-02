package com.dongbaishun.baishitong.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by DongBaishun on 2016/7/30.
 */
public class MyApplication extends Application {

  private static Context ctx;

  @Override
  public void onCreate() {
    super.onCreate();
    ctx = getApplicationContext();
  }

  public static Context getCtx() {
    return ctx;
  }
}
