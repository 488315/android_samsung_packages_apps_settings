package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.os.BatteryStatsManager;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.util.Log;

import com.android.settingslib.utils.AsyncLoaderCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryUsageStatsLoader extends AsyncLoaderCompat {
    public final BatteryStatsManager mBatteryStatsManager;
    public final boolean mIncludeBatteryHistory;

    public BatteryUsageStatsLoader(Context context, boolean z) {
        super(context);
        this.mBatteryStatsManager =
                (BatteryStatsManager) context.getSystemService(BatteryStatsManager.class);
        this.mIncludeBatteryHistory = z;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        BatteryUsageStatsQuery.Builder builder = new BatteryUsageStatsQuery.Builder();
        if (this.mIncludeBatteryHistory) {
            builder.includeBatteryHistory();
        }
        try {
            return this.mBatteryStatsManager.getBatteryUsageStats(
                    builder.includeProcessStateData().build());
        } catch (RuntimeException e) {
            Log.e("BatteryUsageStatsLoader", "loadInBackground() for getBatteryUsageStats()", e);
            return new BatteryUsageStats.Builder(new String[0]).build();
        }
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}
}
