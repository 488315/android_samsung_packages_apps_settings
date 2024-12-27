package com.android.settings.fuelgauge;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.InstallSourceInfo;
import android.content.pm.PackageManager;
import android.os.BatteryStats;
import android.os.BatteryStatsManager;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UidBatteryConsumer;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.internal.util.ArrayUtils;
import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;
import com.android.settings.fuelgauge.batterytip.BatteryDatabaseManager;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.fuelgauge.Estimate;
import com.android.settingslib.utils.PowerUtil;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.utils.ThreadUtils;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.fuelgauge.DeviceCareManager;
import com.samsung.android.settings.fuelgauge.Reason;
import com.samsung.android.util.SemLog;

import kotlin.jvm.internal.Intrinsics;

import java.time.Duration;
import java.time.Instant;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryUtils {
    public static BatteryUtils sInstance;
    public final AppOpsManager mAppOpsManager;
    public final Context mContext;
    public DeviceCareManager mDeviceCareManager;
    public final PackageManager mPackageManager;
    PowerUsageFeatureProvider mPowerUsageFeatureProvider;

    public BatteryUtils(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mPowerUsageFeatureProvider = featureFactoryImpl.getPowerUsageFeatureProvider();
        this.mDeviceCareManager = new DeviceCareManager(context);
    }

    public static String buildBatteryUsageTimeInfo(Context context, long j, int i, int i2) {
        return j <= 30000
                ? context.getString(i)
                : context.getString(
                        i2,
                        ((SpannableStringBuilder)
                                        StringUtil.formatElapsedTime(context, j, false, false))
                                .toString()
                                .replaceAll(",", ApnSettings.MVNO_NONE));
    }

    public static BatteryUtils getInstance(Context context) {
        BatteryUtils batteryUtils = sInstance;
        if (batteryUtils == null
                || batteryUtils.mPackageManager == null
                || batteryUtils.mAppOpsManager == null) {
            sInstance = new BatteryUtils(context.getApplicationContext());
        }
        return sInstance;
    }

    public static String getLoggingPackageName(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                InstallSourceInfo installSourceInfo =
                        context.getPackageManager().getInstallSourceInfo(str);
                if (installSourceInfo != null
                        && "com.android.vending"
                                .equals(installSourceInfo.getInitiatingPackageName())) {
                    return str;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return SignalSeverity.NONE;
    }

    public static void logRuntime(long j, String str, String str2) {
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(str2, ": ");
        m.append(System.currentTimeMillis() - j);
        m.append("ms");
        Log.d(str, m.toString());
    }

    public static MessageLite parseProtoFromString(String str, MessageLite messageLite) {
        GeneratedMessageLite.MethodToInvoke methodToInvoke =
                GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE;
        if (str == null || str.isEmpty()) {
            return (GeneratedMessageLite)
                    ((GeneratedMessageLite) messageLite).dynamicMethod(methodToInvoke, null);
        }
        try {
            return ((GeneratedMessageLite.DefaultInstanceBasedParser)
                            ((Parser)
                                    ((GeneratedMessageLite) messageLite)
                                            .dynamicMethod(
                                                    GeneratedMessageLite.MethodToInvoke.GET_PARSER,
                                                    null)))
                    .parseFrom(Base64.decode(str, 0));
        } catch (InvalidProtocolBufferException e) {
            Log.e("BatteryUtils", "Failed to deserialize proto class", e);
            return (GeneratedMessageLite)
                    ((GeneratedMessageLite) messageLite).dynamicMethod(methodToInvoke, null);
        }
    }

    public final void clearForceAppStandby(final String str) {
        final int packageUid = getPackageUid(str);
        if (packageUid == -1 || this.mAppOpsManager.checkOpNoThrow(70, packageUid, str) != 1) {
            return;
        }
        if (isPreOApp(str)) {
            this.mAppOpsManager.setMode(63, packageUid, str, 0);
        }
        if (this.mAppOpsManager.checkOpNoThrow(70, packageUid, str) == 1) {
            ((ActivityManager) this.mContext.getSystemService(ActivityManager.class))
                    .noteAppRestrictionEnabled(str, packageUid, 50, false, 4, "settings", 1, 0L);
        }
        this.mAppOpsManager.setMode(70, packageUid, str, 0);
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.fuelgauge.BatteryUtils$$ExternalSyntheticLambda1
                    public final /* synthetic */ int f$1 = 0;

                    @Override // java.lang.Runnable
                    public final void run() {
                        BatteryUtils batteryUtils = BatteryUtils.this;
                        int i = this.f$1;
                        int i2 = packageUid;
                        String str2 = str;
                        BatteryDatabaseManager batteryDatabaseManager =
                                BatteryDatabaseManager.getInstance(batteryUtils.mContext);
                        if (i == 1) {
                            batteryDatabaseManager.insertAction(
                                    System.currentTimeMillis(), i2, str2);
                        } else if (i == 0) {
                            batteryDatabaseManager.deleteAction(i2, str2);
                        }
                    }
                });
    }

    public final BatteryInfo getBatteryInfo(String str) {
        BatteryUsageStats build;
        try {
            build =
                    ((BatteryStatsManager)
                                    this.mContext.getSystemService(BatteryStatsManager.class))
                            .getBatteryUsageStats(
                                    new BatteryUsageStatsQuery.Builder()
                                            .includeBatteryHistory()
                                            .build());
        } catch (RuntimeException e) {
            Log.e("BatteryUtils", "getBatteryInfo() error from getBatteryUsageStats()", e);
            build = new BatteryUsageStats.Builder(new String[0]).build();
        }
        long currentTimeMillis = System.currentTimeMillis();
        Intent batteryIntent =
                com.android.settingslib.fuelgauge.BatteryUtils.getBatteryIntent(this.mContext);
        SystemClock.elapsedRealtime();
        int i = PowerUtil.$r8$clinit;
        Estimate enhancedEstimate = getEnhancedEstimate();
        if (enhancedEstimate == null) {
            enhancedEstimate = new Estimate(build.getBatteryTimeRemainingMs(), -1L, false);
        }
        logRuntime(currentTimeMillis, str, "BatteryInfoLoader post query");
        BatteryInfo batteryInfo =
                BatteryInfo.getBatteryInfo(this.mContext, batteryIntent, build, enhancedEstimate);
        logRuntime(currentTimeMillis, str, "BatteryInfoLoader.loadInBackground");
        try {
            build.close();
        } catch (Exception e2) {
            Log.e("BatteryUtils", "BatteryUsageStats.close() failed", e2);
        }
        return batteryInfo;
    }

    public Estimate getEnhancedEstimate() {
        Estimate estimate;
        PowerUsageFeatureProvider powerUsageFeatureProvider;
        Context context = this.mContext;
        Intrinsics.checkNotNullParameter(context, "context");
        ContentResolver contentResolver = context.getContentResolver();
        Instant ofEpochMilli =
                Instant.ofEpochMilli(
                        Settings.Global.getLong(
                                context.getContentResolver(),
                                "battery_estimates_last_update_time",
                                -1L));
        Intrinsics.checkNotNullExpressionValue(ofEpochMilli, "ofEpochMilli(...)");
        if (Duration.between(ofEpochMilli, Instant.now()).compareTo(Duration.ofMinutes(1L)) > 0) {
            estimate = null;
        } else {
            estimate =
                    new Estimate(
                            Settings.Global.getLong(
                                    contentResolver, "time_remaining_estimate_millis", -1L),
                            Settings.Global.getLong(
                                    contentResolver, "average_time_to_discharge", -1L),
                            Settings.Global.getInt(
                                            contentResolver,
                                            "time_remaining_estimate_based_on_usage",
                                            0)
                                    == 1);
        }
        if (estimate == null
                && (powerUsageFeatureProvider = this.mPowerUsageFeatureProvider) != null) {
            powerUsageFeatureProvider.getClass();
        }
        return estimate;
    }

    public long getForegroundActivityTotalTimeUs(BatteryStats.Uid uid, long j) {
        BatteryStats.Timer foregroundActivityTimer = uid.getForegroundActivityTimer();
        if (foregroundActivityTimer != null) {
            return foregroundActivityTimer.getTotalTimeLocked(j, 0);
        }
        return 0L;
    }

    public long getForegroundServiceTotalTimeUs(BatteryStats.Uid uid, long j) {
        BatteryStats.Timer foregroundServiceTimer = uid.getForegroundServiceTimer();
        if (foregroundServiceTimer != null) {
            return foregroundServiceTimer.getTotalTimeLocked(j, 0);
        }
        return 0L;
    }

    public final int getPackageUid(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return this.mPackageManager.getPackageUid(str, 128);
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    public final boolean isPreOApp(String str) {
        try {
            return this.mPackageManager.getApplicationInfo(str, 128).targetSdkVersion < 26;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("BatteryUtils", "Cannot find package: " + str, e);
            return false;
        }
    }

    public void reset() {
        sInstance = null;
    }

    public final void setForceAppStandby(
            final int i, final int i2, final int i3, final String str) {
        if (isPreOApp(str)) {
            this.mAppOpsManager.setMode(63, i, str, i2);
        }
        this.mAppOpsManager.setMode(70, i, str, i2);
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.fuelgauge.BatteryUtils$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BatteryUtils batteryUtils = BatteryUtils.this;
                        int i4 = i2;
                        int i5 = i;
                        String str2 = str;
                        int i6 = i3;
                        BatteryDatabaseManager batteryDatabaseManager =
                                BatteryDatabaseManager.getInstance(batteryUtils.mContext);
                        if (i4 == 1) {
                            batteryDatabaseManager.insertAction(
                                    System.currentTimeMillis(), i5, str2);
                        } else if (i4 == 0) {
                            batteryDatabaseManager.deleteAction(i5, str2);
                        }
                        DeviceCareManager deviceCareManager = batteryUtils.mDeviceCareManager;
                        deviceCareManager.getClass();
                        if (i4 > 1) {
                            return;
                        }
                        String str3 = Reason.sReasonToString[i6];
                        StringBuilder m =
                                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                        i5, "update p=", str2, " uid=", " mode=");
                        m.append(i4);
                        m.append(" reason=");
                        m.append(str3);
                        SemLog.i("DeviceCareManager", m.toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("package_name", str2);
                        bundle.putInt(NetworkAnalyticsConstants.DataPoints.UID, i5);
                        bundle.putInt("mode", i4);
                        bundle.putString("reason", str3);
                        try {
                            deviceCareManager
                                    .mContext
                                    .getContentResolver()
                                    .call(
                                            DeviceCareManager.AUTHORITY_FAS_URI,
                                            "FasDataChanged",
                                            (String) null,
                                            bundle);
                        } catch (Exception e) {
                            SemLog.e("DeviceCareManager", "update Exception e=" + e.toString());
                        }
                    }
                });
    }

    public final boolean shouldHideUidBatteryConsumer(
            UidBatteryConsumer uidBatteryConsumer, String[] strArr) {
        PowerUsageFeatureProvider powerUsageFeatureProvider = this.mPowerUsageFeatureProvider;
        int uid = uidBatteryConsumer.getUid();
        ((PowerUsageFeatureProviderImpl) powerUsageFeatureProvider).getClass();
        if (uid < 0 || uid >= 10000) {
            if (strArr != null) {
                for (String str : strArr) {
                    if (ArrayUtils.contains(PowerUsageFeatureProviderImpl.PACKAGES_SYSTEM, str)) {
                        break;
                    }
                }
            }
            if (shouldHideUidBatteryConsumerUnconditionally(uidBatteryConsumer, strArr)) {
                break;
            }
            return false;
        }
        return true;
    }

    public final boolean shouldHideUidBatteryConsumerUnconditionally(
            UidBatteryConsumer uidBatteryConsumer, String[] strArr) {
        int uid = uidBatteryConsumer.getUid();
        if (uid != -5) {
            if (uid < 0) {
                return true;
            }
            if (strArr != null) {
                for (String str : strArr) {
                    Context context = this.mContext;
                    Intent intent = AppUtils.sBrowserIntent;
                    Boolean bool =
                            (Boolean)
                                    ApplicationsState.getInstance(
                                                    (Application) context.getApplicationContext())
                                            .mSystemModules
                                            .get(str);
                    if (bool == null ? false : bool.booleanValue()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
