package com.omar.myapps.bottomnavapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.omar.myapps.bottomnavapplication.ui.list.ListFragment;
import com.omar.myapps.bottomnavapplication.ui.tasbih.TasbihFragment;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements ListFragment.OnDataPass {

    DataBaseHelper dataBaseHelper;
    BottomNavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        getSettingFromDBAndGiveITToUtils();

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //  AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.navigation_list, R.id.navigation_dashboard, R.id.navigation_tasbih)
        //       .build();

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
            navView.setBackground(getResources().getDrawable(R.drawable.day_bg_but_nav_view));
        }else {
            navView.setBackground(getResources().getDrawable(R.drawable.night_bg_but_nav_view));
        }
    }
}



