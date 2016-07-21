package com.dongbaishun.baishitong.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by DongBaishun on 2016/7/16.
 */
public class MyToast {
    public static void SToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static void IToast(Context context, int id) {
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
    }
}
