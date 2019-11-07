package com.shenjing.dengyuejinfu.ui.activity.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shenjing.dengyuejinfu.App;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.ui.fragment.ViewPagerCustomerDetail;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/7/2919:29
 * version: 1.0
 * desc   :
 */
public class ViewPagerCustomerDetailAdapter extends FragmentPagerAdapter {
    private String[] title = App.getAppContext().getResources().getStringArray(R.array.customer_detail_title);
    int customer_id;
    private List<Integer> direct;
    private List<Integer> inDirect;

    public ViewPagerCustomerDetailAdapter(@NonNull FragmentManager fm, int behavior,int customerId,List<Integer> directs,List<Integer> inDirects) {
        super(fm, behavior);
        this.customer_id=customerId;
        this.direct=directs;
        this.inDirect=inDirects;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ViewPagerCustomerDetail.newInstance(customer_id,position,direct,inDirect);
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
