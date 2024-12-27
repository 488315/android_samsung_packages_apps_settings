package com.android.settings.fuelgauge.batteryusage;

import android.app.usage.IUsageStatsManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.BatteryConsumer;
import android.os.BatteryStatsManager;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.Build;
import android.os.ServiceManager;
import android.os.UidBatteryConsumer;
import android.os.UserBatteryConsumer;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.PowerProfile;
import com.android.settings.fuelgauge.BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.settingslib.fuelgauge.BatteryUtils;

import com.google.common.base.Preconditions;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DataProcessor {

    @VisibleForTesting static final long DEFAULT_USAGE_DURATION_FOR_INCOMPLETE_INTERVAL = 30000;

    @VisibleForTesting static final int SELECTED_INDEX_ALL = -1;

    @VisibleForTesting static Set<String> sTestSystemAppsPackageNames;
    public static final Map EMPTY_BATTERY_MAP = new ArrayMap();
    public static final BatteryHistEntry EMPTY_BATTERY_HIST_ENTRY =
            new BatteryHistEntry(new ContentValues());

    @VisibleForTesting
    static final Comparator<AppUsageEvent> APP_USAGE_EVENT_TIMESTAMP_COMPARATOR =
            Comparator.comparing(new DataProcessor$$ExternalSyntheticLambda0(0));

    @VisibleForTesting
    static final Comparator<BatteryEvent> BATTERY_EVENT_TIMESTAMP_COMPARATOR =
            Comparator.comparing(new DataProcessor$$ExternalSyntheticLambda0(4));

    @VisibleForTesting static boolean sDebug = false;

    @VisibleForTesting static long sTestCurrentTimeMillis = 0;

    @VisibleForTesting
    static IUsageStatsManager sUsageStatsManager =
            IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));

    @VisibleForTesting
    public static Map<Long, Map<String, List<AppUsagePeriod>>> buildAppUsagePeriodList(
            Context context, List<AppUsageEvent> list, List<BatteryEvent> list2, long j, long j2) {
        ArrayList arrayList;
        if (list.isEmpty()) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayMap arrayMap = new ArrayMap();
        for (AppUsageEvent appUsageEvent : list) {
            AppUsageEventType type = appUsageEvent.getType();
            if (type == AppUsageEventType.ACTIVITY_RESUMED
                    || type == AppUsageEventType.ACTIVITY_STOPPED) {
                int instanceId = appUsageEvent.getInstanceId();
                if (arrayMap.get(Integer.valueOf(instanceId)) == null) {
                    arrayMap.put(Integer.valueOf(instanceId), new ArrayList());
                }
                ((List) arrayMap.get(Integer.valueOf(instanceId))).add(appUsageEvent);
            } else if (type == AppUsageEventType.DEVICE_SHUTDOWN) {
                arrayList2.add(appUsageEvent);
            }
        }
        if (arrayMap.isEmpty()) {
            return null;
        }
        ArrayMap arrayMap2 = new ArrayMap();
        int i = 0;
        int i2 = 0;
        while (i2 < arrayMap.size()) {
            List list3 = (List) arrayMap.valueAt(i2);
            if (list3 != null && !list3.isEmpty()) {
                AppUsageEvent appUsageEvent2 = (AppUsageEvent) list3.get(i);
                long userId = appUsageEvent2.getUserId();
                String effectivePackageName =
                        ConvertUtils.getEffectivePackageName(
                                context,
                                sUsageStatsManager,
                                appUsageEvent2.getPackageName(),
                                appUsageEvent2.getTaskRootPackageName());
                list3.addAll(arrayList2);
                Collections.sort(list3, APP_USAGE_EVENT_TIMESTAMP_COMPARATOR);
                List<AppUsagePeriod> excludePowerConnectedTimeFromAppUsagePeriodList =
                        excludePowerConnectedTimeFromAppUsagePeriodList(
                                buildAppUsagePeriodListPerInstance(list3, j, j2), list2);
                if (!excludePowerConnectedTimeFromAppUsagePeriodList.isEmpty()) {
                    arrayList = arrayList2;
                    arrayMap2.computeIfAbsent(
                            Long.valueOf(userId), new DataProcessor$$ExternalSyntheticLambda0(2));
                    Map map = (Map) arrayMap2.get(Long.valueOf(userId));
                    map.computeIfAbsent(
                            effectivePackageName, new DataProcessor$$ExternalSyntheticLambda0(3));
                    ((List) map.get(effectivePackageName))
                            .addAll(excludePowerConnectedTimeFromAppUsagePeriodList);
                    i2++;
                    arrayList2 = arrayList;
                    i = 0;
                }
            }
            arrayList = arrayList2;
            i2++;
            arrayList2 = arrayList;
            i = 0;
        }
        for (Long l : arrayMap2.keySet()) {
            l.getClass();
            if (arrayMap2.get(l) != null) {
                Iterator it = ((Map) arrayMap2.get(l)).keySet().iterator();
                while (it.hasNext()) {
                    Collections.sort(
                            (List) ((Map) arrayMap2.get(l)).get((String) it.next()),
                            Comparator.comparing(new DataProcessor$$ExternalSyntheticLambda0(1)));
                }
            }
        }
        if (arrayMap2.isEmpty()) {
            return null;
        }
        return arrayMap2;
    }

    @VisibleForTesting
    public static List<AppUsagePeriod> buildAppUsagePeriodListPerInstance(
            List<AppUsageEvent> list, long j, long j2) {
        ArrayList arrayList = new ArrayList();
        AppUsagePeriod.Builder newBuilder = AppUsagePeriod.newBuilder();
        for (AppUsageEvent appUsageEvent : list) {
            long timestamp = appUsageEvent.getTimestamp();
            if (appUsageEvent.getType() == AppUsageEventType.ACTIVITY_RESUMED) {
                if (!((AppUsagePeriod) newBuilder.instance).hasStartTime()) {
                    newBuilder.setStartTime(timestamp);
                }
            } else if (appUsageEvent.getType() == AppUsageEventType.ACTIVITY_STOPPED) {
                newBuilder.setEndTime(timestamp);
                if (!((AppUsagePeriod) newBuilder.instance).hasStartTime()) {
                    newBuilder.setStartTime(
                            ((AppUsagePeriod) newBuilder.instance).getEndTime()
                                    - DEFAULT_USAGE_DURATION_FOR_INCOMPLETE_INTERVAL);
                }
                validateAndAddToPeriodList(arrayList, (AppUsagePeriod) newBuilder.build(), j, j2);
                newBuilder.clear();
            } else if (appUsageEvent.getType() == AppUsageEventType.DEVICE_SHUTDOWN
                    && ((AppUsagePeriod) newBuilder.instance).hasStartTime()) {
                newBuilder.setEndTime(
                        Math.min(
                                ((AppUsagePeriod) newBuilder.instance).getStartTime()
                                        + DEFAULT_USAGE_DURATION_FOR_INCOMPLETE_INTERVAL,
                                timestamp));
                validateAndAddToPeriodList(arrayList, (AppUsagePeriod) newBuilder.build(), j, j2);
                newBuilder.clear();
            }
        }
        if (((AppUsagePeriod) newBuilder.instance).hasStartTime()
                && ((AppUsagePeriod) newBuilder.instance).getStartTime() < j2) {
            newBuilder.setEndTime(j2);
            validateAndAddToPeriodList(arrayList, (AppUsagePeriod) newBuilder.build(), j, j2);
            newBuilder.clear();
        }
        return arrayList;
    }

    public static void computeUsageDiffDataPerEntry(BatteryDiffEntry batteryDiffEntry, Map map) {
        String str = batteryDiffEntry.mKey;
        ArrayMap arrayMap = (ArrayMap) map;
        BatteryDiffEntry batteryDiffEntry2 = (BatteryDiffEntry) arrayMap.get(str);
        if (batteryDiffEntry2 == null) {
            arrayMap.put(str, batteryDiffEntry.m888clone());
            return;
        }
        batteryDiffEntry2.mForegroundUsageTimeInMs += batteryDiffEntry.mForegroundUsageTimeInMs;
        batteryDiffEntry2.mForegroundServiceUsageTimeInMs +=
                batteryDiffEntry.mForegroundServiceUsageTimeInMs;
        batteryDiffEntry2.mBackgroundUsageTimeInMs += batteryDiffEntry.mBackgroundUsageTimeInMs;
        batteryDiffEntry2.mScreenOnTimeInMs += batteryDiffEntry.mScreenOnTimeInMs;
        batteryDiffEntry2.mConsumePower += batteryDiffEntry.mConsumePower;
        batteryDiffEntry2.mForegroundUsageConsumePower +=
                batteryDiffEntry.mForegroundUsageConsumePower;
        batteryDiffEntry2.mForegroundServiceUsageConsumePower +=
                batteryDiffEntry.mForegroundServiceUsageConsumePower;
        batteryDiffEntry2.mBackgroundUsageConsumePower +=
                batteryDiffEntry.mBackgroundUsageConsumePower;
        batteryDiffEntry2.mCachedUsageConsumePower += batteryDiffEntry.mCachedUsageConsumePower;
    }

    @VisibleForTesting
    public static List<BatteryHistEntry> convertToBatteryHistEntry(
            List<BatteryEntry> list, final BatteryUsageStats batteryUsageStats) {
        if (list != null && !list.isEmpty()) {
            return (List)
                    list.stream()
                            .filter(new DataProcessor$$ExternalSyntheticLambda3())
                            .map(
                                    new Function() { // from class:
                                                     // com.android.settings.fuelgauge.batteryusage.DataProcessor$$ExternalSyntheticLambda4
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            BatteryUsageStats batteryUsageStats2 =
                                                    batteryUsageStats;
                                            BatteryEntry batteryEntry = (BatteryEntry) obj;
                                            ContentValues contentValues = new ContentValues();
                                            String str = ApnSettings.MVNO_NONE;
                                            if (batteryEntry == null
                                                    || batteryUsageStats2 == null) {
                                                contentValues.put("packageName", "fake_package");
                                            } else {
                                                contentValues.put(
                                                        NetworkAnalyticsConstants.DataPoints.UID,
                                                        Long.valueOf(batteryEntry.mUid));
                                                contentValues.put(
                                                        "userId",
                                                        Long.valueOf(UserHandle.getUserId(r4)));
                                                String str2 = batteryEntry.mDefaultPackageName;
                                                if (str2 == null) {
                                                    str2 = ApnSettings.MVNO_NONE;
                                                }
                                                contentValues.put("packageName", str2);
                                                contentValues.put(
                                                        "consumerType",
                                                        Integer.valueOf(
                                                                batteryEntry.mConsumerType));
                                            }
                                            contentValues.put(
                                                    PhoneRestrictionPolicy.TIMESTAMP, (Long) 0L);
                                            contentValues.put(
                                                    "isFullChargeCycleStart", Boolean.FALSE);
                                            DeviceBatteryState.Builder newBuilder =
                                                    DeviceBatteryState.newBuilder();
                                            newBuilder.copyOnWrite();
                                            DeviceBatteryState.m936$$Nest$msetBatteryLevel(
                                                    (DeviceBatteryState) newBuilder.instance);
                                            newBuilder.copyOnWrite();
                                            DeviceBatteryState.m937$$Nest$msetBatteryStatus(
                                                    (DeviceBatteryState) newBuilder.instance);
                                            newBuilder.copyOnWrite();
                                            DeviceBatteryState.m935$$Nest$msetBatteryHealth(
                                                    (DeviceBatteryState) newBuilder.instance);
                                            DeviceBatteryState deviceBatteryState =
                                                    (DeviceBatteryState) newBuilder.build();
                                            BatteryInformation.Builder newBuilder2 =
                                                    BatteryInformation.newBuilder();
                                            newBuilder2.copyOnWrite();
                                            BatteryInformation.m898$$Nest$msetDeviceBatteryState(
                                                    (BatteryInformation) newBuilder2.instance,
                                                    deviceBatteryState);
                                            newBuilder2.copyOnWrite();
                                            BatteryInformation.m895$$Nest$msetBootTimestamp(
                                                    (BatteryInformation) newBuilder2.instance);
                                            String id = TimeZone.getDefault().getID();
                                            newBuilder2.copyOnWrite();
                                            BatteryInformation.m907$$Nest$msetZoneId(
                                                    (BatteryInformation) newBuilder2.instance, id);
                                            if (batteryEntry != null
                                                    && batteryUsageStats2 != null) {
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation.m904$$Nest$msetIsHidden(
                                                        (BatteryInformation) newBuilder2.instance,
                                                        batteryEntry.mIsHidden);
                                                String str3 = batteryEntry.mName;
                                                if (str3 != null) {
                                                    str = str3;
                                                }
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation.m892$$Nest$msetAppLabel(
                                                        (BatteryInformation) newBuilder2.instance,
                                                        str);
                                                double consumedPower =
                                                        batteryUsageStats2.getConsumedPower();
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation.m906$$Nest$msetTotalPower(
                                                        (BatteryInformation) newBuilder2.instance,
                                                        consumedPower);
                                                double d = batteryEntry.mConsumedPower;
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation.m897$$Nest$msetConsumePower(
                                                        (BatteryInformation) newBuilder2.instance,
                                                        d);
                                                double d2 =
                                                        batteryEntry.mBatteryConsumer
                                                                        instanceof
                                                                        UidBatteryConsumer
                                                                ? batteryEntry
                                                                        .mConsumedPowerInForeground
                                                                : 0.0d;
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation
                                                        .m902$$Nest$msetForegroundUsageConsumePower(
                                                                (BatteryInformation)
                                                                        newBuilder2.instance,
                                                                d2);
                                                double d3 =
                                                        batteryEntry.mBatteryConsumer
                                                                        instanceof
                                                                        UidBatteryConsumer
                                                                ? batteryEntry
                                                                        .mConsumedPowerInForegroundService
                                                                : 0.0d;
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation
                                                        .m900$$Nest$msetForegroundServiceUsageConsumePower(
                                                                (BatteryInformation)
                                                                        newBuilder2.instance,
                                                                d3);
                                                double d4 =
                                                        batteryEntry.mBatteryConsumer
                                                                        instanceof
                                                                        UidBatteryConsumer
                                                                ? batteryEntry
                                                                        .mConsumedPowerInBackground
                                                                : 0.0d;
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation
                                                        .m893$$Nest$msetBackgroundUsageConsumePower(
                                                                (BatteryInformation)
                                                                        newBuilder2.instance,
                                                                d4);
                                                double d5 =
                                                        batteryEntry.mBatteryConsumer
                                                                        instanceof
                                                                        UidBatteryConsumer
                                                                ? batteryEntry
                                                                        .mConsumedPowerInCached
                                                                : 0.0d;
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation
                                                        .m896$$Nest$msetCachedUsageConsumePower(
                                                                (BatteryInformation)
                                                                        newBuilder2.instance,
                                                                d5);
                                                double d6 = batteryEntry.mPercent;
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation.m905$$Nest$msetPercentOfTotal(
                                                        (BatteryInformation) newBuilder2.instance,
                                                        d6);
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation.m899$$Nest$msetDrainType(
                                                        (BatteryInformation) newBuilder2.instance,
                                                        batteryEntry.mPowerComponentId);
                                                long j =
                                                        batteryEntry.mBatteryConsumer
                                                                        instanceof
                                                                        UidBatteryConsumer
                                                                ? batteryEntry.mTimeInForegroundMs
                                                                : batteryEntry.mUsageDurationMs;
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation
                                                        .m903$$Nest$msetForegroundUsageTimeInMs(
                                                                (BatteryInformation)
                                                                        newBuilder2.instance,
                                                                j);
                                                long j2 =
                                                        batteryEntry.mBatteryConsumer
                                                                        instanceof
                                                                        UidBatteryConsumer
                                                                ? batteryEntry
                                                                        .mTimeInForegroundServiceMs
                                                                : 0L;
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation
                                                        .m901$$Nest$msetForegroundServiceUsageTimeInMs(
                                                                (BatteryInformation)
                                                                        newBuilder2.instance,
                                                                j2);
                                                long j3 =
                                                        batteryEntry.mBatteryConsumer
                                                                        instanceof
                                                                        UidBatteryConsumer
                                                                ? batteryEntry.mTimeInBackgroundMs
                                                                : 0L;
                                                newBuilder2.copyOnWrite();
                                                BatteryInformation
                                                        .m894$$Nest$msetBackgroundUsageTimeInMs(
                                                                (BatteryInformation)
                                                                        newBuilder2.instance,
                                                                j3);
                                            }
                                            BatteryInformation batteryInformation =
                                                    (BatteryInformation) newBuilder2.build();
                                            contentValues.put(
                                                    "batteryInformation",
                                                    Base64.encodeToString(
                                                            batteryInformation.toByteArray(), 0));
                                            if (Build.TYPE.equals("userdebug")) {
                                                contentValues.put(
                                                        "batteryInformationDebug",
                                                        batteryInformation.toString());
                                            }
                                            return new BatteryHistEntry(contentValues);
                                        }
                                    })
                            .collect(Collectors.toList());
        }
        Log.w("DataProcessor", "batteryEntryList is null or empty in convertToBatteryHistEntry()");
        return null;
    }

    @VisibleForTesting
    public static List<AppUsagePeriod> excludePowerConnectedTimeFromAppUsagePeriodList(
            List<AppUsagePeriod> list, List<BatteryEvent> list2) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (AppUsagePeriod appUsagePeriod : list) {
            long startTime = appUsagePeriod.getStartTime();
            while (i < list2.size()) {
                BatteryEvent batteryEvent = list2.get(i);
                long timestamp = batteryEvent.getTimestamp();
                long startTime2 = appUsagePeriod.getStartTime();
                BatteryEventType batteryEventType = BatteryEventType.POWER_DISCONNECTED;
                BatteryEventType batteryEventType2 = BatteryEventType.POWER_CONNECTED;
                if (timestamp < startTime2) {
                    if (batteryEvent.getType() != batteryEventType2) {
                        if (batteryEvent.getType() == batteryEventType) {
                            startTime = appUsagePeriod.getStartTime();
                        }
                        i++;
                    }
                    startTime = 0;
                    i++;
                } else {
                    if (batteryEvent.getTimestamp() > appUsagePeriod.getEndTime()) {
                        break;
                    }
                    if (batteryEvent.getType() != batteryEventType2 || startTime == 0) {
                        if (batteryEvent.getType() == batteryEventType) {
                            startTime = batteryEvent.getTimestamp();
                        }
                        i++;
                    } else {
                        AppUsagePeriod.Builder newBuilder = AppUsagePeriod.newBuilder();
                        newBuilder.setStartTime(startTime);
                        newBuilder.setEndTime(batteryEvent.getTimestamp());
                        arrayList.add((AppUsagePeriod) newBuilder.build());
                        startTime = 0;
                        i++;
                    }
                }
            }
            if (startTime != 0) {
                AppUsagePeriod.Builder newBuilder2 = AppUsagePeriod.newBuilder();
                newBuilder2.setStartTime(startTime);
                newBuilder2.setEndTime(appUsagePeriod.getEndTime());
                arrayList.add((AppUsagePeriod) newBuilder2.build());
            }
        }
        return arrayList;
    }

    @VisibleForTesting
    public static long[] findNearestTimestamp(List<Long> list, final long j) {
        final long[] jArr = {Long.MIN_VALUE, Long.MAX_VALUE};
        list.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.fuelgauge.batteryusage.DataProcessor$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        long j2 = j;
                        long[] jArr2 = jArr;
                        Long l = (Long) obj;
                        if (l.longValue() <= j2 && l.longValue() > jArr2[0]) {
                            jArr2[0] = l.longValue();
                        }
                        if (l.longValue() < j2 || l.longValue() >= jArr2[1]) {
                            return;
                        }
                        jArr2[1] = l.longValue();
                    }
                });
        long j2 = jArr[0];
        if (j2 == Long.MIN_VALUE) {
            j2 = 0;
        }
        jArr[0] = j2;
        long j3 = jArr[1];
        jArr[1] = j3 != Long.MAX_VALUE ? j3 : 0L;
        return jArr;
    }

    @VisibleForTesting
    public static BatteryDiffData generateBatteryDiffData(
            Context context,
            UserIdsSeries userIdsSeries,
            long j,
            List<BatteryHistEntry> list,
            Set<String> set,
            Set<Integer> set2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (list == null || list.isEmpty()) {
            Log.w(
                    "DataProcessor",
                    "batteryHistEntryList is null or empty in generateBatteryDiffData()");
            long j2 = sTestCurrentTimeMillis;
            return new BatteryDiffData(
                    context,
                    j,
                    j2 > 0 ? j2 : System.currentTimeMillis(),
                    100,
                    BatteryStatus.getBatteryLevel(BatteryUtils.getBatteryIntent(context)),
                    0L,
                    arrayList,
                    arrayList2,
                    set,
                    set2,
                    false);
        }
        for (BatteryHistEntry batteryHistEntry : list) {
            if (batteryHistEntry.mConsumerType == 1) {
                if (true
                        ^ ((ArrayList) userIdsSeries.mVisibleUserIds)
                                .contains(Integer.valueOf((int) batteryHistEntry.mUserId))) {}
            }
            BatteryDiffEntry batteryDiffEntry =
                    new BatteryDiffEntry(
                            context,
                            batteryHistEntry.mUid,
                            batteryHistEntry.mUserId,
                            batteryHistEntry.getKey(),
                            batteryHistEntry.mIsHidden,
                            batteryHistEntry.mDrainType,
                            batteryHistEntry.mPackageName,
                            batteryHistEntry.mAppLabel,
                            batteryHistEntry.mConsumerType,
                            batteryHistEntry.mForegroundUsageTimeInMs,
                            batteryHistEntry.mForegroundServiceUsageTimeInMs,
                            batteryHistEntry.mBackgroundUsageTimeInMs,
                            0L,
                            batteryHistEntry.mConsumePower,
                            batteryHistEntry.mForegroundUsageConsumePower,
                            batteryHistEntry.mForegroundServiceUsageConsumePower,
                            batteryHistEntry.mBackgroundUsageConsumePower,
                            batteryHistEntry.mCachedUsageConsumePower);
            if (batteryDiffEntry.isSystemEntry()) {
                arrayList2.add(batteryDiffEntry);
            } else {
                arrayList.add(batteryDiffEntry);
            }
        }
        long j3 = sTestCurrentTimeMillis;
        return new BatteryDiffData(
                context,
                j,
                j3 > 0 ? j3 : System.currentTimeMillis(),
                100,
                BatteryStatus.getBatteryLevel(BatteryUtils.getBatteryIntent(context)),
                0L,
                arrayList,
                arrayList2,
                set,
                set2,
                false);
    }

    public static List generateBatteryEntryListFromBatteryUsageStats(
            Context context, BatteryUsageStats batteryUsageStats) {
        int i;
        int i2;
        List list;
        if (batteryUsageStats == null) {
            Log.w("DataProcessor", "batteryUsageStats is null content");
            return null;
        }
        boolean z = false;
        double averagePowerForOrdinal =
                new PowerProfile(context).getAveragePowerForOrdinal("screen.full.display", 0);
        boolean z2 = averagePowerForOrdinal >= 10.0d;
        if (!z2) {
            Log.w("DataProcessor", "shouldShowBatteryAttributionList(): " + averagePowerForOrdinal);
        }
        if (!z2) {
            return null;
        }
        com.android.settings.fuelgauge.BatteryUtils batteryUtils =
                com.android.settings.fuelgauge.BatteryUtils.getInstance(context);
        int max = Math.max(0, batteryUsageStats.getDischargePercentage());
        PackageManager packageManager = context.getPackageManager();
        SparseArray sparseArray = new SparseArray();
        ArrayList arrayList = new ArrayList();
        List uidBatteryConsumers = batteryUsageStats.getUidBatteryConsumers();
        uidBatteryConsumers.sort(
                Comparator.comparingInt(new DataProcessor$$ExternalSyntheticLambda13()));
        int size = uidBatteryConsumers.size();
        int i3 = 0;
        while (i3 < size) {
            UidBatteryConsumer uidBatteryConsumer =
                    (UidBatteryConsumer) uidBatteryConsumers.get(i3);
            int realUid = getRealUid(uidBatteryConsumer);
            String[] packagesForUid = packageManager.getPackagesForUid(realUid);
            if (batteryUtils.shouldHideUidBatteryConsumerUnconditionally(
                    uidBatteryConsumer, packagesForUid)) {
                i = i3;
                i2 = size;
                list = uidBatteryConsumers;
            } else {
                boolean shouldHideUidBatteryConsumer =
                        batteryUtils.shouldHideUidBatteryConsumer(
                                uidBatteryConsumer, packagesForUid);
                int indexOfKey = sparseArray.indexOfKey(realUid);
                if (indexOfKey < 0) {
                    i = i3;
                    i2 = size;
                    list = uidBatteryConsumers;
                    sparseArray.put(
                            realUid,
                            new BatteryEntry(
                                    context,
                                    uidBatteryConsumer,
                                    shouldHideUidBatteryConsumer,
                                    realUid,
                                    packagesForUid,
                                    null));
                } else {
                    i = i3;
                    i2 = size;
                    list = uidBatteryConsumers;
                    BatteryEntry batteryEntry = (BatteryEntry) sparseArray.valueAt(indexOfKey);
                    batteryEntry.mConsumedPower =
                            uidBatteryConsumer.getConsumedPower() + batteryEntry.mConsumedPower;
                    batteryEntry.mTimeInForegroundMs =
                            uidBatteryConsumer.getTimeInProcessStateMs(1)
                                    + batteryEntry.mTimeInForegroundMs;
                    batteryEntry.mTimeInForegroundServiceMs =
                            uidBatteryConsumer.getTimeInProcessStateMs(3)
                                    + batteryEntry.mTimeInForegroundServiceMs;
                    batteryEntry.mTimeInBackgroundMs =
                            uidBatteryConsumer.getTimeInProcessStateMs(2)
                                    + batteryEntry.mTimeInBackgroundMs;
                    double d = batteryEntry.mConsumedPowerInForeground;
                    BatteryConsumer.Dimensions[] dimensionsArr = BatteryEntry.BATTERY_DIMENSIONS;
                    batteryEntry.mConsumedPowerInForeground =
                            BatteryEntry.safeGetConsumedPower(uidBatteryConsumer, dimensionsArr[0])
                                    + d;
                    batteryEntry.mConsumedPowerInForegroundService =
                            BatteryEntry.safeGetConsumedPower(uidBatteryConsumer, dimensionsArr[1])
                                    + batteryEntry.mConsumedPowerInForegroundService;
                    batteryEntry.mConsumedPowerInBackground =
                            BatteryEntry.safeGetConsumedPower(uidBatteryConsumer, dimensionsArr[2])
                                    + batteryEntry.mConsumedPowerInBackground;
                    batteryEntry.mConsumedPowerInCached =
                            BatteryEntry.safeGetConsumedPower(uidBatteryConsumer, dimensionsArr[3])
                                    + batteryEntry.mConsumedPowerInCached;
                    if (batteryEntry.mDefaultPackageName == null) {
                        batteryEntry.mDefaultPackageName =
                                uidBatteryConsumer.getPackageWithHighestDrain();
                    }
                }
            }
            i3 = i + 1;
            uidBatteryConsumers = list;
            size = i2;
        }
        BatteryConsumer aggregateBatteryConsumer = batteryUsageStats.getAggregateBatteryConsumer(0);
        int i4 = 0;
        while (i4 < 19) {
            arrayList.add(
                    new BatteryEntry(
                            context,
                            i4,
                            aggregateBatteryConsumer.getConsumedPower(i4),
                            aggregateBatteryConsumer.getUsageDurationMillis(i4),
                            (i4 == 7 || i4 == 12) ? true : z));
            i4++;
            aggregateBatteryConsumer = aggregateBatteryConsumer;
            z = false;
        }
        BatteryConsumer batteryConsumer = aggregateBatteryConsumer;
        for (int i5 = 1000; i5 < batteryConsumer.getCustomPowerComponentCount() + 1000; i5++) {
            arrayList.add(
                    new BatteryEntry(
                            context,
                            i5,
                            batteryConsumer.getCustomPowerComponentName(i5),
                            batteryConsumer.getConsumedPowerForCustomComponent(i5)));
        }
        List userBatteryConsumers = batteryUsageStats.getUserBatteryConsumers();
        int size2 = userBatteryConsumers.size();
        int i6 = 0;
        while (i6 < size2) {
            arrayList.add(
                    new BatteryEntry(
                            context,
                            (UserBatteryConsumer) userBatteryConsumers.get(i6),
                            true,
                            -1,
                            null,
                            null));
            i6++;
            userBatteryConsumers = userBatteryConsumers;
        }
        int size3 = sparseArray.size();
        for (int i7 = 0; i7 < size3; i7++) {
            arrayList.add((BatteryEntry) sparseArray.valueAt(i7));
        }
        arrayList.sort(BatteryEntry.COMPARATOR);
        double consumedPower = batteryUsageStats.getConsumedPower();
        for (int i8 = 0; i8 < arrayList.size(); i8++) {
            BatteryEntry batteryEntry2 = (BatteryEntry) arrayList.get(i8);
            double d2 = batteryEntry2.mConsumedPower;
            batteryUtils.getClass();
            double d3 = 0.0d;
            if (consumedPower != 0.0d) {
                d3 = max * (d2 / consumedPower);
            }
            batteryEntry2.mPercent = d3;
        }
        return arrayList;
    }

    public static Map generateBatteryUsageMap(
            Context context, Map map, BatteryLevelData batteryLevelData) {
        ArrayList arrayList;
        final ArrayMap arrayMap = new ArrayMap();
        if (batteryLevelData == null) {
            Preconditions.checkArgument(map.size() == 1);
            BatteryDiffData batteryDiffData =
                    (BatteryDiffData) map.values().stream().toList().get(0);
            ArrayMap arrayMap2 = new ArrayMap();
            arrayMap2.put(-1, batteryDiffData);
            arrayMap.put(-1, arrayMap2);
            return arrayMap;
        }
        List list = batteryLevelData.mHourlyBatteryLevelsPerDay;
        int i = 0;
        while (true) {
            arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                break;
            }
            ArrayMap arrayMap3 = new ArrayMap();
            arrayMap.put(Integer.valueOf(i), arrayMap3);
            if (arrayList.get(i) != null) {
                List list2 =
                        ((BatteryLevelData.PeriodBatteryLevelData) arrayList.get(i)).mTimestamps;
                for (int i2 = 0; i2 < list2.size() - 1; i2++) {
                    arrayMap3.put(
                            Integer.valueOf(i2), (BatteryDiffData) map.get((Long) list2.get(i2)));
                }
            }
            i++;
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            Map map2 = (Map) arrayMap.get(Integer.valueOf(i3));
            if (map2 == null) {
                map2 = new ArrayMap();
                arrayMap.put(Integer.valueOf(i3), map2);
            }
            map2.put(-1, getAccumulatedUsageDiffData(context, map2.values()));
        }
        final ArrayList arrayList2 = new ArrayList();
        arrayMap.keySet()
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.settings.fuelgauge.batteryusage.DataProcessor$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                arrayList2.add(
                                        (BatteryDiffData)
                                                ((Map) arrayMap.get((Integer) obj)).get(-1));
                            }
                        });
        ArrayMap arrayMap4 = new ArrayMap();
        arrayMap4.put(-1, getAccumulatedUsageDiffData(context, arrayList2));
        arrayMap.put(-1, arrayMap4);
        if (arrayMap.get(-1) == null || !((Map) arrayMap.get(-1)).containsKey(-1)) {
            Log.e(
                    "DataProcessor",
                    "no [SELECTED_INDEX_ALL][SELECTED_INDEX_ALL] in batteryUsageMap");
            return null;
        }
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            if (arrayMap.get(Integer.valueOf(i4)) == null
                    || !((Map) arrayMap.get(Integer.valueOf(i4))).containsKey(-1)) {
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                i4,
                                "no [",
                                "][SELECTED_INDEX_ALL] in batteryUsageMap, daily size is: ");
                m.append(arrayList.size());
                Log.e("DataProcessor", m.toString());
                return null;
            }
            if (arrayList.get(i4) != null) {
                List list3 =
                        ((BatteryLevelData.PeriodBatteryLevelData) arrayList.get(i4)).mTimestamps;
                for (int i5 = 0; i5 < list3.size() - 1; i5++) {
                    if (!((Map) arrayMap.get(Integer.valueOf(i4)))
                            .containsKey(Integer.valueOf(i5))) {
                        StringBuilder m2 =
                                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                                        "no [",
                                        "][",
                                        i4,
                                        i5,
                                        "] in batteryUsageMap, hourly size is: ");
                        m2.append(list3.size() - 1);
                        Log.e("DataProcessor", m2.toString());
                        return null;
                    }
                }
            }
        }
        return arrayMap;
    }

    public static BatteryDiffData getAccumulatedUsageDiffData(
            Context context, Collection collection) {
        ArrayMap arrayMap = new ArrayMap();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = collection.iterator();
        long j = 0;
        int i = -1;
        int i2 = -1;
        long j2 = Long.MAX_VALUE;
        long j3 = 0;
        while (it.hasNext()) {
            BatteryDiffData batteryDiffData = (BatteryDiffData) it.next();
            if (batteryDiffData != null) {
                long j4 = batteryDiffData.mStartTimestamp;
                if (j2 > j4) {
                    i = batteryDiffData.mStartBatteryLevel;
                    j2 = j4;
                }
                long j5 = batteryDiffData.mEndTimestamp;
                if (j3 > j5) {
                    i2 = batteryDiffData.mEndBatteryLevel;
                    j3 = j5;
                }
                j += batteryDiffData.mScreenOnTime;
                Iterator it2 = batteryDiffData.mAppEntries.iterator();
                while (it2.hasNext()) {
                    computeUsageDiffDataPerEntry((BatteryDiffEntry) it2.next(), arrayMap);
                }
                Iterator it3 = batteryDiffData.mSystemEntries.iterator();
                while (it3.hasNext()) {
                    computeUsageDiffDataPerEntry((BatteryDiffEntry) it3.next(), arrayMap);
                }
            }
        }
        for (BatteryDiffEntry batteryDiffEntry : arrayMap.values()) {
            if (batteryDiffEntry.isSystemEntry()) {
                arrayList2.add(batteryDiffEntry);
            } else {
                arrayList.add(batteryDiffEntry);
            }
        }
        return new BatteryDiffData(
                context,
                j2,
                j3,
                i,
                i2,
                j,
                arrayList,
                arrayList2,
                new ArraySet(),
                new ArraySet(),
                true);
    }

    public static Map getBatteryDiffDataMap(
            Context context,
            UserIdsSeries userIdsSeries,
            List list,
            Map map,
            Map map2,
            Set set,
            Set set2) {
        int i;
        long j;
        int i2;
        List list2;
        BatteryDiffData batteryDiffData;
        ArrayMap arrayMap;
        ArrayMap arrayMap2;
        ArrayList arrayList;
        UserIdsSeries userIdsSeries2 = userIdsSeries;
        List list3 = list;
        Map map3 = map;
        Map map4 = map2;
        ArrayMap arrayMap3 = new ArrayMap();
        int i3 = 0;
        while (i3 < list.size()) {
            if (list3.get(i3) != null) {
                List list4 = ((BatteryLevelData.PeriodBatteryLevelData) list3.get(i3)).mTimestamps;
                int i4 = 0;
                while (i4 < list4.size() - 1) {
                    Long l = (Long) list4.get(i4);
                    int i5 = i4 + 1;
                    Long l2 = (Long) list4.get(i5);
                    int intValue =
                            ((Integer)
                                            ((ArrayList)
                                                            ((BatteryLevelData
                                                                                    .PeriodBatteryLevelData)
                                                                            list3.get(i3))
                                                                    .mLevels)
                                                    .get(i4))
                                    .intValue();
                    int intValue2 =
                            ((Integer)
                                            ((ArrayList)
                                                            ((BatteryLevelData
                                                                                    .PeriodBatteryLevelData)
                                                                            list3.get(i3))
                                                                    .mLevels)
                                                    .get(i5))
                                    .intValue();
                    long longValue = l2.longValue() - l.longValue();
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add((Map) map3.getOrDefault(l, EMPTY_BATTERY_MAP));
                    Calendar sharpHourCalendar = TimestampUtils.getSharpHourCalendar(l.longValue());
                    sharpHourCalendar.add(11, 1);
                    for (Long valueOf = Long.valueOf(sharpHourCalendar.getTimeInMillis());
                            valueOf.longValue() < l2.longValue();
                            valueOf = Long.valueOf(valueOf.longValue() + 3600000)) {
                        arrayList2.add((Map) map3.getOrDefault(valueOf, EMPTY_BATTERY_MAP));
                    }
                    arrayList2.add((Map) map3.getOrDefault(l2, EMPTY_BATTERY_MAP));
                    long longValue2 = l.longValue();
                    long longValue3 = l2.longValue();
                    Map map5 =
                            (map4 == null || map4.get(Integer.valueOf(i3)) == null)
                                    ? null
                                    : (Map)
                                            ((Map) map4.get(Integer.valueOf(i3)))
                                                    .get(Integer.valueOf(i4));
                    if (map5 != null) {
                        ArrayList arrayList3 = new ArrayList();
                        for (Long l3 : map5.keySet()) {
                            int i6 = i5;
                            if (!(!((ArrayList) userIdsSeries2.mVisibleUserIds)
                                            .contains(Integer.valueOf((int) l3.longValue())))
                                    && map5.get(l3) != null) {
                                Iterator it = ((Map) map5.get(l3)).keySet().iterator();
                                while (it.hasNext()) {
                                    List list5 =
                                            (List) ((Map) map5.get(l3)).get((String) it.next());
                                    if (list5 != null) {
                                        arrayList3.addAll(list5);
                                    }
                                }
                            }
                            i5 = i6;
                        }
                        i = i5;
                        j = Math.min(longValue, getScreenOnTime(arrayList3));
                    } else {
                        i = i5;
                        j = 0;
                    }
                    ArrayList arrayList4 = new ArrayList();
                    ArrayList arrayList5 = new ArrayList();
                    ArraySet arraySet = new ArraySet();
                    Iterator it2 = arrayList2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            Map map6 = (Map) it2.next();
                            if (map6.isEmpty()) {
                                batteryDiffData =
                                        new BatteryDiffData(
                                                context,
                                                longValue2,
                                                longValue3,
                                                intValue,
                                                intValue2,
                                                0L,
                                                arrayList4,
                                                arrayList5,
                                                set,
                                                set2,
                                                false);
                                arrayMap = arrayMap3;
                                i2 = i3;
                                list2 = list4;
                                break;
                            }
                            arraySet.addAll(map6.keySet());
                        } else {
                            Iterator it3 = arraySet.iterator();
                            while (it3.hasNext()) {
                                String str = (String) it3.next();
                                if (str != null) {
                                    ArrayList arrayList6 = new ArrayList();
                                    Iterator it4 = arrayList2.iterator();
                                    Iterator it5 = it3;
                                    BatteryHistEntry batteryHistEntry = null;
                                    while (it4.hasNext()) {
                                        List list6 = list4;
                                        Map map7 = (Map) it4.next();
                                        ArrayList arrayList7 = arrayList2;
                                        BatteryHistEntry batteryHistEntry2 =
                                                EMPTY_BATTERY_HIST_ENTRY;
                                        BatteryHistEntry batteryHistEntry3 =
                                                (BatteryHistEntry)
                                                        map7.getOrDefault(str, batteryHistEntry2);
                                        arrayList6.add(batteryHistEntry3);
                                        if (batteryHistEntry == null
                                                && batteryHistEntry3 != batteryHistEntry2) {
                                            batteryHistEntry = batteryHistEntry3;
                                        }
                                        arrayList2 = arrayList7;
                                        list4 = list6;
                                    }
                                    List list7 = list4;
                                    ArrayList arrayList8 = arrayList2;
                                    if (batteryHistEntry == null) {
                                        it3 = it5;
                                        arrayList2 = arrayList8;
                                        list4 = list7;
                                    } else {
                                        int i7 = i3;
                                        long j2 = batteryHistEntry.mUserId;
                                        int i8 = batteryHistEntry.mConsumerType;
                                        if (i8 == 1) {
                                            if (!((ArrayList) userIdsSeries2.mVisibleUserIds)
                                                    .contains(Integer.valueOf((int) j2))) {
                                                userIdsSeries2 = userIdsSeries;
                                                it3 = it5;
                                                arrayList2 = arrayList8;
                                                list4 = list7;
                                                i3 = i7;
                                            }
                                        }
                                        long j3 = j;
                                        ArrayList arrayList9 = arrayList4;
                                        Long l4 = l;
                                        long j4 = 0;
                                        long j5 = 0;
                                        long j6 = 0;
                                        double d = 0.0d;
                                        double d2 = 0.0d;
                                        double d3 = 0.0d;
                                        double d4 = 0.0d;
                                        double d5 = 0.0d;
                                        int i9 = 0;
                                        while (true) {
                                            arrayMap2 = arrayMap3;
                                            if (i9 >= arrayList6.size() - 1) {
                                                break;
                                            }
                                            BatteryHistEntry batteryHistEntry4 =
                                                    (BatteryHistEntry) arrayList6.get(i9);
                                            i9++;
                                            BatteryHistEntry batteryHistEntry5 =
                                                    (BatteryHistEntry) arrayList6.get(i9);
                                            Map map8 = map5;
                                            long j7 = j2;
                                            long j8 = batteryHistEntry4.mForegroundUsageTimeInMs;
                                            ArrayList arrayList10 = arrayList6;
                                            long j9 = longValue;
                                            long j10 = batteryHistEntry5.mForegroundUsageTimeInMs;
                                            j6 = (j10 > j8 ? j10 - j8 : 0L) + j6;
                                            long j11 =
                                                    batteryHistEntry5
                                                            .mForegroundServiceUsageTimeInMs;
                                            long j12 =
                                                    batteryHistEntry4
                                                            .mForegroundServiceUsageTimeInMs;
                                            j4 += j11 > j12 ? j11 - j12 : 0L;
                                            long j13 = batteryHistEntry5.mBackgroundUsageTimeInMs;
                                            long j14 = batteryHistEntry4.mBackgroundUsageTimeInMs;
                                            j5 += j13 > j14 ? j13 - j14 : 0L;
                                            d =
                                                    getDiffValue(
                                                                    batteryHistEntry4.mConsumePower,
                                                                    batteryHistEntry5.mConsumePower)
                                                            + d;
                                            d2 =
                                                    getDiffValue(
                                                                    batteryHistEntry4
                                                                            .mForegroundUsageConsumePower,
                                                                    batteryHistEntry5
                                                                            .mForegroundUsageConsumePower)
                                                            + d2;
                                            d3 =
                                                    getDiffValue(
                                                                    batteryHistEntry4
                                                                            .mForegroundServiceUsageConsumePower,
                                                                    batteryHistEntry5
                                                                            .mForegroundServiceUsageConsumePower)
                                                            + d3;
                                            d4 =
                                                    getDiffValue(
                                                                    batteryHistEntry4
                                                                            .mBackgroundUsageConsumePower,
                                                                    batteryHistEntry5
                                                                            .mBackgroundUsageConsumePower)
                                                            + d4;
                                            d5 =
                                                    getDiffValue(
                                                                    batteryHistEntry4
                                                                            .mCachedUsageConsumePower,
                                                                    batteryHistEntry5
                                                                            .mCachedUsageConsumePower)
                                                            + d5;
                                            arrayList6 = arrayList10;
                                            map5 = map8;
                                            arrayMap3 = arrayMap2;
                                            j2 = j7;
                                            longValue = j9;
                                        }
                                        Map map9 = map5;
                                        long j15 = j2;
                                        long j16 = longValue;
                                        long j17 =
                                                (i8 == 3 && batteryHistEntry.mDrainType == 0)
                                                        ? j3
                                                        : j6;
                                        if (j17 == 0 && j4 == 0 && j5 == 0 && d == 0.0d) {
                                            userIdsSeries2 = userIdsSeries;
                                            arrayList4 = arrayList9;
                                            map5 = map9;
                                            it3 = it5;
                                            arrayList2 = arrayList8;
                                            list4 = list7;
                                            i3 = i7;
                                            j = j3;
                                            l = l4;
                                            arrayMap3 = arrayMap2;
                                            longValue = j16;
                                        } else {
                                            float f = j17 + j5 + j4;
                                            longValue = j16;
                                            float f2 = longValue;
                                            if (f > f2) {
                                                float f3 = f2 / f;
                                                if (sDebug) {
                                                    Log.w(
                                                            "DataProcessor",
                                                            String.format(
                                                                    "abnormal usage time %d|%d|%d"
                                                                        + " for:\n"
                                                                        + "%s",
                                                                    Long.valueOf(
                                                                            Duration.ofMillis(j17)
                                                                                    .getSeconds()),
                                                                    Long.valueOf(
                                                                            Duration.ofMillis(j4)
                                                                                    .getSeconds()),
                                                                    Long.valueOf(
                                                                            Duration.ofMillis(j5)
                                                                                    .getSeconds()),
                                                                    batteryHistEntry));
                                                }
                                                j17 = Math.round(j17 * f3);
                                                j4 = Math.round(j4 * f3);
                                                j5 = Math.round(j5 * f3);
                                                double d6 = f3;
                                                d *= d6;
                                                d2 *= d6;
                                                d3 *= d6;
                                                d4 *= d6;
                                                d5 *= d6;
                                            }
                                            map5 = map9;
                                            long min =
                                                    Math.min(
                                                            longValue,
                                                            getScreenOnTime(
                                                                    map5,
                                                                    j15,
                                                                    batteryHistEntry.mPackageName));
                                            long j18 = longValue - min;
                                            long min2 = Math.min(j5, j18);
                                            BatteryDiffEntry batteryDiffEntry =
                                                    new BatteryDiffEntry(
                                                            context,
                                                            batteryHistEntry.mUid,
                                                            batteryHistEntry.mUserId,
                                                            batteryHistEntry.getKey(),
                                                            batteryHistEntry.mIsHidden,
                                                            batteryHistEntry.mDrainType,
                                                            batteryHistEntry.mPackageName,
                                                            batteryHistEntry.mAppLabel,
                                                            batteryHistEntry.mConsumerType,
                                                            j17,
                                                            Math.min(j4, j18 - min2),
                                                            min2,
                                                            min,
                                                            d,
                                                            d2,
                                                            d3,
                                                            d4,
                                                            d5);
                                            if (batteryDiffEntry.isSystemEntry()) {
                                                arrayList5.add(batteryDiffEntry);
                                                arrayList = arrayList9;
                                            } else {
                                                arrayList = arrayList9;
                                                arrayList.add(batteryDiffEntry);
                                            }
                                            userIdsSeries2 = userIdsSeries;
                                            arrayList4 = arrayList;
                                            it3 = it5;
                                            arrayList2 = arrayList8;
                                            list4 = list7;
                                            i3 = i7;
                                            j = j3;
                                            l = l4;
                                            arrayMap3 = arrayMap2;
                                        }
                                    }
                                }
                            }
                            i2 = i3;
                            list2 = list4;
                            batteryDiffData =
                                    new BatteryDiffData(
                                            context,
                                            longValue2,
                                            longValue3,
                                            intValue,
                                            intValue2,
                                            j,
                                            arrayList4,
                                            arrayList5,
                                            set,
                                            set2,
                                            false);
                            arrayMap = arrayMap3;
                        }
                    }
                    arrayMap.put(l, batteryDiffData);
                    list3 = list;
                    map3 = map;
                    map4 = map2;
                    arrayMap3 = arrayMap;
                    i4 = i;
                    list4 = list2;
                    i3 = i2;
                    userIdsSeries2 = userIdsSeries;
                }
            }
            i3++;
            list3 = list;
            map3 = map;
            map4 = map2;
            arrayMap3 = arrayMap3;
            userIdsSeries2 = userIdsSeries;
        }
        return arrayMap3;
    }

    public static List getBatteryHistListFromFromStatsService(Context context) {
        List<BatteryHistEntry> list = null;
        try {
            BatteryUsageStats batteryUsageStats =
                    ((BatteryStatsManager) context.getSystemService(BatteryStatsManager.class))
                            .getBatteryUsageStats(
                                    new BatteryUsageStatsQuery.Builder()
                                            .includeBatteryHistory()
                                            .includeProcessStateData()
                                            .build());
            list =
                    convertToBatteryHistEntry(
                            generateBatteryEntryListFromBatteryUsageStats(
                                    context, batteryUsageStats),
                            batteryUsageStats);
            if (batteryUsageStats != null) {
                try {
                    batteryUsageStats.close();
                } catch (Exception e) {
                    Log.e("DataProcessor", "BatteryUsageStats.close() failed", e);
                }
            }
        } catch (RuntimeException e2) {
            Log.e("DataProcessor", "load batteryUsageStats:", e2);
        }
        return list;
    }

    public static Map getCurrentBatteryHistoryMapFromStatsService(Context context) {
        List batteryHistListFromFromStatsService = getBatteryHistListFromFromStatsService(context);
        return batteryHistListFromFromStatsService == null
                ? new ArrayMap()
                : (Map)
                        batteryHistListFromFromStatsService.stream()
                                .collect(
                                        Collectors.toMap(
                                                new DataProcessor$$ExternalSyntheticLambda0(5),
                                                new DataProcessor$$ExternalSyntheticLambda0(6)));
    }

    public static double getDiffValue(double d, double d2) {
        if (d2 > d) {
            return d2 - d;
        }
        return 0.0d;
    }

    public static Map getHistoryMapWithExpectedTimestamps(Map map) {
        long j;
        ArrayMap arrayMap;
        String str;
        long j2;
        List<Long> list;
        String str2;
        long j3;
        ArrayList arrayList;
        ArrayMap arrayMap2;
        int i;
        int i2;
        Long l;
        double d;
        Iterator it;
        Long l2;
        Long l3;
        Map map2;
        long j4;
        double interpolate;
        Map map3 = map;
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList2 = new ArrayList(map.keySet());
        ArrayMap arrayMap3 = new ArrayMap();
        String str3 = "DataProcessor";
        if (arrayList2.isEmpty()) {
            Log.d(
                    "DataProcessor",
                    "empty batteryHistoryMap in getHistoryMapWithExpectedTimestamps()");
            return arrayMap3;
        }
        Collections.sort(arrayList2);
        long j5 = sTestCurrentTimeMillis;
        if (j5 <= 0) {
            j5 = System.currentTimeMillis();
        }
        List<Long> timestampSlots = getTimestampSlots(arrayList2, j5);
        if (arrayList2.isEmpty() || timestampSlots.isEmpty()) {
            j = currentTimeMillis;
            arrayMap = arrayMap3;
            str = "DataProcessor";
        } else {
            int size = timestampSlots.size();
            char c = 0;
            Long l4 = timestampSlots.get(0);
            l4.getClass();
            char c2 = 1;
            int i3 = size - 1;
            Long l5 = timestampSlots.get(i3);
            l5.getClass();
            arrayMap3.put(l4, (Map) map3.get(l4));
            int i4 = 1;
            while (i4 < i3) {
                Long l6 = timestampSlots.get(i4);
                long longValue = l6.longValue();
                long[] findNearestTimestamp = findNearestTimestamp(arrayList2, longValue);
                long j6 = findNearestTimestamp[c];
                long j7 = findNearestTimestamp[c2];
                if (j7 == 0) {
                    log("job scheduler is delayed", longValue, null);
                    arrayMap3.put(l6, new ArrayMap());
                } else {
                    long j8 = j7 - longValue;
                    if (j8 < 5000) {
                        log("force align into the nearest slot", longValue, null);
                        arrayMap3.put(l6, (Map) map3.get(Long.valueOf(j7)));
                    } else {
                        j2 = 0;
                        if (j6 == 0) {
                            log("no lower timestamp slot data", longValue, null);
                            arrayMap3.put(l6, new ArrayMap());
                            j3 = currentTimeMillis;
                            arrayList = arrayList2;
                            arrayMap2 = arrayMap3;
                            list = timestampSlots;
                            str2 = str3;
                        } else {
                            Map map4 = (Map) map3.get(Long.valueOf(j6));
                            Map map5 = (Map) map3.get(Long.valueOf(j7));
                            BatteryHistEntry batteryHistEntry =
                                    (BatteryHistEntry) map5.values().stream().findFirst().get();
                            list = timestampSlots;
                            str2 = str3;
                            j3 = currentTimeMillis;
                            if (j6
                                    < batteryHistEntry.mTimestamp
                                            - batteryHistEntry.mBootTimestamp) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(longValue);
                                if (calendar.get(11) != 0
                                        || calendar.get(12) != 0
                                        || calendar.get(13) != 0
                                        || calendar.get(14) != 0) {
                                    if (j8 < 600000) {
                                        log("force align into the nearest slot", longValue, null);
                                        arrayMap3.put(l6, map5);
                                    } else {
                                        log("in the different booting section", longValue, null);
                                        arrayMap3.put(l6, new ArrayMap());
                                    }
                                    arrayList = arrayList2;
                                    arrayMap2 = arrayMap3;
                                }
                            }
                            log("apply interpolation arithmetic", longValue, null);
                            ArrayMap arrayMap4 = new ArrayMap();
                            double d2 = j7 - j6;
                            double d3 = longValue - j6;
                            Iterator it2 = map5.keySet().iterator();
                            while (it2.hasNext()) {
                                ArrayList arrayList3 = arrayList2;
                                String str4 = (String) it2.next();
                                int i5 = i3;
                                BatteryHistEntry batteryHistEntry2 =
                                        (BatteryHistEntry) map4.get(str4);
                                Map map6 = map5;
                                BatteryHistEntry batteryHistEntry3 =
                                        (BatteryHistEntry) map5.get(str4);
                                if (batteryHistEntry2 != null) {
                                    it = it2;
                                    l2 = l5;
                                    l3 = l6;
                                    map2 = map4;
                                    boolean z =
                                            batteryHistEntry2.mForegroundUsageTimeInMs
                                                    > batteryHistEntry3.mForegroundUsageTimeInMs;
                                    d = d2;
                                    boolean z2 =
                                            batteryHistEntry2.mBackgroundUsageTimeInMs
                                                    > batteryHistEntry3.mBackgroundUsageTimeInMs;
                                    if (z || z2) {
                                        arrayMap4.put(str4, batteryHistEntry3);
                                        log(
                                                "abnormal reset condition is found",
                                                longValue,
                                                batteryHistEntry3);
                                        map4 = map2;
                                        arrayList2 = arrayList3;
                                        i3 = i5;
                                        map5 = map6;
                                        l5 = l2;
                                        it2 = it;
                                        l6 = l3;
                                        d2 = d;
                                    }
                                } else {
                                    d = d2;
                                    it = it2;
                                    l2 = l5;
                                    l3 = l6;
                                    map2 = map4;
                                }
                                double d4 = d3 / d;
                                double interpolate2 =
                                        BatteryHistEntry.interpolate(
                                                batteryHistEntry2 == null
                                                        ? 0.0d
                                                        : batteryHistEntry2.mTotalPower,
                                                batteryHistEntry3.mTotalPower,
                                                d4);
                                double interpolate3 =
                                        BatteryHistEntry.interpolate(
                                                batteryHistEntry2 == null
                                                        ? 0.0d
                                                        : batteryHistEntry2.mConsumePower,
                                                batteryHistEntry3.mConsumePower,
                                                d4);
                                double interpolate4 =
                                        BatteryHistEntry.interpolate(
                                                batteryHistEntry2 == null
                                                        ? 0.0d
                                                        : batteryHistEntry2
                                                                .mForegroundUsageConsumePower,
                                                batteryHistEntry3.mForegroundUsageConsumePower,
                                                d4);
                                double interpolate5 =
                                        BatteryHistEntry.interpolate(
                                                batteryHistEntry2 == null
                                                        ? 0.0d
                                                        : batteryHistEntry2
                                                                .mForegroundServiceUsageConsumePower,
                                                batteryHistEntry3
                                                        .mForegroundServiceUsageConsumePower,
                                                d4);
                                double interpolate6 =
                                        BatteryHistEntry.interpolate(
                                                batteryHistEntry2 == null
                                                        ? 0.0d
                                                        : batteryHistEntry2
                                                                .mBackgroundUsageConsumePower,
                                                batteryHistEntry3.mBackgroundUsageConsumePower,
                                                d4);
                                double interpolate7 =
                                        BatteryHistEntry.interpolate(
                                                batteryHistEntry2 != null
                                                        ? batteryHistEntry2.mCachedUsageConsumePower
                                                        : 0.0d,
                                                batteryHistEntry3.mCachedUsageConsumePower,
                                                d4);
                                double d5 = d3;
                                double interpolate8 =
                                        BatteryHistEntry.interpolate(
                                                batteryHistEntry2 == null
                                                        ? 0L
                                                        : batteryHistEntry2
                                                                .mForegroundUsageTimeInMs,
                                                batteryHistEntry3.mForegroundUsageTimeInMs,
                                                d4);
                                ArrayMap arrayMap5 = arrayMap3;
                                double interpolate9 =
                                        BatteryHistEntry.interpolate(
                                                batteryHistEntry2 == null
                                                        ? 0L
                                                        : batteryHistEntry2
                                                                .mForegroundServiceUsageTimeInMs,
                                                batteryHistEntry3.mForegroundServiceUsageTimeInMs,
                                                d4);
                                int i6 = i4;
                                double interpolate10 =
                                        BatteryHistEntry.interpolate(
                                                batteryHistEntry2 == null
                                                        ? 0L
                                                        : batteryHistEntry2
                                                                .mBackgroundUsageTimeInMs,
                                                batteryHistEntry3.mBackgroundUsageTimeInMs,
                                                d4);
                                int i7 = batteryHistEntry3.mBatteryLevel;
                                if (batteryHistEntry2 == null) {
                                    interpolate = i7;
                                    j4 = longValue;
                                } else {
                                    j4 = longValue;
                                    interpolate =
                                            BatteryHistEntry.interpolate(
                                                    batteryHistEntry2.mBatteryLevel, i7, d4);
                                }
                                Map map7 = map2;
                                arrayMap4.put(
                                        str4,
                                        new BatteryHistEntry(
                                                batteryHistEntry3,
                                                batteryHistEntry3.mBootTimestamp - j8,
                                                j4,
                                                interpolate2,
                                                interpolate3,
                                                interpolate4,
                                                interpolate5,
                                                interpolate6,
                                                interpolate7,
                                                Math.round(interpolate8),
                                                Math.round(interpolate9),
                                                Math.round(interpolate10),
                                                (int) Math.round(interpolate)));
                                if (batteryHistEntry2 == null) {
                                    long j9 = j4;
                                    log("cannot find lower entry data", j9, batteryHistEntry3);
                                    map4 = map7;
                                    i3 = i5;
                                    map5 = map6;
                                    l5 = l2;
                                    it2 = it;
                                    l6 = l3;
                                    d3 = d5;
                                    arrayMap3 = arrayMap5;
                                    i4 = i6;
                                    longValue = j9;
                                    arrayList2 = arrayList3;
                                    d2 = d;
                                } else {
                                    map4 = map7;
                                    arrayList2 = arrayList3;
                                    i3 = i5;
                                    map5 = map6;
                                    l5 = l2;
                                    it2 = it;
                                    l6 = l3;
                                    d2 = d;
                                    d3 = d5;
                                    arrayMap3 = arrayMap5;
                                    i4 = i6;
                                    longValue = j4;
                                }
                            }
                            arrayList = arrayList2;
                            arrayMap2 = arrayMap3;
                            i = i3;
                            i2 = i4;
                            l = l5;
                            arrayMap2.put(l6, arrayMap4);
                            i4 = i2 + 1;
                            map3 = map;
                            arrayMap3 = arrayMap2;
                            str3 = str2;
                            timestampSlots = list;
                            currentTimeMillis = j3;
                            arrayList2 = arrayList;
                            i3 = i;
                            l5 = l;
                            c = 0;
                            c2 = 1;
                        }
                        i = i3;
                        i2 = i4;
                        l = l5;
                        i4 = i2 + 1;
                        map3 = map;
                        arrayMap3 = arrayMap2;
                        str3 = str2;
                        timestampSlots = list;
                        currentTimeMillis = j3;
                        arrayList2 = arrayList;
                        i3 = i;
                        l5 = l;
                        c = 0;
                        c2 = 1;
                    }
                }
                j3 = currentTimeMillis;
                arrayList = arrayList2;
                arrayMap2 = arrayMap3;
                list = timestampSlots;
                str2 = str3;
                i = i3;
                i2 = i4;
                l = l5;
                j2 = 0;
                i4 = i2 + 1;
                map3 = map;
                arrayMap3 = arrayMap2;
                str3 = str2;
                timestampSlots = list;
                currentTimeMillis = j3;
                arrayList2 = arrayList;
                i3 = i;
                l5 = l;
                c = 0;
                c2 = 1;
            }
            j = currentTimeMillis;
            arrayMap = arrayMap3;
            str = str3;
            arrayMap.put(
                    l5,
                    Map.of("CURRENT_TIME_BATTERY_HISTORY_PLACEHOLDER", EMPTY_BATTERY_HIST_ENTRY));
        }
        Log.d(
                str,
                String.format(
                        "getHistoryMapWithExpectedTimestamps() size=%d in %d/ms",
                        Integer.valueOf(arrayMap.size()),
                        BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0.m(
                                j)));
        return arrayMap;
    }

    public static BatteryLevelData getLevelDataThroughProcessedHistoryMap(
            Context context, Map map) {
        int i;
        ArrayMap arrayMap = (ArrayMap) map;
        if (arrayMap.size() < 2) {
            return null;
        }
        ArrayMap arrayMap2 = new ArrayMap();
        for (Long l : arrayMap.keySet()) {
            long longValue = l.longValue();
            Map map2 = (Map) arrayMap.get(l);
            if (map2 == null || map2.isEmpty()) {
                Log.e(
                        "DataProcessor",
                        "abnormal entry list in the timestamp:"
                                + ConvertUtils.utcToLocalTimeForLogging(longValue));
                i = -1;
            } else if (map2.containsKey("CURRENT_TIME_BATTERY_HISTORY_PLACEHOLDER")) {
                i =
                        Integer.valueOf(
                                BatteryStatus.getBatteryLevel(
                                        BatteryUtils.getBatteryIntent(context)));
            } else {
                float f = 0.0f;
                while (map2.values().iterator().hasNext()) {
                    f += ((BatteryHistEntry) r3.next()).mBatteryLevel;
                }
                i = Integer.valueOf(Math.round(f / map2.size()));
            }
            arrayMap2.put(l, i);
        }
        return new BatteryLevelData(arrayMap2);
    }

    public static int getRealUid(UidBatteryConsumer uidBatteryConsumer) {
        int uid = uidBatteryConsumer.getUid();
        if (UserHandle.getAppIdFromSharedAppGid(uidBatteryConsumer.getUid()) > 0) {
            uid =
                    UserHandle.getUid(
                            0, UserHandle.getAppIdFromSharedAppGid(uidBatteryConsumer.getUid()));
        }
        int appId = UserHandle.getAppId(uid);
        if (appId < 1000
                || appId >= 10000
                || "mediaserver".equals(uidBatteryConsumer.getPackageWithHighestDrain())) {
            return uid;
        }
        return 1000;
    }

    @VisibleForTesting
    public static long getScreenOnTime(
            Map<Long, Map<String, List<AppUsagePeriod>>> map, long j, String str) {
        if (map == null || map.get(Long.valueOf(j)) == null) {
            return 0L;
        }
        return getScreenOnTime(map.get(Long.valueOf(j)).get(str));
    }

    public static Set getSystemAppsUids(Context context) {
        ArraySet arraySet = new ArraySet(1);
        try {
            arraySet.add(
                    Integer.valueOf(
                            context.getPackageManager().getUidForSharedUser("android.uid.shared")));
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return arraySet;
    }

    @VisibleForTesting
    public static List<Long> getTimestampSlots(List<Long> list, long j) {
        ArrayList arrayList = new ArrayList();
        if (list.isEmpty()) {
            return arrayList;
        }
        Long l = list.get(0);
        long longValue = l.longValue();
        if (longValue >= j) {
            return arrayList;
        }
        arrayList.add(l);
        Calendar sharpHourCalendar = TimestampUtils.getSharpHourCalendar(longValue);
        sharpHourCalendar.add(11, 1);
        for (long timeInMillis = sharpHourCalendar.getTimeInMillis();
                timeInMillis < j;
                timeInMillis += 3600000) {
            arrayList.add(Long.valueOf(timeInMillis));
        }
        arrayList.add(Long.valueOf(j));
        return arrayList;
    }

    @VisibleForTesting
    public static boolean isFromFullCharge(Map<String, BatteryHistEntry> map) {
        if (map == null) {
            Log.d("DataProcessor", "entryList is null in isFromFullCharge()");
            return false;
        }
        ArrayList arrayList = new ArrayList(map.keySet());
        if (arrayList.isEmpty()) {
            Log.d("DataProcessor", "empty entryList in isFromFullCharge()");
            return false;
        }
        BatteryHistEntry batteryHistEntry = map.get(arrayList.get(0));
        return batteryHistEntry.mBatteryStatus == 5 || batteryHistEntry.mBatteryLevel >= 100;
    }

    public static void log(String str, long j, BatteryHistEntry batteryHistEntry) {
        if (sDebug) {
            Log.d(
                    "DataProcessor",
                    String.format(
                            batteryHistEntry != null ? "%s %s:\n%s" : "%s %s:%s",
                            ConvertUtils.utcToLocalTimeForLogging(j),
                            str,
                            batteryHistEntry));
        }
    }

    public static void validateAndAddToPeriodList(
            List list, AppUsagePeriod appUsagePeriod, long j, long j2) {
        long min = Math.min(Math.max(appUsagePeriod.getStartTime(), j), j2);
        long min2 = Math.min(Math.max(appUsagePeriod.getEndTime(), j), j2);
        if (min < min2) {
            AppUsagePeriod.Builder newBuilder = AppUsagePeriod.newBuilder();
            newBuilder.setStartTime(min);
            newBuilder.setEndTime(min2);
            ((ArrayList) list).add((AppUsagePeriod) newBuilder.build());
        }
    }

    public static long getScreenOnTime(List list) {
        long j = 0;
        if (list != null && !list.isEmpty()) {
            int i = 0;
            long j2 = 0;
            for (AppUsageEndPoint appUsageEndPoint :
                    (List)
                            list.stream()
                                    .flatMap(new DataProcessor$$ExternalSyntheticLambda0(7))
                                    .sorted(new DataProcessor$$ExternalSyntheticLambda9())
                                    .collect(Collectors.toList())) {
                if (appUsageEndPoint.getType() == AppUsageEndPointType.START) {
                    int i2 = i + 1;
                    if (i == 0) {
                        j2 = appUsageEndPoint.getTimestamp();
                    }
                    i = i2;
                } else {
                    i--;
                    if (i == 0) {
                        j = (appUsageEndPoint.getTimestamp() - j2) + j;
                    }
                }
            }
        }
        return j;
    }
}
