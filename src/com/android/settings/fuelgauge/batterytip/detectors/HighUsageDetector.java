package com.android.settings.fuelgauge.batterytip.detectors;

import android.content.Context;
import android.os.BatteryUsageStats;
import android.os.UidBatteryConsumer;
import android.util.Log;

import com.android.internal.util.ArrayUtils;
import com.android.settings.fuelgauge.BatteryInfo;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.fuelgauge.batterytip.AppInfo;
import com.android.settings.fuelgauge.batterytip.BatteryTipPolicy;
import com.android.settings.fuelgauge.batterytip.HighUsageDataParser;
import com.android.settings.fuelgauge.batterytip.tips.HighUsageTip;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HighUsageDetector {
    public final BatteryInfo mBatteryInfo;
    public final BatteryUsageStats mBatteryUsageStats;
    BatteryUtils mBatteryUtils;
    HighUsageDataParser mDataParser;
    boolean mDischarging;
    public final List mHighUsageAppList = new ArrayList();
    public final BatteryTipPolicy mPolicy;

    public HighUsageDetector(
            Context context,
            BatteryTipPolicy batteryTipPolicy,
            BatteryUsageStats batteryUsageStats,
            BatteryInfo batteryInfo) {
        this.mPolicy = batteryTipPolicy;
        this.mBatteryUsageStats = batteryUsageStats;
        this.mBatteryInfo = batteryInfo;
        this.mBatteryUtils = BatteryUtils.getInstance(context);
        this.mDataParser =
                new HighUsageDataParser(
                        batteryTipPolicy.highUsageBatteryDraining,
                        batteryTipPolicy.highUsagePeriodMs);
        this.mDischarging = batteryInfo.discharging;
    }

    public final HighUsageTip detect() {
        BatteryUtils batteryUtils = this.mBatteryUtils;
        BatteryUsageStats batteryUsageStats = this.mBatteryUsageStats;
        long currentTimeMillis = System.currentTimeMillis();
        batteryUtils.getClass();
        long statsStartTimestamp = currentTimeMillis - batteryUsageStats.getStatsStartTimestamp();
        BatteryTipPolicy batteryTipPolicy = this.mPolicy;
        if (batteryTipPolicy.highUsageEnabled && this.mDischarging) {
            parseBatteryData();
            HighUsageDataParser highUsageDataParser = this.mDataParser;
            int i = highUsageDataParser.mBatteryDrain;
            int i2 = highUsageDataParser.mThreshold;
            boolean z = batteryTipPolicy.testHighUsageTip;
            if (i > i2 || z) {
                double consumedPower = this.mBatteryUsageStats.getConsumedPower();
                int dischargePercentage = this.mBatteryUsageStats.getDischargePercentage();
                List<UidBatteryConsumer> uidBatteryConsumers =
                        this.mBatteryUsageStats.getUidBatteryConsumers();
                uidBatteryConsumers.sort(new HighUsageDetector$$ExternalSyntheticLambda0());
                for (UidBatteryConsumer uidBatteryConsumer : uidBatteryConsumers) {
                    BatteryUtils batteryUtils2 = this.mBatteryUtils;
                    double consumedPower2 = uidBatteryConsumer.getConsumedPower();
                    batteryUtils2.getClass();
                    if ((consumedPower != 0.0d
                                            ? dischargePercentage * (consumedPower2 / consumedPower)
                                            : 0.0d)
                                    + 0.5d
                            >= 1.0d) {
                        BatteryUtils batteryUtils3 = this.mBatteryUtils;
                        if (!batteryUtils3.shouldHideUidBatteryConsumer(
                                uidBatteryConsumer,
                                batteryUtils3.mPackageManager.getPackagesForUid(
                                        uidBatteryConsumer.getUid()))) {
                            List list = this.mHighUsageAppList;
                            AppInfo.Builder builder = new AppInfo.Builder();
                            builder.mUid = uidBatteryConsumer.getUid();
                            String[] packagesForUid =
                                    this.mBatteryUtils.mPackageManager.getPackagesForUid(
                                            uidBatteryConsumer.getUid());
                            builder.mPackageName =
                                    ArrayUtils.isEmpty(packagesForUid) ? null : packagesForUid[0];
                            ((ArrayList) list).add(new AppInfo(builder));
                            if (((ArrayList) this.mHighUsageAppList).size()
                                    >= batteryTipPolicy.highUsageAppCount) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                if (z && ((ArrayList) this.mHighUsageAppList).isEmpty()) {
                    List list2 = this.mHighUsageAppList;
                    AppInfo.Builder builder2 = new AppInfo.Builder();
                    builder2.mPackageName = KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG;
                    builder2.mScreenOnTimeMs = TimeUnit.HOURS.toMillis(3L);
                    ((ArrayList) list2).add(new AppInfo(builder2));
                }
            }
        }
        return new HighUsageTip(this.mHighUsageAppList, statsStartTimestamp);
    }

    public void parseBatteryData() {
        try {
            this.mBatteryInfo.parseBatteryHistory(this.mDataParser);
        } catch (IllegalStateException e) {
            Log.e("HighUsageDetector", "parseBatteryData() failed", e);
        }
    }
}
