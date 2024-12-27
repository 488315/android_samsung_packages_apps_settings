package com.android.settingslib.bluetooth;

import android.content.Context;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.FrameworkStatsLog;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class HearingAidStatsLogUtils {
    public static final HashMap HISTORY_TYPE_TO_SP_NAME_MAPPING;
    public static final HashMap sDeviceAddressToBondEntryMap = new HashMap();
    public static final Set sJustBondedDeviceAddressSet = new HashSet();

    static {
        HashMap hashMap = new HashMap();
        HISTORY_TYPE_TO_SP_NAME_MAPPING = hashMap;
        hashMap.put(0, "bt_hearing_aids_paired_history");
        hashMap.put(1, "bt_hearing_aids_connected_history");
        hashMap.put(2, "bt_hearing_devices_paired_history");
        hashMap.put(3, "bt_hearing_devices_connected_history");
    }

    public static void addCurrentTimeToHistory(Context context, int i) {
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (HearingAidStatsLogUtils.class) {
            LinkedList history = getHistory(context, i);
            if (history == null) {
                Log.w(
                        "HearingAidStatsLogUtils",
                        "Couldn't find shared preference name matched type=" + i);
            } else if (history.peekLast() != null
                    && dayDifference(currentTimeMillis, ((Long) history.peekLast()).longValue())
                            == 0) {
                Log.w("HearingAidStatsLogUtils", "Skip this record, it's same day record");
            } else {
                history.add(Long.valueOf(currentTimeMillis));
                context.getSharedPreferences("accessibility_prefs", 0)
                        .edit()
                        .putString(
                                (String) HISTORY_TYPE_TO_SP_NAME_MAPPING.get(Integer.valueOf(i)),
                                (String)
                                        history.stream()
                                                .map(
                                                        new HearingAidStatsLogUtils$$ExternalSyntheticLambda2())
                                                .collect(Collectors.joining(",")))
                        .apply();
            }
        }
    }

    public static LinkedList convertToHistoryList(String str) {
        if (str == null || str.isEmpty()) {
            return new LinkedList();
        }
        LinkedList linkedList = new LinkedList();
        for (String str2 : str.split(",")) {
            if (!str2.isEmpty()) {
                linkedList.offer(Long.valueOf(Long.parseLong(str2)));
            }
        }
        return linkedList;
    }

    public static long dayDifference(long j, long j2) {
        ZoneId systemDefault = ZoneId.systemDefault();
        return Math.abs(
                ChronoUnit.DAYS.between(
                        Instant.ofEpochMilli(j).atZone(systemDefault).toLocalDate(),
                        Instant.ofEpochMilli(j2).atZone(systemDefault).toLocalDate()));
    }

    @VisibleForTesting
    public static HashMap<String, Integer> getDeviceAddressToBondEntryMap() {
        return sDeviceAddressToBondEntryMap;
    }

    public static synchronized LinkedList getHistory(Context context, int i) {
        synchronized (HearingAidStatsLogUtils.class) {
            String str = (String) HISTORY_TYPE_TO_SP_NAME_MAPPING.get(Integer.valueOf(i));
            if (!"bt_hearing_aids_paired_history".equals(str)
                    && !"bt_hearing_devices_paired_history".equals(str)) {
                if (!"bt_hearing_aids_connected_history".equals(str)
                        && !"bt_hearing_devices_connected_history".equals(str)) {
                    return null;
                }
                LinkedList convertToHistoryList =
                        convertToHistoryList(
                                context.getSharedPreferences("accessibility_prefs", 0)
                                        .getString(str, ApnSettings.MVNO_NONE));
                removeRecordsBeforeDay(convertToHistoryList, 7);
                return convertToHistoryList;
            }
            LinkedList convertToHistoryList2 =
                    convertToHistoryList(
                            context.getSharedPreferences("accessibility_prefs", 0)
                                    .getString(str, ApnSettings.MVNO_NONE));
            removeRecordsBeforeDay(convertToHistoryList2, 30);
            return convertToHistoryList2;
        }
    }

    public static void logHearingAidInfo(CachedBluetoothDevice cachedBluetoothDevice) {
        String address = cachedBluetoothDevice.mDevice.getAddress();
        HashMap hashMap = sDeviceAddressToBondEntryMap;
        if (!hashMap.containsKey(address)) {
            Log.w(
                    "HearingAidStatsLogUtils",
                    "The device address was not found. Hearing aid device info is not logged.");
            return;
        }
        int intValue = ((Integer) hashMap.getOrDefault(address, -1)).intValue();
        HearingAidInfo hearingAidInfo = cachedBluetoothDevice.mHearingAidInfo;
        FrameworkStatsLog.write(
                FileType.SSF,
                hearingAidInfo != null ? hearingAidInfo.mMode : -1,
                cachedBluetoothDevice.getDeviceSide(),
                intValue);
        hashMap.remove(address);
    }

    public static void removeRecordsBeforeDay(LinkedList linkedList, int i) {
        if (linkedList.isEmpty()) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        while (linkedList.peekFirst() != null
                && dayDifference(currentTimeMillis, ((Long) linkedList.peekFirst()).longValue())
                        >= i) {
            linkedList.poll();
        }
    }

    public static void updateHistoryIfNeeded(
            Context context,
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i) {
        String address = cachedBluetoothDevice.mDevice.getAddress();
        Set set = sJustBondedDeviceAddressSet;
        if (((HashSet) set).contains(address)) {
            final int i2 = 0;
            if (cachedBluetoothDevice.getProfiles().stream()
                    .anyMatch(
                            new Predicate() { // from class:
                                              // com.android.settingslib.bluetooth.HearingAidStatsLogUtils$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    LocalBluetoothProfile localBluetoothProfile2 =
                                            (LocalBluetoothProfile) obj;
                                    switch (i2) {
                                        case 0:
                                            return (localBluetoothProfile2
                                                            instanceof HearingAidProfile)
                                                    || (localBluetoothProfile2
                                                            instanceof HapClientProfile);
                                        default:
                                            return localBluetoothProfile2 instanceof HeadsetProfile;
                                    }
                                }
                            })) {
                addCurrentTimeToHistory(context, 0);
            } else {
                final int i3 = 1;
                if (cachedBluetoothDevice.getProfiles().stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.android.settingslib.bluetooth.HearingAidStatsLogUtils$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        LocalBluetoothProfile localBluetoothProfile2 =
                                                (LocalBluetoothProfile) obj;
                                        switch (i3) {
                                            case 0:
                                                return (localBluetoothProfile2
                                                                instanceof HearingAidProfile)
                                                        || (localBluetoothProfile2
                                                                instanceof HapClientProfile);
                                            default:
                                                return localBluetoothProfile2
                                                        instanceof HeadsetProfile;
                                        }
                                    }
                                })) {
                    addCurrentTimeToHistory(context, 2);
                }
            }
            ((HashSet) set).remove(cachedBluetoothDevice.mDevice.getAddress());
        }
        if (i == 2) {
            if ((localBluetoothProfile instanceof HearingAidProfile)
                    || (localBluetoothProfile instanceof HapClientProfile)) {
                addCurrentTimeToHistory(context, 1);
            } else if (localBluetoothProfile instanceof HeadsetProfile) {
                addCurrentTimeToHistory(context, 3);
            }
        }
    }
}
