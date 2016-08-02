package com.dongbaishun.baishitong.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongbaishun.baishitong.NetUrl.NetState;
import com.dongbaishun.baishitong.R;

/**
 * Created by DongBaishun on 2016/7/30.
 */
public class RefreshNetworkFragment extends Fragment {
  private ImageView img;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstaceState) {

    View view = inflater.inflate(R.layout.errorfragment, container, false);

    img = (ImageView) view.findViewById(R.id.img_refresh_network);
    img.setOnClickListener(new View.OnClickListener() {
      FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
      Fragment fragment = null;

      @Override
      public void onClick(View v) {
        if (NetState.Network_login_state == 0) {
          fragment = new LoginFragment();
          ft.replace(R.id.container, fragment);
          ft.commit();
        } else if (NetState.Network_login_state == 1 && NetState.Network_online_state == 1) {
          fragment = new OnlineFragment();
          ft.replace(R.id.container, fragment);
          ft.commit();
        }
      }
    });
    return view;
  }
}
