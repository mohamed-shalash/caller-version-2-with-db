package com.example.caller_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity{
TabLayout tab;
ViewPager2 pager;
fragmentstateadabter adabter;
DrawerLayout drawerlayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar =findViewById(R.id.Main_activity_toolbar);
        setSupportActionBar(toolbar);

        tab =findViewById(R.id.tabLayout_main_activity);
        pager =findViewById(R.id.viewPager2_main_activity);
        tab.addTab(tab.newTab().setText("names"));
        tab.addTab(tab.newTab().setText("calling"));

        FragmentManager fm =getSupportFragmentManager();
        adabter =new fragmentstateadabter(fm,getLifecycle());




        pager.setAdapter(adabter);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tab.selectTab(tab.getTabAt(position));
            }
        });

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public void clicktest(View view){
        drawerlayout.closeDrawer(GravityCompat.START);
    }

    public void take_photo(View view){
        Intent intent =new Intent(getBaseContext(),take_image.class);
        startActivity(intent);
    }
    public void aboutus(View view){
        Intent intent =new Intent(getBaseContext(),about_US.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigator,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_drower:
                drawerlayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.hide_drower:
                drawerlayout.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        finish();
        startActivity(getIntent());
    }


}