package com.example.android.coachescorner.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cebuc on 4/16/2018.
 */

public class CoachesCornerViewPagerAdapter extends FragmentStatePagerAdapter {
//public class CoachesCornerViewPagerAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public CoachesCornerViewPagerAdapter(FragmentManager fragmentManager) {

        super(fragmentManager);

//        mFragmentList = new ArrayList<Fragment>();
    }

    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//
//        //Object ret = super.instantiateItem(container, position);
//        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//
////        mFragmentList.add(fragment);
//
//
//        //mFragmentList.set(position, (Fragment) ret);
//
//        return mFragmentList.get(position);
//
//    }


    @Override
    public int getItemPosition(@NonNull Object object) {

        return POSITION_NONE;
    }


    @Override
    public int getCount() {

        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mFragmentTitleList.get(position);
    }

    public Fragment getFragment(int position) {
        Fragment fragment = mFragmentList.get(position);
        if (fragment != null) {
            return fragment;
        }

        return null;

    }

}
