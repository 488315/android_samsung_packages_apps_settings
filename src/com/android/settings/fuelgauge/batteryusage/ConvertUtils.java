package com.android.settings.fuelgauge.batteryusage;

import android.app.usage.IUsageStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.LocaleList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ConvertUtils {
    static int sUsageSource = -1;

    public static BatteryDiffEntry convertToBatteryDiffEntry(
            Context context, BatteryUsageDiff batteryUsageDiff) {
        return new BatteryDiffEntry(
                context,
                batteryUsageDiff.getUid(),
                batteryUsageDiff.getUserId(),
                batteryUsageDiff.getKey(),
                batteryUsageDiff.getIsHidden(),
                batteryUsageDiff.getComponentId(),
                batteryUsageDiff.getPackageName(),
                batteryUsageDiff.getLabel(),
                batteryUsageDiff.getConsumerType(),
                batteryUsageDiff.getForegroundUsageTime(),
                batteryUsageDiff.getForegroundServiceUsageTime(),
                batteryUsageDiff.getBackgroundUsageTime(),
                batteryUsageDiff.getScreenOnTime(),
                batteryUsageDiff.getConsumePower(),
                batteryUsageDiff.getForegroundUsageConsumePower(),
                batteryUsageDiff.getForegroundServiceUsageConsumePower(),
                batteryUsageDiff.getBackgroundUsageConsumePower(),
                batteryUsageDiff.getCachedUsageConsumePower());
    }

    public static BatteryUsageDiff convertToBatteryUsageDiff(
            BatteryDiffEntry batteryDiffEntry,
            BatteryOptimizationModeCache batteryOptimizationModeCache) {
        BatteryUsageDiff.Builder newBuilder = BatteryUsageDiff.newBuilder();
        long j = batteryDiffEntry.mUid;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m929$$Nest$msetUid((BatteryUsageDiff) newBuilder.instance, j);
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m930$$Nest$msetUserId(
                (BatteryUsageDiff) newBuilder.instance, batteryDiffEntry.mUserId);
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m924$$Nest$msetIsHidden(
                (BatteryUsageDiff) newBuilder.instance, batteryDiffEntry.mIsHidden);
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m917$$Nest$msetComponentId(
                (BatteryUsageDiff) newBuilder.instance, batteryDiffEntry.mComponentId);
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m919$$Nest$msetConsumerType(
                (BatteryUsageDiff) newBuilder.instance, batteryDiffEntry.mConsumerType);
        double d = batteryDiffEntry.mConsumePower;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m918$$Nest$msetConsumePower((BatteryUsageDiff) newBuilder.instance, d);
        double d2 = batteryDiffEntry.mForegroundUsageConsumePower;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m922$$Nest$msetForegroundUsageConsumePower(
                (BatteryUsageDiff) newBuilder.instance, d2);
        double d3 = batteryDiffEntry.mBackgroundUsageConsumePower;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m914$$Nest$msetBackgroundUsageConsumePower(
                (BatteryUsageDiff) newBuilder.instance, d3);
        double d4 = batteryDiffEntry.mForegroundServiceUsageConsumePower;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m920$$Nest$msetForegroundServiceUsageConsumePower(
                (BatteryUsageDiff) newBuilder.instance, d4);
        double d5 = batteryDiffEntry.mCachedUsageConsumePower;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m916$$Nest$msetCachedUsageConsumePower(
                (BatteryUsageDiff) newBuilder.instance, d5);
        long j2 = batteryDiffEntry.mForegroundUsageTimeInMs;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m923$$Nest$msetForegroundUsageTime(
                (BatteryUsageDiff) newBuilder.instance, j2);
        long j3 = batteryDiffEntry.mForegroundServiceUsageTimeInMs;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m921$$Nest$msetForegroundServiceUsageTime(
                (BatteryUsageDiff) newBuilder.instance, j3);
        long j4 = batteryDiffEntry.mBackgroundUsageTimeInMs;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m915$$Nest$msetBackgroundUsageTime(
                (BatteryUsageDiff) newBuilder.instance, j4);
        long j5 = batteryDiffEntry.mScreenOnTimeInMs;
        newBuilder.copyOnWrite();
        BatteryUsageDiff.m928$$Nest$msetScreenOnTime((BatteryUsageDiff) newBuilder.instance, j5);
        String str = batteryDiffEntry.mKey;
        if (str != null) {
            newBuilder.copyOnWrite();
            BatteryUsageDiff.m925$$Nest$msetKey((BatteryUsageDiff) newBuilder.instance, str);
        }
        String str2 = batteryDiffEntry.mLegacyPackageName;
        if (str2 != null) {
            newBuilder.copyOnWrite();
            BatteryUsageDiff.m927$$Nest$msetPackageName(
                    (BatteryUsageDiff) newBuilder.instance, str2);
        }
        String str3 = batteryDiffEntry.mLegacyLabel;
        if (str3 != null) {
            newBuilder.copyOnWrite();
            BatteryUsageDiff.m926$$Nest$msetLabel((BatteryUsageDiff) newBuilder.instance, str3);
        }
        return (BatteryUsageDiff) newBuilder.build();
    }

    public static String getEffectivePackageName(
            Context context, IUsageStatsManager iUsageStatsManager, String str, String str2) {
        int i;
        int i2;
        if (sUsageSource == -1) {
            Uri uri = DatabaseUtils.BATTERY_CONTENT_URI;
            SharedPreferences sharedPreferences =
                    context.getApplicationContext()
                            .getSharedPreferences("battery_usage_shared_prefs", 0);
            if (sharedPreferences == null || !sharedPreferences.contains("last_usage_source")) {
                try {
                    i = iUsageStatsManager.getUsageSource();
                } catch (RemoteException e) {
                    Log.e("DatabaseUtils", "Failed to getUsageSource", e);
                    i = 2;
                }
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putInt("last_usage_source", i).apply();
                }
                i2 = i;
            } else {
                i2 = sharedPreferences.getInt("last_usage_source", 2);
            }
            sUsageSource = i2;
        }
        int i3 = sUsageSource;
        if (i3 == 1) {
            return !TextUtils.isEmpty(str2) ? str2 : str;
        }
        if (i3 != 2) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i3, "Unexpected usage source: ", "ConvertUtils");
        }
        return str;
    }

    public static Locale getLocale(Context context) {
        if (context == null) {
            return Locale.getDefault();
        }
        LocaleList locales = context.getResources().getConfiguration().getLocales();
        return (locales == null || locales.isEmpty()) ? Locale.getDefault() : locales.get(0);
    }

    public static String utcToLocalTimeForLogging(long j) {
        return DateFormat.format(
                        DateFormat.getBestDateTimePattern(Locale.US, "MMM dd,yyyy HH:mm:ss"), j)
                .toString();
    }
}
