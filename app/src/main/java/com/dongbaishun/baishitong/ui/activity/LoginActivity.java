package com.dongbaishun.baishitong.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dongbaishun.baishitong.NetUrl.NetUrl;
import com.dongbaishun.baishitong.R;
import com.dongbaishun.baishitong.Util.MLog;
import com.dongbaishun.baishitong.Util.MyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
  final String TAG = "LoginActivity";
  private Button bt_login;
  private EditText userEdit;
  private EditText pawdEdit;
  private String username;
  private String password;
  private TextView bt_logup;

  OkHttpClient client = new OkHttpClient();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    MLog.iLog("debugggg", "结束加载布局");
    bt_login = (Button) findViewById(R.id.login_btn_login);
    bt_logup = (TextView) findViewById(R.id.logup_user);
    MLog.iLog("debugggg", "button和textview无错误");
    bt_login.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        userEdit = (EditText) findViewById(R.id.login_edit_account);
        pawdEdit = (EditText) findViewById(R.id.login_edit_pwd);
        username = userEdit.getText().toString();
        password = pawdEdit.getText().toString();
        MLog.iLog(TAG, "onClick");
        MLog.iLog("debugggg", "onClick 登录.");
        if (username.equals("") || password.equals("")) {
          MyToast.SToast(LoginActivity.this, "输入不能为空");
        } else {
          MLog.iLog(TAG, "username:" + username);
          MLog.iLog(TAG, "password:" + password);

          FormBody body = new FormBody.Builder()
                  .add("username", username)
                  .add("password", password)
                  .build();
          //可以通过Request.Builder设置更多的参数比如：header、method等
          Request request = new Request.Builder()
                  .url(NetUrl.LOG_IN)
                  .post(body)
                  .build();

          //request的对象去构造得到一个Call对象，类似于将你的请求封装成了任务，既然是任务，就会有execute()和cancel()等方法
          client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              MyToast.SToast(LoginActivity.this, "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              final String res = response.body().string();
              int retCode = 0;
              int retHasLogin = 0;
              String retToken = "";
              MLog.iLog(TAG, res);
              try {
                JSONObject jsonObject = new JSONObject(res);
                retCode = jsonObject.getInt("success");
                retHasLogin = jsonObject.getInt("hasLogin");
                retToken = jsonObject.getString("token");
              } catch (JSONException e) {
                e.printStackTrace();
              }
              MLog.iLog(TAG, "retCode:" + retCode);

              final int isSucceed = retCode;
              final int isLogin = retHasLogin;
              final String newToken = retToken;
              runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                  Looper.prepare();
                  if (isSucceed == 1) {
                    if (isLogin == 1) {
                      new AlertDialog.Builder(LoginActivity.this).setTitle("系统提示")//设置对话框标题
                              .setMessage("您已登录！")//设置显示的内容
                              .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                  //finish();
                                  startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                  LoginActivity.this.finish();
                                }
                              })
                              .setNegativeButton("返回",
                                      new DialogInterface.OnClickListener() {//添加返回按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//响应事件
                                          Log.i("alertdialog", " 请重新输入账号密码！");
                                        }
                                      })
                              .show();//在按键响应事件中显示此对话框
                    } else {
                      startActivity(new Intent(LoginActivity.this, MainActivity.class));
                      //MyToken.setToken(newToken);
                      LoginActivity.this.finish();
                      //Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                  } else if (isSucceed == 0) {
                    //MLog.iLog(TAG, "用户名密码不匹配！");
                    //Toast.makeText(LoginActivity.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
                  } else {
                    MLog.iLog(TAG, "error");
                  }
                  //               Looper.loop();
                }
              });
            }
          });
        }
      }
    });

    bt_logup.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        MLog.iLog(TAG, "logup_intent");
        Intent intent = new Intent(LoginActivity.this, LogUpActivity.class);
        if (intent != null) {
          startActivity(intent);
        } else {
          MyToast.SToast(LoginActivity.this, "请求失败");
        }
      }
    });

  }
}

