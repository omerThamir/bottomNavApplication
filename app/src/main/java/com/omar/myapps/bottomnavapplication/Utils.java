package com.omar.myapps.bottomnavapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String Day_Night_Mode;
    public static String Sound_On_Off;
    public static String Animation_On_Off;
    public static String ProgressBARMaxLimit;
    DataBaseHelper dataBaseHelper;

    static ArrayList<String> arrayList;
    static ArrayList<String> favOrNotArrayList;
    static Context mContext;

    public Utils(Context context) {
        mContext = context;
        arrayList = new ArrayList<>();
        favOrNotArrayList = new ArrayList<>();
    }

    public static String Openedlist;

    public static void getDateFrom(Context context, String TABLE_NAME) {

        Cursor cursor = new DataBaseHelper(context).getData(TABLE_NAME);
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "لم يتم اضافة اي عنصر الى القائمة المفضلة", Toast.LENGTH_LONG).show();
        }
        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0));// only for 1st column and add them to array list of prays
            favOrNotArrayList.add(cursor.getString(2));// only for 1st column and add them to array list of prays
        }
        cursor.close();
    }

    public static ArrayList<String> getArrayList() {
        return arrayList;
    }


    public static String getClickedPrayerName() {
        return clickedPrayerName;
    }

    public static void setClickedPrayerName(String clickedPrayerName) {
        Utils.clickedPrayerName = clickedPrayerName;
    }

    static String clickedPrayerName;

    public static String replaceToArabicNumbers(String originalNumber) {
        if (originalNumber != null) {
            return originalNumber.replaceAll("0", "٠")
                    .replaceAll("1", "١")
                    .replaceAll("2", "٢")
                    .replaceAll("3", "٣")
                    .replaceAll("4", "٤")
                    .replaceAll("5", "٥")
                    .replaceAll("6", "٦")
                    .replaceAll("7", "٧")
                    .replaceAll("8", "٨")
                    .replaceAll("9", "٩");
        }
        return "";
    }

    public static void showSnakeBar(View v, String message) {
        Snackbar snackbar = Snackbar.make(v, message, BaseTransientBottomBar.LENGTH_LONG);
        snackbar.show();
        View view = snackbar.getView();
        TextView snakBarTV = view.findViewById(R.id.snackbar_text);
        snakBarTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snakBarTV.setTextSize(25f);
    }

    public static int getLastUpdatedValueFromTable(String TableName, String ZkrName) {
        return new DataBaseHelper(mContext).getSpecificDataFrom(TableName, ZkrName);
    }

    public static List<String> getFAVorNotList() {
        return favOrNotArrayList;
    }
}
