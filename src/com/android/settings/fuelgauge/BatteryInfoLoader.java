package com.android.settings.fuelgauge;

import android.content.Context;

import com.android.settingslib.utils.AsyncLoaderCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryInfoLoader extends AsyncLoaderCompat {
    BatteryUtils mBatteryUtils;

    public BatteryInfoLoader(Context context) {
        super(context);
        this.mBatteryUtils = BatteryUtils.getInstance(context);
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        return this.mBatteryUtils.getBatteryInfo("BatteryInfoLoader");
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}
}
