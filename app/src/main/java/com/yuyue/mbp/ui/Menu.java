package com.yuyue.mbp.ui;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.yuyue.mbp.R;
import com.yuyue.mbp.ui.base.AppBaseActivity;
import com.yuyue.mbp.ui.fragment.DataFragment;
import com.yuyue.mbp.ui.fragment.HomeFragment;
/**
 * Created by Renyx on 2018/6/7
 */
public class Menu extends AppBaseActivity implements View.OnClickListener{
    private FragmentManager fm;
    private TextView home;
    private TextView data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.buttom_menu);
        initview();
        setDefaultFragment();

    }

    private void initview(){
        home = (TextView) findViewById(R.id.tab_menu_home);
        home.setOnClickListener(this);
        home.setSelected(true);

        data = (TextView) findViewById(R.id.tab_menu_data);
        data.setOnClickListener(this);
    }
    private void setTabSelection(int posion) {
        setTabStyle(posion);
        FragmentTransaction ft = fm.beginTransaction();
        switch (posion) {
            case 0:
                ft.replace(R.id.tb,new HomeFragment());
                break;
            case 1:
                ft.replace(R.id.tb,new DataFragment());
                break;
        }
        ft.commit();

    }
    private void setTabStyle(int position) {
        switch (position) {
            case 0:
                home.setSelected(true);
                data.setSelected(false);
                break;
            case 1:
                home.setSelected(false);
                data.setSelected(true);
                break;
        }
    }
    private void setDefaultFragment() {
        fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.tb, new HomeFragment());
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_menu_home:
                setTabSelection(0);
                break;
            case R.id.tab_menu_data:
                setTabSelection(1);
                break;
        }
    }

    public void onMenuClick(View menu){
        Intent intent = new Intent();

        switch (menu.getId()) {
            case R.id.tab_menu_measure:
                intent.setClass(Menu.this,MeasureActivity.class);
                startActivity(intent);
                break;
        }
    }

}
