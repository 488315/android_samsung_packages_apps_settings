package com.samsung.android.settings.uwb.labs.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;

import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.server.uwb.bigdata.db.UwbDbHelper;
import com.samsung.android.server.uwb.bigdata.db.UwbDbReader;
import com.samsung.android.settings.uwb.labs.common.UwbLabsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class StatisticsDataManager {
    public static StatisticsDataManager INSTANCE = null;
    public static long mPrevDayRangingTime = 0;
    public static long mRangingTimeSum = 0;
    public static long mTodayRangingTime = 0;
    public static int mUwbState = 1;
    public final UwbDbReader mUwbDbReader;
    public static long mStateChangedTime = System.currentTimeMillis();
    public static final long[] mWeeklyRangingCnt = {0, 0, 0, 0, 0, 0, 0};
    public static final long[] mWeeklyRangingUsage = {0, 0, 0, 0, 0, 0, 0};
    public static final long[][] mWeeklyPackageUsage = {
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0}
    };
    public static final long[][] mWeeklyPackageUsageCnt = {
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0},
        new long[] {0, 0, 0}
    };

    public StatisticsDataManager(Context context) {
        UwbDbReader uwbDbReader = new UwbDbReader();
        if (UwbDbHelper.INSTANCE == null) {
            UwbDbHelper uwbDbHelper = new UwbDbHelper(context, "/data/uwb/uwb.db", null, 1);
            Log.d("UwbDbHelper", "BigDataDbHelper is created");
            UwbDbHelper.INSTANCE = uwbDbHelper;
        }
        SQLiteDatabase readableDatabase = UwbDbHelper.INSTANCE.getReadableDatabase();
        uwbDbReader.mDb = readableDatabase;
        if (readableDatabase == null) {
            Log.e("UwbDbReader", "database is not found");
        } else {
            Log.d("UwbDbReader", "database is opened : " + readableDatabase);
        }
        this.mUwbDbReader = uwbDbReader;
    }

    public static synchronized StatisticsDataManager getInstance(Context context) {
        StatisticsDataManager statisticsDataManager;
        synchronized (StatisticsDataManager.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new StatisticsDataManager(context);
                }
                statisticsDataManager = INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return statisticsDataManager;
    }

    public final boolean getUwbUsage() {
        long[][] jArr;
        long[][] jArr2;
        int i;
        StatisticsDataManager statisticsDataManager;
        char c;
        Iterator it;
        char c2;
        int i2;
        long j;
        int i3 = 1;
        long[] jArr3 = mWeeklyRangingUsage;
        Arrays.fill(jArr3, 0L);
        long[] jArr4 = mWeeklyRangingCnt;
        Arrays.fill(jArr4, 0L);
        int i4 = 0;
        while (true) {
            jArr = mWeeklyPackageUsageCnt;
            jArr2 = mWeeklyPackageUsage;
            if (i4 >= 7) {
                break;
            }
            Arrays.fill(jArr2[i4], 0L);
            Arrays.fill(jArr[i4], 0L);
            i4++;
        }
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i5 = calendar.get(7);
        int i6 = i5 - 1;
        if (i6 == 0) {
            statisticsDataManager = this;
            i = 6;
        } else {
            i = i5 - 2;
            statisticsDataManager = this;
        }
        UwbDbReader uwbDbReader = statisticsDataManager.mUwbDbReader;
        uwbDbReader.getClass();
        ArrayList arrayList = null;
        try {
            Cursor rawQuery = uwbDbReader.mDb.rawQuery("SELECT * FROM uwb_usage", null);
            if (rawQuery == null) {
                Log.e("UwbDbReader", "cursor is NULL");
            } else if (rawQuery.moveToFirst()) {
                ArrayList arrayList2 = new ArrayList();
                do {
                    UwbDbHelper uwbDbHelper = UwbDbHelper.INSTANCE;
                    arrayList2.add(
                            new UwbDbReader.UwbDbUsageData(
                                    rawQuery.getInt(1),
                                    rawQuery.getInt(3),
                                    rawQuery.getInt(4),
                                    rawQuery.getLong(5),
                                    rawQuery.getLong(6),
                                    rawQuery.getString(2)));
                } while (rawQuery.moveToNext());
                rawQuery.close();
                arrayList = arrayList2;
            } else {
                rawQuery.close();
            }
        } catch (SQLiteException e) {
            Log.e("UwbDbReader", "Error reading uwb.db file using SQLite3", e);
        }
        if (arrayList == null) {
            return false;
        }
        Iterator it2 = arrayList.iterator();
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        while (it2.hasNext()) {
            UwbDbReader.UwbDbUsageData uwbDbUsageData = (UwbDbReader.UwbDbUsageData) it2.next();
            uwbDbUsageData.getClass();
            long[][] jArr5 = jArr;
            long j5 = uwbDbUsageData.mUpdatedTime;
            Date date2 = new Date(j5);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);
            int i7 = calendar2.get(7) - i3;
            long[] jArr6 = jArr3;
            long hours = UwbLabsUtils.getHours(currentTimeMillis - j5) / 24;
            if (hours >= 7) {
                StringBuilder m =
                        SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                                currentTimeMillis, "exclude data: today - ", ", comparedDay - ");
                m.append(j5);
                m.append(" ,diff - ");
                m.append(hours);
                Log.i("StatisticsDataManager", m.toString());
                it = it2;
                i2 = i;
                c2 = 2;
                c = 0;
            } else {
                long j6 = uwbDbUsageData.mRangingDuration;
                j4 += j6;
                int i8 = i;
                if (i7 >= 7) {
                    Log.e("StatisticsDataManager", "failed to update: ranging time data is wrong.");
                } else {
                    jArr6[i7] = jArr6[i7] + j6;
                }
                String[] split = uwbDbUsageData.mPackageName.split("\\.");
                int length = split.length;
                String str = length == 0 ? ApnSettings.MVNO_NONE : split[length - 1];
                str.getClass();
                if (str.equals("uwbtest")) {
                    long[] jArr7 = jArr2[i7];
                    c = 0;
                    jArr7[0] = jArr7[0] + j6;
                    long[] jArr8 = jArr5[i7];
                    jArr8[0] = jArr8[0] + 1;
                } else {
                    if (str.equals("settings")) {
                        long[] jArr9 = jArr2[i7];
                        jArr9[1] = jArr9[1] + j6;
                        long[] jArr10 = jArr5[i7];
                        jArr10[1] = jArr10[1] + 1;
                    } else {
                        long[] jArr11 = jArr2[i7];
                        jArr11[2] = jArr11[2] + j6;
                        long[] jArr12 = jArr5[i7];
                        jArr12[2] = jArr12[2] + 1;
                    }
                    c = 0;
                }
                jArr4[i7] = jArr4[i7] + 1;
                StringBuilder m2 =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i7, "DAY[", "] package0 : ");
                long j7 = j2;
                m2.append(jArr2[i7][c]);
                m2.append(" package1 : ");
                it = it2;
                m2.append(jArr2[i7][1]);
                m2.append(" package2 : ");
                c2 = 2;
                m2.append(jArr2[i7][2]);
                Log.i("StatisticsDataManager", m2.toString());
                StringBuilder sb = new StringBuilder("Read Table: uwb_usage curtime [");
                sb.append(currentTimeMillis);
                sb.append("] today [");
                sb.append(i6);
                sb.append("] updatetime [");
                sb.append(j5);
                sb.append("] updateday [");
                sb.append(i7);
                sb.append("] rangingdurationtime [");
                sb.append(j6);
                Utils$$ExternalSyntheticOutline0.m(sb, "]", "StatisticsDataManager");
                if (i7 == i6) {
                    j = j7 + j6;
                    i2 = i8;
                } else {
                    i2 = i8;
                    j = j7;
                }
                if (i7 == i2) {
                    j3 += j6;
                }
                j2 = j;
            }
            it2 = it;
            i = i2;
            jArr = jArr5;
            jArr3 = jArr6;
            i3 = 1;
        }
        mTodayRangingTime = j2;
        mPrevDayRangingTime = j3;
        mRangingTimeSum = j4;
        return true;
    }
}
