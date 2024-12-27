package com.android.settings.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.widget.FooterPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SpatialAudioSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.spatial_audio_settings);

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SpatialAudioSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1921;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.spatial_audio_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        FooterPreference footerPreference =
                (FooterPreference) findPreference("spatial_audio_footer");
        if (footerPreference != null) {
            footerPreference.setLearnMoreText(
                    getString(R.string.spatial_audio_footer_learn_more_text));
            footerPreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.notification.SpatialAudioSettings$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SpatialAudioSettings spatialAudioSettings = SpatialAudioSettings.this;
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    SpatialAudioSettings.SEARCH_INDEX_DATA_PROVIDER;
                            spatialAudioSettings.getClass();
                            spatialAudioSettings.startActivity(
                                    new Intent("android.settings.BLUETOOTH_SETTINGS")
                                            .setPackage(
                                                    spatialAudioSettings
                                                            .getContext()
                                                            .getPackageName()));
                        }
                    });
        }
    }
}
