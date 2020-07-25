package com.omar.myapps.Azkar;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.omar.myapps.Azkar.ui.list.ListFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity implements ListFragment.OnDataPass {


    String notification_method_was_called;
    DataBaseHelper dataBaseHelper;
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        getSettingFromDBAndGiveItToUtils();

        //start pushing the notification just for the first run
        if (notification_method_was_called.equals("false")) {
            Utils.startAlarm(getApplicationContext());
            dataBaseHelper.updateSettingTable(DataBaseHelper.SETTING_Col7,"true");
        }

        if (Utils.Day_Night_Mode.equals("day")) {
            setTheme(R.style.DayAppTheme);
        } else {
            setTheme(R.style.NightAppTheme);
        }

        setContentView(R.layout.activity_main);

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


    private void getSettingFromDBAndGiveItToUtils() {

        Cursor settingCur = dataBaseHelper.getData(DataBaseHelper.SETTING_TABLE);
        if (settingCur.getCount() == 0) {
            Toast.makeText(this, "there is no data in setting table to show ", Toast.LENGTH_SHORT).show();
        }
        if (settingCur.move(1)) {// getting first record
            Utils.Day_Night_Mode = (settingCur.getString(1));// only for second column(day night)
            Utils.Sound_On_Off = (settingCur.getString(2));// only for 3rd column(sound)
            Utils.Animation_On_Off = (settingCur.getString(3));// only for 4th column(ani,ation)
            Utils.ProgressBARMaxLimit = (settingCur.getString(4));// only for 5th column(max limit)
            Utils.Notification_On_Off = (settingCur.getString(5));// only for 5th column(notification)
            notification_method_was_called = settingCur.getString(6);
        }
        settingCur.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSettingFromDBAndGiveItToUtils();
        if (Utils.Day_Night_Mode.equals("day")) {
            navView.setBackground(getResources().getDrawable(R.color.day_background));
        } else {
            navView.setBackground(getResources().getDrawable(R.color.night_background));
        }
    }

}


