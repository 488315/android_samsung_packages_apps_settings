package com.android.settings.fuelgauge.batterytip;

import android.content.Context;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Log;

import java.time.Duration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryTipPolicy {
    public final int highUsageAppCount;
    public final int highUsageBatteryDraining;
    public final boolean highUsageEnabled;
    public final long highUsagePeriodMs;
    public final boolean lowBatteryEnabled;
    public final KeyValueListParser mParser;
    public final boolean testHighUsageTip;
    public final boolean testLowBatteryTip;

    public BatteryTipPolicy(Context context, KeyValueListParser keyValueListParser) {
        this.mParser = keyValueListParser;
        try {
            keyValueListParser.setString(
                    Settings.Global.getString(
                            context.getContentResolver(), "battery_tip_constants"));
        } catch (IllegalArgumentException unused) {
            Log.e("BatteryTipPolicy", "Bad battery tip constants");
        }
        this.mParser.getBoolean("battery_tip_enabled", true);
        this.mParser.getBoolean("summary_enabled", false);
        this.mParser.getBoolean("battery_saver_tip_enabled", true);
        this.highUsageEnabled = this.mParser.getBoolean("high_usage_enabled", true);
        this.highUsageAppCount = this.mParser.getInt("high_usage_app_count", 3);
        this.highUsagePeriodMs =
                this.mParser.getLong("high_usage_period_ms", Duration.ofHours(2L).toMillis());
        this.highUsageBatteryDraining = this.mParser.getInt("high_usage_battery_draining", 25);
        this.mParser.getBoolean("app_restriction_enabled", true);
        this.mParser.getInt("app_restriction_active_hour", 24);
        this.mParser.getBoolean("reduced_battery_enabled", false);
        this.mParser.getInt("reduced_battery_percent", 50);
        this.lowBatteryEnabled = this.mParser.getBoolean("low_battery_enabled", true);
        this.mParser.getInt("low_battery_hour", 3);
        this.mParser.getInt("data_history_retain_day", 30);
        this.mParser.getInt("excessive_bg_drain_percentage", 10);
        this.mParser.getBoolean("test_battery_saver_tip", false);
        this.testHighUsageTip = this.mParser.getBoolean("test_high_usage_tip", false);
        this.mParser.getBoolean("test_smart_battery_tip", false);
        this.testLowBatteryTip = this.mParser.getBoolean("test_low_battery_tip", false);
    }
}
