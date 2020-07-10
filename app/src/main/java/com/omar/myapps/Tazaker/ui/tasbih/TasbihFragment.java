package com.omar.myapps.Tazaker.ui.tasbih;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omar.myapps.Tazaker.DataBaseHelper;
import com.omar.myapps.Tazaker.R;
import com.omar.myapps.Tazaker.Utils;


public class TasbihFragment extends Fragment {


    class MyListeners {
        private View.OnClickListener showTotalListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TableName = Utils.Openedlist;
                String zkrName = prayerName.getText().toString();
                String arabicNum = Utils.replaceToArabicNumbers(String.valueOf(Utils.getLastUpdatedValueFromTable(TableName, zkrName)));
                Utils.showSnakeBar(v, "المجموع : " + arabicNum);
            }
        };

        private View.OnClickListener TsbihListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // for animation when click
                if (Utils.Animation_On_Off.equals("on")) {
                    doAnimation();
                }

                // for sound when click
                if (Utils.Sound_On_Off.equals("on")) {
                    mediaPlayer.start();
                }

                String prayerNam = prayerName.getText().toString();
                int n = Utils.getLastUpdatedValueFromTable(Utils.Openedlist, prayerNam);
                int updatedValue = n + 1;

                int progressValue = progressBar.getProgress();
                if (progressValue == progressBar.getMax()) {
                    progressValue = 0;
                    progressBar.setProgress(progressValue + 1);
                    totalNumTextView.setText(Utils.replaceToArabicNumbers(String.valueOf(totalNumber += 1)));
                    new DataBaseHelper(v.getContext()).updateDataFor(
                            Utils.Openedlist, "Total_number", String.valueOf(updatedValue), prayerNam);
                } else {

                    progressBar.setProgress(progressValue + 1);
                    totalNumTextView.setText(Utils.replaceToArabicNumbers(String.valueOf(totalNumber += 1)));
                    new DataBaseHelper(v.getContext()).updateDataFor(
                            Utils.Openedlist, "Total_number", String.valueOf(updatedValue), prayerNam);
                }
            }
        };

        private View.OnClickListener Sound_on_OfListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), Utils.Sound_On_Off, Toast.LENGTH_SHORT).show();
                if (Utils.Sound_On_Off.equals("on")) {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col3, "on");
                    soundImageView.setImageResource(R.drawable.ic_sound_off_24);

                    Utils.Sound_On_Off = "off";
                    return;
                } else {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col3, "off");
                    soundImageView.setImageResource(R.drawable.ic_sound_on_24);
                    Utils.Sound_On_Off = "on";
                    initSoundSetting();
                    return;
                }
            }
        };

        private View.OnClickListener resetListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(0);
                totalNumber = 0;
                totalNumTextView.setText(Utils.replaceToArabicNumbers(String.valueOf(0)));
            }
        };

        private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                dbh.updateSettingTable(DataBaseHelper.SETTING_Col5, selectedValue);
                Utils.ProgressBARMaxLimit = selectedValue;

                progressBar.setProgress(0);
                progressBar.setMax(Integer.parseInt(selectedValue));
                totalNumber = 0;
                totalNumTextView.setText(Utils.replaceToArabicNumbers(String.valueOf(0)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        private View.OnClickListener onClickListenerAnimation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), Utils.Sound_On_Off, Toast.LENGTH_SHORT).show();
                if (Utils.Animation_On_Off.equals("on")) {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col4, "on");
                    animiImageView.setImageResource(R.drawable.ic_animi_off_24);
                    Utils.Animation_On_Off = "off";
                    return;
                } else {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col4, "off");
                    animiImageView.setImageResource(R.drawable.ic_animi_on_24);
                    Utils.Animation_On_Off = "on";
                    return;
                }
            }
        };
    }

    private DataBaseHelper dbh;
    private Spinner maxProgressBarLimit;
    private ArrayAdapter<String> spinnerProgressBarLimit;

    private ProgressBar progressBar;
    private TextView totalNumTextView, prayerName;
    int totalNumber = 0;
    MediaPlayer mediaPlayer;

    private ImageView clickedImageView, soundImageView, animiImageView, resetImageView, totalImageView;
    private ScrollView scrollView;


    private void init(View root) {

        soundImageView = root.findViewById(R.id.soundImageView);
        animiImageView = root.findViewById(R.id.animiImageView);
        resetImageView = root.findViewById(R.id.resetImageView);

        totalImageView = root.findViewById(R.id.totalImageView);

        progressBar = root.findViewById(R.id.progressBar);
        totalNumTextView = root.findViewById(R.id.totalNumbertextView);
        totalNumTextView.setText(Utils.replaceToArabicNumbers(String.valueOf(0)));

        prayerName = root.findViewById(R.id.prayerNametextView);
        clickedImageView = root.findViewById(R.id.clickingImage);

        maxProgressBarLimit = root.findViewById(R.id.maxLimitSpinner);

        initSoundSetting();
        initMAXlimitForProgressBar();

        if (Utils.getClickedPrayerName() != null) {
            prayerName.setText(Utils.getClickedPrayerName());
        } else {
            Utils.Openedlist = "ALLAH_NAMES_TABLE";
            new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.ALLAH_NAMES_TABLE);
            prayerName.setText(Utils.getArrayList().get(0));// get 1st element
        }

    }


    private void initMAXlimitForProgressBar() {
        if (Utils.ProgressBARMaxLimit.equals("10")) {
            progressBar.setMax(10);
        } else if (Utils.ProgressBARMaxLimit.equals("100")) {
            progressBar.setMax(100);
        } else if (Utils.ProgressBARMaxLimit.equals("1000")) {
            progressBar.setMax(1000);
        }
    }

    private void initSoundSetting() {
        if (Utils.Sound_On_Off.equals("on")) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.all_eyes_on_me);
            mediaPlayer.setVolume(0.1f, 0.1f);
        }
    }

    void scrollTextViewInsideScrollView(View root) {
        prayerName.setMovementMethod(new ScrollingMovementMethod());

        scrollView = root.findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                prayerName.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });

        prayerName.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                prayerName.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.tasbih_fragment, container, false);
        init(root);

        dbh = new DataBaseHelper(getContext());

        scrollTextViewInsideScrollView(root);

        takeSettingFromDataBase(root);

        clickedImageView.setOnClickListener(new MyListeners().TsbihListener);

        soundImageView.setOnClickListener(new MyListeners().Sound_on_OfListener);

        animiImageView.setOnClickListener(new MyListeners().onClickListenerAnimation);

        resetImageView.setOnClickListener(new MyListeners().resetListener);

        maxProgressBarLimit.setOnItemSelectedListener(new MyListeners().onItemSelectedListener);

        totalImageView.setOnClickListener(new MyListeners().showTotalListener);

        return root;
    }

    private void setDayMood(View root) {
        scrollView.setBackgroundColor(getResources().getColor(R.color.day_background));

        TextView prayerNametextView = root.findViewById(R.id.prayerNametextView);
        prayerNametextView.setTextColor(getResources().getColor(R.color.gray));
        prayerNametextView.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));

        prayerNametextView.setTextColor(getResources().getColor(R.color.gray));

        ImageView clickingImage = root.findViewById(R.id.clickingImage);
        clickingImage.setBackground(getResources().getDrawable(R.drawable.day_changeable_color));

        setSpinnerDayNight("day");

    }

    private void setNightMood(View root) {
        scrollView.setBackgroundColor(getResources().getColor(R.color.night_background));

        TextView prayerNametextView = root.findViewById(R.id.prayerNametextView);
        prayerNametextView.setTextColor(getResources().getColor(R.color.white_color));
        prayerNametextView.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));

        prayerNametextView.setTextColor(getResources().getColor(R.color.white_color));

        ImageView clickingImage = root.findViewById(R.id.clickingImage);
        clickingImage.setBackground(getResources().getDrawable(R.drawable.night_changeable_color));
        setSpinnerDayNight("night");
    }

    private void doAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in_out);
        clickedImageView.startAnimation(animation);
        progressBar.startAnimation(animation);
    }

    void takeSettingFromDataBase(View root) {
        if (Utils.Day_Night_Mode.equals("day")) {
            setDayMood(root);
        } else {
            setNightMood(root);
        }

        if (Utils.Sound_On_Off.equals("on")) {
            soundImageView.setImageResource(R.drawable.ic_sound_on_24);
        } else {
            soundImageView.setImageResource(R.drawable.ic_sound_off_24);
        }

        if (Utils.Animation_On_Off.equals("on")) {
            animiImageView.setImageResource(R.drawable.ic_animi_on_24);
        } else {
            animiImageView.setImageResource(R.drawable.ic_animi_off_24);
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

    private void setSpinnerDayNight(String which) {

        String[] arr = {"10", "100", "1000"};

        if (which == "day") {
            spinnerProgressBarLimit = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_day, arr);
        } else {
            spinnerProgressBarLimit = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_night, arr);
        }
        maxProgressBarLimit.setAdapter(spinnerProgressBarLimit);

    }
}
