package com.dongbaishun.baishitong.NetUrl;

/**
 * Created by DongBaishun on 2016/7/23.
 */
public class MyToken {
  private static String token = "";

  public static String getToken() {
    return token;
  }

  public static void setToken(String newToken) {
    token = newToken;
  }
}
