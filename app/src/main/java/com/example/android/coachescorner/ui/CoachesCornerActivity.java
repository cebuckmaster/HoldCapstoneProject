package com.example.android.coachescorner.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.coachescorner.R;
import com.example.android.coachescorner.common.Utils;
import com.example.android.coachescorner.data.CoachesCornerDBContract;
import com.example.android.coachescorner.data.Player;
import com.example.android.coachescorner.ui.dialogs.AddPlayerDialogFragment;
import com.example.android.coachescorner.ui.fragments.CoachesCornerPlayerFragment;
import com.example.android.coachescorner.ui.fragments.CoachesCornerScheduleFragment;

import java.util.ArrayList;

public class CoachesCornerActivity extends AppCompatActivity
        implements AddPlayerDialogFragment.AddPlayerDialogListener{

    CoachesCornerPlayerFragment mPlayerFragment;
    CoachesCornerScheduleFragment mScheduleFragment;
    CoachesCornerViewPagerAdapter mPagerAdapter;

    ViewPager mViewPager;
    TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaches_corner);

        Log.d("CoachCornerActivity", "in OnCreate");

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            mPlayerFragment = (CoachesCornerPlayerFragment) fragmentManager.getFragment(savedInstanceState, CoachesCornerPlayerFragment.class.getName());
            mScheduleFragment = (CoachesCornerScheduleFragment) fragmentManager.getFragment(savedInstanceState, CoachesCornerScheduleFragment.class.getName());
            FragmentTransaction remove = fragmentManager.beginTransaction();
            remove.remove(mPlayerFragment);
            remove.remove(mScheduleFragment);
            if (!remove.isEmpty()) {
                remove.commit();
                fragmentManager.executePendingTransactions();
            }
        } else {
            mPlayerFragment = new CoachesCornerPlayerFragment();
            mScheduleFragment = new CoachesCornerScheduleFragment();
        }

        mViewPager = (ViewPager) findViewById(R.id.coachescornerpager);

        mPagerAdapter = new CoachesCornerViewPagerAdapter(getSupportFragmentManager());

        mPagerAdapter.addFragment(mScheduleFragment, "Schedule");
        mPagerAdapter.addFragment(mPlayerFragment, "Players");
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.coachescornertabs);
        mTabLayout.setupWithViewPager(mViewPager);


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        Log.d("CoachesCornerActivity", "in OnStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        if (mViewPager != null) {
////            mViewPager.getAdapter().notifyDataSetChanged();
////
////            Log.d("CoachesCornerActivity", "view pager not null");
////        }
//        Log.d("CoachesCornerActivity", "in OnResume");
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("CoachCornerActivity", "in OnSaveInstanceState ");
        getSupportFragmentManager().putFragment(outState, CoachesCornerPlayerFragment.class.getName(), mPlayerFragment);
        getSupportFragmentManager().putFragment(outState, CoachesCornerScheduleFragment.class.getName(), mScheduleFragment);

//        outState.putParcelableArrayList(PLAYERPARCELABLEKEY, mPlayers);
    }

    //This inflates the Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.coachescorner_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cc_menu_settings:
                Intent intentShowSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentShowSettings);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishAddPlayerDialog(String playerName, String playerNum) {

        if (playerName != null && !playerName.isEmpty()) {
            Player player = new Player();
            player.setPlayerName(playerName);
            player.setPlayerNumber(playerNum);
            boolean loadedSuccessfully = Utils.addPlayerToDatabase(this, player);
            if (loadedSuccessfully) {
                mViewPager.getAdapter().notifyDataSetChanged();
                Toast.makeText(this, "Player added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add player to player list", Toast.LENGTH_SHORT).show();
            }

        }

    }

}
