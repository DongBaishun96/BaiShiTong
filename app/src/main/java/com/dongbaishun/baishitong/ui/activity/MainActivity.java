package com.dongbaishun.baishitong.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_whole_main);
    //声明Toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //toolbar标题的文字需在setSupportActionBar之前，不然会无效
    setSupportActionBar(toolbar);

    //浮动按钮 & Snackbar
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Contact: baishun@dongbaishun.com", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
      }
    });

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

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
