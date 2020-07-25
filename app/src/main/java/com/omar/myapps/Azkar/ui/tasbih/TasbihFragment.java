package com.omar.myapps.Azkar.ui.tasbih;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omar.myapps.Azkar.DataBaseHelper;
import com.omar.myapps.Azkar.R;
import com.omar.myapps.Azkar.Utils;


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
                if (Utils.Sound_On_Off.equals("on")) {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col3, "on");
                    soundImageView.setImageResource(R.drawable.ic_sound_off_24);
                    Toast.makeText(getContext(), "تم تعطيل الصوت", Toast.LENGTH_LONG).show();

                    Utils.Sound_On_Off = "off";
                    return;
                } else {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col3, "off");
                    soundImageView.setImageResource(R.drawable.ic_sound_on_24);
                    Utils.Sound_On_Off = "on";
                    initSoundSetting();
                    Toast.makeText(getContext(), "تم تفعيل الصوت", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(), "تم اعادة ضبط العداد الى الصفر", Toast.LENGTH_SHORT).show();
            }
        };

        private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        private View.OnClickListener onClickListenerAnimation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.Animation_On_Off.equals("on")) {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col4, "on");
                    animiImageView.setImageResource(R.drawable.ic_animi_off_24);
                    Utils.Animation_On_Off = "off";
                    Toast.makeText(getContext(), "تم تعطيل الرسوم المتحركة", Toast.LENGTH_SHORT).show();

                    return;
                } else {
                    dbh.updateSettingTable(DataBaseHelper.SETTING_Col4, "off");
                    animiImageView.setImageResource(R.drawable.ic_animi_on_24);
                    Utils.Animation_On_Off = "on";
                    Toast.makeText(getContext(), "تم تفعيل الرسوم المتحركة", Toast.LENGTH_SHORT).show();

                    return;
                }
            }
        };

        private View.OnClickListener ShareListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText(prayerName.getText().toString());
            }
        };
    }

    private DataBaseHelper dbh;
    private Spinner maxProgressBarLimit;
    private ArrayAdapter<String> spinnerProgressBarLimit;

    private ProgressBar progressBar;
    private TextView totalNumTextView, prayerName;
    int totalNumber = 0;
    private MediaPlayer mediaPlayer;

    private ImageView clickedImageView, soundImageView, animiImageView, resetImageView, shareImageView, totalImageView;
    private ScrollView scrollView;
    private LinearLayout tasbihFrag_setting_Layout1, tasbihFrag_setting_Layout2;

    private void init(View root) {

        tasbihFrag_setting_Layout1 = root.findViewById(R.id.tasbihFrag_setting_Layout1);
        tasbihFrag_setting_Layout2 = root.findViewById(R.id.tasbihFrag_setting_Layout2);

        soundImageView = root.findViewById(R.id.soundImageView);
        animiImageView = root.findViewById(R.id.animiImageView);
        resetImageView = root.findViewById(R.id.resetImageView);
        shareImageView = root.findViewById(R.id.shareImageView);
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

        maxProgressBarLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                dbh.updateSettingTable(DataBaseHelper.SETTING_Col5, selectedValue);
                Utils.ProgressBARMaxLimit = selectedValue;

                progressBar.setProgress(0);
                progressBar.setMax(Integer.parseInt(selectedValue));
                totalNumber = 0;
                totalNumTextView.setText(Utils.replaceToArabicNumbers(String.valueOf(0)));
                Toast.makeText(getContext(), "تم  ضبط العداد الى " + selectedValue, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        totalImageView.setOnClickListener(new MyListeners().showTotalListener);


        shareImageView.setOnClickListener(new MyListeners().ShareListener);
        return root;
    }

    private void setDayMood(View root) {
        scrollView.setBackgroundColor(getResources().getColor(R.color.day_background));
        tasbihFrag_setting_Layout1.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));
        tasbihFrag_setting_Layout2.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));


        TextView prayerNametextView = root.findViewById(R.id.prayerNametextView);
        prayerNametextView.setTextColor(getResources().getColor(R.color.violet));
        prayerNametextView.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));

        TextView setMaxProgressBarTV = root.findViewById(R.id.setMaxProgressBarTV);
        setMaxProgressBarTV.setTextColor(getResources().getColor(R.color.violet));
        //setMaxProgressBarTV.setBackground(getResources().getDrawable(R.drawable.day_rounded_bg));


        ImageView clickingImage = root.findViewById(R.id.clickingImage);
        clickingImage.setBackground(getResources().getDrawable(R.drawable.day_changeable_color));

        setSpinnerDayNight("day");

    }

    private void setNightMood(View root) {
        scrollView.setBackgroundColor(getResources().getColor(R.color.night_background));

        tasbihFrag_setting_Layout1.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));
        tasbihFrag_setting_Layout2.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));

        TextView prayerNametextView = root.findViewById(R.id.prayerNametextView);
        prayerNametextView.setTextColor(getResources().getColor(R.color.night_text_color));
        prayerNametextView.setBackground(getResources().getDrawable(R.drawable.night_rounded_bg));

        TextView setMaxProgressBarTV = root.findViewById(R.id.setMaxProgressBarTV);
        setMaxProgressBarTV.setTextColor(getResources().getColor(R.color.night_text_color));
        // setMaxProgressBarTV.setBackground(getResources().getDrawable(R.color.darkSky));

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


        // set spinner selected item
        if (Utils.ProgressBARMaxLimit.equals("10")) {
            maxProgressBarLimit.setSelection(0);
        } else if (Utils.ProgressBARMaxLimit.equals("33")) {
            maxProgressBarLimit.setSelection(1);
        } else if (Utils.ProgressBARMaxLimit.equals("100")) {
            maxProgressBarLimit.setSelection(2);
        } else if (Utils.ProgressBARMaxLimit.equals("1000")) {
            maxProgressBarLimit.setSelection(3);
        }

    }

    private void setSpinnerDayNight(String which) {

        String[] arr = {"10", "33", "100", "1000"};

        if (which == "day") {
            spinnerProgressBarLimit = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_day, arr);
        } else {
            spinnerProgressBarLimit = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_night, arr);
        }
        maxProgressBarLimit.setAdapter(spinnerProgressBarLimit);

    }

    void shareText(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        getContext().startActivity(sendIntent);
    }
}
