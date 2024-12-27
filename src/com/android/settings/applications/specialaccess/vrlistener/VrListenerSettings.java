package com.android.settings.applications.specialaccess.vrlistener;

import android.content.ComponentName;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.utils.ManagedServiceSettings;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VrListenerSettings extends ManagedServiceSettings {
    public static final ManagedServiceSettings.Config CONFIG =
            new ManagedServiceSettings.Config(
                    "VrListenerSettings",
                    "enabled_vr_listeners",
                    "android.service.vr.VrListenerService",
                    null,
                    "android.permission.BIND_VR_LISTENER_SERVICE",
                    "vr listener",
                    R.string.vr_listener_security_warning_title,
                    R.string.vr_listener_security_warning_summary,
                    R.string.no_vr_listeners);
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.vr_listeners_settings);

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.XML;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.vr_listeners_settings;
    }

    public void logSpecialPermissionChange(boolean z, String str) {
        int i = z ? 772 : 773;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        FragmentActivity activity = getActivity();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(activity), i, FileType.XML, 0, str);
    }

    @Override // com.android.settings.utils.ManagedServiceSettings
    public final boolean setEnabled(ComponentName componentName, String str, boolean z) {
        logSpecialPermissionChange(z, componentName.getPackageName());
        return super.setEnabled(componentName, str, z);
    }
}
