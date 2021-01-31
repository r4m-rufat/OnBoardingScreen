package com.kivitool.onboardingscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private RelativeLayout skipLayout;
    private IntroViewPagerAdapter adapter;
    private TabLayout tabIndicator;
    private Button next, getStarted;
    private int position = 0;
    private SharedPreferences.Editor editor;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("intro_screen", null) != null){

            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();

        }else {

            setupIntroScreen();

        }

    }

    private void loadLastScreen() {

        next.setVisibility(View.INVISIBLE);
        getStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        getStarted.setAnimation(animation);

    }

    private void setupIntroScreen(){

        next = findViewById(R.id.nextButton);
        getStarted = findViewById(R.id.getStartedButton);
        tabIndicator = findViewById(R.id.tabLayout);
        skipLayout = findViewById(R.id.skipLayout);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.get_started_button);

        List<ScreenItems> list = new ArrayList<>();
        list.add(new ScreenItems("Home page gives you more informations which contain current, weekly(8 days), hourly(2 days), windly(3 hourly/5 days), sunrise and sunset with animation informations.", "All In One Page", R.drawable.main_page));
        list.add(new ScreenItems("Hourly page and other deatils page(wind, weekly) give you lots of detailed informations. In hourly page, you learn weather forecast for the next 2 days(hour by hour).", "Hourly Page", R.drawable.hourly_page));
        list.add(new ScreenItems("There are so many options such as My location, Settings and others. As you can see Dark Mode is available. They are so comfortable to use.", "More Skills Wait You", R.drawable.drawer));
        list.add(new ScreenItems("Settings page gives you more chance of choice such as foreground notification, wind and temprature units. Specially 8 languages exists in program.", "Settings", R.drawable.settings));
        list.add(new ScreenItems("Add new location page is available for you to search your cities, region, country and also the locations which you searched write your cities database automatically.", "Add New Location", R.drawable.add_location));
        list.add(new ScreenItems("Background page gives you 5 backgrounds choice. As you can see you have 5 choices. Animated, picture and 3 color backgrounds are available.", "Enjoy Backgrounds", R.drawable.intro_backgrounds));
        list.add(new ScreenItems("Daily and current widgets exists. The daily widget is updated every 4 hours and current widget is updated every 2 hours automatically. These widgets won't cause significant problems for your battery life.", "Widgets", R.drawable.widgets));

        screenPager = findViewById(R.id.onBoardingViewPager);
        adapter = new IntroViewPagerAdapter(this, list);
        screenPager.setAdapter(adapter);

        tabIndicator.setupWithViewPager(screenPager);

        skipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                screenPager.setCurrentItem(list.size()-1);
                loadLastScreen();
                skipLayout.setVisibility(View.INVISIBLE);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();

                if (position < list.size()){

                    position++;
                    screenPager.setCurrentItem(position);

                }

                if (position == list.size()-1){

                    // TODO show the Get Started button and hide the indicator
                    skipLayout.setVisibility(View.INVISIBLE);
                    loadLastScreen();

                }

            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == list.size()-1){

                    skipLayout.setVisibility(View.INVISIBLE);
                    loadLastScreen();

                }else {

                    skipLayout.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("intro_screen", "get_started");
                editor.apply();
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();

            }
        });


    }
}