package com.samsung.android.settings.uwb.labs.control.statistics;

import android.content.Context;

import com.samsung.android.settings.uwb.labs.data.StatisticsDataManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyReportController {
    public final StatisticsDataManager mDataManager;

    public WeeklyReportController(Context context) {
        this.mDataManager = StatisticsDataManager.getInstance(context);
    }

    public final long getData(int i, int i2) {
        StatisticsDataManager statisticsDataManager = this.mDataManager;
        switch (i) {
            case 0:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyRangingUsage[0];
            case 1:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyRangingUsage[1];
            case 2:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyRangingUsage[2];
            case 3:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyRangingUsage[3];
            case 4:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyRangingUsage[4];
            case 5:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyRangingUsage[5];
            case 6:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyRangingUsage[6];
            default:
                switch (i) {
                    case 100:
                        statisticsDataManager.getClass();
                        return StatisticsDataManager.mWeeklyPackageUsage[0][i2];
                    case 101:
                        statisticsDataManager.getClass();
                        return StatisticsDataManager.mWeeklyPackageUsage[1][i2];
                    case 102:
                        statisticsDataManager.getClass();
                        return StatisticsDataManager.mWeeklyPackageUsage[2][i2];
                    case 103:
                        statisticsDataManager.getClass();
                        return StatisticsDataManager.mWeeklyPackageUsage[3][i2];
                    case 104:
                        statisticsDataManager.getClass();
                        return StatisticsDataManager.mWeeklyPackageUsage[4][i2];
                    case 105:
                        statisticsDataManager.getClass();
                        return StatisticsDataManager.mWeeklyPackageUsage[5][i2];
                    case 106:
                        statisticsDataManager.getClass();
                        return StatisticsDataManager.mWeeklyPackageUsage[6][i2];
                    default:
                        return -1L;
                }
        }
    }

    public final long[] getData(int i) {
        StatisticsDataManager statisticsDataManager = this.mDataManager;
        switch (i) {
            case 6:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyRangingCnt;
            case 7:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyPackageUsageCnt[0];
            case 8:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyPackageUsageCnt[1];
            case 9:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyPackageUsageCnt[2];
            case 10:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyPackageUsageCnt[3];
            case 11:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyPackageUsageCnt[4];
            case 12:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyPackageUsageCnt[5];
            case 13:
                statisticsDataManager.getClass();
                return StatisticsDataManager.mWeeklyPackageUsageCnt[6];
            default:
                return null;
        }
    }
}
