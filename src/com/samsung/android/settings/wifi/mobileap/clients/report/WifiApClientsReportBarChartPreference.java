package com.samsung.android.settings.wifi.mobileap.clients.report;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApClientPreference;
import com.samsung.android.settings.wifi.mobileap.clients.report.barchart.WifiApClientsMonthlyBarChart;
import com.samsung.android.settings.wifi.mobileap.clients.report.barchart.WifiApClientsWeeklyBarChart;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.AbsRoundBarRenderer;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.CategoryViewConstants$CategoryType;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.ChartConfigSelector;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.DataChartMarker;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.DataChartMarkerMonthly;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.ViewUtils;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.WeeklyChartXLabelFormatter;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.WeeklyXAxisRenderer;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApDailyStackedProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApMonthlyStackedProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApWeeklyStackedProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApDateUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;
import com.samsung.android.settings.wifi.mobileap.views.WifiApStackedProgressBarPreference;
import com.samsung.android.settings.wifi.mobileap.views.chart.WifiApDataUsageIAxisValueFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientsReportBarChartPreference extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public boolean mFirstTime;
    public List mMonthlyStackedProgressBarEntryConfigList;
    public Calendar mSelectedMonthlyCalendar;
    public WifiApWeeklyStackedProgressBarEntryConfig mSelectedWeeklyStackedProgressBarEntryConfig;
    public List mWeeklyStackedProgressBarEntryConfigList;
    public WifiApHotspotUsageReport mWifiApHotspotUsageReport;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.report.WifiApClientsReportBarChartPreference$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public /* synthetic */ AnonymousClass1() {}
    }

    public WifiApClientsReportBarChartPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        WifiApWeeklyStackedProgressBarEntryConfig wifiApWeeklyStackedProgressBarEntryConfig =
                new WifiApWeeklyStackedProgressBarEntryConfig();
        wifiApWeeklyStackedProgressBarEntryConfig.mDailyStackedProgressBarEntryConfigList =
                new ArrayList();
        wifiApWeeklyStackedProgressBarEntryConfig.mWeekOfYearCalendar = Calendar.getInstance();
        this.mSelectedWeeklyStackedProgressBarEntryConfig =
                wifiApWeeklyStackedProgressBarEntryConfig;
        this.mFirstTime = true;
        setLayoutResource(R.layout.sec_wifi_ap_clients_report_barchart_layout_preference);
        this.mContext = context;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i;
        Calendar calendar;
        List list;
        Iterator it;
        String str;
        double d;
        int intValue;
        WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig;
        String str2;
        Iterator it2;
        String str3;
        char c = 1;
        super.onBindViewHolder(preferenceViewHolder);
        Calendar calendar2 = Calendar.getInstance();
        char c2 = 2;
        calendar2.add(2, -5);
        calendar2.set(5, 1);
        Context context = this.mContext;
        long timeInMillis = calendar2.getTimeInMillis();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it3 =
                WifiApFrameworkUtils.getSemWifiManager(context)
                        .getTotalAndTop3ClientsDataUsageBetweenGivenDates(
                                timeInMillis, System.currentTimeMillis())
                        .iterator();
        while (it3.hasNext()) {
            String str4 = (String) it3.next();
            Log.i("WifiApConnectedDeviceUtils", "getTopHotspotClientsForEachDay string: " + str4);
            String[] split = str4.split("\\r?\\n");
            ArrayList arrayList3 = arrayList;
            long parseLong = Long.parseLong(split[c]);
            int parseInt = Integer.parseInt(split[c2]);
            StringBuilder sb = new StringBuilder("getTopHotspotClientsForEachDay - lines[0] ");
            String str5 = " ";
            sb.append(new Date(Long.parseLong(split[0])));
            Log.i("WifiApConnectedDeviceUtils", sb.toString());
            ArrayList arrayList4 = new ArrayList();
            int i2 = 3;
            int i3 = 0;
            while (i3 < parseInt) {
                Iterator it4 = it3;
                String str6 = str5;
                int i4 = parseInt;
                String[] split2 = split[i2].split(str6, 2);
                Context context2 = context;
                WifiApProgressBarEntryConfig wifiApProgressBarEntryConfig =
                        new WifiApProgressBarEntryConfig(
                                Long.parseLong(split2[0]),
                                i3 == 3
                                        ? context.getString(R.string.wifi_ap_other_devices)
                                        : split2[1]);
                arrayList4.add(wifiApProgressBarEntryConfig);
                Log.i(
                        "WifiApConnectedDeviceUtils",
                        "getTopHotspotClientsForEachDay - Adding progress: "
                                + wifiApProgressBarEntryConfig.toString());
                i2++;
                i3++;
                context = context2;
                it3 = it4;
                str5 = str6;
                parseInt = i4;
            }
            WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig2 =
                    new WifiApDailyStackedProgressBarEntryConfig(
                            arrayList4, Long.parseLong(split[0]));
            Log.i(
                    "WifiApConnectedDeviceUtils",
                    "getTopHotspotClientsForEachDay - Adding daily stacked progress: "
                            + wifiApDailyStackedProgressBarEntryConfig2.toString());
            wifiApDailyStackedProgressBarEntryConfig2.mTotalUsed =
                    new WifiApDataUsageConfig(parseLong);
            arrayList2.add(wifiApDailyStackedProgressBarEntryConfig2);
            arrayList = arrayList3;
            context = context;
            it3 = it3;
            c = 1;
            c2 = 2;
        }
        String str7 = " ";
        ArrayList arrayList5 = arrayList;
        Context context3 = context;
        Log.i(
                "WifiApConnectedDeviceUtils",
                "getTopHotspotClientsForEachDay - startDate: "
                        + new Date(timeInMillis)
                        + ", list count: "
                        + arrayList2.size());
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(2, -5);
        calendar3.set(5, 1);
        Calendar calendar4 = Calendar.getInstance();
        Integer num = WifiApDateUtils.NUMBER_OF_DAYS_IN_WEEK;
        calendar4.setTime(new Date(WifiApDateUtils.getLastDayOfWeek(System.currentTimeMillis())));
        String str8 = "Start calendar: ";
        Log.i(
                "WifiApConnectedDeviceUtils",
                "Start calendar: "
                        + calendar3.getTime()
                        + ", End calendar: "
                        + calendar4.getTime());
        while (calendar3.getTimeInMillis() <= calendar4.getTimeInMillis()) {
            Log.i(
                    "WifiApConnectedDeviceUtils",
                    "While loop Start calendar: "
                            + calendar3.getTime()
                            + ", End calendar: "
                            + calendar4.getTime());
            WifiApWeeklyStackedProgressBarEntryConfig wifiApWeeklyStackedProgressBarEntryConfig =
                    new WifiApWeeklyStackedProgressBarEntryConfig();
            wifiApWeeklyStackedProgressBarEntryConfig.mDailyStackedProgressBarEntryConfigList =
                    new ArrayList();
            Log.i(
                    "WifiApWeeklyStackedProgressBarEntryConfig",
                    "Adding empty stacked progress List");
            ArrayList arrayList6 = new ArrayList();
            wifiApWeeklyStackedProgressBarEntryConfig.mDailyStackedProgressBarEntryConfigList =
                    arrayList6;
            wifiApWeeklyStackedProgressBarEntryConfig.mWeekOfYearCalendar =
                    (Calendar) calendar3.clone();
            String str9 = str8;
            Log.i(
                    "WifiApWeeklyStackedProgressBarEntryConfig",
                    "Created "
                            + wifiApWeeklyStackedProgressBarEntryConfig.weekOfYearToString()
                            + ", list Count: "
                            + arrayList6.size());
            Iterator it5 = arrayList2.iterator();
            while (it5.hasNext()) {
                WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig3 =
                        (WifiApDailyStackedProgressBarEntryConfig) it5.next();
                Calendar calendar5 = wifiApDailyStackedProgressBarEntryConfig3.mCalendar;
                if (WifiApDateUtils.isEqualsCalendarWeekOfYear(
                        wifiApWeeklyStackedProgressBarEntryConfig.mWeekOfYearCalendar, calendar5)) {
                    it2 = it5;
                    str3 = str7;
                    if (WifiApDateUtils.isEqualsDate(
                            calendar5.getTimeInMillis(), System.currentTimeMillis())) {
                        Log.i(
                                "WifiApConnectedDeviceUtils",
                                "Adding daily Item for Current day: "
                                        + wifiApDailyStackedProgressBarEntryConfig3.toString());
                        wifiApWeeklyStackedProgressBarEntryConfig
                                .addDailyStackedProgressBarEntryConfigList(
                                        WifiApConnectedDeviceUtils
                                                .getProgressBarEntryConfigForToday(context3));
                    } else {
                        wifiApWeeklyStackedProgressBarEntryConfig
                                .addDailyStackedProgressBarEntryConfigList(
                                        wifiApDailyStackedProgressBarEntryConfig3);
                    }
                    Log.i(
                            "WifiApConnectedDeviceUtils",
                            "Adding daily Item: "
                                    + wifiApDailyStackedProgressBarEntryConfig3.toString());
                } else {
                    it2 = it5;
                    str3 = str7;
                }
                it5 = it2;
                str7 = str3;
            }
            String str10 = str7;
            if (arrayList2.isEmpty()) {
                Log.e("WifiApConnectedDeviceUtils", "Weekly list from framework is empty");
                wifiApWeeklyStackedProgressBarEntryConfig.addDailyStackedProgressBarEntryConfigList(
                        WifiApConnectedDeviceUtils.getProgressBarEntryConfigForToday(context3));
            }
            ArrayList arrayList7 = arrayList5;
            arrayList7.add(wifiApWeeklyStackedProgressBarEntryConfig);
            calendar3.add(3, 1);
            arrayList5 = arrayList7;
            str8 = str9;
            str7 = str10;
        }
        String str11 = str8;
        String str12 = str7;
        ArrayList arrayList8 = arrayList5;
        Log.i(
                "WifiApConnectedDeviceUtils",
                "Outside loop Start calendar: "
                        + calendar3.getTime()
                        + ", End calendar: "
                        + calendar4.getTime());
        Log.i(
                "WifiApConnectedDeviceUtils",
                "getTopHotspotClientsForEachWeek - startDate: "
                        + new Date(timeInMillis)
                        + ", list count: "
                        + arrayList8.size());
        this.mWeeklyStackedProgressBarEntryConfigList = arrayList8;
        Context context4 = this.mContext;
        ArrayList arrayList9 = new ArrayList();
        for (String str13 :
                WifiApFrameworkUtils.getSemWifiManager(context4).getMonthlyDataUsage()) {
            ArrayList arrayList10 = new ArrayList();
            String[] split3 = str13.split(str12, 2);
            String str14 = split3[0];
            long parseLong2 = Long.parseLong(split3[1]);
            long parseLong3 = Long.parseLong(str14);
            arrayList10.add(new WifiApProgressBarEntryConfig(parseLong2, str14));
            arrayList9.add(new WifiApDailyStackedProgressBarEntryConfig(arrayList10, parseLong3));
        }
        this.mMonthlyStackedProgressBarEntryConfigList = arrayList9;
        if (((ArrayList) this.mWeeklyStackedProgressBarEntryConfigList).isEmpty()) {
            i = 1;
        } else {
            i = 1;
            this.mSelectedWeeklyStackedProgressBarEntryConfig =
                    (WifiApWeeklyStackedProgressBarEntryConfig)
                            AlertController$$ExternalSyntheticOutline0.m(
                                    1, (ArrayList) this.mWeeklyStackedProgressBarEntryConfigList);
        }
        if (!((ArrayList) this.mMonthlyStackedProgressBarEntryConfigList).isEmpty()) {
            this.mSelectedMonthlyCalendar =
                    ((WifiApDailyStackedProgressBarEntryConfig)
                                    AlertController$$ExternalSyntheticOutline0.m(
                                            i,
                                            (ArrayList)
                                                    this.mMonthlyStackedProgressBarEntryConfigList))
                            .mCalendar;
        }
        long firstDayOfWeek =
                WifiApDateUtils.getFirstDayOfWeek(
                        this.mWifiApHotspotUsageReport.mReportCalendarRangeSelectorPreference
                                .mSelectedWeekDateInMillis.longValue());
        WifiApWeeklyStackedProgressBarEntryConfig wifiApWeeklyStackedProgressBarEntryConfig2 =
                this.mSelectedWeeklyStackedProgressBarEntryConfig;
        if (wifiApWeeklyStackedProgressBarEntryConfig2 != null) {
            Calendar calendar6 = Calendar.getInstance();
            calendar6.setTime(new Date(firstDayOfWeek));
            if (!WifiApDateUtils.isEqualsCalendarWeekOfYear(
                    wifiApWeeklyStackedProgressBarEntryConfig2.mWeekOfYearCalendar, calendar6)) {
                Iterator it6 =
                        ((ArrayList) this.mWeeklyStackedProgressBarEntryConfigList).iterator();
                while (it6.hasNext()) {
                    WifiApWeeklyStackedProgressBarEntryConfig
                            wifiApWeeklyStackedProgressBarEntryConfig3 =
                                    (WifiApWeeklyStackedProgressBarEntryConfig) it6.next();
                    wifiApWeeklyStackedProgressBarEntryConfig3.getClass();
                    Calendar calendar7 = Calendar.getInstance();
                    calendar7.setTime(new Date(firstDayOfWeek));
                    if (WifiApDateUtils.isEqualsCalendarWeekOfYear(
                            wifiApWeeklyStackedProgressBarEntryConfig3.mWeekOfYearCalendar,
                            calendar7)) {
                        this.mSelectedWeeklyStackedProgressBarEntryConfig =
                                wifiApWeeklyStackedProgressBarEntryConfig3;
                        Log.i(
                                "WifiApClientsReportBarChartPreference",
                                "updatePreference week with: "
                                        + wifiApWeeklyStackedProgressBarEntryConfig3
                                                .weekOfYearToString());
                    }
                }
            }
        }
        Calendar calendar8 = Calendar.getInstance();
        calendar8.setTime(
                new Date(
                        this.mWifiApHotspotUsageReport.mReportCalendarRangeSelectorPreference
                                .mSelectedMonthDateInMillis.longValue()));
        this.mSelectedMonthlyCalendar = calendar8;
        WifiApClientsWeeklyBarChart wifiApClientsWeeklyBarChart =
                (WifiApClientsWeeklyBarChart)
                        preferenceViewHolder.findViewById(R.id.weekly_barchart);
        WifiApClientsMonthlyBarChart wifiApClientsMonthlyBarChart =
                (WifiApClientsMonthlyBarChart)
                        preferenceViewHolder.findViewById(R.id.monthly_barchart);
        TextView textView =
                (TextView) preferenceViewHolder.findViewById(R.id.last_week_month_textview);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.average_textview);
        TextView textView3 = (TextView) preferenceViewHolder.findViewById(R.id.total_textview);
        TextView textView4 =
                (TextView) preferenceViewHolder.findViewById(R.id.average_per_text_view);
        if (wifiApClientsMonthlyBarChart != null) {
            int i5 =
                    this.mWifiApHotspotUsageReport.mWeeklyAndMonthlyTabPreference.mTabIndexSelected;
            String str15 = "Invalid Unit found make default to MB";
            XAxis.XAxisPosition xAxisPosition = XAxis.XAxisPosition.BOTTOM;
            if (i5 != 0) {
                removeDeviceList$1();
                wifiApClientsWeeklyBarChart.setVisibility(8);
                wifiApClientsMonthlyBarChart.setVisibility(0);
                List list2 = this.mMonthlyStackedProgressBarEntryConfigList;
                WifiApMonthlyStackedProgressBarEntryConfig
                        wifiApMonthlyStackedProgressBarEntryConfig =
                                new WifiApMonthlyStackedProgressBarEntryConfig();
                wifiApMonthlyStackedProgressBarEntryConfig.mDailyStackedProgressBarEntryConfigList =
                        new ArrayList();
                long currentTimeMillis = System.currentTimeMillis();
                new WifiApDataUsageConfig(0L);
                Date date = new Date(currentTimeMillis);
                Calendar calendar9 = Calendar.getInstance();
                calendar9.setTime(date);
                Log.i("WifiApDailyStackedProgressBarEntryConfig", "Adding empty progress List");
                Log.i(
                        "WifiApDailyStackedProgressBarEntryConfig",
                        "Created Entry for Calendar: "
                                + calendar9.getTime()
                                + ", list Count: "
                                + new ArrayList().size());
                if (list2 != null) {
                    wifiApMonthlyStackedProgressBarEntryConfig
                                    .mDailyStackedProgressBarEntryConfigList =
                            list2;
                }
                if (!wifiApMonthlyStackedProgressBarEntryConfig
                        .mDailyStackedProgressBarEntryConfigList.isEmpty()) {}
                Calendar calendar10 = this.mSelectedMonthlyCalendar;
                wifiApClientsMonthlyBarChart.mWifiApMonthlyStackedProgressBarEntryConfig =
                        wifiApMonthlyStackedProgressBarEntryConfig;
                List list3 =
                        wifiApMonthlyStackedProgressBarEntryConfig
                                .mDailyStackedProgressBarEntryConfigList;
                Calendar calendar11 = Calendar.getInstance();
                calendar11.add(2, -5);
                calendar11.set(5, 1);
                Calendar calendar12 = Calendar.getInstance();
                calendar12.setTime(new Date(System.currentTimeMillis()));
                Log.i(
                        "WifiApClientsMonthlyBarChart",
                        str11 + calendar11.getTime() + ", End calendar: " + calendar12.getTime());
                ((ArrayList) wifiApClientsMonthlyBarChart.mDailyStackedProgressBarEntryConfigList)
                        .clear();
                while (calendar11.getTimeInMillis() <= calendar12.getTimeInMillis()) {
                    Calendar calendar13 = calendar12;
                    WifiApDailyStackedProgressBarEntryConfig
                            wifiApDailyStackedProgressBarEntryConfig4 =
                                    new WifiApDailyStackedProgressBarEntryConfig(
                                            null, calendar11.getTimeInMillis());
                    Iterator it7 = list3.iterator();
                    while (it7.hasNext()) {
                        WifiApDailyStackedProgressBarEntryConfig
                                wifiApDailyStackedProgressBarEntryConfig5 =
                                        (WifiApDailyStackedProgressBarEntryConfig) it7.next();
                        Calendar calendar14 = wifiApDailyStackedProgressBarEntryConfig5.mCalendar;
                        Integer num2 = WifiApDateUtils.NUMBER_OF_DAYS_IN_WEEK;
                        if (calendar14 != null) {
                            list = list3;
                            it = it7;
                            if (calendar14.get(2) == calendar11.get(2)
                                    && calendar14.get(1) == calendar11.get(1)) {
                                wifiApDailyStackedProgressBarEntryConfig4 =
                                        wifiApDailyStackedProgressBarEntryConfig5;
                            }
                        } else {
                            list = list3;
                            it = it7;
                        }
                        list3 = list;
                        it7 = it;
                    }
                    ((ArrayList)
                                    wifiApClientsMonthlyBarChart
                                            .mDailyStackedProgressBarEntryConfigList)
                            .add(wifiApDailyStackedProgressBarEntryConfig4);
                    calendar11.add(2, 1);
                    calendar12 = calendar13;
                    list3 = list3;
                }
                boolean z = WifiApSettingsUtils.DBG;
                wifiApClientsMonthlyBarChart.mTopWifiApDataUsageConfig =
                        wifiApClientsMonthlyBarChart
                                .mWifiApMonthlyStackedProgressBarEntryConfig
                                .getTopEntryConfig()
                                .getTotalUsage();
                wifiApClientsMonthlyBarChart.mMonthlyBarEntryArrayList.clear();
                int i6 = 0;
                while (i6
                        < ((ArrayList)
                                        wifiApClientsMonthlyBarChart
                                                .mDailyStackedProgressBarEntryConfigList)
                                .size()) {
                    List list4 =
                            ((WifiApDailyStackedProgressBarEntryConfig)
                                            ((ArrayList)
                                                            wifiApClientsMonthlyBarChart
                                                                    .mDailyStackedProgressBarEntryConfigList)
                                                    .get(i6))
                                    .mProgressBarEntryConfigList;
                    if (list4.isEmpty()) {
                        wifiApClientsMonthlyBarChart.mMonthlyBarEntryArrayList.add(
                                new BarEntry(i6, 0.0f));
                    } else if ("MB"
                            .equals(
                                    wifiApClientsMonthlyBarChart.mTopWifiApDataUsageConfig
                                            .getUsageValueUnit(
                                                    wifiApClientsMonthlyBarChart.mContext))) {
                        Iterator it8 = list4.iterator();
                        while (it8.hasNext()) {
                            wifiApClientsMonthlyBarChart.mMonthlyBarEntryArrayList.add(
                                    new BarEntry(
                                            i6,
                                            (float)
                                                    ((WifiApProgressBarEntryConfig) it8.next())
                                                            .mDataUsageConfig.getUsageValueInMB()));
                            calendar10 = calendar10;
                        }
                    } else {
                        calendar = calendar10;
                        for (Iterator it9 = list4.iterator(); it9.hasNext(); it9 = it9) {
                            WifiApProgressBarEntryConfig wifiApProgressBarEntryConfig2 =
                                    (WifiApProgressBarEntryConfig) it9.next();
                            ArrayList arrayList11 =
                                    wifiApClientsMonthlyBarChart.mMonthlyBarEntryArrayList;
                            wifiApProgressBarEntryConfig2.mDataUsageConfig.getClass();
                            arrayList11.add(
                                    new BarEntry(
                                            i6,
                                            (float)
                                                    new BigDecimal(r2.mUsageValueInBytes / 1.0E9d)
                                                            .setScale(2, RoundingMode.HALF_UP)
                                                            .doubleValue()));
                            wifiApClientsMonthlyBarChart = wifiApClientsMonthlyBarChart;
                        }
                        i6++;
                        wifiApClientsMonthlyBarChart = wifiApClientsMonthlyBarChart;
                        calendar10 = calendar;
                    }
                    calendar = calendar10;
                    i6++;
                    wifiApClientsMonthlyBarChart = wifiApClientsMonthlyBarChart;
                    calendar10 = calendar;
                }
                Calendar calendar15 = calendar10;
                WifiApClientsMonthlyBarChart wifiApClientsMonthlyBarChart2 =
                        wifiApClientsMonthlyBarChart;
                int i7 = 0;
                int i8 = 0;
                while (i7
                        < ((ArrayList)
                                        wifiApClientsMonthlyBarChart2
                                                .mDailyStackedProgressBarEntryConfigList)
                                .size()) {
                    Calendar calendar16 = calendar15;
                    if (WifiApDateUtils.isEqualsDate(
                            ((WifiApDailyStackedProgressBarEntryConfig)
                                            ((ArrayList)
                                                            wifiApClientsMonthlyBarChart2
                                                                    .mDailyStackedProgressBarEntryConfigList)
                                                    .get(i7))
                                    .mCalendar,
                            calendar16)) {
                        wifiApClientsMonthlyBarChart2.mSelectedXAxisIndexInReverseOrder = 5 - i8;
                        TooltipPopup$$ExternalSyntheticOutline0.m(
                                new StringBuilder("updateCalenderSelectedBar "),
                                wifiApClientsMonthlyBarChart2.mSelectedXAxisIndexInReverseOrder,
                                "WifiApClientsMonthlyBarChart");
                    }
                    i8++;
                    i7++;
                    calendar15 = calendar16;
                }
                wifiApClientsMonthlyBarChart2.mMonthlyIBarDataSetArrayList.clear();
                ArrayList arrayList12 = new ArrayList();
                arrayList12.add(
                        Integer.valueOf(WifiApClientsMonthlyBarChart.STACKED_BAR_COLORS[0]));
                BarDataSet barDataSet =
                        new BarDataSet(wifiApClientsMonthlyBarChart2.mMonthlyBarEntryArrayList);
                wifiApClientsMonthlyBarChart2.mMonthlyBarDataSet = barDataSet;
                barDataSet.mColors = arrayList12;
                barDataSet.mValueTextSize = Utils.convertDpToPixel(11.0f);
                BarDataSet barDataSet2 = wifiApClientsMonthlyBarChart2.mMonthlyBarDataSet;
                barDataSet2.mDrawIcons = true;
                barDataSet2.mDrawValues = false;
                BarData barData = new BarData(barDataSet2);
                wifiApClientsMonthlyBarChart2.mMonthlyBarData = barData;
                barData.mBarWidth = 0.42f;
                wifiApClientsMonthlyBarChart2.setData(barData);
                wifiApClientsMonthlyBarChart2.mScaleXEnabled = false;
                wifiApClientsMonthlyBarChart2.mScaleYEnabled = false;
                wifiApClientsMonthlyBarChart2.mDoubleTapToZoomEnabled = false;
                wifiApClientsMonthlyBarChart2.mDragXEnabled = false;
                wifiApClientsMonthlyBarChart2.mDragYEnabled = false;
                wifiApClientsMonthlyBarChart2.mLegend.mEnabled = false;
                wifiApClientsMonthlyBarChart2.setVisibleXRangeMaximum(6.0f);
                wifiApClientsMonthlyBarChart2.onWindowFocusChanged(true);
                wifiApClientsMonthlyBarChart2.mSelectionListener =
                        wifiApClientsMonthlyBarChart2.chartValueSelectedMonthly;
                wifiApClientsMonthlyBarChart2.mAutoScaleMinMaxEnabled = true;
                wifiApClientsMonthlyBarChart2.mTouchEnabled = true;
                XAxis xAxis = wifiApClientsMonthlyBarChart2.mXAxis;
                YAxis yAxis = wifiApClientsMonthlyBarChart2.mAxisLeft;
                YAxis yAxis2 = wifiApClientsMonthlyBarChart2.mAxisRight;
                boolean z2 = WifiApSettingsUtils.DBG;
                yAxis.mDrawGridLines = false;
                xAxis.mDrawGridLines = false;
                wifiApClientsMonthlyBarChart2.mDescription.mEnabled = false;
                wifiApClientsMonthlyBarChart2.mHighlightFullBarEnabled = true;
                wifiApClientsMonthlyBarChart2.setHovered(true);
                xAxis.mPosition = xAxisPosition;
                xAxis.mTextColor =
                        wifiApClientsMonthlyBarChart2
                                .getContext()
                                .getColor(R.color.wifi_ap_secondary_text_color);
                ArrayList arrayList13 = new ArrayList();
                for (int i9 = 0;
                        i9
                                < ((ArrayList)
                                                wifiApClientsMonthlyBarChart2
                                                        .mDailyStackedProgressBarEntryConfigList)
                                        .size();
                        i9++) {
                    arrayList13.add(
                            i9,
                            new SimpleDateFormat("MM")
                                    .format(
                                            ((WifiApDailyStackedProgressBarEntryConfig)
                                                            ((ArrayList)
                                                                            wifiApClientsMonthlyBarChart2
                                                                                    .mDailyStackedProgressBarEntryConfigList)
                                                                    .get(i9))
                                                    .mCalendar.getTime()));
                }
                xAxis.mAxisValueFormatter = new IndexAxisValueFormatter(arrayList13);
                yAxis.mEnabled = false;
                yAxis.setAxisMinimum(0.0f);
                float f = yAxis2.mAxisMaximum;
                yAxis2.mGranularityEnabled = true;
                yAxis2.setGranularity();
                YAxis.AxisDependency axisDependency = yAxis2.mAxisDependency;
                if (f < 3.0f) {
                    wifiApClientsMonthlyBarChart2.setVisibleYRange(0.0f, 3.0f, axisDependency);
                } else {
                    wifiApClientsMonthlyBarChart2.setVisibleYRange(
                            f * 1.1f, f * 1.25f, axisDependency);
                }
                yAxis2.setLabelCount(4);
                yAxis2.mTextColor =
                        wifiApClientsMonthlyBarChart2
                                .getContext()
                                .getColor(R.color.wifi_ap_secondary_text_color);
                yAxis2.mDrawAxisLine = false;
                yAxis2.setAxisMinimum(0.0f);
                yAxis2.mTypeface = Typeface.create(Typeface.create("sec", 0), 400, false);
                yAxis2.setTextSize(11.0f);
                yAxis2.mTextColor =
                        wifiApClientsMonthlyBarChart2
                                .getContext()
                                .getColor(R.color.wifi_ap_graph_xy_axis_text_color);
                wifiApClientsMonthlyBarChart2.mDragDecelerationEnabled = false;
                wifiApClientsMonthlyBarChart2.mDragDecelerationFrictionCoef = 0.0f;
                Context context5 = wifiApClientsMonthlyBarChart2.mContext;
                CategoryViewConstants$CategoryType categoryViewConstants$CategoryType =
                        CategoryViewConstants$CategoryType.CATEGORY_DATA_MONTHLY;
                wifiApClientsMonthlyBarChart2.mXAxisRendererMonthly =
                        ChartConfigSelector.getXAxisRenderer(
                                context5,
                                categoryViewConstants$CategoryType,
                                wifiApClientsMonthlyBarChart2);
                AbsRoundBarRenderer barChartRenderer =
                        ChartConfigSelector.getBarChartRenderer(
                                wifiApClientsMonthlyBarChart2.mContext,
                                categoryViewConstants$CategoryType,
                                wifiApClientsMonthlyBarChart2);
                wifiApClientsMonthlyBarChart2.mMonthlyBarChartRenderer = barChartRenderer;
                wifiApClientsMonthlyBarChart2.mXAxisRenderer =
                        wifiApClientsMonthlyBarChart2.mXAxisRendererMonthly;
                if (barChartRenderer != null) {
                    wifiApClientsMonthlyBarChart2.mRenderer = barChartRenderer;
                }
                Context context6 = wifiApClientsMonthlyBarChart2.mContext;
                WifiApDataUsageIAxisValueFormatter wifiApDataUsageIAxisValueFormatter =
                        new WifiApDataUsageIAxisValueFormatter(context6);
                String usageValueUnit =
                        wifiApClientsMonthlyBarChart2.mTopWifiApDataUsageConfig.getUsageValueUnit(
                                context6);
                if (usageValueUnit.contentEquals("MB") || usageValueUnit.contentEquals("GB")) {
                    wifiApDataUsageIAxisValueFormatter.mDataUsageUnit = usageValueUnit;
                } else {
                    Log.i(
                            "WifiApDataUsageIAxisValueFormatter",
                            "Invalid Unit found make default to MB");
                    wifiApDataUsageIAxisValueFormatter.mDataUsageUnit = "MB";
                }
                yAxis2.mAxisValueFormatter = wifiApDataUsageIAxisValueFormatter;
                wifiApClientsMonthlyBarChart2.mDrawMarkers = true;
                Context context7 = wifiApClientsMonthlyBarChart2.mContext;
                List list5 = wifiApClientsMonthlyBarChart2.mDailyStackedProgressBarEntryConfigList;
                WifiApDailyStackedProgressBarEntryConfig topEntryConfig =
                        wifiApClientsMonthlyBarChart2.mWifiApMonthlyStackedProgressBarEntryConfig
                                .getTopEntryConfig();
                DataChartMarkerMonthly dataChartMarkerMonthly =
                        new DataChartMarkerMonthly(
                                context7,
                                categoryViewConstants$CategoryType,
                                wifiApClientsMonthlyBarChart2);
                dataChartMarkerMonthly.mDailyStackedProgressBarEntryConfigList = new ArrayList();
                new WifiApDataUsageConfig(0L);
                dataChartMarkerMonthly.mDailyStackedProgressBarEntryConfigList = list5;
                topEntryConfig.getTotalUsage();
                ((LinearLayout) dataChartMarkerMonthly.findViewById(R.id.common_marker_layout))
                        .setLayoutDirection(dataChartMarkerMonthly.mIsRTL ? 1 : 0);
                dataChartMarkerMonthly.mMarkerXLabelText =
                        (TextView) dataChartMarkerMonthly.findViewById(R.id.chart_marker_x_label);
                dataChartMarkerMonthly.mMarkerYValueText =
                        (TextView) dataChartMarkerMonthly.findViewById(R.id.chart_marker_y_value);
                dataChartMarkerMonthly.mMarkerHeight =
                        dataChartMarkerMonthly
                                .mContext
                                .getResources()
                                .getDimension(R.dimen.app_detail_chart_marker_layout_height);
                wifiApClientsMonthlyBarChart2.mMonthlyMarkerView = dataChartMarkerMonthly;
                wifiApClientsMonthlyBarChart2.mMarker = dataChartMarkerMonthly;
                wifiApClientsMonthlyBarChart2.setExtraOffsets(0.0f, 20.0f);
                float entryCount =
                        (wifiApClientsMonthlyBarChart2.mMonthlyBarData.getEntryCount()
                                        - wifiApClientsMonthlyBarChart2
                                                .mSelectedXAxisIndexInReverseOrder)
                                - 1.0f;
                Highlight[] highlightArr = {new Highlight(entryCount, entryCount)};
                wifiApClientsMonthlyBarChart2.mIndicesToHighlight = highlightArr;
                Highlight highlight = highlightArr[0];
                if (highlight == null) {
                    wifiApClientsMonthlyBarChart2.mChartTouchListener.mLastHighlighted = null;
                } else {
                    wifiApClientsMonthlyBarChart2.mChartTouchListener.mLastHighlighted = highlight;
                }
                wifiApClientsMonthlyBarChart2.invalidate();
                WeeklyXAxisRenderer weeklyXAxisRenderer =
                        (WeeklyXAxisRenderer) wifiApClientsMonthlyBarChart2.mXAxisRendererMonthly;
                weeklyXAxisRenderer.mSelectedInfoLoaded = true;
                weeklyXAxisRenderer.mSelectedLabel =
                        5 - wifiApClientsMonthlyBarChart2.mSelectedXAxisIndexInReverseOrder;
                wifiApClientsMonthlyBarChart2.invalidate();
                textView.setText(
                        this.mContext.getString(R.string.wifi_ap_last_data_six_month_used));
                List list6 = this.mMonthlyStackedProgressBarEntryConfigList;
                double d2 = 0.0d;
                if (list6 != null && ((ArrayList) list6).size() > 0) {
                    Iterator it10 =
                            ((ArrayList) this.mMonthlyStackedProgressBarEntryConfigList).iterator();
                    int i10 = 0;
                    double d3 = 0.0d;
                    while (it10.hasNext()) {
                        double d4 =
                                ((WifiApDailyStackedProgressBarEntryConfig) it10.next())
                                        .getTotalUsage()
                                        .mUsageValueInBytes;
                        if (d4 == 0.0d) {
                            i10++;
                        }
                        d3 += d4;
                    }
                    d2 =
                            d3
                                    / (((ArrayList) this.mMonthlyStackedProgressBarEntryConfigList)
                                                    .size()
                                            - i10);
                }
                textView2.setText(
                        new WifiApDataUsageConfig((long) d2)
                                .getUsageValueInLocaleString(this.mContext));
                textView4.setText(
                        String.format(
                                this.mContext.getString(R.string.wifi_ap_avg_number_used_per_month),
                                ApnSettings.MVNO_NONE));
                WifiApDataUsageConfig wifiApDataUsageConfig = new WifiApDataUsageConfig(0L);
                Iterator it11 =
                        ((ArrayList) this.mMonthlyStackedProgressBarEntryConfigList).iterator();
                while (it11.hasNext()) {
                    WifiApDailyStackedProgressBarEntryConfig
                            wifiApDailyStackedProgressBarEntryConfig6 =
                                    (WifiApDailyStackedProgressBarEntryConfig) it11.next();
                    if (WifiApDateUtils.isEqualsDate(
                            wifiApDailyStackedProgressBarEntryConfig6.mDateInMillis,
                            this.mSelectedMonthlyCalendar.getTimeInMillis())) {
                        wifiApDataUsageConfig =
                                wifiApDailyStackedProgressBarEntryConfig6.getTotalUsage();
                    }
                }
                textView3.setText(
                        String.format(
                                this.mContext.getString(
                                        R.string.wifi_ap_total_number_used_this_month),
                                wifiApDataUsageConfig.getUsageValueInLocaleString(this.mContext)));
                wifiApClientsMonthlyBarChart2.mOnBarSelectedCLickListener = new AnonymousClass1();
                return;
            }
            wifiApClientsWeeklyBarChart.setVisibility(0);
            wifiApClientsMonthlyBarChart.setVisibility(8);
            WifiApWeeklyStackedProgressBarEntryConfig wifiApWeeklyStackedProgressBarEntryConfig4 =
                    this.mSelectedWeeklyStackedProgressBarEntryConfig;
            wifiApClientsWeeklyBarChart.mWeeklyStackedProgressBarEntryConfig =
                    wifiApWeeklyStackedProgressBarEntryConfig4;
            List<WifiApDailyStackedProgressBarEntryConfig> list7 =
                    wifiApWeeklyStackedProgressBarEntryConfig4
                            .mDailyStackedProgressBarEntryConfigList;
            Calendar calendar17 = Calendar.getInstance();
            calendar17.setTime(
                    new Date(
                            WifiApDateUtils.getFirstDayOfWeek(
                                    wifiApClientsWeeklyBarChart.mWeeklyStackedProgressBarEntryConfig
                                            .mWeekOfYearCalendar.getTimeInMillis())));
            Calendar calendar18 = Calendar.getInstance();
            calendar18.setTime(
                    new Date(
                            WifiApDateUtils.getLastDayOfWeek(
                                    wifiApClientsWeeklyBarChart.mWeeklyStackedProgressBarEntryConfig
                                            .mWeekOfYearCalendar.getTimeInMillis())));
            Log.i(
                    "WifiApClientsWeeklyBarChart",
                    str11 + calendar17.getTime() + ", End calendar: " + calendar18.getTime());
            ((ArrayList) wifiApClientsWeeklyBarChart.mDailyStackedProgressBarEntryConfigList)
                    .clear();
            while (calendar17.getTimeInMillis() <= calendar18.getTimeInMillis()) {
                Calendar calendar19 = calendar18;
                WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig7 =
                        new WifiApDailyStackedProgressBarEntryConfig(
                                null, calendar17.getTimeInMillis());
                for (WifiApDailyStackedProgressBarEntryConfig
                        wifiApDailyStackedProgressBarEntryConfig8 : list7) {
                    if (WifiApDateUtils.isEqualsDate(
                            wifiApDailyStackedProgressBarEntryConfig8.mCalendar, calendar17)) {
                        wifiApDailyStackedProgressBarEntryConfig7 =
                                wifiApDailyStackedProgressBarEntryConfig8;
                    }
                }
                ((ArrayList) wifiApClientsWeeklyBarChart.mDailyStackedProgressBarEntryConfigList)
                        .add(wifiApDailyStackedProgressBarEntryConfig7);
                calendar17.add(6, 1);
                calendar18 = calendar19;
            }
            boolean z3 = WifiApSettingsUtils.DBG;
            if (wifiApClientsWeeklyBarChart.mCurrentWeekClick) {
                Calendar calendar20 = Calendar.getInstance();
                Iterator it12 =
                        ((ArrayList)
                                        wifiApClientsWeeklyBarChart
                                                .mDailyStackedProgressBarEntryConfigList)
                                .iterator();
                int i11 = 0;
                while (it12.hasNext()) {
                    if (WifiApDateUtils.isEqualsDate(
                            ((WifiApDailyStackedProgressBarEntryConfig) it12.next()).mCalendar,
                            calendar20)) {
                        wifiApClientsWeeklyBarChart.mShiftIndexFromRightToLeft = 6 - i11;
                    }
                    i11++;
                }
            }
            wifiApClientsWeeklyBarChart.mTopWifiApDataUsageConfig =
                    wifiApClientsWeeklyBarChart
                            .mWeeklyStackedProgressBarEntryConfig
                            .getTopEntryConfig()
                            .getTotalUsage();
            long firstDayOfWeek2 =
                    WifiApDateUtils.getFirstDayOfWeek(
                            wifiApWeeklyStackedProgressBarEntryConfig4.mWeekOfYearCalendar
                                    .getTimeInMillis());
            Log.i(
                    "WifiApClientsWeeklyBarChart",
                    "updating BarGraph for startDayOfWeek:"
                            + new Date(firstDayOfWeek2)
                            + ", endDayOfWeek: "
                            + WifiApDateUtils.getLastDayOfWeek(
                                    wifiApWeeklyStackedProgressBarEntryConfig4.mWeekOfYearCalendar
                                            .getTimeInMillis()));
            wifiApClientsWeeklyBarChart.mWeeklyIBarDataSets.clear();
            wifiApClientsWeeklyBarChart.mWeeklyBarEntryArrayList.clear();
            int i12 = 0;
            while (i12
                    < ((ArrayList)
                                    wifiApClientsWeeklyBarChart
                                            .mDailyStackedProgressBarEntryConfigList)
                            .size()) {
                List list8 =
                        ((WifiApDailyStackedProgressBarEntryConfig)
                                        ((ArrayList)
                                                        wifiApClientsWeeklyBarChart
                                                                .mDailyStackedProgressBarEntryConfigList)
                                                .get(i12))
                                .mProgressBarEntryConfigList;
                if (list8.isEmpty()) {
                    wifiApClientsWeeklyBarChart.mWeeklyBarEntryArrayList.add(
                            new BarEntry(i12, new float[] {0.0f, 0.0f, 0.0f, 0.0f}));
                } else if ("MB"
                        .equals(
                                wifiApClientsWeeklyBarChart.mTopWifiApDataUsageConfig
                                        .getUsageValueUnit(wifiApClientsWeeklyBarChart.mContext))) {
                    float[] fArr = new float[4];
                    fArr[0] = 0.0f;
                    fArr[1] = 0.0f;
                    fArr[2] = 0.0f;
                    fArr[3] = 0.0f;
                    Iterator it13 = list8.iterator();
                    int i13 = 3;
                    while (it13.hasNext()) {
                        fArr[i13] =
                                (float)
                                        ((WifiApProgressBarEntryConfig) it13.next())
                                                .mDataUsageConfig.getUsageValueInMB();
                        i13--;
                    }
                    wifiApClientsWeeklyBarChart.mWeeklyBarEntryArrayList.add(
                            new BarEntry(i12, fArr));
                } else {
                    float[] fArr2 = new float[4];
                    fArr2[0] = 0.0f;
                    fArr2[1] = 0.0f;
                    fArr2[2] = 0.0f;
                    fArr2[3] = 0.0f;
                    Iterator it14 = list8.iterator();
                    int i14 = 3;
                    while (it14.hasNext()) {
                        ((WifiApProgressBarEntryConfig) it14.next()).mDataUsageConfig.getClass();
                        fArr2[i14] =
                                (float)
                                        new BigDecimal(r13.mUsageValueInBytes / 1.0E9d)
                                                .setScale(2, RoundingMode.HALF_UP)
                                                .doubleValue();
                        i14--;
                        str15 = str15;
                    }
                    str2 = str15;
                    wifiApClientsWeeklyBarChart.mWeeklyBarEntryArrayList.add(
                            new BarEntry(i12, fArr2));
                    i12++;
                    str15 = str2;
                }
                str2 = str15;
                i12++;
                str15 = str2;
            }
            String str16 = str15;
            ((ArrayList) wifiApClientsWeeklyBarChart.mSelectedBarEntriesDataInMB).size();
            ArrayList arrayList14 = new ArrayList();
            for (int i15 = 3; i15 >= 0; i15--) {
                arrayList14.add(
                        Integer.valueOf(WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS[i15]));
            }
            BarDataSet barDataSet3 =
                    new BarDataSet(wifiApClientsWeeklyBarChart.mWeeklyBarEntryArrayList);
            wifiApClientsWeeklyBarChart.mWeeklyBarDataSet = barDataSet3;
            barDataSet3.mColors = arrayList14;
            wifiApClientsWeeklyBarChart.mWeeklyIBarDataSets.add(barDataSet3);
            BarData barData2 = new BarData(wifiApClientsWeeklyBarChart.mWeeklyIBarDataSets);
            wifiApClientsWeeklyBarChart.mWeeklyBarData = barData2;
            wifiApClientsWeeklyBarChart.setData(barData2);
            wifiApClientsWeeklyBarChart.mWeeklyBarData.mBarWidth = 0.5f;
            BarDataSet barDataSet4 = wifiApClientsWeeklyBarChart.mWeeklyBarDataSet;
            barDataSet4.getClass();
            barDataSet4.mValueTextSize = Utils.convertDpToPixel(11.0f);
            wifiApClientsWeeklyBarChart.mLegend.mEnabled = false;
            BarDataSet barDataSet5 = wifiApClientsWeeklyBarChart.mWeeklyBarDataSet;
            barDataSet5.mDrawIcons = true;
            barDataSet5.mDrawValues = false;
            wifiApClientsWeeklyBarChart.mHighlightFullBarEnabled = true;
            wifiApClientsWeeklyBarChart.setHovered(true);
            wifiApClientsWeeklyBarChart.mDescription.mEnabled = false;
            wifiApClientsWeeklyBarChart.mScaleXEnabled = false;
            wifiApClientsWeeklyBarChart.mScaleYEnabled = false;
            wifiApClientsWeeklyBarChart.mDoubleTapToZoomEnabled = false;
            wifiApClientsWeeklyBarChart.mDragXEnabled = true;
            wifiApClientsWeeklyBarChart.mDragYEnabled = true;
            wifiApClientsWeeklyBarChart.setVisibleXRangeMaximum(7.0f);
            wifiApClientsWeeklyBarChart.onWindowFocusChanged(true);
            wifiApClientsWeeklyBarChart.mSelectionListener =
                    wifiApClientsWeeklyBarChart.chartValueSelected;
            wifiApClientsWeeklyBarChart.mGestureListener =
                    wifiApClientsWeeklyBarChart.chartGestureListener;
            wifiApClientsWeeklyBarChart.mAutoScaleMinMaxEnabled = true;
            XAxis xAxis2 = wifiApClientsWeeklyBarChart.mXAxis;
            YAxis yAxis3 = wifiApClientsWeeklyBarChart.mAxisLeft;
            YAxis yAxis4 = wifiApClientsWeeklyBarChart.mAxisRight;
            boolean z4 = WifiApSettingsUtils.DBG;
            yAxis3.mDrawGridLines = false;
            xAxis2.mDrawGridLines = false;
            yAxis3.mEnabled = false;
            yAxis3.setAxisMinimum(0.0f);
            float f2 = yAxis4.mAxisMaximum;
            yAxis4.mGranularityEnabled = true;
            yAxis4.setGranularity();
            YAxis.AxisDependency axisDependency2 = yAxis4.mAxisDependency;
            if (f2 < 3.0f) {
                wifiApClientsWeeklyBarChart.setVisibleYRange(0.0f, 3.0f, axisDependency2);
            } else {
                wifiApClientsWeeklyBarChart.setVisibleYRange(
                        f2 * 1.1f, f2 * 1.25f, axisDependency2);
            }
            yAxis4.setLabelCount(4);
            yAxis4.mDrawAxisLine = false;
            yAxis4.setAxisMinimum(0.0f);
            wifiApClientsWeeklyBarChart.mDragDecelerationEnabled = false;
            wifiApClientsWeeklyBarChart.mDragDecelerationFrictionCoef = 0.0f;
            wifiApClientsWeeklyBarChart.mTouchEnabled = true;
            Context context8 = wifiApClientsWeeklyBarChart.mContext;
            CategoryViewConstants$CategoryType categoryViewConstants$CategoryType2 =
                    CategoryViewConstants$CategoryType.CATEGORY_DATA;
            wifiApClientsWeeklyBarChart.mXAxisRenderer =
                    ChartConfigSelector.getXAxisRenderer(
                            context8,
                            categoryViewConstants$CategoryType2,
                            wifiApClientsWeeklyBarChart);
            WeeklyChartXLabelFormatter weeklyChartXLabelFormatter =
                    new WeeklyChartXLabelFormatter();
            weeklyChartXLabelFormatter.mLabels = new ArrayList();
            TextStyle textStyle = TextStyle.NARROW_STANDALONE;
            ArrayList arrayList15 = new ArrayList();
            int value = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek().getValue();
            int i16 = 0;
            while (true) {
                str = str16;
                if (i16 >= DayOfWeek.SUNDAY.getValue()) {
                    break;
                }
                if (value > 7) {
                    value = DayOfWeek.MONDAY.getValue();
                }
                arrayList15.add(DayOfWeek.of(value).getDisplayName(textStyle, Locale.getDefault()));
                value++;
                i16++;
                str16 = str;
                textView4 = textView4;
            }
            TextView textView5 = textView4;
            if (ViewUtils.isRTL()) {
                Collections.reverse(arrayList15);
            }
            weeklyChartXLabelFormatter.mLabels.clear();
            weeklyChartXLabelFormatter.mLabels.addAll(arrayList15);
            wifiApClientsWeeklyBarChart.mXLabelFormatter = weeklyChartXLabelFormatter;
            wifiApClientsWeeklyBarChart.mWeeklyBarRenderer =
                    ChartConfigSelector.getBarChartRenderer(
                            wifiApClientsWeeklyBarChart.mContext,
                            categoryViewConstants$CategoryType2,
                            wifiApClientsWeeklyBarChart);
            WeeklyChartXLabelFormatter weeklyChartXLabelFormatter2 =
                    wifiApClientsWeeklyBarChart.mXLabelFormatter;
            if (weeklyChartXLabelFormatter2 == null) {
                xAxis2.mAxisValueFormatter = new DefaultAxisValueFormatter(xAxis2.mDecimals);
            } else {
                xAxis2.mAxisValueFormatter = weeklyChartXLabelFormatter2;
            }
            wifiApClientsWeeklyBarChart.mXAxisRenderer = wifiApClientsWeeklyBarChart.mXAxisRenderer;
            AbsRoundBarRenderer absRoundBarRenderer =
                    wifiApClientsWeeklyBarChart.mWeeklyBarRenderer;
            if (absRoundBarRenderer != null) {
                wifiApClientsWeeklyBarChart.mRenderer = absRoundBarRenderer;
            }
            wifiApClientsWeeklyBarChart.setExtraOffsets(0.0f, 20.0f);
            xAxis2.mPosition = xAxisPosition;
            xAxis2.mTextColor =
                    wifiApClientsWeeklyBarChart
                            .getContext()
                            .getColor(R.color.wifi_ap_secondary_text_color);
            ArrayList arrayList16 = new ArrayList();
            for (int i17 = 0;
                    i17
                            < ((ArrayList)
                                            wifiApClientsWeeklyBarChart
                                                    .mDailyStackedProgressBarEntryConfigList)
                                    .size();
                    i17++) {
                arrayList16.add(
                        i17,
                        new SimpleDateFormat("dd")
                                .format(
                                        ((WifiApDailyStackedProgressBarEntryConfig)
                                                        ((ArrayList)
                                                                        wifiApClientsWeeklyBarChart
                                                                                .mDailyStackedProgressBarEntryConfigList)
                                                                .get(i17))
                                                .mCalendar.getTime()));
            }
            xAxis2.mAxisValueFormatter = new IndexAxisValueFormatter(arrayList16);
            yAxis4.mTextColor =
                    wifiApClientsWeeklyBarChart.mContext.getColor(
                            R.color.wifi_ap_secondary_text_color);
            yAxis4.mTypeface = Typeface.create(Typeface.create("sec", 0), 400, false);
            yAxis4.setTextSize(11.0f);
            yAxis4.mTextColor =
                    wifiApClientsWeeklyBarChart
                            .getContext()
                            .getColor(R.color.wifi_ap_graph_xy_axis_text_color);
            Context context9 = wifiApClientsWeeklyBarChart.mContext;
            WifiApDataUsageIAxisValueFormatter wifiApDataUsageIAxisValueFormatter2 =
                    new WifiApDataUsageIAxisValueFormatter(context9);
            String usageValueUnit2 =
                    wifiApClientsWeeklyBarChart.mTopWifiApDataUsageConfig.getUsageValueUnit(
                            context9);
            if (usageValueUnit2.contentEquals("MB") || usageValueUnit2.contentEquals("GB")) {
                wifiApDataUsageIAxisValueFormatter2.mDataUsageUnit = usageValueUnit2;
            } else {
                Log.i("WifiApDataUsageIAxisValueFormatter", str);
                wifiApDataUsageIAxisValueFormatter2.mDataUsageUnit = "MB";
            }
            yAxis4.mAxisValueFormatter = wifiApDataUsageIAxisValueFormatter2;
            wifiApClientsWeeklyBarChart.mDrawMarkers = true;
            Context context10 = wifiApClientsWeeklyBarChart.mContext;
            List list9 = wifiApClientsWeeklyBarChart.mDailyStackedProgressBarEntryConfigList;
            WifiApDailyStackedProgressBarEntryConfig topEntryConfig2 =
                    wifiApClientsWeeklyBarChart.mWeeklyStackedProgressBarEntryConfig
                            .getTopEntryConfig();
            DataChartMarker dataChartMarker =
                    new DataChartMarker(
                            context10,
                            categoryViewConstants$CategoryType2,
                            wifiApClientsWeeklyBarChart);
            dataChartMarker.mDailyStackedProgressBarEntryConfigList = new ArrayList();
            new WifiApDataUsageConfig(0L);
            dataChartMarker.mDailyStackedProgressBarEntryConfigList = list9;
            topEntryConfig2.getTotalUsage();
            ((LinearLayout) dataChartMarker.findViewById(R.id.common_marker_layout))
                    .setLayoutDirection(dataChartMarker.mIsRTL ? 1 : 0);
            dataChartMarker.mMarkerXLabelText =
                    (TextView) dataChartMarker.findViewById(R.id.chart_marker_x_label);
            dataChartMarker.mMarkerYValueText =
                    (TextView) dataChartMarker.findViewById(R.id.chart_marker_y_value);
            dataChartMarker.mMarkerHeight =
                    dataChartMarker
                            .mContext
                            .getResources()
                            .getDimension(R.dimen.app_detail_chart_marker_layout_height);
            wifiApClientsWeeklyBarChart.mMarker = dataChartMarker;
            float entryCount2 = wifiApClientsWeeklyBarChart.mWeeklyBarData.getEntryCount();
            wifiApClientsWeeklyBarChart.mIndexOfBarChartToBeHighlight = entryCount2;
            float f3 =
                    (entryCount2 - wifiApClientsWeeklyBarChart.mShiftIndexFromRightToLeft) - 1.0f;
            Highlight[] highlightArr2 = {new Highlight(f3, f3)};
            wifiApClientsWeeklyBarChart.mIndicesToHighlight = highlightArr2;
            Highlight highlight2 = highlightArr2[0];
            if (highlight2 == null) {
                wifiApClientsWeeklyBarChart.mChartTouchListener.mLastHighlighted = null;
            } else {
                wifiApClientsWeeklyBarChart.mChartTouchListener.mLastHighlighted = highlight2;
            }
            wifiApClientsWeeklyBarChart.invalidate();
            wifiApClientsWeeklyBarChart.updateBarSelectedListener$1(
                    ((WifiApDailyStackedProgressBarEntryConfig)
                                    ((ArrayList)
                                                    wifiApClientsWeeklyBarChart
                                                            .mDailyStackedProgressBarEntryConfigList)
                                            .get(
                                                    (int)
                                                            ((wifiApClientsWeeklyBarChart
                                                                                    .mIndexOfBarChartToBeHighlight
                                                                            - wifiApClientsWeeklyBarChart
                                                                                    .mShiftIndexFromRightToLeft)
                                                                    - 1.0f)))
                            .mDateInMillis);
            WeeklyXAxisRenderer weeklyXAxisRenderer2 =
                    (WeeklyXAxisRenderer) wifiApClientsWeeklyBarChart.mXAxisRenderer;
            weeklyXAxisRenderer2.mSelectedInfoLoaded = true;
            weeklyXAxisRenderer2.mSelectedLabel =
                    6 - wifiApClientsWeeklyBarChart.mShiftIndexFromRightToLeft;
            wifiApClientsWeeklyBarChart.invalidate();
            textView.setText(this.mContext.getString(R.string.wifi_ap_last_seven_days_data_used));
            WifiApWeeklyStackedProgressBarEntryConfig wifiApWeeklyStackedProgressBarEntryConfig5 =
                    this.mSelectedWeeklyStackedProgressBarEntryConfig;
            Calendar calendar21 = wifiApWeeklyStackedProgressBarEntryConfig5.mWeekOfYearCalendar;
            Integer num3 = WifiApDateUtils.NUMBER_OF_DAYS_IN_WEEK;
            Calendar calendar22 = Calendar.getInstance();
            int i18 = calendar22.get(3);
            int i19 = calendar22.get(1);
            int i20 = calendar21.get(3);
            int i21 = calendar21.get(1);
            if (i18 == i20 && i19 == i21) {
                d = wifiApWeeklyStackedProgressBarEntryConfig5.getTotalUsage().mUsageValueInBytes;
                intValue = WifiApDateUtils.getNumberOfDaysElapsedInCurrentWeek();
            } else {
                d = wifiApWeeklyStackedProgressBarEntryConfig5.getTotalUsage().mUsageValueInBytes;
                intValue = WifiApDateUtils.NUMBER_OF_DAYS_IN_WEEK.intValue();
            }
            WifiApDataUsageConfig wifiApDataUsageConfig2 =
                    new WifiApDataUsageConfig((long) (d / intValue));
            Log.i(
                    "WifiApWeeklyStackedProgressBarEntryConfig",
                    "Average Usage(MB): "
                            + wifiApDataUsageConfig2.getUsageValueInMB()
                            + ", "
                            + wifiApWeeklyStackedProgressBarEntryConfig5.weekOfYearToString()
                            + ", "
                            + WifiApDateUtils.getNumberOfDaysElapsedInCurrentWeek());
            textView2.setText(wifiApDataUsageConfig2.getUsageValueInLocaleString(this.mContext));
            textView5.setText(
                    String.format(
                            this.mContext.getString(R.string.wifi_ap_avg_number_per_day),
                            ApnSettings.MVNO_NONE));
            textView3.setText(
                    String.format(
                            this.mContext.getString(R.string.wifi_ap_total_number_used_this_week),
                            this.mSelectedWeeklyStackedProgressBarEntryConfig
                                    .getTotalUsage()
                                    .getUsageValueInLocaleString(this.mContext)));
            wifiApClientsWeeklyBarChart.mOnBarSelectedCLickListener = new AnonymousClass1();
            if (this.mFirstTime) {
                long currentTimeMillis2 = System.currentTimeMillis();
                Iterator it15 =
                        this.mSelectedWeeklyStackedProgressBarEntryConfig
                                .mDailyStackedProgressBarEntryConfigList.iterator();
                while (true) {
                    if (!it15.hasNext()) {
                        wifiApDailyStackedProgressBarEntryConfig = null;
                        break;
                    }
                    WifiApDailyStackedProgressBarEntryConfig
                            wifiApDailyStackedProgressBarEntryConfig9 =
                                    (WifiApDailyStackedProgressBarEntryConfig) it15.next();
                    if (WifiApDateUtils.isEqualsDate(
                            wifiApDailyStackedProgressBarEntryConfig9.mDateInMillis,
                            currentTimeMillis2)) {
                        wifiApDailyStackedProgressBarEntryConfig =
                                wifiApDailyStackedProgressBarEntryConfig9;
                        break;
                    }
                }
                if (wifiApDailyStackedProgressBarEntryConfig != null) {
                    updateDeviceList(wifiApDailyStackedProgressBarEntryConfig);
                }
                this.mFirstTime = false;
            }
        }
    }

    public final void removeDeviceList$1() {
        PreferenceCategory preferenceCategory = (PreferenceCategory) getParent();
        preferenceCategory.removePreferenceRecursively(String.valueOf(100));
        preferenceCategory.removePreferenceRecursively(String.valueOf(101));
        preferenceCategory.removePreferenceRecursively(String.valueOf(102));
        preferenceCategory.removePreferenceRecursively(String.valueOf(103));
    }

    public final void updateDeviceList(
            WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig) {
        removeDeviceList$1();
        List list = wifiApDailyStackedProgressBarEntryConfig.mProgressBarEntryConfigList;
        int size = list.size();
        Log.i("WifiApClientsReportBarChartPreference", "hotspotClientList.size(): " + size);
        PreferenceCategory preferenceCategory = (PreferenceCategory) getParent();
        for (int i = 0; i < size; i++) {
            int i2 = i + 100;
            int i3 = WifiApStackedProgressBarPreference.PROGRESS_COLORS[i];
            WifiApProgressBarEntryConfig wifiApProgressBarEntryConfig =
                    (WifiApProgressBarEntryConfig) list.get(i);
            String str = wifiApProgressBarEntryConfig.mProgressEntryName;
            Context context = this.mContext;
            String usageValueInLocaleString =
                    wifiApProgressBarEntryConfig.mDataUsageConfig.getUsageValueInLocaleString(
                            context);
            WifiApClientPreference wifiApClientPreference = new WifiApClientPreference(context);
            wifiApClientPreference.setLayoutResource(
                    R.layout.sec_wifi_ap_progress_bar_list_preference);
            wifiApClientPreference.mContext = context;
            wifiApClientPreference.mWarningColor = context.getColor(R.color.wifi_ap_warning_color);
            int colorFromAttribute =
                    WifiApSettingsUtils.getColorFromAttribute(
                            wifiApClientPreference.getContext(), android.R.attr.textColorPrimary);
            wifiApClientPreference.mSummaryColor = colorFromAttribute;
            wifiApClientPreference.setSummaryTextColor(colorFromAttribute);
            wifiApClientPreference.setIcon(
                    context.getDrawable(R.drawable.sec_wifi_ap_progress_index_dot));
            ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
            scaleType.getClass();
            if (wifiApClientPreference.mIconScaleType != scaleType) {
                wifiApClientPreference.mIconScaleType = scaleType;
                wifiApClientPreference.notifyChanged();
            }
            wifiApClientPreference.mIconWidthInPx =
                    WifiApSettingsUtils.convertDpToPixel(
                            ((WifiApPreference) wifiApClientPreference).mContext, -2);
            wifiApClientPreference.mIconHeightInPx =
                    WifiApSettingsUtils.convertDpToPixel(
                            ((WifiApPreference) wifiApClientPreference).mContext, 40);
            wifiApClientPreference.notifyChanged();
            wifiApClientPreference.mIconColor = i3;
            if (TextUtils.isEmpty(str)) {
                wifiApClientPreference.setTitle(R.string.wifi_ap_other_devices);
            } else {
                wifiApClientPreference.setTitle(str);
            }
            wifiApClientPreference.mDividerAllowedAbove = false;
            wifiApClientPreference.mDividerAllowedBelow = false;
            wifiApClientPreference.notifyChanged();
            wifiApClientPreference.setSummary(usageValueInLocaleString);
            wifiApClientPreference.setOnPreferenceClickListener(null);
            wifiApClientPreference.setOrder(i2);
            wifiApClientPreference.mDividerAllowedAbove = false;
            wifiApClientPreference.mDividerAllowedBelow = false;
            wifiApClientPreference.notifyChanged();
            wifiApClientPreference.mSecondaryAlertIconClickListener = null;
            wifiApClientPreference.notifyChanged();
            wifiApClientPreference.setSecondaryIcon(null);
            wifiApClientPreference.mProgressBarVisibility = false;
            wifiApClientPreference.notifyChanged();
            wifiApClientPreference.setSecondaryIconDividerVisibility(false);
            wifiApClientPreference.setSelectable(true);
            wifiApClientPreference.setEnabled(true);
            wifiApClientPreference.setKey(String.valueOf(i2));
            preferenceCategory.addPreference(wifiApClientPreference);
        }
        if (size <= 0
                || preferenceCategory.findPreference("sec_wifi_ap_dummy_preference") == null) {
            return;
        }
        preferenceCategory.findPreference("sec_wifi_ap_dummy_preference").setVisible(true);
    }

    public WifiApClientsReportBarChartPreference(
            Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApClientsReportBarChartPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApClientsReportBarChartPreference(Context context) {
        this(context, null);
    }
}
