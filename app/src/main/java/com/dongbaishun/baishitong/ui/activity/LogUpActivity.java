package com.dongbaishun.baishitong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dongbaishun.baishitong.NetUrl.NetUrl;
import com.dongbaishun.baishitong.R;
import com.dongbaishun.baishitong.Util.MLog;
import com.dongbaishun.baishitong.Util.MyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DongBaishun on 2016/7/18.
 */
public class LogUpActivity extends AppCompatActivity {
  private Button bt_post;
  private String newUserName = "";
  private String newPassWord = "";
  private String newPassWordAgain = "";

  OkHttpClient client = new OkHttpClient();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log_up);

    bt_post = (Button) findViewById(R.id.btn_log_up);

    bt_post.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        newUserName = ((EditText) findViewById(R.id.edit_new_username)).getText().toString().trim();
        newPassWord = ((EditText) findViewById(R.id.edit_new_password)).getText().toString().trim();
        newPassWordAgain = ((EditText) findViewById(R.id.edit_new_password_again)).getText().toString().trim();
        Log.i("TEST", "button onClick");
        if (newUserName.equals("") || newPassWord.equals("") || newPassWordAgain.equals("")) {
          Log.i("TEST", "null");
          if (TextUtils.isEmpty(((EditText) findViewById(R.id.edit_new_username)).getText())) {
            Toast.makeText(LogUpActivity.this, "empty", Toast.LENGTH_SHORT).show();
            Log.i("LogUppppp", "emptyLog");
          }
          Toast.makeText(LogUpActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
          Log.i("TEST", "input errors");
          ((EditText) findViewById(R.id.edit_new_username)).setText("");
          ((EditText) findViewById(R.id.edit_new_password)).setText("");
          ((EditText) findViewById(R.id.edit_new_password_again)).setText("");
        } else if (newPassWord.equals(newPassWordAgain)) {
          Log.i("LogUppppp", newUserName);
          Log.i("LogUppppp", newPassWord);
          Log.i("LogUppppp", newPassWordAgain);

          new Thread(new Runnable() {
            @Override
            public void run() {
              Log.e("当前线程：", "" + Thread.currentThread().getName());
              Log.i("Thread", newUserName);
              Log.i("Thread", newPassWord);
              Log.i("Thread", newPassWordAgain);
              FormBody body = new FormBody.Builder()
                      .add("username", newUserName)
                      .add("password", newPassWord)
                      .build();

              final Request request = new Request.Builder()
                      .url(NetUrl.LOG_UP)
                      .post(body)
                      .build();

              Response response = null;
              try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                  Looper.prepare();
                  String res = response.body().string();
                  Log.i("LogUP", "打印POST响应的数据：" + res);
                  int retCode = 0;
                  try {
                    JSONObject jsonObject = new JSONObject(res);
                    retCode = jsonObject.getInt("success");
                  } catch (JSONException e) {
                    e.printStackTrace();
                  }
                  final int isSucceed = retCode;
                  MLog.iLog("isSucceed", "isSucceed:" + isSucceed);
                  if (isSucceed == 1) {
                    Toast.makeText(LogUpActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    MLog.iLog("upupup", "准备跳转");
                    Intent intent = new Intent(LogUpActivity.this, LoginActivity.class);
                    if (intent != null) {
                      startActivity(intent);
                    } else {
                      Toast.makeText(LogUpActivity.this, "跳转失败", Toast.LENGTH_SHORT).show();
                    }
                  } else {
                    MyToast.SToast(LogUpActivity.this, "注册失败");
                  }
                  Looper.loop();
                } else {
                  throw new IOException("Unexpected code " + response);
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          }).start();
        } else {
          Toast.makeText(LogUpActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
          Log.i("LogUppppp", "密码不一致");
        }
      }
    });
  }
}
