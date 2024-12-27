package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.content.Intent;

import com.android.settings.fuelgauge.PowerUsageFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerUsageAdvanced$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PowerUsageAdvanced f$0;

    public /* synthetic */ PowerUsageAdvanced$$ExternalSyntheticLambda0(
            PowerUsageAdvanced powerUsageAdvanced, int i) {
        this.$r8$classId = i;
        this.f$0 = powerUsageAdvanced;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PowerUsageAdvanced powerUsageAdvanced = this.f$0;
        switch (i) {
            case 0:
                BaseSearchIndexProvider baseSearchIndexProvider =
                        PowerUsageAdvanced.SEARCH_INDEX_DATA_PROVIDER;
                if (powerUsageAdvanced.getContext() != null) {
                    Context context = powerUsageAdvanced.getContext();
                    int i2 = BootBroadcastReceiver.$r8$clinit;
                    Context applicationContext = context.getApplicationContext();
                    Intent intent =
                            new Intent("com.android.settings.battery.action.PERIODIC_JOB_RECHECK");
                    intent.setClass(applicationContext, BootBroadcastReceiver.class);
                    applicationContext.sendBroadcast(intent);
                    return;
                }
                return;
            case 1:
                BaseSearchIndexProvider baseSearchIndexProvider2 =
                        PowerUsageAdvanced.SEARCH_INDEX_DATA_PROVIDER;
                powerUsageAdvanced.getClass();
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                PowerUsageFeatureProviderImpl powerUsageFeatureProvider =
                        featureFactoryImpl.getPowerUsageFeatureProvider();
                powerUsageAdvanced.getContext();
                powerUsageFeatureProvider.getClass();
                powerUsageAdvanced.mHandler.post(
                        new PowerUsageAdvanced$$ExternalSyntheticLambda0(powerUsageAdvanced, 2));
                return;
            default:
                BaseSearchIndexProvider baseSearchIndexProvider3 =
                        PowerUsageAdvanced.SEARCH_INDEX_DATA_PROVIDER;
                powerUsageAdvanced.isResumed();
                return;
        }
    }
}
