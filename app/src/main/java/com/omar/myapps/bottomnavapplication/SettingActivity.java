package com.omar.myapps.bottomnavapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    private TextView tv, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14;
    private Switch dayNightSwitch, resetAlLCounterSwitch, resetFavSwitch, soundSwitch, animationSwitch, resetEveningZKRSwitch, resetMorningZKRSwitch, resetDuaaQuranSwitch, resetAyatTasbihSwitch, reseAllahNameSwitch, resetAyatEsghhfarSwitch;
    private DataBaseHelper dbh;

    private ScrollView Setting_ScrollView;
    private Spinner maxProgressBarLimit;
    ArrayAdapter<String> spinnerProgressBarLimit;


    private void init() {

        Setting_ScrollView = findViewById(R.id.rootSettingScrollView);

        dayNightSwitch = findViewById(R.id.switch1);
        soundSwitch = findViewById(R.id.soundSwitch);
        animationSwitch = findViewById(R.id.animationSwitch);


        resetAlLCounterSwitch = findViewById(R.id.resetAllCountersSwitch);
        resetFavSwitch = findViewById(R.id.resetFavSwitch);
        reseAllahNameSwitch = findViewById(R.id.reseAllahNameSwitch);
        resetAyatEsghhfarSwitch = findViewById(R.id.resetAyatEsghhfarSwitch);
        resetAyatTasbihSwitch = findViewById(R.id.resetAyatTasbihSwitch);
        resetDuaaQuranSwitch = findViewById(R.id.resetDuaaQuranSwitch);
        resetMorningZKRSwitch = findViewById(R.id.resetMorningZKRSwitch);
        resetEveningZKRSwitch = findViewById(R.id.resetEveningZKRSwitch);


        maxProgressBarLimit = findViewById(R.id.maxLimitSpinner);

        tv = findViewById(R.id.setTV0);
        tv1 = findViewById(R.id.setTV1);
        tv2 = findViewById(R.id.setTV2);
        tv3 = findViewById(R.id.setTV3);
        tv4 = findViewById(R.id.setTV4);
        tv5 = findViewById(R.id.setTV5);
        tv6 = findViewById(R.id.setTV6);

        tv7 = findViewById(R.id.setTV7);
        tv8 = findViewById(R.id.setTV8);
        tv9 = findViewById(R.id.setTV9);
        tv10 = findViewById(R.id.setTV10);
        tv11 = findViewById(R.id.setTV11);
        tv12 = findViewById(R.id.setTV12);
        tv13 = findViewById(R.id.setTV13);
        tv14 = findViewById(R.id.setTV14);


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


        resetAlLCounterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    showYesNoDialogBuilder("ALL_TABLES", resetAlLCounterSwitch);
                }
            }
        });

        resetFavSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    showYesNoDialogBuilder(DataBaseHelper.FAVORITE_ZKR_TABLE, resetFavSwitch);
                }
            }
        });

        reseAllahNameSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    showYesNoDialogBuilder(DataBaseHelper.ALLAH_NAMES_TABLE, reseAllahNameSwitch);
                }
            }
        });

        resetAyatEsghhfarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    showYesNoDialogBuilder(DataBaseHelper.AYATS_ESGHFAR_TABLE, resetAyatEsghhfarSwitch);
                }
            }
        });

        resetAyatTasbihSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    showYesNoDialogBuilder(DataBaseHelper.AYATS_TASBIH_TABLE, resetAyatTasbihSwitch);
                }
            }
        });

        resetDuaaQuranSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    showYesNoDialogBuilder(DataBaseHelper.DUAA_FROM_QURAN_TABLE, resetDuaaQuranSwitch);
                }
            }
        });

        resetMorningZKRSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    showYesNoDialogBuilder(DataBaseHelper.MORNING_ZKR_TABLE, resetMorningZKRSwitch);
                }
            }
        });

        resetEveningZKRSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // show yes no  dialog the delete
                    showYesNoDialogBuilder(DataBaseHelper.EVENING_ZKR_TABLE, resetEveningZKRSwitch);
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


        maxProgressBarLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                dbh.updateSettingTable(DataBaseHelper.SETTING_Col5, selectedValue);
                Utils.ProgressBARMaxLimit = selectedValue;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showYesNoDialogBuilder(final String TABLE_NAME, final Switch Working_Switch) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String title_FOR_ALL_TABLES = "تصفير العدادات";
        String message_FOR_ALL_TABLES = "سيتم تصفيرجميع العدادات, هل تصفيرجميع العدادات؟";

        if (TABLE_NAME.equals("ALL_TABLES")) {

            builder.setTitle(title_FOR_ALL_TABLES);
            builder.setMessage(message_FOR_ALL_TABLES);
        } else {
            builder.setTitle("تصفير العداد");
            builder.setMessage("سيتم تصفير هذا العداد , هل تصفيرهذا ذلك؟");
        }
        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TABLE_NAME.equals("ALL_TABLES")) {
                    dbh.resetAllCountersToZero();
                } else dbh.resetCounterOFTABLEToZero(TABLE_NAME);
            }
        });

        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Working_Switch.setChecked(false);
            }
        });

        builder.show();
    }

    private void setNightMoodForSettingActivity() {
        Setting_ScrollView.setBackgroundColor(getResources().getColor(R.color.night_background));
        setAllTextColorAsWhite();
        setSpinnerDayNight("night");
    }


    private void setAllTextColorAsWhite() {
        tv.setTextColor(getResources().getColor(R.color.white_color));
        tv1.setTextColor(getResources().getColor(R.color.white_color));
        tv2.setTextColor(getResources().getColor(R.color.white_color));
        tv3.setTextColor(getResources().getColor(R.color.white_color));
        tv4.setTextColor(getResources().getColor(R.color.white_color));
        tv5.setTextColor(getResources().getColor(R.color.white_color));
        tv6.setTextColor(getResources().getColor(R.color.white_color));
        tv7.setTextColor(getResources().getColor(R.color.white_color));
        tv8.setTextColor(getResources().getColor(R.color.white_color));
        tv9.setTextColor(getResources().getColor(R.color.white_color));
        tv10.setTextColor(getResources().getColor(R.color.white_color));
        tv11.setTextColor(getResources().getColor(R.color.white_color));
        tv12.setTextColor(getResources().getColor(R.color.white_color));
        tv13.setTextColor(getResources().getColor(R.color.white_color));
        tv14.setTextColor(getResources().getColor(R.color.white_color));
    }

    private void setDayMoodForSettingActivity() {
        Setting_ScrollView.setBackgroundColor(getResources().getColor(R.color.day_background));
        setAllTextColorAsblack();
        setSpinnerDayNight("day");
    }

    private void setAllTextColorAsblack() {
        tv.setTextColor(getResources().getColor(R.color.night_background));
        tv1.setTextColor(getResources().getColor(R.color.night_background));
        tv2.setTextColor(getResources().getColor(R.color.night_background));
        tv3.setTextColor(getResources().getColor(R.color.night_background));
        tv4.setTextColor(getResources().getColor(R.color.night_background));
        tv5.setTextColor(getResources().getColor(R.color.night_background));
        tv6.setTextColor(getResources().getColor(R.color.night_background));
        tv7.setTextColor(getResources().getColor(R.color.night_background));
        tv8.setTextColor(getResources().getColor(R.color.night_background));
        tv9.setTextColor(getResources().getColor(R.color.night_background));
        tv10.setTextColor(getResources().getColor(R.color.night_background));
        tv11.setTextColor(getResources().getColor(R.color.night_background));
        tv12.setTextColor(getResources().getColor(R.color.night_background));
        tv13.setTextColor(getResources().getColor(R.color.night_background));
        tv14.setTextColor(getResources().getColor(R.color.night_background));

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

        if (Utils.ProgressBARMaxLimit.equals("10")) {
            maxProgressBarLimit.setSelection(0);
        } else if (Utils.ProgressBARMaxLimit.equals("100")) {
            maxProgressBarLimit.setSelection(1);
        } else if (Utils.ProgressBARMaxLimit.equals("1000")) {
            maxProgressBarLimit.setSelection(2);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        takeSettingsFromDataBase();
    }

    void setSpinnerDayNight(String which) {

        String[] arr = {"10", "100", "1000"};

        if (which == "day") {
            spinnerProgressBarLimit = new ArrayAdapter<String>(this, R.layout.spinner_item_day, arr);
        } else {
            spinnerProgressBarLimit = new ArrayAdapter<String>(this, R.layout.spinner_item_night, arr);
        }
        maxProgressBarLimit.setAdapter(spinnerProgressBarLimit);

    }
}
