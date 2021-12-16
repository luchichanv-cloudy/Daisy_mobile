package com.example.daisy_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.daisy_mobile.ui.kitchenorder.Kitchenorder;
import com.example.daisy_mobile.ui.kitchensetting.Kitchensetting;
import com.example.daisy_mobile.ui.menu.Kitchenmenu;
import com.example.daisy_mobile.ui.sales.Kitchensales;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class kitchen_main extends AppCompatActivity {
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_main);
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        toolbar.setTitle("Menu");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_menu:
                    toolbar.setTitle("Menu");
                    fragment = new Kitchenmenu();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_order:
                    toolbar.setTitle("Order");
                    fragment = new Kitchenorder();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_sales:
                    toolbar.setTitle("Sales");
                    fragment = new Kitchensales();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_setting:
                    toolbar.setTitle("Setting");
                    fragment = new Kitchensetting();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}