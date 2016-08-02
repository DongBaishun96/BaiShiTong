package com.dongbaishun.baishitong.ui.fragment;


import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dongbaishun.baishitong.NetUrl.NetUrl;
import com.dongbaishun.baishitong.R;
import com.dongbaishun.baishitong.Util.MLog;
import com.dongbaishun.baishitong.Util.MyToast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by DongBaishun on 2016/7/27.
 */
public class EduFragment extends Fragment {
  EditText editAccountText;
  EditText editPassText;
  String editAccount = "";
  String editPass = "";
  Button edu_post;

  final static String TAG = "eduTag";

  OkHttpClient client = new OkHttpClient();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstaceState) {

    View view = inflater.inflate(R.layout.edufragment, container, false);
    editAccountText = (EditText) view.findViewById(R.id.edu_account);
    editPassText = (EditText) view.findViewById(R.id.edu_editPass);
    edu_post = (Button) view.findViewById(R.id.button_edu);

    loginEdu();

    return view;//null
  }

  private void loginEdu() {
    edu_post.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        editAccount = editAccountText.getText().toString();
        editPass = editPassText.getText().toString();
        if (editAccount.equals("") || editPass.equals("")) {
          MyToast.SToast(getActivity(), "输入不能不为空");
        } else {
          new Thread(new Runnable() {
            @Override
            public void run() {
              Log.e("当前线程：", "" + Thread.currentThread().getName());

              Headers headers = new Headers.Builder()
                      .add("Origin", "http://10.5.2.80")
                      .add("Upgrade-Insecure-Requests", "1")
                      .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
                      .add("Content-type", "application/x-www-form-urlencoded")
                      .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                      .add("Referer", "http://10.5.2.80/(ulay2bi0ct2leszjcnypiuqv)/default2.aspx")
                      .add("Accept-Encoding", "gzip, deflate")
                      .add("Accept-Language", "zh-CN,zh;q=0.8")
                      .build();

              final FormBody body = new FormBody.Builder()
                      //RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"))
                      .add("TextBox1", editAccount)
                      .add("TextBox2", editPass)
                      .add("RadioButtonList1", "学生")
                      .add("Button1", " 登 录 ")
                      .build();
              //body.create(MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"))
              //public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
              final Request request = new Request.Builder()
                      //.create(MediaType.parse("application/x-www-form-urlencoded;charset=gb2312")
                      .url(NetUrl.NETWORK_LOGIN)
                      .headers(headers)
                      .post(body)
                      .build();

              Response response = null;
              try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                  String responsebody = response.body().string();
                  MLog.iLog(TAG, responsebody);
                  //Looper.prepare();
                  //MyToast.SToast(getActivity(), "登录成功！");
                  MLog.iLog(TAG, "登录成功！");
                  String partResponse = responsebody.substring(0, 10);
                  Looper.prepare();
                  MyToast.SToast(getActivity(), partResponse);
                  Looper.loop();
                } else {
                  MLog.iLog(TAG, "登录失败");
                }
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }).start();
        }
      }
    });
  }
}
