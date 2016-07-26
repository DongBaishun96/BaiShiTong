package com.dongbaishun.baishitong.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.dongbaishun.baishitong.R;
import com.dongbaishun.baishitong.Util.MLog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MLog.iLog("debugggg", "开始加载布局");
    setContentView(R.layout.activity_whole_main);
    MLog.iLog("debugggg", "下面声明布局");
    //声明Toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //toolbar标题的文字需在setSupportActionBar之前，不然会无效
    setSupportActionBar(toolbar);
    MLog.iLog("debugggg", "结束toolbar声明");
    //浮动按钮 & Snackbar
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Contact: baishun@dongbaishun.com", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
      }
    });
    MLog.iLog("debugggg", "结束浮动声明");

    //DrawerLayout
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    //切换开关 toggle
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    /*
    以下两行顺序随意
     */
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    //抽屉内容填充 xml->menu
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    MLog.iLog("debugggg", "结束drawlayout布局");
  }

  /*
    重写后退键：如果有抽屉则返回关闭；没有抽屉调用超类的后退
   */
  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
    MLog.iLog("debugggg", "onBackPressed无错");
  }

  /*
  ToolBar 标题
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    //实例化你的searchable 传递给SearchView
    SearchManager searchManager =
            (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView =
            (SearchView) menu.findItem(R.id.search).getActionView();
    searchView.setSearchableInfo(
            searchManager.getSearchableInfo(getComponentName()));
    MLog.iLog("debugggg", "onCreateMenu无错");
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    if (id == R.id.history) {
      //Warning:(96, 5) 'if' statement can be replaced with 'return id == R.id.history || super.onOptionsItemSelected(item);'
      return true;
    }
    MLog.iLog("debugggg", "onOptionItem无错");
    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
      startActivity(intent);
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {
      //SD卡读写权限
      int REQUEST_EXTERNAL_STORAGE = 1;
      String[] PERMISSIONS_STORAGE = {
              android.Manifest.permission.READ_EXTERNAL_STORAGE,
              android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
      };
      int permission = ActivityCompat.checkSelfPermission(MainActivity.this,
              android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
      if (permission != PackageManager.PERMISSION_GRANTED) {
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
                MainActivity.this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
        );
      }
      Intent intent1 = new Intent(Intent.ACTION_PICK, null);
      intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
      startActivityForResult(intent1, 1);
    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    MLog.iLog("debugggg", "onNavigationItem无错");
    return true;
  }
}
