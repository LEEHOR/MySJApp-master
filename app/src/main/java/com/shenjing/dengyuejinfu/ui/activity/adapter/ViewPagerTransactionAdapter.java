package com.shenjing.dengyuejinfu.ui.activity.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shenjing.dengyuejinfu.ui.fragment.ViewPagerTransaction;


/**
 * author : Leehor
 * date   : 2019/7/2919:29
 * version: 1.0
 * desc   :
 */
public class ViewPagerTransactionAdapter extends FragmentPagerAdapter {
    private String[] title = {"代还", "快捷", "扫码"};

    public ViewPagerTransactionAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ViewPagerTransaction.newInstance(position);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
