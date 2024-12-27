package com.android.settings.slices;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SliceDataConverter {
    public final Context mContext;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;

    public SliceDataConverter(Context context) {
        this.mContext = context;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    public List<AccessibilityServiceInfo> getAccessibilityServiceInfoList() {
        return AccessibilityManager.getInstance(this.mContext)
                .getInstalledAccessibilityServiceList();
    }
}
