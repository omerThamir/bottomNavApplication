package com.omar.myapps.bottomnavapplication.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.omar.myapps.bottomnavapplication.DataBaseHelper;
import com.omar.myapps.bottomnavapplication.MainActivity;
import com.omar.myapps.bottomnavapplication.R;
import com.omar.myapps.bottomnavapplication.SettingActivity;
import com.omar.myapps.bottomnavapplication.Utils;

public class DashboardFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final Button showmessageBtn = root.findViewById(R.id.button_dashboard);
        showmessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "done hhhhhhhhh", Toast.LENGTH_SHORT).show();
            }
        });

        final TextView allahNamesTv = root.findViewById(R.id.allahNamesTV);

        allahNamesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.Openedlist = "ALLAH_NAMES_TABLE";
                new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.ALLAH_NAMES_TABLE);
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

        root.findViewById(R.id.settingBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });


        return root;
    }
}
