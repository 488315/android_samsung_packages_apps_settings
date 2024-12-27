package com.samsung.android.settings.wifi.develop.history.view;

import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;

import com.samsung.android.settings.wifi.develop.history.model.UsabilityStats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ChartDataFactory$BaseChartData {
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT =
            new SimpleDateFormat("MM-dd HH:mm:ss");
    public long mMaxDomainValue;
    public long mMaxRangeValue;
    public List mPoints = new ArrayList();
    public List mSecondPoints = new ArrayList();
    public long mStartTime;

    public ChartDataFactory$BaseChartData(List list) {
        if (list.isEmpty()) {
            return;
        }
        this.mStartTime = ((UsabilityStats) list.get(0)).mTimeStampMillis;
        this.mMaxDomainValue =
                ((UsabilityStats) PrioritySet$$ExternalSyntheticOutline0.m(1, list))
                                .mTimeStampMillis
                        - this.mStartTime;
    }

    public final Date[] getDomainAxisLabels() {
        long j = this.mMaxDomainValue;
        Calendar calendar = Calendar.getInstance();
        calendar.add(14, (int) (j * (-1)));
        return new Date[] {new Date(calendar.getTimeInMillis()), Calendar.getInstance().getTime()};
    }

    public long getDomainAxisTick() {
        return 3000L;
    }

    public abstract CharSequence[] getValueAxisLabels();

    public abstract long getValueAxisOffset();

    public boolean showValueLabel() {
        return this instanceof ApplicationHistoryChartPreference.C1TcpChart;
    }
}
