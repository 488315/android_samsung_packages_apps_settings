package com.samsung.android.settings.wifi.mobileap.clients.report;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApDateUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApCalendarRangeSelectorPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientsReportCalendarRangeSelectorPreference
        extends WifiApCalendarRangeSelectorPreference {
    public final Context mContext;
    public final ArrayList mMonthDateInMillisArrayList;
    public final AnonymousClass1 mNextButtonClickListener;
    public final AnonymousClass1 mPreviousButtonClickListener;
    public Long mSelectedMonthDateInMillis;
    public Long mSelectedWeekDateInMillis;
    public final ArrayList mWeekDateInMillisArrayList;
    public WifiApHotspotUsageReport mWifiApHotspotUsageReport;

    /* JADX WARN: Type inference failed for: r10v4, types: [com.samsung.android.settings.wifi.mobileap.clients.report.WifiApClientsReportCalendarRangeSelectorPreference$1] */
    /* JADX WARN: Type inference failed for: r10v5, types: [com.samsung.android.settings.wifi.mobileap.clients.report.WifiApClientsReportCalendarRangeSelectorPreference$1] */
    public WifiApClientsReportCalendarRangeSelectorPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mWeekDateInMillisArrayList = new ArrayList();
        this.mSelectedWeekDateInMillis = 0L;
        this.mMonthDateInMillisArrayList = new ArrayList();
        this.mSelectedMonthDateInMillis = 0L;
        final int i3 = 0;
        this.mPreviousButtonClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.mobileap.clients.report.WifiApClientsReportCalendarRangeSelectorPreference.1
                    public final /* synthetic */ WifiApClientsReportCalendarRangeSelectorPreference
                            this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i3) {
                            case 0:
                                WifiApClientsReportCalendarRangeSelectorPreference
                                        wifiApClientsReportCalendarRangeSelectorPreference =
                                                this.this$0;
                                if (wifiApClientsReportCalendarRangeSelectorPreference
                                                .mWifiApHotspotUsageReport
                                                .mWeeklyAndMonthlyTabPreference
                                                .mTabIndexSelected
                                        == 0) {
                                    int indexOf =
                                            wifiApClientsReportCalendarRangeSelectorPreference
                                                    .mWeekDateInMillisArrayList.indexOf(
                                                    wifiApClientsReportCalendarRangeSelectorPreference
                                                            .mSelectedWeekDateInMillis);
                                    if (indexOf > 0) {
                                        WifiApClientsReportCalendarRangeSelectorPreference
                                                wifiApClientsReportCalendarRangeSelectorPreference2 =
                                                        this.this$0;
                                        wifiApClientsReportCalendarRangeSelectorPreference2
                                                        .mSelectedWeekDateInMillis =
                                                (Long)
                                                        wifiApClientsReportCalendarRangeSelectorPreference2
                                                                .mWeekDateInMillisArrayList.get(
                                                                indexOf - 1);
                                    }
                                } else {
                                    int findMatchingMonthIndex =
                                            wifiApClientsReportCalendarRangeSelectorPreference
                                                    .findMatchingMonthIndex();
                                    if (findMatchingMonthIndex > 0) {
                                        WifiApClientsReportCalendarRangeSelectorPreference
                                                wifiApClientsReportCalendarRangeSelectorPreference3 =
                                                        this.this$0;
                                        wifiApClientsReportCalendarRangeSelectorPreference3
                                                        .mSelectedMonthDateInMillis =
                                                (Long)
                                                        wifiApClientsReportCalendarRangeSelectorPreference3
                                                                .mMonthDateInMillisArrayList.get(
                                                                findMatchingMonthIndex - 1);
                                    }
                                }
                                this.this$0.updatePreference();
                                this.this$0.mWifiApHotspotUsageReport.mReportBarChartPreference
                                        .notifyChanged();
                                break;
                            default:
                                WifiApClientsReportCalendarRangeSelectorPreference
                                        wifiApClientsReportCalendarRangeSelectorPreference4 =
                                                this.this$0;
                                if (wifiApClientsReportCalendarRangeSelectorPreference4
                                                .mWifiApHotspotUsageReport
                                                .mWeeklyAndMonthlyTabPreference
                                                .mTabIndexSelected
                                        == 0) {
                                    int indexOf2 =
                                            wifiApClientsReportCalendarRangeSelectorPreference4
                                                    .mWeekDateInMillisArrayList.indexOf(
                                                    wifiApClientsReportCalendarRangeSelectorPreference4
                                                            .mSelectedWeekDateInMillis);
                                    if (indexOf2
                                            < this.this$0.mWeekDateInMillisArrayList.size() - 1) {
                                        WifiApClientsReportCalendarRangeSelectorPreference
                                                wifiApClientsReportCalendarRangeSelectorPreference5 =
                                                        this.this$0;
                                        wifiApClientsReportCalendarRangeSelectorPreference5
                                                        .mSelectedWeekDateInMillis =
                                                (Long)
                                                        wifiApClientsReportCalendarRangeSelectorPreference5
                                                                .mWeekDateInMillisArrayList.get(
                                                                indexOf2 + 1);
                                    }
                                } else {
                                    int findMatchingMonthIndex2 =
                                            wifiApClientsReportCalendarRangeSelectorPreference4
                                                    .findMatchingMonthIndex();
                                    if (findMatchingMonthIndex2
                                            < this.this$0.mMonthDateInMillisArrayList.size() - 1) {
                                        WifiApClientsReportCalendarRangeSelectorPreference
                                                wifiApClientsReportCalendarRangeSelectorPreference6 =
                                                        this.this$0;
                                        wifiApClientsReportCalendarRangeSelectorPreference6
                                                        .mSelectedMonthDateInMillis =
                                                (Long)
                                                        wifiApClientsReportCalendarRangeSelectorPreference6
                                                                .mMonthDateInMillisArrayList.get(
                                                                findMatchingMonthIndex2 + 1);
                                    }
                                }
                                this.this$0.updatePreference();
                                this.this$0.mWifiApHotspotUsageReport.mReportBarChartPreference
                                        .notifyChanged();
                                break;
                        }
                    }
                };
        final int i4 = 1;
        this.mNextButtonClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.mobileap.clients.report.WifiApClientsReportCalendarRangeSelectorPreference.1
                    public final /* synthetic */ WifiApClientsReportCalendarRangeSelectorPreference
                            this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i4) {
                            case 0:
                                WifiApClientsReportCalendarRangeSelectorPreference
                                        wifiApClientsReportCalendarRangeSelectorPreference =
                                                this.this$0;
                                if (wifiApClientsReportCalendarRangeSelectorPreference
                                                .mWifiApHotspotUsageReport
                                                .mWeeklyAndMonthlyTabPreference
                                                .mTabIndexSelected
                                        == 0) {
                                    int indexOf =
                                            wifiApClientsReportCalendarRangeSelectorPreference
                                                    .mWeekDateInMillisArrayList.indexOf(
                                                    wifiApClientsReportCalendarRangeSelectorPreference
                                                            .mSelectedWeekDateInMillis);
                                    if (indexOf > 0) {
                                        WifiApClientsReportCalendarRangeSelectorPreference
                                                wifiApClientsReportCalendarRangeSelectorPreference2 =
                                                        this.this$0;
                                        wifiApClientsReportCalendarRangeSelectorPreference2
                                                        .mSelectedWeekDateInMillis =
                                                (Long)
                                                        wifiApClientsReportCalendarRangeSelectorPreference2
                                                                .mWeekDateInMillisArrayList.get(
                                                                indexOf - 1);
                                    }
                                } else {
                                    int findMatchingMonthIndex =
                                            wifiApClientsReportCalendarRangeSelectorPreference
                                                    .findMatchingMonthIndex();
                                    if (findMatchingMonthIndex > 0) {
                                        WifiApClientsReportCalendarRangeSelectorPreference
                                                wifiApClientsReportCalendarRangeSelectorPreference3 =
                                                        this.this$0;
                                        wifiApClientsReportCalendarRangeSelectorPreference3
                                                        .mSelectedMonthDateInMillis =
                                                (Long)
                                                        wifiApClientsReportCalendarRangeSelectorPreference3
                                                                .mMonthDateInMillisArrayList.get(
                                                                findMatchingMonthIndex - 1);
                                    }
                                }
                                this.this$0.updatePreference();
                                this.this$0.mWifiApHotspotUsageReport.mReportBarChartPreference
                                        .notifyChanged();
                                break;
                            default:
                                WifiApClientsReportCalendarRangeSelectorPreference
                                        wifiApClientsReportCalendarRangeSelectorPreference4 =
                                                this.this$0;
                                if (wifiApClientsReportCalendarRangeSelectorPreference4
                                                .mWifiApHotspotUsageReport
                                                .mWeeklyAndMonthlyTabPreference
                                                .mTabIndexSelected
                                        == 0) {
                                    int indexOf2 =
                                            wifiApClientsReportCalendarRangeSelectorPreference4
                                                    .mWeekDateInMillisArrayList.indexOf(
                                                    wifiApClientsReportCalendarRangeSelectorPreference4
                                                            .mSelectedWeekDateInMillis);
                                    if (indexOf2
                                            < this.this$0.mWeekDateInMillisArrayList.size() - 1) {
                                        WifiApClientsReportCalendarRangeSelectorPreference
                                                wifiApClientsReportCalendarRangeSelectorPreference5 =
                                                        this.this$0;
                                        wifiApClientsReportCalendarRangeSelectorPreference5
                                                        .mSelectedWeekDateInMillis =
                                                (Long)
                                                        wifiApClientsReportCalendarRangeSelectorPreference5
                                                                .mWeekDateInMillisArrayList.get(
                                                                indexOf2 + 1);
                                    }
                                } else {
                                    int findMatchingMonthIndex2 =
                                            wifiApClientsReportCalendarRangeSelectorPreference4
                                                    .findMatchingMonthIndex();
                                    if (findMatchingMonthIndex2
                                            < this.this$0.mMonthDateInMillisArrayList.size() - 1) {
                                        WifiApClientsReportCalendarRangeSelectorPreference
                                                wifiApClientsReportCalendarRangeSelectorPreference6 =
                                                        this.this$0;
                                        wifiApClientsReportCalendarRangeSelectorPreference6
                                                        .mSelectedMonthDateInMillis =
                                                (Long)
                                                        wifiApClientsReportCalendarRangeSelectorPreference6
                                                                .mMonthDateInMillisArrayList.get(
                                                                findMatchingMonthIndex2 + 1);
                                    }
                                }
                                this.this$0.updatePreference();
                                this.this$0.mWifiApHotspotUsageReport.mReportBarChartPreference
                                        .notifyChanged();
                                break;
                        }
                    }
                };
        this.mContext = context;
        System.currentTimeMillis();
        this.mWeekDateInMillisArrayList = new ArrayList();
        this.mMonthDateInMillisArrayList = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, -5);
        calendar.set(5, 1);
        Calendar calendar2 = Calendar.getInstance();
        while (true) {
            if (calendar.getTimeInMillis() > calendar2.getTimeInMillis()
                    && calendar.get(3) != calendar2.get(3)) {
                break;
            }
            Log.i(
                    "WifiApClientsReportCalendarRangeSelectorPreference",
                    "Adding Weekly date: "
                            + WifiApDateUtils.getDateRangeString(
                                    this.mContext, Long.valueOf(calendar.getTimeInMillis())));
            this.mWeekDateInMillisArrayList.add(Long.valueOf(calendar.getTimeInMillis()));
            calendar.add(3, 1);
        }
        this.mSelectedWeekDateInMillis =
                (Long)
                        AlertController$$ExternalSyntheticOutline0.m(
                                1, this.mWeekDateInMillisArrayList);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(2, -5);
        calendar3.set(5, 1);
        while (true) {
            if (calendar3.getTimeInMillis() > calendar2.getTimeInMillis()
                    && calendar3.get(2) != calendar2.get(2)) {
                this.mSelectedMonthDateInMillis =
                        (Long)
                                AlertController$$ExternalSyntheticOutline0.m(
                                        1, this.mMonthDateInMillisArrayList);
                super.mNextButtonClickListener = this.mNextButtonClickListener;
                super.mPreviousButtonClickListener = this.mPreviousButtonClickListener;
                seslSetSummaryColor(this.mContext.getColor(R.color.wifi_ap_title_text_color));
                return;
            }
            Log.i(
                    "WifiApClientsReportCalendarRangeSelectorPreference",
                    "Adding Monthly date: "
                            + WifiApDateUtils.getDateRangeString(
                                    this.mContext, Long.valueOf(calendar3.getTimeInMillis())));
            this.mMonthDateInMillisArrayList.add(Long.valueOf(calendar3.getTimeInMillis()));
            calendar3.add(2, 1);
        }
    }

    public final int findMatchingMonthIndex() {
        Iterator it = this.mMonthDateInMillisArrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            long longValue = ((Long) it.next()).longValue();
            long longValue2 = this.mSelectedMonthDateInMillis.longValue();
            Integer num = WifiApDateUtils.NUMBER_OF_DAYS_IN_WEEK;
            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar.setTime(new Date(longValue));
            calendar2.setTime(new Date(longValue2));
            if (WifiApDateUtils.isEqualsDate(calendar, calendar2)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public final void updatePreference() {
        if (this.mWifiApHotspotUsageReport.mWeeklyAndMonthlyTabPreference.mTabIndexSelected == 0) {
            setSummary(
                    WifiApDateUtils.getDateRangeString(
                            this.mContext, this.mSelectedWeekDateInMillis));
            int indexOf = this.mWeekDateInMillisArrayList.indexOf(this.mSelectedWeekDateInMillis);
            if (indexOf <= 0) {
                this.mIsPreviousButtonEnabled = false;
            } else {
                this.mIsPreviousButtonEnabled = true;
            }
            if (indexOf == this.mWeekDateInMillisArrayList.size() - 1) {
                this.mIsNextButtonEnabled = false;
                return;
            } else {
                this.mIsNextButtonEnabled = true;
                return;
            }
        }
        Context context = this.mContext;
        Long l = this.mSelectedMonthDateInMillis;
        Integer num = WifiApDateUtils.NUMBER_OF_DAYS_IN_WEEK;
        setSummary(DateUtils.formatDateTime(context, l.longValue(), 56));
        int findMatchingMonthIndex = findMatchingMonthIndex();
        if (findMatchingMonthIndex <= 0) {
            this.mIsPreviousButtonEnabled = false;
        } else {
            this.mIsPreviousButtonEnabled = true;
        }
        if (findMatchingMonthIndex == this.mMonthDateInMillisArrayList.size() - 1) {
            this.mIsNextButtonEnabled = false;
        } else {
            this.mIsNextButtonEnabled = true;
        }
    }

    public WifiApClientsReportCalendarRangeSelectorPreference(
            Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApClientsReportCalendarRangeSelectorPreference(
            Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApClientsReportCalendarRangeSelectorPreference(Context context) {
        this(context, null);
    }
}
