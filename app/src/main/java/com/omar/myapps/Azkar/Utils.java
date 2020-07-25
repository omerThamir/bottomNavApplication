package com.omar.myapps.Azkar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Utils {


    public static String Notification_On_Off;

    public static void startAlarm(Context context) {

        Calendar alarmTime = Calendar.getInstance();
        // HOUR_OF_DAY=15 = 3 pm
        // HOUR_OF_DAY=8 = 8 am
        alarmTime.set(Calendar.HOUR_OF_DAY, 8);
        alarmTime.set(Calendar.MINUTE, 0);

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY
                /*1 * 60 * 1000  every one minute */, pendingIntent);
        enableReceiver(context);
    }

    /**
     * Notice that in the manifest, the boot receiver is set to android:enabled="false".
     * This means that the receiver will not be called unless the application explicitly enables it.
     * This prevents the boot receiver from being called unnecessarily. You can enable a receiver
     * (for example, if the user sets an alarm) as follows:
     *
     * @param context
     */

    public static void enableReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static void disableReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static void cancel(Context context) {

        Intent alarmIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            disableReceiver(context);
        }
    }


    // public static String Notification_On_Off;
    public static String Day_Night_Mode;
    public static String Sound_On_Off;
    public static String Animation_On_Off;
    public static String ProgressBARMaxLimit;

    static ArrayList<String> arrayList;
    static ArrayList<String> favOrNotArrayList;
    static Context mContext;

    public Utils(Context context) {
        mContext = context;
        arrayList = new ArrayList<>();
        favOrNotArrayList = new ArrayList<>();
    }

    public static String Openedlist;

    public void Add_WC_ZkrArrayList() {
        String[] zkrWC = {"عند الدخول الى الخلاء\n اللّهُـمَّ إِنِّـي أَعـوذُ بِـكَ مِـنَ الْخُـبْثِ وَالْخَبائِث.",
                "بعد الخروج من الخلاء\n اللّهُـمَّ غُفْـرانَك."};
        arrayList.add(zkrWC[0]);
        arrayList.add(zkrWC[1]);
        Toast.makeText(mContext, arrayList.size() + "", Toast.LENGTH_SHORT).show();
    }

    public void Add_Food_ZkrArrayList() {
        String[] FoodZkr = {" الذكر عند الطعام والشراب.\n" +
                "    بِسْمِ اللهِ. فإنْ نسي في أَوَّلِهِ، فَليَقُلْ: بِسْمِ اللَّه أَوَّلَهُ وَآخِرَهُ.",
                "الذكر عند الفراغ من الطعام والشراب.\n الْحَمْدُ للهِ الَّذِي أَطْعَمَنِي هَذَا, وَرَزَقَنِيهِ مِنْ غَيْرِ حَوْلٍ مِّنِّي وَلاَ قُوَّةٍ. الْحَمْدُ لِلَّهِ كَثِيرًا طَيِّبًا مُبَارَكًا فِيهِ غَيْرَ مَكْفِيٍّ وَلَا مُوَدَّعٍ وَلَا مُسْتَغْنًى عَنْهُ رَبَّنَا."
                , "الذكر عند شرب اللبن.\n اَللَّهُمَّ بَارِكْ لَنَا فِيهِ, وَزِدْنَا ",
                "هدى النبى فى الشرب.\n كَانَ صَلَّى اللهُ عَلَيْهِ وَسَلَّمَ يَشْرَبُ فِي ثَلاَثَةِ أَنْفَاسٍ، إِذَا أَدْنَى الإِنَاءَ إِلَى فَمِهِ سَمَّى اللهَ تَعَالَى, وَإِذَا أَخَّرَهُ حَمِدَ اللهَ تَعَالَى، يَفْعَلُ ذَلِكَ ثَلاَثَ مَرَّاتٍ    مِنْهُ ."};
        for (String str : FoodZkr) {
            arrayList.add(str);
        }
    }

    public void Add_Wdhoo_ZkrArrayList() {
        String[] WdhooZkr = {"الذكر قبل الوضوء قبل الوضوء.\n بِسْمِ ٱللّٰهِ.",
                "الذكر بعد الوضوء.\n" +
                        "أشْهَدُ أن لا إله إلا الله وحْدَهُ لا شريكَ لهُ ، وأشْهَدُ أنَّ محمداً عَبدُهُ ورسُولُه.",
                "الذكر بعد الوضوء.\n" +
                        "اللَّهُمَّ اجْعَلْني مِنَ التَّوَّابينَ واجْعَلْنِي من المُتَطَهِّرِينَ.",
                "الذكر بعد الوضوء.\n" +
                        "سُبْحَانَكَ اللَّهُمَّ وبَحَمْدكَ أشْهدُ أنْ لا إلهَ إلا أنْتَ أَسْتَغْفِرُكَ وأتُوبُ إِلَيْكَ."};
        for (String str : WdhooZkr) {
            arrayList.add(str);
        }
    }

    public void Add_Masjed_ZkrArrayList() {
        String[] MasjedZkr = {" دُعَاءُ الذَّهَابِ إلَى المَسْجِدِ\n" +
                "اللّهُـمَّ اجْعَـلْ في قَلْبـي نورا ، وَفي لِسـاني نورا، وَاجْعَـلْ في سَمْعي نورا، وَاجْعَـلْ في بَصَري نورا، وَاجْعَـلْ مِنْ خَلْفي نورا، وَمِنْ أَمامـي نورا، وَاجْعَـلْ مِنْ فَوْقـي نورا ، وَمِن تَحْتـي نورا .اللّهُـمَّ أَعْطِنـي نورا."
                ,
                "  دُعَاءُ دُخُولِ المَسْجِدِ\n" +
                        "يَبْدَأُ بِرِجْلِهِ اليُمْنَى، وَيَقُولُ:\n أَعوذُ باللهِ العَظيـم وَبِوَجْهِـهِ الكَرِيـم وَسُلْطـانِه القَديـم مِنَ الشّيْـطانِ الرَّجـيم، بِسْمِ اللَّهِ، وَالصَّلاةُ وَالسَّلامُ عَلَى رَسُولِ الله، اللّهُـمَّ افْتَـحْ لي أَبْوابَ رَحْمَتـِك.",

                " دُعَاءُ الخُرُوجِ مِنَ المَسْجِدِ\n" +
                        "يَبْدَأُ بِرِجْلِهِ الْيُسْرَى، وَيَقُولُ:\n" +
                        "بِسْـمِ اللَّـهِ وَالصَّلاةُ وَالسَّلامُ عَلَى رَسُولِ اللَّهِ، اللَّهُمَّ إنِّي أَسْأَلُكَ مِنْ فَضْلِكَ، اللَّهُمَّ اعْصِمْنِي مِنَ الشَّيْطَانِ الرَّجِيم."};
        for (String str : MasjedZkr) {
            arrayList.add(str);
        }
    }


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

    public static String[] duaaFromQuran = {"بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ * الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ * الرَّحْمَنِ الرَّحِيمِ * مَالِكِ يَوْمِ الدِّينِ * إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ * اهْدِنَا الصِّرَاط الْمُسْتَقِيمَ * صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ. سورة الفاتحة ",
            "رَبَّنَا تَقَبَّلْ مِنَّا إِنَّكَ أَنتَ السَّمِيعُ الْعَلِيمُ. \n(سورة البقرة - الآية ۱۲٧)",
            "وَتُبْ عَلَيْنَآ إِنَّكَ أَنتَ التَّوَّابُ الرَّحِيمُ. \n(سورة البقرة - الآية ۱۲۸)",
            "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ. \n(سورة البقرة - الآية ۲۰۱)",
            "سَمِعْنَا وَأَطَعْنَا غُفْرَانَكَ رَبَّنَا وَإِلَيْكَ الْمَصِير. \n(سورة البقرة - الآية ۲۸٥)",
            "رَبَّنَا لاَ تُؤَاخِذْنَا إِن نَّسِينَا أَوْ أَخْطَأْنَا رَبَّنَا وَلاَ تَحْمِلْ عَلَيْنَا إِصْرًا كَمَا حَمَلْتَهُ عَلَى الَّذِينَ مِن قَبْلِنَا رَبَّنَا وَلاَ تُحَمِّلْنَا مَا لاَ طَاقَةَ لَنَا بِهِ وَاعْفُ عَنَّا وَاغْفِرْ لَنَا وَارْحَمْنَآ أَنتَ مَوْلاَنَا فَانصُرْنَا عَلَى الْقَوْمِ الْكَافِرِينَ. \n(سورة البقرة - الآية ۲۸٦)",
            "رَبَّنَا لاَ تُزِغْ قُلُوبَنَا بَعْدَ إِذْ هَدَيْتَنَا وَهَبْ لَنَا مِن لَّدُنكَ رَحْمَةً إِنَّكَ أَنتَ الْوَهَّابُ. \n(سورة آل عمران - الآية ۸)",
            "رَبَّنَا إِنَّنَا آمَنَّا فَاغْفِرْ لَنَا ذُنُوبَنَا وَقِنَا عَذَابَ النَّارِ. \n(سورة آل عمران - الآية ۱٦)",
            "رَبِّ هَبْ لِي مِن لَّدُنْكَ ذُرِّيَّةً طَيِّبَةً إِنَّكَ سَمِيعُ الدُّعَاءِ. \n(سورة آل عمران - الآية ۳۸)",
            "رَبَّنَا آمَنَّا بِمَا أَنزَلَتْ وَاتَّبَعْنَا الرَّسُولَ فَاكْتُبْنَا مَعَ الشَّاهِدِينَ. \n(سورة آل عمران - الآية ٥۳)",
            "ربَّنَا اغْفِرْ لَنَا ذُنُوبَنَا وَإِسْرَافَنَا فِي أَمْرِنَا وَثَبِّتْ أَقْدَامَنَا وانصُرْنَا عَلَى الْقَوْمِ الْكَافِرِينَ. \n(سورة آل عمران - الآية ۱٤٧)",
            "رَبَّنَا مَا خَلَقْتَ هَذا بَاطِلاً سُبْحَانَكَ فَقِنَا عَذَابَ النَّارِ * رَبَّنَا إِنَّكَ مَن تُدْخِلِ النَّارَ فَقَدْ أَخْزَيْتَهُ وَمَا لِلظَّالِمِينَ مِنْ أَنصَارٍ * رَّبَّنَا إِنَّنَا سَمِعْنَا مُنَادِيًا يُنَادِي لِلإِيمَانِ أَنْ آمِنُواْ بِرَبِّكُمْ فَآمَنَّا رَبَّنَا فَاغْفِرْ لَنَا ذُنُوبَنَا وَكَفِّرْ عَنَّا سَيِّئَاتِنَا وَتَوَفَّنَا مَعَ الأبْرَارِ * رَبَّنَا وَآتِنَا مَا وَعَدتَّنَا عَلَى رُسُلِكَ وَلاَ تُخْزِنَا يَوْمَ الْقِيَامَةِ إِنَّكَ لاَ تُخْلِفُ الْمِيعَادَ. \n(سورة آل عمران - الآية ۱۹۱-۱۹٤)",
            "رَبَّنَا آمَنَّا فَاكْتُبْنَا مَعَ الشَّاهِدِينَ. \n(سورة المائدة - الآية ۸۳)",
            "رَبَّنَا ظَلَمْنَا أَنفُسَنَا وَإِن لَّمْ تَغْفِرْ لَنَا وَتَرْحَمْنَا لَنَكُونَنَّ مِنَ الْخَاسِرِينَ. \n(سورة الأعراف - الآية ۲۳)",
            "رَبَّنَا لاَ تَجْعَلْنَا مَعَ الْقَوْمِ الظَّالِمِينَ. \n(سورة الأعراف - الآية ٤٧)",
            "أَنْتَ وَلِيُّنَا فَاغْفِرْ لَنَا وَارْحَمْنَا وَأَنْتَ خَيْرُ الْغَافِرِينَ* وَاكْتُبْ لَنَا فِي هَذِهِ الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ. \n(سورة الأعراف - الآية ۱٥٥,۱٥٦)",
            "حَسْبِيَ اللَّهُ لا إِلَـهَ إِلاَّ هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ. \n(سورة التوبة - الآية ۱۲۹)",
            "رَبَّنَا لاَ تَجْعَلْنَا فِتْنَةً لِّلْقَوْمِ الظَّالِمِينَ * وَنَجِّنَا بِرَحْمَتِكَ مِنَ الْقَوْمِ الْكَافِرِين. \n(سورة يونس - الآية ۸٥,۸٦)",
            "رَبِّ إِنِّي أَعُوذُ بِكَ أَنْ أَسْأَلَكَ مَا لَيْسَ لِي بِهِ عِلْمٌ وَإِلاَّ تَغْفِرْ لِي وَتَرْحَمْنِي أَكُن مِّنَ الْخَاسِرِينَ. \n(سورة هود - الآية ٤٧)",
            "رَبِّ قَدْ آتَيْتَنِي مِنَ الْمُلْكِ وَعَلَّمْتَنِي مِن تَأْوِيلِ الْأَحَادِيثِ ۚ فَاطِرَ السَّمَاوَاتِ وَالْأَرْضِ أَنتَ وَلِيِّي فِي الدُّنْيَا وَالْآخِرَةِ ۖ تَوَفَّنِي مُسْلِمًا وَأَلْحِقْنِي بِالصَّالِحِينَ. \n(سورة يوسف - الآية ۱۰۱)",
            "رَبِّ اجْعَلْ هَـذَا الْبَلَدَ آمِنًا وَاجْنُبْنِي وَبَنِيَّ أَن نَّعْبُدَ الأَصْنَامَ. \n(سورة ابراهيم - الآية ۳٥)",
            "رَبِّ اجْعَلْنِي مُقِيمَ الصَّلاَةِ وَمِن ذُرِّيَّتِي رَبَّنَا وَتَقَبَّلْ دُعَاءِ. \n(سورة ابراهيم - الآية ٤۰)",
            "رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ وَلِلْمُؤْمِنِينَ يَوْمَ يَقُومُ الْحِسَابُ. \n(سورة ابراهيم - الآية ٤۱)",
            "رَبَّنَا آتِنَا مِن لَّدُنكَ رَحْمَةً وَهَيِّئْ لَنَا مِنْ أَمْرِنَا رَشَدًا. \n(سورة الكهف - الآية ۱۰)",
            "رَبِّ اشْرَحْ لِي صَدْرِي* وَيَسِّرْ لِي أَمْرِي* وَاحْلُلْ عُقْدَةً مِّن لِّسَانِي، يَفْقَهُوا قَوْلِي. \n(سورة طه - الآية ۲٥-۲۸)",
            "رَّبِّ زِدْنِي عِلْمًا. \n(سورة طه - الآية ۱۱٤)",
            "لا إِلَهَ إِلا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ. \n(سورة الأنبياء - الآية ۸٧)",
            "رَبِّ لا تَذَرْنِي فَرْدًا وَأَنتَ خَيْرُ الْوَارِثِينَ. \n(سورة الأنبياء - الآية ۸۹)",
            "رَبِّ أَعُوذُ بِكَ مِنْ هَمَزَاتِ الشَّيَاطِينِ * وَأَعُوذُ بِكَ رَبِّ أَنْ يَحْضُرُون. \n(سورة المؤمنون - الآية ۹٧,۹۸)",
            "رَبَّنَا آمَنَّا فَاغْفِرْ لَنَا وَارْحَمْنَا وَأَنتَ خَيْرُ الرَّاحِمِينَ. \n(سورة المؤمنون - الآية ۱۰۹)",
            "رَّبِّ اغْفِرْ وَارْحَمْ وَأَنتَ خَيْرُ الرَّاحِمِينَ. \n(سورة المؤمنون - الآية ۱۱۸)",
            "رَبَّنَا اصْرِفْ عَنَّا عَذَابَ جَهَنَّمَ إِنَّ عَذَابَهَا كَانَ غَرَامًا*إِنَّهَا سَاءَتْ مُسْتَقَرًّا وَمُقَامًا. \n(سورة الفرقان - الآية ٦٥,٦٦)",
            "رَبَّنَا هَبْ لَنَا مِنْ أَزْوَاجِنَا وَذُرِّيَّاتِنَا قُرَّةَ أَعْيُنٍ وَاجْعَلْنَا لِلْمُتَّقِينَ إِمَامًا. \n(سورة الفرقان - الآية ٧٤)",
            "رَبِّ هَبْ لِي حُكْمًا وَأَلْحِقْنِي بِالصَّالِحِينَ* وَاجْعَلْ لِي لِسَانَ صِدْقٍ فِي الآخِرِينَ * وَاجْعَلْنِي مِن وَرَثَةِ جَنَّةِ النَّعِيمِ. \n(سورة الشعراء - الآية ۸۳,۸٤,۸٥)",
            "وَلا تُخْزِنِي يَوْمَ يُبْعَثُونَ * يَوْمَ لَا يَنْفَعُ مَالٌ وَلَا بَنُونَ * إِلَّا مَنْ أَتَى اللَّهَ بِقَلْبٍ سَلِيمٍ. \n(سورة الشعراء - الآية ۸٧,۸۸,۹۹)",
            "رَبِّ أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ الَّتِي أَنْعَمْتَ عَلَيَّ وَعَلَى وَالِدَيَّ وَأَنْ أَعْمَلَ صَالِحًا تَرْضَاهُ وَأَدْخِلْنِي بِرَحْمَتِكَ فِي عِبَادِكَ الصَّالِحِين. \n(سورة النمل - الآية ۱۹)",
            " رَبِّ إِنِّي ظَلَمْتُ نَفْسِي فَاغْفِرْلى. \n(سورة القصص - الآية ۱٦)",
            "رَبِّ نَجِّنِي مِنَ الْقَوْمِ الظَّالِمِينَ. \n(سورة القصص - الآية ۲۱)",
            "عَسَى رَبِّي أَن يَهْدِيَنِي سَوَاءَ السَّبِيلِ. \n(سورة القصص - الآية ۲۲)",
            "رَبِّ إِنِّي لِمَا أَنزَلْتَ إِلَيَّ مِنْ خَيْرٍ فَقِيرٌ. \n(سورة القصص - الآية ۲٤)",
            "رَبِّ انصُرْنِي عَلَى الْقَوْمِ الْمُفْسِدِينَ. \n(سورة العنكبوت - الآية ۳۰)",
            "رَبِّ هَبْ لِي مِنَ الصَّالِحِينَ. \n(سورة الصافات - الآية ۱۰۰)",
            "رَبِّ أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ الَّتِي أَنْعَمْتَ عَلَيَّ وَعَلَى وَالِدَيَّ وَأَنْ أَعْمَلَ صَالِحًا تَرْضَاهُ وَأَصْلِحْ لِي فِي ذُرِّيَّتِي إِنِّي تُبْتُ إِلَيْكَ وَإِنِّي مِنَ الْمُسْلِمِينَ. \n(سورة الأحقاف - الآية ۱٥)",
            "رَبَّنَا اغْفِرْ لَنَا وَلِإِخْوَانِنَا الَّذِينَ سَبَقُونَا بِالإِيمَانِ وَلا تَجْعَلْ فِي قُلُوبِنَا غِلاً لِّلَّذِينَ آمَنُوا رَبَّنَا إِنَّكَ رَؤُوفٌ رَّحِيمٌ. \n(سورة الحشر - الآية ۱۰)",
            "رَّبَّنَا عَلَيْكَ تَوَكَّلْنَا وَإِلَيْكَ أَنَبْنَا وَإِلَيْكَ الْمَصِيرُ. \n(سورة الممتحنة - الآية ٤)",
            "رَبَّنَا لا تَجْعَلْنَا فِتْنَةً لِّلَّذِينَ كَفَرُوا وَاغْفِرْ لَنَا رَبَّنَا إِنَّكَ أَنتَ الْعَزِيزُ الْحَكِيمُ. \n(سورة الممتحنة - الآية ٥)",
            "رَبَّنَا أَتْمِمْ لَنَا نُورَنَا وَاغْفِرْ لَنَا إِنَّكَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ. \n(سورة التحريم - الآية ۸  )",
            "رَبِّ اغْفِرْ لِي وَلِوَالِدَيَّ وَلِمَن دَخَلَ بَيْتِيَ مُؤْمِنًا وَلِلْمُؤْمِنِينَ وَالْمُؤْمِنَاتِ. \n(سورة نوح - الآية ۲۸ )"
    };
}
