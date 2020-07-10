package com.omar.myapps.Tazaker.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.omar.myapps.Tazaker.DataBaseHelper;
import com.omar.myapps.Tazaker.MainActivity;
import com.omar.myapps.Tazaker.R;
import com.omar.myapps.Tazaker.SettingActivity;
import com.omar.myapps.Tazaker.Utils;

public class DashboardFragment extends Fragment {

    private void initToolBar(View root) {
        Toolbar toolbar = root.findViewById(R.id.toolbar);

        //placing toolbar in place of actionbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true); // to tell main activity that this frag has its own menu
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initToolBar(root);


        final TextView allahNamesTv = root.findViewById(R.id.allahNamesTV);

        allahNamesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.Openedlist = "ALLAH_NAMES_TABLE";
                new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.ALLAH_NAMES_TABLE);
                ((MainActivity) getActivity()).switchToFragment2();
            }
        });

        root.findViewById(R.id.TasbihFromQuranTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.Openedlist = "AYATS_TASBIH_TABLE";
                new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.AYATS_TASBIH_TABLE);
                ((MainActivity) getActivity()).switchToFragment2();
            }
        });


        root.findViewById(R.id.esghfarFromQuranTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.Openedlist = "AYATS_ESGHFAR_TABLE";
                new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.AYATS_ESGHFAR_TABLE);
                ((MainActivity) getActivity()).switchToFragment2();
            }
        });


        root.findViewById(R.id.duaaFromQuranTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.Openedlist = "DUAA_FROM_QURAN_TABLE";
                new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.DUAA_FROM_QURAN_TABLE);
                ((MainActivity) getActivity()).switchToFragment2();
            }
        });

        root.findViewById(R.id.morning_zkr_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.Openedlist = "MORNING_ZKR_TABLE";
                new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.MORNING_ZKR_TABLE);
                ((MainActivity) getActivity()).switchToFragment2();
            }
        });

        root.findViewById(R.id.eveningZKRtv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.Openedlist = "EVENING_ZKR_TABLE";
                new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.EVENING_ZKR_TABLE);
                ((MainActivity) getActivity()).switchToFragment2();
            }
        });

        root.findViewById(R.id.favTextV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.Openedlist = "FAVORITE_ZKR_TABLE";
                new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.FAVORITE_ZKR_TABLE);
                ((MainActivity) getActivity()).switchToFragment2();
            }
        });

        setDayNightMoodForThisFragment(root);

        return root;
    }

    private void setDayNightMoodForThisFragment(View root) {
        if (Utils.Day_Night_Mode != null) {
            if (Utils.Day_Night_Mode.equals("day")) {
                setDayMood(root);
            } else {
                setNightMood(root);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_items, menu);
        //  super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.setting_itemID:
                startActivity(new Intent(getContext(), SettingActivity.class));
                return true;
            case R.id.about_itemID:

                return true;

            case R.id.appsID:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=omar+thamer")));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setNightMood(View root) {
        ScrollView dash_frag_scrollView = root.findViewById(R.id.dashFrag_SrollVIEW);
        dash_frag_scrollView.setBackgroundColor(getResources().getColor(R.color.night_background));

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.night_background));
        toolbar.setPopupTheme(R.style.NightPopupTheme);
        // and change all text views in dashboard view to gray or black

        TextView favTextV = root.findViewById(R.id.favTextV);
        favTextV.setTextColor(getResources().getColor(R.color.white_color));
        favTextV.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));

        TextView eveningZKRtv = root.findViewById(R.id.eveningZKRtv);
        eveningZKRtv.setTextColor(getResources().getColor(R.color.white_color));
        eveningZKRtv.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));

        TextView morning_zkr_tv = root.findViewById(R.id.morning_zkr_tv);
        morning_zkr_tv.setTextColor(getResources().getColor(R.color.white_color));
        morning_zkr_tv.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));

        TextView tasbhihFromQuranTV = root.findViewById(R.id.TasbihFromQuranTV);
        tasbhihFromQuranTV.setTextColor(getResources().getColor(R.color.white_color));
        tasbhihFromQuranTV.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));

        TextView esghfarFromQuranTV = root.findViewById(R.id.esghfarFromQuranTV);
        esghfarFromQuranTV.setTextColor(getResources().getColor(R.color.white_color));
        esghfarFromQuranTV.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));


        TextView duaaFromQuranTV = root.findViewById(R.id.duaaFromQuranTV);
        duaaFromQuranTV.setTextColor(getResources().getColor(R.color.white_color));
        duaaFromQuranTV.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));

        TextView allahNamesTV = root.findViewById(R.id.allahNamesTV);
        allahNamesTV.setTextColor(getResources().getColor(R.color.white_color));
        allahNamesTV.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));
    }

    private void setDayMood(View root) {
        ScrollView dash_frag_scrollView = root.findViewById(R.id.dashFrag_SrollVIEW);
        dash_frag_scrollView.setBackgroundColor(getResources().getColor(R.color.day_background));

      Toolbar toolbar=  root.findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.DayPopupTheme);
        toolbar.setBackgroundColor(getResources().getColor(R.color.day_toolBarColor));


        TextView favTextV = root.findViewById(R.id.favTextV);
        favTextV.setTextColor(getResources().getColor(R.color.gray));
        favTextV.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));

        TextView eveningZKRtv = root.findViewById(R.id.eveningZKRtv);
        eveningZKRtv.setTextColor(getResources().getColor(R.color.gray));
        eveningZKRtv.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));

        TextView tasbhihFromQuranTV = root.findViewById(R.id.TasbihFromQuranTV);
        tasbhihFromQuranTV.setTextColor(getResources().getColor(R.color.gray));
        tasbhihFromQuranTV.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));

        TextView esghfarFromQuranTV = root.findViewById(R.id.esghfarFromQuranTV);
        esghfarFromQuranTV.setTextColor(getResources().getColor(R.color.gray));
        esghfarFromQuranTV.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));

        TextView morning_zkr_tv = root.findViewById(R.id.morning_zkr_tv);
        morning_zkr_tv.setTextColor(getResources().getColor(R.color.gray));
        eveningZKRtv.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));

        TextView duaaFromQuranTV = root.findViewById(R.id.duaaFromQuranTV);
        duaaFromQuranTV.setTextColor(getResources().getColor(R.color.gray));
        eveningZKRtv.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));

        TextView allahNamesTV = root.findViewById(R.id.allahNamesTV);
        allahNamesTV.setTextColor(getResources().getColor(R.color.gray));
        eveningZKRtv.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));

    }
}
