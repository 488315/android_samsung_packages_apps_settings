package com.samsung.android.settings.uwb.labs.control.statistics;

import android.content.Context;

import com.samsung.android.settings.uwb.labs.data.StatisticsDataManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class StatisticsSummaryController {
    public final StatisticsDataManager mDataManager;

    public StatisticsSummaryController(Context context) {
        this.mDataManager = StatisticsDataManager.getInstance(context);
    }

    public final long getData(int i, int i2) {
        int i3;
        StatisticsDataManager statisticsDataManager = this.mDataManager;
        if (i == 0) {
            statisticsDataManager.getClass();
            i3 = StatisticsDataManager.mUwbState;
        } else {
            if (i == 1) {
                statisticsDataManager.getClass();
                return StatisticsDataManager.mStateChangedTime;
            }
            if (i == 2) {
                statisticsDataManager.getClass();
                return StatisticsDataManager.mTodayRangingTime;
            }
            if (i == 3) {
                statisticsDataManager.getClass();
                return StatisticsDataManager.mPrevDayRangingTime;
            }
            if (i == 4) {
                statisticsDataManager.getClass();
                return StatisticsDataManager.mRangingTimeSum;
            }
            if (i != 5) {
                return -1L;
            }
            statisticsDataManager.getClass();
            i3 = 0;
        }
        return i3;
    }
}
