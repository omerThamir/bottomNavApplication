package com.omar.myapps.Tazaker;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.omar.myapps.Tazaker.ui.list.ListFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ListFragment.OnDataPass {

    DataBaseHelper dataBaseHelper;
    BottomNavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        getSettingFromDBAndGiveITToUtils();


        if (Utils.Day_Night_Mode.equals("day")) {
            setTheme(R.style.DayAppTheme);
        } else {
            setTheme(R.style.NightAppTheme);
        }

        setContentView(R.layout.activity_main);


        // to set notification
        myAlarm();
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //  AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.navigation_list, R.id.navigation_dashboard, R.id.navigation_tasbih)
        //       .build();

        //  navView.setBackground(getResources().getDrawable(R.color.night_bottomNavBarColor));
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //   NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void myAlarm() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 20); // 20 = 8 am
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);// push notification after one day


        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void switchToFragment2() {
        View view = navView.findViewById(R.id.navigation_list);
        view.performClick();
    }

    public void switchToFragment3() {
        View view = navView.findViewById(R.id.navigation_tasbih);
        view.performClick();
    }


    @Override
    public void onDataPass(String data) {
        Utils.setClickedPrayerName(data);

    }


    private void getSettingFromDBAndGiveITToUtils() {

        /**
         * public boolean updateDatabseSettingTable(String ColumnToBeUpdated, Boolean newValue)
         *    dbh.updateDatabseSettingTable("1",false);
         *    id always  =1 for the id record becouse we have only one record
         *    the we but true for night mood, and false for day mood
         *
         *   dbh.updateDatabseSettingTable(dbh.SettingCol2, true);
         */

        // get the data base data
        Cursor settingCur = dataBaseHelper.getData(DataBaseHelper.SETTING_TABLE);
        if (settingCur.getCount() == 0) {
            Toast.makeText(this, "there is no data in setting table to show ", Toast.LENGTH_SHORT).show();
        }
        if (settingCur.move(1)) {// getting  first record

            Utils.Day_Night_Mode = (settingCur.getString(1));// only for second column(day night)
            Utils.Sound_On_Off = (settingCur.getString(2));// only for 3rd column(sound)
            Utils.Animation_On_Off = (settingCur.getString(3));// only for 4th column(ani,ation)
            Utils.ProgressBARMaxLimit = (settingCur.getString(4));// only for 5th column(max limit)
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSettingFromDBAndGiveITToUtils();
        if (Utils.Day_Night_Mode.equals("day")) {
            navView.setBackground(getResources().getDrawable(R.color.day_background));
        } else {
            navView.setBackground(getResources().getDrawable(R.color.night_background));
        }
    }


    void recreateMainActivity() {
        getParent().recreate();
    }
}



