package com.android.settings.fuelgauge.batterytip.actions;

import android.content.Context;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatteryTipAction {
    public final Context mContext;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;

    public BatteryTipAction(Context context) {
        this.mContext = context;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    public abstract void handlePositiveAction(int i);
}
