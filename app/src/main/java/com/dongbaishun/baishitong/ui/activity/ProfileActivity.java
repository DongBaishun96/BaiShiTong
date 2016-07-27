package com.dongbaishun.baishitong.ui.activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
//import android.os.manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import com.dongbaishun.baishitong.R;
import com.dongbaishun.baishitong.Util.MLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Manifest;

/**
 * Created by DongBaishun on 2016/7/22.
 */
@SuppressLint("SdCardPath")
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
  private final String TAG = "LOG_PROFILE";
  private ImageView ivHead;//头像显示
  private Button btnTakephoto;//拍照
  private Button btnPhotos;//相册
  private Bitmap head;//头像Bitmap
  private static String path = "/sdcard/myHead/";
  //private static String path = Environment.getExternalStorageDirectory().getAbsolutePath();//sd路径

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_whole_profile);
    initView();
  }

  private void initView() {

    //声明Toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //toolbar标题的文字需在setSupportActionBar之前，不然会无效
    setSupportActionBar(toolbar);

    //初始化控件
    btnPhotos = (Button) findViewById(R.id.btn_photos);
    btnTakephoto = (Button) findViewById(R.id.btn_takephoto);
    btnPhotos.setOnClickListener(this);
    btnTakephoto.setOnClickListener(this);
    ivHead = (ImageView) findViewById(R.id.iv_head);

    //SD卡读写权限
    int REQUEST_EXTERNAL_STORAGE = 1;
    String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    int permission = ActivityCompat.checkSelfPermission(ProfileActivity.this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (permission != PackageManager.PERMISSION_GRANTED) {
      // We don't have permission so prompt the user
      ActivityCompat.requestPermissions(
              ProfileActivity.this,
              PERMISSIONS_STORAGE,
              REQUEST_EXTERNAL_STORAGE
      );
    }

    Bitmap bt = BitmapFactory.decodeFile(path + "/head.jpg");//从Sd中找头像，转换成Bitmap
    if (bt != null) {
      MLog.iLog(TAG, "bt不为空");
      Drawable drawable = new BitmapDrawable(bt);//转换成drawable
      ivHead.setImageDrawable(drawable);
    } else {
      MLog.iLog(TAG, "Sd卡中没有存head.jpg");
      /**
       *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
       *
       */
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_photos://从相册里面取照片
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1, 1);
        break;
      case R.id.btn_takephoto://调用相机拍照
        //相机拍照权限
        int REQUEST_EXTERNAL_CAMERA = 1;
        String[] PERMISSIONS_CAMERA = {
                android.Manifest.permission.CAMERA,
        };
        int permission = ActivityCompat.checkSelfPermission(ProfileActivity.this,
                android.Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
          // We don't have permission so prompt the user
          ActivityCompat.requestPermissions(
                  ProfileActivity.this,
                  PERMISSIONS_CAMERA,
                  REQUEST_EXTERNAL_CAMERA
          );
        }
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "head.jpg")));
        startActivityForResult(intent2, 2);//采用ForResult打开
        break;
      default:
        MLog.iLog(TAG, "OnClick error");
        break;
    }
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case 1:
        if (resultCode == RESULT_OK) {
          cropPhoto(data.getData());//裁剪图片
        }
        break;

      case 2:
        if (resultCode == RESULT_OK) {
          File temp = new File(Environment.getExternalStorageDirectory()
                  + "/head.jpg");
          cropPhoto(Uri.fromFile(temp));//裁剪图片
        }
        break;

      case 3:
        if (data != null) {
          Bundle extras = data.getExtras();
          head = extras.getParcelable("data");
          if (head != null) {
            /**
             * 上传服务器代码
             */
            MLog.iLog(TAG, "照片上传服务器");
            setPicToView(head);//保存在SD卡中
            ivHead.setImageBitmap(head);//用ImageView显示出来
          }
        }
        break;
      default:
        break;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  /**
   * 调用系统的裁剪
   *
   * @param uri
   */
  public void cropPhoto(Uri uri) {
    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.setDataAndType(uri, "image/*");
    intent.putExtra("crop", "true");
    // aspectX aspectY 是宽高的比例
    intent.putExtra("aspectX", 1);
    intent.putExtra("aspectY", 1);
    // outputX outputY 是裁剪图片宽高
    intent.putExtra("outputX", 150);
    intent.putExtra("outputY", 150);
    intent.putExtra("return-data", true);
    startActivityForResult(intent, 3);
  }

  private void setPicToView(Bitmap mBitmap) {
    String sdStatus = Environment.getExternalStorageState();
    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
      MLog.iLog(TAG, "sd卡不可用");
      return;
    }
    FileOutputStream b = null;
    /*
    判断文件夹是否存在，修改时间2016.07.26 8:49
     */
    File file = new File(path);
    if (!file.exists()) {
      boolean createStatu = file.mkdirs();// 创建文件夹
      if (createStatu) {
        MLog.iLog(TAG, "创建path文件夹成功");
      } else {
        MLog.iLog(TAG, "创建path文件夹失败");
      }
    }
    String fileName = path + "head.jpg";//图片名字
    try {
      MLog.iLog(TAG, "filename:" + fileName);
      b = new FileOutputStream(fileName);
      mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      try {
        //关闭流
        b.flush();
        b.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /*
  ToolBar 标题
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_profile, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.upload) {
      startActivity(new Intent(ProfileActivity.this, MainActivity.class));
      ProfileActivity.this.finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
