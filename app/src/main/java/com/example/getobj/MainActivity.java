package com.example.getobj;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.getobj.FragmentPage.FragmentFocus;
import com.example.getobj.FragmentPage.FragmentRecycleList;
import com.example.getobj.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FragmentRecycleList fragmentRecycleList = new FragmentRecycleList();
    private FragmentFocus fragmentFocus = new FragmentFocus();
    private int now = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        Objects.requireNonNull(binding.tabLayout.getTabAt(1)).select();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Fragment recycle = new FragmentRecycleList();
//        fragmentTransaction.add(R.id.fragmentContainerView, recycle, "recycle");
//        fragmentTransaction.show(recycle);
//        fragmentTransaction.commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerView, fragmentRecycleList, "recycle");
        fragmentTransaction.add(R.id.fragmentContainerView, fragmentFocus, "focus");
        fragmentTransaction.hide(fragmentFocus);
        fragmentTransaction.commit();
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentChange(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void fragmentChange(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (now) {
            case 0:
                fragmentTransaction.hide(fragmentFocus);
                break;
            case 1:
                fragmentTransaction.hide(fragmentRecycleList);
                break;
        }
        switch (position) {
            case 0:
                fragmentTransaction.show(fragmentFocus);
                break;
            case 1:
                fragmentTransaction.show(fragmentRecycleList);
        }
        fragmentTransaction.commit();
        now = position;
    }
}