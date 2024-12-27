package com.android.settings.fuelgauge.batteryusage;

import android.util.ArraySet;

import androidx.core.text.PrecomputedTextCompat$Params$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.fuelgauge.PowerUsageFeatureProviderImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryDiffData {
    public final List mAppEntries;
    public final int mEndBatteryLevel;
    public final long mEndTimestamp;
    public final long mScreenOnTime;
    public final int mStartBatteryLevel;
    public final long mStartTimestamp;
    public final List mSystemEntries;

    /* JADX WARN: Removed duplicated region for block: B:42:0x009c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BatteryDiffData(
            android.content.Context r16,
            long r17,
            long r19,
            int r21,
            int r22,
            long r23,
            java.util.List r25,
            java.util.List r26,
            java.util.Set r27,
            java.util.Set r28,
            boolean r29) {
        /*
            Method dump skipped, instructions count: 395
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.fuelgauge.batteryusage.BatteryDiffData.<init>(android.content.Context,"
                    + " long, long, int, int, long, java.util.List, java.util.List, java.util.Set,"
                    + " java.util.Set, boolean):void");
    }

    @VisibleForTesting
    public static boolean needsCombineInSystemApp(
            BatteryDiffEntry batteryDiffEntry,
            List<String> list,
            Set<String> set,
            Set<Integer> set2) {
        if (batteryDiffEntry.mIsHidden) {
            return true;
        }
        String packageName = batteryDiffEntry.getPackageName();
        if (packageName == null || packageName.isEmpty()) {
            return false;
        }
        if (list.contains(packageName)) {
            return true;
        }
        return set.contains(packageName)
                || set2.contains(Integer.valueOf((int) batteryDiffEntry.mUid));
    }

    public static void processAndSortEntries(List list) {
        if (list.isEmpty()) {
            return;
        }
        Iterator it = list.iterator();
        double d = 0.0d;
        while (it.hasNext()) {
            d += ((BatteryDiffEntry) it.next()).mConsumePower;
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ((BatteryDiffEntry) it2.next()).setTotalConsumePower(d);
        }
        Iterator it3 = list.iterator();
        int i = 0;
        int i2 = 0;
        while (it3.hasNext()) {
            double d2 = ((BatteryDiffEntry) it3.next()).mPercentage;
            if (d2 < 1.0d) {
                i2++;
            } else {
                int round = Math.round((float) d2);
                i += round;
                i2 += round;
            }
        }
        if (i > 100 || i2 < 100) {
            Collections.sort(list, BatteryDiffEntry.COMPARATOR);
            for (int i3 = 0; i3 < i - 100 && i3 < list.size(); i3++) {
                ((BatteryDiffEntry) list.get(i3)).mAdjustPercentageOffset = -1;
            }
            for (int i4 = 0; i4 < 100 - i2 && i4 < list.size(); i4++) {
                ((BatteryDiffEntry) list.get(i4)).mAdjustPercentageOffset = 1;
            }
        }
        Collections.sort(list, BatteryDiffEntry.COMPARATOR);
    }

    public static void purgeBatteryDiffData(
            PowerUsageFeatureProviderImpl powerUsageFeatureProviderImpl, List list) {
        powerUsageFeatureProviderImpl.getClass();
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        ArraySet arraySet3 = new ArraySet();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            BatteryDiffEntry batteryDiffEntry = (BatteryDiffEntry) it.next();
            long j =
                    batteryDiffEntry.isSystemEntry()
                            ? batteryDiffEntry.mForegroundUsageTimeInMs
                            : batteryDiffEntry.mScreenOnTimeInMs;
            double d = batteryDiffEntry.mConsumePower;
            String packageName = batteryDiffEntry.getPackageName();
            Integer valueOf = Integer.valueOf(batteryDiffEntry.mComponentId);
            if ((j < 0.0d && d < 0.0d)
                    || "fake_package".equals(packageName)
                    || arraySet.contains(valueOf)
                    || (packageName != null && arraySet3.contains(packageName))) {
                it.remove();
            }
            if (packageName != null && arraySet2.contains(packageName)) {
                batteryDiffEntry.mBackgroundUsageTimeInMs = 0L;
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BatteryDiffData{");
        sb.append("startTimestamp:" + ConvertUtils.utcToLocalTimeForLogging(this.mStartTimestamp));
        sb.append("|endTimestamp:" + ConvertUtils.utcToLocalTimeForLogging(this.mEndTimestamp));
        StringBuilder m =
                PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                        PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                                new StringBuilder("|startLevel:"),
                                this.mStartBatteryLevel,
                                sb,
                                "|endLevel:"),
                        this.mEndBatteryLevel,
                        sb,
                        "|screenOnTime:");
        m.append(this.mScreenOnTime);
        sb.append(m.toString());
        sb.append("|appEntries.size:" + this.mAppEntries.size());
        sb.append("|systemEntries.size:" + this.mSystemEntries.size());
        sb.append("}");
        return sb.toString();
    }
}
