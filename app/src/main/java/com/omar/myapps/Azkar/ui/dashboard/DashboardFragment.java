package com.omar.myapps.Azkar.ui.dashboard;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.omar.myapps.Azkar.DataBaseHelper;
import com.omar.myapps.Azkar.MainActivity;
import com.omar.myapps.Azkar.R;
import com.omar.myapps.Azkar.SettingActivity;
import com.omar.myapps.Azkar.Utils;

public class DashboardFragment extends Fragment {

    private void initToolBar(View root) {
        Toolbar toolbar = root.findViewById(R.id.toolbar);

        //placing toolbar in place of actionbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true); // to tell main activity that this frag has its own menu
    }
    int i=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initToolBar(root);

        setDayNightMoodForThisFragment(root);
        ListView listView = root.findViewById(R.id.listView);

        String[] items = {"المفضلة", "أسماء الله الحسنى", "أذكار الصباح", "أدعية من القرآن الكريم", "آيات التسبيح في القرآن الكريم", "آيات الاستغفار في القرآن الكريم", "أذكار المساء", "دعاء ختم القرآن الكريم", "أذكار الخلاء", "دعاء للميّت", "أذكار الطعام والشراب", "أذكار الوضوء", "أذكار المسجد"};
        ArrayAdapter<String> myadapter;
        if (Utils.Day_Night_Mode.equals("day")) {
            myadapter = new ArrayAdapter<String>(getContext(), R.layout.day_listview_row, items);
            listView.setDivider(getResources().getDrawable(R.color.white));
            listView.setDividerHeight(20);
        } else {
            myadapter = new ArrayAdapter<String>(getContext(), R.layout.night_listview_row, items);
            listView.setDivider(getResources().getDrawable(R.color.night_background));
            listView.setDividerHeight(20);
        }

        listView.setAdapter(myadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Utils.Openedlist = "FAVORITE_ZKR_TABLE";
                    new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.FAVORITE_ZKR_TABLE);
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 1) {
                    Utils.Openedlist = "ALLAH_NAMES_TABLE";
                    new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.ALLAH_NAMES_TABLE);
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 2) {
                    Utils.Openedlist = "MORNING_ZKR_TABLE";
                    new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.MORNING_ZKR_TABLE);
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 3) {
                    Utils.Openedlist = "DUAA_FROM_QURAN_TABLE";
                    new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.DUAA_FROM_QURAN_TABLE);
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 4) {
                    Utils.Openedlist = "AYATS_TASBIH_TABLE";
                    new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.AYATS_TASBIH_TABLE);
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 5) {
                    Utils.Openedlist = "AYATS_ESGHFAR_TABLE";
                    new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.AYATS_ESGHFAR_TABLE);
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 6) {
                    Utils.Openedlist = "EVENING_ZKR_TABLE";
                    new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.EVENING_ZKR_TABLE);
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 7) {
                    Utils.Openedlist = "Duaa_Khhatm_TABLE";
                    new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.Duaa_Khhatm_TABLE);
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 8) {
                    Utils.Openedlist = "WC_ZKR_TABLE";
                    new Utils(getContext()).Add_WC_ZkrArrayList();
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 9) {
                    Utils.Openedlist = "Duaa_Almait_TABLE";
                    new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.Duaa_Almait_TABLE);
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 10) {
                    Utils.Openedlist = "FOOD_ZKR_TABLE";
                    new Utils(getContext()).Add_Food_ZkrArrayList();
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 11) {
                    Utils.Openedlist = "WDHOO_ZKR_TABLE";
                    new Utils(getContext()).Add_Wdhoo_ZkrArrayList();
                    ((MainActivity) getActivity()).switchToFragment2();
                } else if (position == 12) {
                    Utils.Openedlist = "MASJED_ZKR_TABLE";
                    new Utils(getContext()).Add_Masjed_ZkrArrayList();
                    ((MainActivity) getActivity()).switchToFragment2();
                }
            }
        });



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
        if (Utils.Day_Night_Mode.equals("day")) {
            inflater.inflate(R.menu.day_menu_items, menu);
        } else inflater.inflate(R.menu.night_menu_items, menu);
        //  super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.setting_itemID:
                startActivity(new Intent(getContext(), SettingActivity.class));

                return true;

            case R.id.about_itemID:
                showAboutAppDialog(getString(R.string.about_app));
                return true;

            case R.id.appsID:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=omar+thamer")));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAboutAppDialog(String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Azkar-اذكار");
        builder.setMessage(message);
        builder.setIcon(getContext().getDrawable(R.mipmap.ic_launcher_round));

        builder.setPositiveButton("رجوع", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.show();
    }

    private void setNightMood(View root) {
        LinearLayout dashFragmentlayout = root.findViewById(R.id.dashFragmentlayout);
        dashFragmentlayout.setBackgroundColor(getResources().getColor(R.color.night_background));

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.night_background));
        toolbar.setPopupTheme(R.style.NightPopupTheme);
        // and change all text views in dashboard view to gray or black
    }

    private void setDayMood(View root) {
        LinearLayout dashFragmentlayout = root.findViewById(R.id.dashFragmentlayout);
        dashFragmentlayout.setBackgroundColor(getResources().getColor(R.color.white));

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.DayPopupTheme);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));

    }
}
