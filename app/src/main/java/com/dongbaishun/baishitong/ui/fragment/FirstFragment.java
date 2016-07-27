package com.dongbaishun.baishitong.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongbaishun.baishitong.R;

/**
 * Created by DongBaishun on 2016/7/26.
 */
public class FirstFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstaceState) {
    return inflater.inflate(R.layout.firstfragment, null);
  }
}
