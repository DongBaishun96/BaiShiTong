package com.dongbaishun.baishitong.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dongbaishun.baishitong.NetUrl.NetState;
import com.dongbaishun.baishitong.NetUrl.NetUrl;
import com.dongbaishun.baishitong.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DongBaishun on 2016/7/30.
 */
public class OnlineFragment extends Fragment {

  Button bt_logout;
  TextView tv;

  private Handler mainhandler;

  final static String TAG = "OnlineTaggggggg";

  OkHttpClient client = new OkHttpClient();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstaceState) {

    View view = inflater.inflate(R.layout.onlinefragment, container, false);
    bt_logout = (Button) view.findViewById(R.id.button_logout);
    tv = (TextView) view.findViewById(R.id.textHint);

    bt_logout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        logout();
      }
    });

    mainhandler = new Handler() {
      public void handleMessage(android.os.Message msg) {
        switch (msg.what) {
          case 1:
            tv.setText("注销成功");
            NetState.Network_online_state = 0;
            NetState.Network_login_state = 0;
            break;
          default:
            tv.setText("注销出错");
            NetState.Network_online_state = 0;
        }
      }
    };

    return view;//null
  }

  private void logout() {

    new Thread(new Runnable() {
      @Override
      public void run() {
        Log.e("当前线程：" + TAG, "" + Thread.currentThread().getName());
        Headers headers = new Headers.Builder()
                .add("Upgrade-Insecure-Requests", "1")
                .add("Content-Type", "application/x-www-form-urlencoded")
                .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .add("Accept-Encoding", "gzip, deflate")
                .add("Accept-Language", "zh-CN,zh;q=0.8")
                .build();

        FormBody body = new FormBody.Builder()
                .add("action", "auto_logout")
                .build();

        Request request = new Request.Builder()
                .url(NetUrl.NETWORK_LOGIN)
                .headers(headers)
                .post(body)
                .build();

        Response response = null;

        try {
          response = client.newCall(request).execute();
          if (response.isSuccessful()) {
            Message msg = new Message();
            msg.obj = "注销成功";
            msg.what = 1;
            mainhandler.sendMessage(msg);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
