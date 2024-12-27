package com.samsung.android.settings.wifi.develop.history.model;

import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UsabilityStats {
    public final int mLinkSpeedMbps;
    public final int mRssi;
    public final long mTimeStampMillis;
    public final long mTotalBeaconRx;
    public final long mTotalTxBad;
    public final long mTotalTxRetries;
    public final long mTotalTxSuccess;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    enum StatItem {
        TIME_STAMP("TimeStamp"),
        RSSI("Rssi"),
        LINK_SPEED("LinkSpeed"),
        BCN_CNT("BcnCnt"),
        TX_GOOD("TxGood"),
        TX_BAD("TxBad"),
        TX_RETRY("TxRetry");

        public static int mMaxIndex = -1;
        private int mIndex = -1;
        private final String mKey;

        StatItem(String str) {
            this.mKey = str;
        }

        public static StatItem get(String str) {
            for (StatItem statItem : values()) {
                if (statItem.mKey.equals(str)) {
                    return statItem;
                }
            }
            return null;
        }

        public static int getIntegerValue(StatItem statItem, String[] strArr) {
            int i = statItem.mIndex;
            if (i != -1) {
                return Integer.parseInt(strArr[i]);
            }
            return 0;
        }

        public static long getLongValue(StatItem statItem, String[] strArr) {
            int i = statItem.mIndex;
            if (i != -1) {
                return Long.parseLong(strArr[i]);
            }
            return 0L;
        }

        public final void setIndex(int i) {
            this.mIndex = i;
            mMaxIndex = Math.max(mMaxIndex, i);
        }
    }

    public UsabilityStats(long j, int i, int i2, long j2, long j3, long j4, long j5) {
        this.mTimeStampMillis = j;
        this.mRssi = i;
        this.mLinkSpeedMbps = i2;
        this.mTotalBeaconRx = j2;
        this.mTotalTxSuccess = j3;
        this.mTotalTxBad = j4;
        this.mTotalTxRetries = j5;
    }

    public static UsabilityStats parse(String str) {
        if (str == null) {
            return new UsabilityStats();
        }
        String[] split = str.split(",");
        if (split.length >= StatItem.mMaxIndex + 1) {
            try {
                return new UsabilityStats(
                        StatItem.getLongValue(StatItem.TIME_STAMP, split),
                        StatItem.getIntegerValue(StatItem.RSSI, split),
                        StatItem.getIntegerValue(StatItem.LINK_SPEED, split),
                        StatItem.getLongValue(StatItem.BCN_CNT, split),
                        StatItem.getLongValue(StatItem.TX_GOOD, split),
                        StatItem.getLongValue(StatItem.TX_BAD, split),
                        StatItem.getLongValue(StatItem.TX_RETRY, split));
            } catch (NumberFormatException unused) {
                SemLog.e("UsabilityStats", "malformed UsabilityStats ");
            }
        }
        return new UsabilityStats();
    }

    public static synchronized List parseEntry(String str) {
        ArrayList arrayList;
        synchronized (UsabilityStats.class) {
            try {
                arrayList = new ArrayList();
                String[] split = str.split("\n");
                if (split != null && split.length >= 2) {
                    String str2 = split[0];
                    for (StatItem statItem : StatItem.values()) {
                        statItem.setIndex(-1);
                    }
                    StatItem.mMaxIndex = -1;
                    if (str2 != null) {
                        String[] split2 = str2.split(",");
                        for (int i = 0; i < split2.length; i++) {
                            StatItem statItem2 = StatItem.get(split2[i]);
                            if (statItem2 != null) {
                                statItem2.setIndex(i);
                            }
                        }
                        if (StatItem.mMaxIndex != -1) {
                            for (int i2 = 1; i2 < split.length; i2++) {
                                UsabilityStats parse = parse(split[i2]);
                                if (parse.mTimeStampMillis != 0) {
                                    arrayList.add(parse);
                                }
                            }
                            if (!arrayList.isEmpty()) {
                                arrayList.sort(
                                        Comparator.comparingLong(
                                                new UsabilityStats$$ExternalSyntheticLambda0()));
                                final long j =
                                        ((UsabilityStats) arrayList.get(arrayList.size() - 1))
                                                        .mTimeStampMillis
                                                - 172800000;
                                arrayList.removeIf(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.wifi.develop.history.model.UsabilityStats$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((UsabilityStats) obj).mTimeStampMillis < j;
                                            }
                                        });
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final String toString() {
        return "timeStamp="
                + this.mTimeStampMillis
                + ", rssi="
                + this.mRssi
                + ", linkSpeedMbps="
                + this.mLinkSpeedMbps
                + ", totalBeaconRx="
                + this.mTotalBeaconRx
                + ", totalTxSuccess="
                + this.mTotalTxSuccess
                + ", totalTxBad="
                + this.mTotalTxBad
                + ", totalTxRetries="
                + this.mTotalTxRetries;
    }

    public UsabilityStats() {
        this.mTimeStampMillis = 0L;
        this.mRssi = 0;
        this.mLinkSpeedMbps = 0;
        this.mTotalBeaconRx = 0L;
        this.mTotalTxSuccess = 0L;
        this.mTotalTxBad = 0L;
        this.mTotalTxRetries = 0L;
    }
}
