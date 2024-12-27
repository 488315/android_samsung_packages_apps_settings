package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.os.BatteryConsumer;
import android.os.UidBatteryConsumer;
import android.os.UserBatteryConsumer;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.utils.StringUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryEntry {
    public final BatteryConsumer mBatteryConsumer;
    public double mConsumedPower;
    public double mConsumedPowerInBackground;
    public double mConsumedPowerInCached;
    public double mConsumedPowerInForeground;
    public double mConsumedPowerInForegroundService;
    public final int mConsumerType;
    public final Context mContext;
    public String mDefaultPackageName;
    public final int mIconId;
    public final boolean mIsHidden;
    public final String mName;
    public double mPercent;
    public final int mPowerComponentId;
    public long mTimeInBackgroundMs;
    public long mTimeInForegroundMs;
    public long mTimeInForegroundServiceMs;
    public final int mUid;
    public final long mUsageDurationMs;
    public static final BatteryConsumer.Dimensions[] BATTERY_DIMENSIONS = {
        new BatteryConsumer.Dimensions(-1, 1),
        new BatteryConsumer.Dimensions(-1, 3),
        new BatteryConsumer.Dimensions(-1, 2),
        new BatteryConsumer.Dimensions(-1, 4)
    };
    public static final ArrayMap sUidCache = new ArrayMap();
    public static final BatteryEntry$$ExternalSyntheticLambda0 COMPARATOR =
            new BatteryEntry$$ExternalSyntheticLambda0();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NameAndIcon {
        public final Drawable mIcon;
        public final int mIconId;
        public final String mName;
        public final String mPackageName;

        public NameAndIcon(String str, String str2, Drawable drawable, int i) {
            this.mName = str;
            this.mIcon = drawable;
            this.mIconId = i;
            this.mPackageName = str2;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UidToDetail {}

    public BatteryEntry(
            Context context,
            BatteryConsumer batteryConsumer,
            boolean z,
            int i,
            String[] strArr,
            String str) {
        this.mContext = context;
        this.mBatteryConsumer = batteryConsumer;
        this.mIsHidden = z;
        this.mDefaultPackageName = str;
        this.mPowerComponentId = -1;
        if (!(batteryConsumer instanceof UidBatteryConsumer)) {
            if (!(batteryConsumer instanceof UserBatteryConsumer)) {
                throw new IllegalArgumentException("Unsupported: " + batteryConsumer);
            }
            this.mUid = -1;
            this.mConsumerType = 2;
            this.mConsumedPower = batteryConsumer.getConsumedPower();
            this.mName =
                    getNameAndIconFromUserId(
                                    context, ((UserBatteryConsumer) batteryConsumer).getUserId())
                            .mName;
            return;
        }
        this.mUid = i;
        this.mConsumerType = 1;
        this.mConsumedPower = batteryConsumer.getConsumedPower();
        UidBatteryConsumer uidBatteryConsumer = (UidBatteryConsumer) batteryConsumer;
        if (this.mDefaultPackageName == null) {
            if (strArr == null || strArr.length != 1) {
                this.mDefaultPackageName =
                        i == 1000
                                ? RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME
                                : uidBatteryConsumer.getPackageWithHighestDrain();
            } else {
                this.mDefaultPackageName = strArr[0];
            }
        }
        if (this.mDefaultPackageName != null) {
            PackageManager packageManager = context.getPackageManager();
            try {
                this.mName =
                        packageManager
                                .getApplicationLabel(
                                        packageManager.getApplicationInfo(
                                                this.mDefaultPackageName, 0))
                                .toString();
            } catch (PackageManager.NameNotFoundException unused) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder(
                                "PackageManager failed to retrieve ApplicationInfo for: "),
                        this.mDefaultPackageName,
                        "BatteryEntry");
                this.mName = this.mDefaultPackageName;
            }
        }
        this.mTimeInForegroundMs = uidBatteryConsumer.getTimeInProcessStateMs(1);
        this.mTimeInForegroundServiceMs = uidBatteryConsumer.getTimeInProcessStateMs(3);
        this.mTimeInBackgroundMs = uidBatteryConsumer.getTimeInProcessStateMs(2);
        BatteryConsumer.Dimensions[] dimensionsArr = BATTERY_DIMENSIONS;
        this.mConsumedPowerInForeground =
                safeGetConsumedPower(uidBatteryConsumer, dimensionsArr[0]);
        this.mConsumedPowerInForegroundService =
                safeGetConsumedPower(uidBatteryConsumer, dimensionsArr[1]);
        this.mConsumedPowerInBackground =
                safeGetConsumedPower(uidBatteryConsumer, dimensionsArr[2]);
        this.mConsumedPowerInCached = safeGetConsumedPower(uidBatteryConsumer, dimensionsArr[3]);
    }

    public static NameAndIcon getNameAndIconFromPowerComponent(Context context, int i) {
        String string;
        int i2;
        if (i == 0) {
            string = context.getResources().getString(R.string.power_screen);
            i2 = R.drawable.ic_settings_display;
        } else if (i == 1) {
            string = context.getResources().getString(R.string.power_cpu);
            i2 = R.drawable.ic_settings_cpu;
        } else if (i == 2) {
            string = context.getResources().getString(R.string.power_bluetooth);
            i2 = R.drawable.ic_settings_bluetooth;
        } else if (i == 3) {
            string = context.getResources().getString(R.string.power_camera);
            i2 = R.drawable.ic_settings_camera;
        } else if (i == 6) {
            string = context.getResources().getString(R.string.power_flashlight);
            i2 = R.drawable.ic_settings_flashlight;
        } else if (i == 8) {
            string = context.getResources().getString(R.string.power_cell);
            i2 = R.drawable.ic_settings_cellular;
        } else if (i == 10) {
            string = context.getResources().getString(R.string.power_gps);
            i2 = R.drawable.ic_settings_gps;
        } else if (i == 11) {
            string = context.getResources().getString(R.string.power_wifi);
            i2 = R.drawable.ic_settings_wireless_no_theme;
        } else if (i == 14) {
            string = context.getResources().getString(R.string.power_phone);
            i2 = R.drawable.ic_settings_voice_calls;
        } else if (i != 15) {
            Log.w(
                    "BatteryEntry",
                    "unknown attribute:"
                            + DebugUtils.constantToString(
                                    BatteryConsumer.class, "POWER_COMPONENT_", i));
            i2 = R.drawable.ic_power_system;
            string = null;
        } else {
            string = context.getResources().getString(R.string.ambient_display_screen_title);
            i2 = R.drawable.ic_settings_aod;
        }
        return new NameAndIcon(string, null, null, i2);
    }

    public static NameAndIcon getNameAndIconFromUserId(Context context, int i) {
        String string;
        Drawable drawable;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        UserInfo userInfo = userManager.getUserInfo(i);
        if (userInfo != null) {
            drawable = Utils.getUserIcon(context, userManager, userInfo);
            string = Utils.getUserLabel(context, userInfo);
        } else {
            string =
                    context.getResources()
                            .getString(R.string.running_process_item_removed_user_label);
            drawable = null;
        }
        return new NameAndIcon(string, null, drawable, 0);
    }

    public static double safeGetConsumedPower(
            UidBatteryConsumer uidBatteryConsumer, BatteryConsumer.Dimensions dimensions) {
        try {
            return uidBatteryConsumer.getConsumedPower(dimensions);
        } catch (IllegalArgumentException e) {
            Log.e("BatteryEntry", "safeGetConsumedPower failed:" + e);
            return 0.0d;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BatteryEntry{");
        sb.append("\n\tname=" + this.mName + " isHidden=" + this.mIsHidden);
        sb.append(
                String.format(
                        "\n\tconsume=%.2f%% | %f",
                        Double.valueOf(this.mPercent), Double.valueOf(this.mConsumedPower)));
        sb.append(
                String.format(
                        "\n\tconsume power= foreground:%f foregroundService:%f",
                        Double.valueOf(this.mConsumedPowerInForeground),
                        Double.valueOf(this.mConsumedPowerInForegroundService)));
        sb.append(
                String.format(
                        "\n\tconsume power= background:%f cached:%f",
                        Double.valueOf(this.mConsumedPowerInBackground),
                        Double.valueOf(this.mConsumedPowerInCached)));
        sb.append(
                String.format(
                        "\n"
                            + "\ttime= foreground:%s foregroundService:%s background:%s"
                            + " usageDuration:%s",
                        StringUtil.formatElapsedTime(
                                this.mContext, (double) this.mTimeInForegroundMs, true, false),
                        StringUtil.formatElapsedTime(
                                this.mContext,
                                (double) this.mTimeInForegroundServiceMs,
                                true,
                                false),
                        StringUtil.formatElapsedTime(
                                this.mContext, (double) this.mTimeInBackgroundMs, true, false),
                        StringUtil.formatElapsedTime(
                                this.mContext, (double) this.mUsageDurationMs, true, false)));
        sb.append(
                String.format(
                        "\n\tpackage:%s uid:%d",
                        this.mDefaultPackageName, Integer.valueOf(this.mUid)));
        return sb.toString();
    }

    public BatteryEntry(Context context, int i, double d, long j, boolean z) {
        this.mContext = context;
        this.mBatteryConsumer = null;
        this.mUid = -1;
        this.mIsHidden = z;
        this.mPowerComponentId = i;
        this.mConsumedPower = d;
        this.mUsageDurationMs = j;
        this.mConsumerType = 3;
        NameAndIcon nameAndIconFromPowerComponent = getNameAndIconFromPowerComponent(context, i);
        int i2 = nameAndIconFromPowerComponent.mIconId;
        this.mIconId = i2;
        this.mName = nameAndIconFromPowerComponent.mName;
        if (i2 != 0) {
            context.getDrawable(i2);
        }
    }

    public BatteryEntry(Context context, int i, String str, double d) {
        this.mContext = context;
        this.mBatteryConsumer = null;
        this.mUid = -1;
        this.mIsHidden = false;
        this.mPowerComponentId = i;
        this.mIconId = R.drawable.ic_power_system;
        context.getDrawable(R.drawable.ic_power_system);
        this.mName = str;
        this.mConsumedPower = d;
        this.mConsumerType = 3;
    }
}
