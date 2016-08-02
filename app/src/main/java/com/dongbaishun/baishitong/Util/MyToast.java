package com.dongbaishun.baishitong.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by DongBaishun on 2016/7/16.
 */
public class MyToast {
  private static Toast toast;

  public static void SToast(Context context, String string) {
    if (toast == null) {
      toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
    } else {
      toast.show();
    }
  }

  public static void IToast(Context context, int id) {
    Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
  }
}
