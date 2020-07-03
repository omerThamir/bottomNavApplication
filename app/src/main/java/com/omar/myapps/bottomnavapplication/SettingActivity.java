package com.omar.myapps.bottomnavapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    private TextView tv, tv1, tv2, tv3, tv4, tv5, tv6;
    private Switch dayNightSwitch, resetCountersSwitch, soundSwitch, animationSwitch;
    private DataBaseHelper dbh;

    private ScrollView Setting_ScrollView;

    private void init() {

        Setting_ScrollView= findViewById(R.id.rootSettingScrollView);

        dayNightSwitch = findViewById(R.id.switch1);
        soundSwitch = findViewById(R.id.soundSwitch);
        animationSwitch = findViewById(R.id.animationSwitch);
        resetCountersSwitch = findViewById(R.id.resetSwitch);

        tv = findViewById(R.id.setTV0);
        tv1 = findViewById(R.id.setTV1);
        tv2 = findViewById(R.id.setTV2);
        tv3 = findViewById(R.id.setTV3);
        tv4 = findViewById(R.id.setTV4);
        tv5 = findViewById(R.id.setTV5);
        tv6 = findViewById(R.id.setTV6);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
        dbh = new DataBaseHelper(getApplicationContext());
        takeSettingsFromDataBase();

        //  set night mood changes, update database and set util methods
        dayNightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setNightMoodForSettingActivity();
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col2, "night");
                    Utils.Day_Night_Mode = "night"; //true
                } else {
                    setDayMoodForSettingActivity();
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col2, "day");
                    Utils.Day_Night_Mode = "day"; //false
                }
            }
        });

        // fro updating database new value and make changes for ability to add new

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col3, "on");
                    Utils.Sound_On_Off = "on";
                } else {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col3, "off");
                    Utils.Sound_On_Off = "off";
                }
            }
        });

        animationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col4, "on");
                    Utils.Animation_On_Off = "on";
                } else {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col4, "of");
                    Utils.Animation_On_Off = "off";
                }
            }
        });


        resetCountersSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // dbh.resetNumber_OF_prayers_T0_Zero();
                }
            }
        });

        findViewById(R.id.backSetringBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                finish();
            }
        });

    }


    private void setNightMoodForSettingActivity() {
        Setting_ScrollView.setBackgroundColor(getResources().getColor(R.color.night_background));
        setAllTextColorAsWhite();
    }


    private void setAllTextColorAsWhite() {
        tv.setTextColor(getResources().getColor(R.color.gray));
        tv1.setTextColor(getResources().getColor(R.color.gray));
        tv2.setTextColor(getResources().getColor(R.color.gray));
        tv3.setTextColor(getResources().getColor(R.color.gray));
        tv4.setTextColor(getResources().getColor(R.color.gray));
        tv5.setTextColor(getResources().getColor(R.color.gray));
        tv6.setTextColor(getResources().getColor(R.color.gray));
    }

    private void setDayMoodForSettingActivity() {
        Setting_ScrollView.setBackgroundColor(getResources().getColor(R.color.day_background));
        setAllTextColorAsblack();
    }

    private void setAllTextColorAsblack() {
        tv.setTextColor(getResources().getColor(R.color.night_background));
        tv1.setTextColor(getResources().getColor(R.color.night_background));
        tv2.setTextColor(getResources().getColor(R.color.night_background));
        tv3.setTextColor(getResources().getColor(R.color.night_background));
        tv4.setTextColor(getResources().getColor(R.color.night_background));
        tv5.setTextColor(getResources().getColor(R.color.night_background));
        tv6.setTextColor(getResources().getColor(R.color.night_background));

        //for description setting category

    }


    void takeSettingsFromDataBase() {

        String n = Utils.Day_Night_Mode;
        if (n.equals("day")) {
            dayNightSwitch.setChecked(false);
            setDayMoodForSettingActivity();
        } else {
            dayNightSwitch.setChecked(true);
            setNightMoodForSettingActivity();
        }


        // set soundOn/off switch
        if (Utils.Sound_On_Off.equals("on")) {
            soundSwitch.setChecked(true);

        } else {
            soundSwitch.setChecked(false);

        }

        if (Utils.Animation_On_Off.equals("on")) {
            animationSwitch.setChecked(true);

        } else {
            animationSwitch.setChecked(false);

        }

        // set sound spinner selected item
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        takeSettingsFromDataBase();
    }
}
