package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;

import com.android.settings.R;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settingslib.utils.StringUtil;

import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryDiffEntry {
    public static Locale sCurrentLocale;
    public int mAdjustPercentageOffset;
    Drawable mAppIcon;
    int mAppIconId;
    String mAppLabel;
    public double mBackgroundUsageConsumePower;
    public long mBackgroundUsageTimeInMs;
    public double mCachedUsageConsumePower;
    public final int mComponentId;
    public double mConsumePower;
    public final int mConsumerType;
    public final Context mContext;
    public String mDefaultPackageName;
    public double mForegroundServiceUsageConsumePower;
    public long mForegroundServiceUsageTimeInMs;
    public double mForegroundUsageConsumePower;
    public long mForegroundUsageTimeInMs;
    public final boolean mIsHidden;
    boolean mIsLoaded;
    public final String mKey;
    public final String mLegacyLabel;
    public final String mLegacyPackageName;
    public double mPercentage;
    public long mScreenOnTimeInMs;
    public double mTotalConsumePower;
    public final long mUid;
    public final long mUserId;
    public final UserManager mUserManager;
    boolean mValidForRestriction;
    public static final Object sResourceCacheLock = new Object();
    public static final Object sPackageNameAndUidCacheLock = new Object();
    public static final Object sValidForRestrictionLock = new Object();
    public static final Map sResourceCache = new ArrayMap();
    public static final Map sPackageNameAndUidCache = new ArrayMap();
    static final Map<String, Boolean> sValidForRestriction = new ArrayMap();
    public static final BatteryDiffEntry$$ExternalSyntheticLambda0 COMPARATOR =
            new BatteryDiffEntry$$ExternalSyntheticLambda0();
    public static final Map SPECIAL_ENTRY_MAP =
            Map.of(
                    "A|SystemApps",
                    Pair.create(
                            Integer.valueOf(R.string.battery_usage_system_apps),
                            Integer.valueOf(R.drawable.ic_power_system)),
                    "A|UninstalledApps",
                    Pair.create(
                            Integer.valueOf(R.string.battery_usage_uninstalled_apps),
                            Integer.valueOf(R.drawable.ic_battery_uninstalled)),
                    "S|Others",
                    Pair.create(
                            Integer.valueOf(R.string.battery_usage_others),
                            Integer.valueOf(R.drawable.ic_settings_battery_usage_others)));

    public BatteryDiffEntry(
            Context context,
            long j,
            long j2,
            String str,
            boolean z,
            int i,
            String str2,
            String str3,
            int i2,
            long j3,
            long j4,
            long j5,
            long j6,
            double d,
            double d2,
            double d3,
            double d4,
            double d5) {
        this.mDefaultPackageName = null;
        this.mAppLabel = null;
        this.mAppIcon = null;
        this.mIsLoaded = false;
        this.mValidForRestriction = true;
        this.mContext = context;
        this.mUid = j;
        this.mUserId = j2;
        this.mKey = str;
        this.mIsHidden = z;
        this.mComponentId = i;
        this.mLegacyPackageName = str2;
        this.mLegacyLabel = str3;
        this.mConsumerType = i2;
        this.mForegroundUsageTimeInMs = j3;
        this.mForegroundServiceUsageTimeInMs = j4;
        this.mBackgroundUsageTimeInMs = j5;
        this.mScreenOnTimeInMs = j6;
        this.mConsumePower = d;
        this.mForegroundUsageConsumePower = d2;
        this.mForegroundServiceUsageConsumePower = d3;
        this.mBackgroundUsageConsumePower = d4;
        this.mCachedUsageConsumePower = d5;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    public static void clearCache() {
        synchronized (sResourceCacheLock) {
            ((ArrayMap) sResourceCache).clear();
        }
        synchronized (sValidForRestrictionLock) {
            sValidForRestriction.clear();
        }
        synchronized (sPackageNameAndUidCacheLock) {
            ((ArrayMap) sPackageNameAndUidCache).clear();
        }
    }

    public static void putResourceCache(String str, BatteryEntry.NameAndIcon nameAndIcon) {
        synchronized (sResourceCacheLock) {
            ((ArrayMap) sResourceCache).put(str, nameAndIcon);
        }
    }

    public final int getAppIconId() {
        loadLabelAndIcon();
        return this.mAppIconId;
    }

    public final String getAppLabel() {
        loadLabelAndIcon();
        String str = this.mAppLabel;
        return (str == null || str.length() == 0) ? this.mLegacyLabel : this.mAppLabel;
    }

    public final String getPackageName() {
        String str = this.mDefaultPackageName;
        if (str == null) {
            str = this.mLegacyPackageName;
        }
        if (str == null) {
            return str;
        }
        String[] split = str.split(":");
        return (split == null || split.length <= 0) ? str : split[0];
    }

    public final boolean isSystemEntry() {
        if (this.mIsHidden) {
            return false;
        }
        int i = this.mConsumerType;
        return i == 2 || i == 3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:148:0x0309, code lost:

       r0 = r17;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadLabelAndIcon() {
        /*
            Method dump skipped, instructions count: 935
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry.loadLabelAndIcon():void");
    }

    public final void setTotalConsumePower(double d) {
        this.mTotalConsumePower = d;
        this.mPercentage = d != 0.0d ? (this.mConsumePower / d) * 100.0d : 0.0d;
        this.mAdjustPercentageOffset = 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BatteryDiffEntry{");
        sb.append("\n\tname=" + this.mAppLabel + " restrictable=" + this.mValidForRestriction);
        sb.append(
                String.format(
                        "\n\tconsume=%.2f%% %f/%f",
                        Double.valueOf(this.mPercentage),
                        Double.valueOf(this.mConsumePower),
                        Double.valueOf(this.mTotalConsumePower)));
        sb.append(
                String.format(
                        "\n\tconsume power= foreground:%f foregroundService:%f",
                        Double.valueOf(this.mForegroundUsageConsumePower),
                        Double.valueOf(this.mForegroundServiceUsageConsumePower)));
        sb.append(
                String.format(
                        "\n\tconsume power= background:%f cached:%f",
                        Double.valueOf(this.mBackgroundUsageConsumePower),
                        Double.valueOf(this.mCachedUsageConsumePower)));
        sb.append(
                String.format(
                        "\n\ttime= foreground:%s foregroundService:%s background:%s screen-on:%s",
                        StringUtil.formatElapsedTime(
                                this.mContext, (double) this.mForegroundUsageTimeInMs, true, false),
                        StringUtil.formatElapsedTime(
                                this.mContext,
                                (double) this.mForegroundServiceUsageTimeInMs,
                                true,
                                false),
                        StringUtil.formatElapsedTime(
                                this.mContext, (double) this.mBackgroundUsageTimeInMs, true, false),
                        StringUtil.formatElapsedTime(
                                this.mContext, (double) this.mScreenOnTimeInMs, true, false)));
        sb.append(
                String.format(
                        "\n\tpackage:%s|%s uid:%d userId:%d",
                        this.mLegacyPackageName,
                        getPackageName(),
                        Long.valueOf(this.mUid),
                        Long.valueOf(this.mUserId)));
        return sb.toString();
    }

    public void updateRestrictionFlagState() {
        if (isSystemEntry()) {
            this.mValidForRestriction = false;
            return;
        }
        if (BatteryUtils.getInstance(this.mContext).getPackageUid(getPackageName()) == -1) {
            this.mValidForRestriction = false;
            return;
        }
        try {
            this.mValidForRestriction =
                    this.mContext.getPackageManager().getPackageInfo(getPackageName(), 4198976)
                            != null;
        } catch (Exception e) {
            Log.e(
                    "BatteryDiffEntry",
                    String.format(
                            "getPackageInfo() error %s for package=%s",
                            e.getCause(), getPackageName()));
            this.mValidForRestriction = false;
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final BatteryDiffEntry m888clone() {
        return new BatteryDiffEntry(
                this.mContext,
                this.mUid,
                this.mUserId,
                this.mKey,
                this.mIsHidden,
                this.mComponentId,
                this.mLegacyPackageName,
                this.mLegacyLabel,
                this.mConsumerType,
                this.mForegroundUsageTimeInMs,
                this.mForegroundServiceUsageTimeInMs,
                this.mBackgroundUsageTimeInMs,
                this.mScreenOnTimeInMs,
                this.mConsumePower,
                this.mForegroundUsageConsumePower,
                this.mForegroundServiceUsageConsumePower,
                this.mBackgroundUsageConsumePower,
                this.mCachedUsageConsumePower);
    }

    public BatteryDiffEntry(Context context, String str, String str2, int i) {
        this(
                context, 0L, 0L, str, false, -1, null, str2, i, 0L, 0L, 0L, 0L, 0.0d, 0.0d, 0.0d,
                0.0d, 0.0d);
    }
}
