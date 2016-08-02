package com.dongbaishun.baishitong.NetUrl;

/**
 * Created by DongBaishun on 2016/7/16.
 */

/**
 * 将所有的接口地址集结在此
 * 不要忘记用 ipconfig 命令查看自己的IP地址
 * 同时记得把 WAMP 打开，确保服务器在运行
 */
public class NetUrl {

  public static final String BASE = "http://123.206.50.237/baishitong/";

  public static final String LOG_IN = BASE + "login.php";

  public static final String LOG_UP = BASE + "logUp.php";

  public static final String NETWORK_LOGIN = "http://10.0.0.55/srun_portal_phone.php";

  public static final String SOFTWARE_NETWORK = "http://10.0.0.55/srun_portal_phone.php";

  //public static final String SOFTWARE_LOGOUT = "http://10.0.0.55/srun_portal_phone.php";

  public static final String PCAuthActionURL = "http://10.0.0.55:801/include/auth_action.php";

  public static final String NETWORK_LOGOUT = "http://10.0.0.55:801/srun_portal_phone_succeed.php";

  public static final String EDU_LOGIN = "http://10.5.2.80/(ulay2bi0ct2leszjcnypiuqv)/default2.aspx";

  //public static final String HelpCenter = "http://10.0.0.55:8800";

  //public static final String SelfService = "http://10.0.0.55:8800";

  public static final String DoLoginURL = "http://10.0.0.55/cgi-bin/do_login";

  public static final String KeepLiveURL = "http://10.0.0.55/cgi-bin/keeplive";

  public static final String DoLogoutURL = "http://10.0.0.55/cgi-bin/do_logout";
}
