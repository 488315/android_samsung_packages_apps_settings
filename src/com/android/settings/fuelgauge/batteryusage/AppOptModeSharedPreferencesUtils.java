package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;
import com.android.settings.fuelgauge.BatteryOptimizeHistoricalLogEntry;
import com.android.settings.fuelgauge.BatteryOptimizeUtils;
import com.android.settings.fuelgauge.BatteryUtils;

import com.google.protobuf.MessageLite;

import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppOptModeSharedPreferencesUtils {
    public static final AppOptModeSharedPreferencesUtils INSTANCE =
            new AppOptModeSharedPreferencesUtils();
    public static final Object appOptimizationModeLock = new Object();
    public static final AppOptimizationModeEvent defaultInstance =
            AppOptimizationModeEvent.getDefaultInstance();

    public static final void deleteAppOptimizationModeEventByUid(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (appOptimizationModeLock) {
            List listOf = CollectionsKt__CollectionsJVMKt.listOf(Integer.valueOf(i));
            SharedPreferences.Editor edit = getSharedPreferences(context).edit();
            Iterator it = listOf.iterator();
            while (it.hasNext()) {
                edit.remove(String.valueOf(((Number) it.next()).intValue()));
            }
            edit.apply();
        }
    }

    public static final List getAllEvents(Context context) {
        List list;
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (appOptimizationModeLock) {
            Collection values = getAppOptModeEventsMap(context).values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            list = CollectionsKt___CollectionsKt.toList(values);
        }
        return list;
    }

    public static ArrayMap getAppOptModeEventsMap(Context context) {
        Set<String> set;
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        Map<String, ?> all = sharedPreferences.getAll();
        if (all == null || (set = all.keySet()) == null) {
            set = EmptySet.INSTANCE;
        }
        if (set.isEmpty()) {
            return new ArrayMap();
        }
        ArrayMap arrayMap = new ArrayMap(set.size());
        for (String str : set) {
            String string = sharedPreferences.getString(str, null);
            if (string != null) {
                Intrinsics.checkNotNull(str);
                Integer valueOf = Integer.valueOf(Integer.parseInt(str));
                MessageLite parseProtoFromString =
                        BatteryUtils.parseProtoFromString(string, defaultInstance);
                Intrinsics.checkNotNullExpressionValue(
                        parseProtoFromString, "parseProtoFromString(...)");
                arrayMap.put(valueOf, (AppOptimizationModeEvent) parseProtoFromString);
            }
        }
        return arrayMap;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sharedPreferences =
                context.getApplicationContext()
                        .getSharedPreferences("app_optimization_mode_shared_prefs", 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        return sharedPreferences;
    }

    public static void updateSharedPreferences(Context context, Map map) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        for (Map.Entry entry : ((ArrayMap) map).entrySet()) {
            int intValue = ((Number) entry.getKey()).intValue();
            AppOptimizationModeEvent appOptimizationModeEvent =
                    (AppOptimizationModeEvent) entry.getValue();
            String valueOf = String.valueOf(intValue);
            String encodeToString =
                    Base64.encodeToString(appOptimizationModeEvent.toByteArray(), 0);
            Intrinsics.checkNotNullExpressionValue(encodeToString, "encodeToString(...)");
            edit.putString(valueOf, encodeToString);
        }
        edit.apply();
    }

    public final void updateAppOptModeExpirationInternal(
            Context context,
            List<Integer> list,
            List<String> packageNames,
            List<Integer> optimizationModes,
            long[] expirationTimes,
            Function2 getBatteryOptimizeUtils) {
        List<Integer> uids = list;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uids, "uids");
        Intrinsics.checkNotNullParameter(packageNames, "packageNames");
        Intrinsics.checkNotNullParameter(optimizationModes, "optimizationModes");
        Intrinsics.checkNotNullParameter(expirationTimes, "expirationTimes");
        Intrinsics.checkNotNullParameter(getBatteryOptimizeUtils, "getBatteryOptimizeUtils");
        synchronized (appOptimizationModeLock) {
            try {
                ArrayMap appOptModeEventsMap = getAppOptModeEventsMap(context);
                ArrayMap arrayMap = new ArrayMap();
                int size = list.size();
                int i = 0;
                while (i < size) {
                    int intValue = uids.get(i).intValue();
                    String str = packageNames.get(i);
                    int intValue2 = optimizationModes.get(i).intValue();
                    int i2 = i;
                    int i3 = size;
                    int updateBatteryOptimizationMode =
                            INSTANCE.updateBatteryOptimizationMode(
                                    context,
                                    intValue,
                                    str,
                                    intValue2,
                                    BatteryOptimizeHistoricalLogEntry.Action.EXTERNAL_UPDATE,
                                    (BatteryOptimizeUtils)
                                            getBatteryOptimizeUtils.invoke(
                                                    Integer.valueOf(intValue), str));
                    if (updateBatteryOptimizationMode != 0) {
                        AppOptimizationModeEvent appOptimizationModeEvent =
                                (AppOptimizationModeEvent)
                                        appOptModeEventsMap.get(Integer.valueOf(intValue));
                        int resetOptimizationMode =
                                appOptimizationModeEvent != null
                                        ? appOptimizationModeEvent.getResetOptimizationMode()
                                        : updateBatteryOptimizationMode;
                        long j = expirationTimes[i2];
                        if (j != -1) {
                            Log.d(
                                    "AppOptModeSharedPreferencesUtils",
                                    "setOptimizationMode("
                                            + str
                                            + ") from "
                                            + updateBatteryOptimizationMode
                                            + " to "
                                            + intValue2
                                            + " with expiration time "
                                            + j);
                            Integer valueOf = Integer.valueOf(intValue);
                            AppOptimizationModeEvent.Builder newBuilder =
                                    AppOptimizationModeEvent.newBuilder();
                            newBuilder.copyOnWrite();
                            AppOptimizationModeEvent.m876$$Nest$msetUid(
                                    (AppOptimizationModeEvent) newBuilder.instance, intValue);
                            newBuilder.copyOnWrite();
                            AppOptimizationModeEvent.m874$$Nest$msetPackageName(
                                    (AppOptimizationModeEvent) newBuilder.instance, str);
                            newBuilder.copyOnWrite();
                            AppOptimizationModeEvent.m875$$Nest$msetResetOptimizationMode(
                                    (AppOptimizationModeEvent) newBuilder.instance,
                                    resetOptimizationMode);
                            newBuilder.copyOnWrite();
                            AppOptimizationModeEvent.m873$$Nest$msetExpirationTime(
                                    (AppOptimizationModeEvent) newBuilder.instance, j);
                            arrayMap.put(valueOf, newBuilder.build());
                        }
                    }
                    i = i2 + 1;
                    uids = list;
                    size = i3;
                }
                if (!arrayMap.isEmpty()) {
                    updateSharedPreferences(context, arrayMap);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int updateBatteryOptimizationMode(
            Context context,
            int i,
            String packageName,
            int i2,
            BatteryOptimizeHistoricalLogEntry.Action action,
            BatteryOptimizeUtils batteryOptimizeUtils) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(action, "action");
        Intrinsics.checkNotNullParameter(batteryOptimizeUtils, "batteryOptimizeUtils");
        if (!((batteryOptimizeUtils.isDisabledForOptimizeModeOnly()
                        || batteryOptimizeUtils.isSystemOrDefaultApp())
                ? false
                : true)) {
            Log.w(
                    "AppOptModeSharedPreferencesUtils",
                    "Fail to update immutable optimization mode for: ".concat(packageName));
            return 0;
        }
        int appOptimizationMode = batteryOptimizeUtils.getAppOptimizationMode(true);
        batteryOptimizeUtils.setAppUsageState(i2, action);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        i2, "setAppUsageState(", packageName, ") to ", " with action = "),
                action.name(),
                "AppOptModeSharedPreferencesUtils");
        return appOptimizationMode;
    }

    public static /* synthetic */ void getUNLIMITED_EXPIRE_TIME$annotations() {}
}
