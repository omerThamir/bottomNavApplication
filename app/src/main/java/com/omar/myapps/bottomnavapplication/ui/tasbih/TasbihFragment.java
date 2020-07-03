package com.omar.myapps.bottomnavapplication.ui.tasbih;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omar.myapps.bottomnavapplication.DataBaseHelper;
import com.omar.myapps.bottomnavapplication.R;
import com.omar.myapps.bottomnavapplication.Utils;


public class TasbihFragment extends Fragment {


    ProgressBar progressBar;
    TextView totalNumTextView, prayerName;
    int totalNumber = 0;
    private MediaPlayer mediaPlayer;

    private Button showTotalBTN;
    private ImageView clickedImageView;

    private View.OnClickListener showTotalBTnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String TableName = Utils.Openedlist;
            String zkrName = prayerName.getText().toString();
            String arabicNum = Utils.replaceToArabicNumbers(String.valueOf(Utils.getLastUpdatedValueFromTable(TableName, zkrName)));
            Utils.showSnakeBar(v, "المجموع : " + arabicNum);
        }
    };

    private View.OnClickListener ImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doAnimation();

            /*
// for sound when click
                    if (Utils.getSoundIsOn() == 1) {
                        mediaPlayer.start();
                    }

                    // for animation
                    if (Utils.getAnimationOn() == 1) {
                        doAnimation();
                    }


             */
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

               /*         if (Utils.getSoundIsOn() == 1) {
                            mediaPlayer.start();
                        }

                */
                progressBar.setProgress(progressValue + 1);
                totalNumTextView.setText(Utils.replaceToArabicNumbers(String.valueOf(totalNumber += 1)));
                new DataBaseHelper(v.getContext()).updateDataFor(
                        Utils.Openedlist, "Total_number", String.valueOf(updatedValue), prayerNam);
            }
        }
    };


    private void init(View root) {

        progressBar = root.findViewById(R.id.progressBar);
        //  progressBar.setMax(Utils.getCounterLimit());
        totalNumTextView = root.findViewById(R.id.totalNumbertextView);
        prayerName = root.findViewById(R.id.prayerNametextView);
        showTotalBTN = root.findViewById(R.id.showTotalBTN);
        clickedImageView = root.findViewById(R.id.clickingImage);
        //  initSoundSetting();

    }

    void scrollTextViewInsideScrollView(View root) {
        prayerName.setMovementMethod(new ScrollingMovementMethod());

        ScrollView scrollView = root.findViewById(R.id.scrollView);
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
        scrollTextViewInsideScrollView(root);


        if (Utils.getClickedPrayerName() != null) {
            prayerName.setText(Utils.getClickedPrayerName());

        } else Toast.makeText(getContext(), "no data inside bundle", Toast.LENGTH_SHORT).show();


        clickedImageView.setOnClickListener(ImageOnClickListener);
        showTotalBTN.setOnClickListener(showTotalBTnListener);

        return root;
    }

    private void doAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in_out);
        clickedImageView.startAnimation(animation);
        progressBar.startAnimation(animation);
    }
}
