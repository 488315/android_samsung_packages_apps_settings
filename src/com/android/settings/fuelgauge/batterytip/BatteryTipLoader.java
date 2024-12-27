package com.android.settings.fuelgauge.batterytip;

import android.R;
import android.content.Context;
import android.os.BatteryUsageStats;
import android.os.PowerManager;
import android.util.KeyValueListParser;
import android.util.Log;

import com.android.settings.fuelgauge.BatteryInfo;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.fuelgauge.batterytip.detectors.HighUsageDetector;
import com.android.settings.fuelgauge.batterytip.tips.BatteryDefenderTip;
import com.android.settings.fuelgauge.batterytip.tips.IncompatibleChargerTip;
import com.android.settings.fuelgauge.batterytip.tips.LowBatteryTip;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.utils.AsyncLoaderCompat;

import java.util.ArrayList;
import java.util.Collections;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryTipLoader extends AsyncLoaderCompat {
    public final BatteryUsageStats mBatteryUsageStats;
    BatteryUtils mBatteryUtils;

    public BatteryTipLoader(Context context, BatteryUsageStats batteryUsageStats) {
        super(context);
        this.mBatteryUsageStats = batteryUsageStats;
        this.mBatteryUtils = BatteryUtils.getInstance(context);
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        ArrayList arrayList = new ArrayList();
        BatteryTipPolicy batteryTipPolicy =
                new BatteryTipPolicy(this.mContext, new KeyValueListParser(','));
        BatteryInfo batteryInfo = this.mBatteryUtils.getBatteryInfo("BatteryTipLoader");
        Context applicationContext = this.mContext.getApplicationContext();
        arrayList.add(
                new HighUsageDetector(
                                applicationContext,
                                batteryTipPolicy,
                                this.mBatteryUsageStats,
                                batteryInfo)
                        .detect());
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getPowerUsageFeatureProvider().getClass();
        int i = 2;
        int i2 = batteryInfo.isBatteryDefender ? 0 : 2;
        boolean z = true;
        boolean z2 = batteryInfo.pluggedStatus != 0;
        BatteryDefenderTip batteryDefenderTip = new BatteryDefenderTip(8, i2, false);
        batteryDefenderTip.mIsPluggedIn = z2;
        arrayList.add(batteryDefenderTip);
        boolean containsIncompatibleChargers =
                Utils.containsIncompatibleChargers(
                        applicationContext, "IncompatibleChargerDetector");
        int i3 = containsIncompatibleChargers ? 0 : 2;
        Log.d(
                "IncompatibleChargerDetector",
                "detect() state= "
                        + i3
                        + " isIncompatibleCharging: "
                        + containsIncompatibleChargers);
        arrayList.add(new IncompatibleChargerTip(10, i3, false));
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl2.getBatterySettingsFeatureProvider().getClass();
        int integer =
                applicationContext
                        .getResources()
                        .getInteger(R.integer.config_nightDisplayColorTemperatureMin);
        boolean isPowerSaveMode =
                ((PowerManager) applicationContext.getSystemService(PowerManager.class))
                        .isPowerSaveMode();
        boolean z3 = batteryInfo.batteryLevel <= integer;
        boolean z4 = batteryTipPolicy.lowBatteryEnabled && !isPowerSaveMode;
        if (!batteryTipPolicy.testLowBatteryTip && (!batteryInfo.discharging || !z3)) {
            z = false;
        }
        if (z4 && z) {
            i = 0;
        }
        LowBatteryTip lowBatteryTip = new LowBatteryTip(5, i, false);
        lowBatteryTip.mPowerSaveModeOn = isPowerSaveMode;
        arrayList.add(lowBatteryTip);
        Collections.sort(arrayList);
        return arrayList;
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}
}
