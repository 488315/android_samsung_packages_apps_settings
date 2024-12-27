package com.samsung.android.settings.wifi.develop.history.model;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.util.SemLog;
import com.sec.ims.settings.ImsProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TcpSocketStats {
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final Date mDate;
    public final int mGoodSockets;
    public final String mResult;
    public final int mSlowSockets;
    public final String mUid;
    public final int mWaitSockets;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    enum StatItem {
        TIME_STAMP("dateTime"),
        UID("UID"),
        PACKAGE_NAME("PN"),
        E(ImsProfile.TIMER_NAME_E),
        S("S"),
        R("R"),
        RESULT("Result");

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

        public static Date getDate(String[] strArr) {
            int i = TIME_STAMP.mIndex;
            return i != -1 ? TcpSocketStats.DEFAULT_DATE_FORMAT.parse(strArr[i]) : new Date(0L);
        }

        public static int getIntegerValue(StatItem statItem, String[] strArr) {
            int i = statItem.mIndex;
            if (i != -1) {
                return Integer.parseInt(strArr[i]);
            }
            return 0;
        }

        public static String getStringValue(StatItem statItem, String[] strArr) {
            int i = statItem.mIndex;
            return i != -1 ? strArr[i] : ApnSettings.MVNO_NONE;
        }

        public final void setIndex(int i) {
            this.mIndex = i;
            mMaxIndex = Math.max(mMaxIndex, i);
        }
    }

    public TcpSocketStats(Date date, String str, int i, int i2, int i3, String str2) {
        this.mDate = date;
        this.mUid = str;
        this.mGoodSockets = i;
        this.mWaitSockets = i2;
        this.mSlowSockets = i3;
        this.mResult = str2;
    }

    public static TcpSocketStats parse(String[] strArr) {
        if (strArr == null) {
            return new TcpSocketStats();
        }
        if (strArr.length >= StatItem.mMaxIndex + 1) {
            try {
                return new TcpSocketStats(
                        StatItem.getDate(strArr),
                        StatItem.getStringValue(StatItem.UID, strArr),
                        StatItem.getIntegerValue(StatItem.E, strArr),
                        StatItem.getIntegerValue(StatItem.S, strArr),
                        StatItem.getIntegerValue(StatItem.R, strArr),
                        StatItem.getStringValue(StatItem.RESULT, strArr));
            } catch (NumberFormatException | ParseException unused) {
                SemLog.e("TcpSocketStats", "malformed TcpSocketStats");
            }
        }
        return new TcpSocketStats();
    }

    public static synchronized Map parseEntry(String str) {
        HashMap hashMap;
        synchronized (TcpSocketStats.class) {
            try {
                hashMap = new HashMap();
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
                                String[] split3 = split[i2].split(",");
                                String stringValue =
                                        StatItem.getStringValue(StatItem.PACKAGE_NAME, split3);
                                TcpSocketStats parse = parse(split3);
                                if (!hashMap.containsKey(stringValue)) {
                                    hashMap.put(stringValue, new ArrayList());
                                }
                                ((List) hashMap.get(stringValue)).add(parse);
                            }
                            Iterator it = hashMap.entrySet().iterator();
                            while (it.hasNext()) {
                                ((List) ((Map.Entry) it.next()).getValue())
                                        .sort(
                                                Comparator.comparing(
                                                        new TcpSocketStats$$ExternalSyntheticLambda0()));
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return hashMap;
    }

    public final String toString() {
        return "mDate="
                + this.mDate
                + ", mUid="
                + this.mUid
                + ", mGoodSockets="
                + this.mGoodSockets
                + ", mWaitSockets="
                + this.mWaitSockets
                + ", mSlowSockets="
                + this.mSlowSockets
                + ", mResult="
                + this.mResult;
    }

    public TcpSocketStats() {
        this.mDate = new Date(0L);
        this.mUid = ApnSettings.MVNO_NONE;
        this.mGoodSockets = 0;
        this.mWaitSockets = 0;
        this.mSlowSockets = 0;
        this.mResult = ApnSettings.MVNO_NONE;
    }
}
