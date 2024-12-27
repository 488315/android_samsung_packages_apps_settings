package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemProperties;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecNotificationIntelligenceFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_notification_intelligence_main_settings);
    public Configuration mConfiguration;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.SecNotificationIntelligenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return SemFloatingFeature.getInstance()
                                    .getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_AI_VERSION")
                            >= 20251
                    || SystemProperties.getBoolean(
                            SecNotificationIntelligenceController.SUPPORT_NOTIFICATION_HIGHLIGHT,
                            false);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecNotificationIntelligenceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_notification_intelligence_main_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        Preference preference;
        super.onConfigurationChanged(configuration);
        if (this.mConfiguration.orientation != configuration.orientation
                && (preference = getPreferenceScreen().getPreference(0)) != null) {
            getPreferenceScreen().removePreference(preference);
            LayoutPreference layoutPreference =
                    new LayoutPreference(getContext(), R.layout.sec_noti_intelligence_illustration);
            layoutPreference.setSelectable(false);
            layoutPreference.setOrder(0);
            getPreferenceScreen().addPreference(layoutPreference);
            setPreview$2();
            if (getListView() != null) {
                getListView().getAdapter().notifyDataSetChanged();
            }
        }
        this.mConfiguration.updateFrom(configuration);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mConfiguration = new Configuration();
        if (getContext() != null) {
            this.mConfiguration.updateFrom(getContext().getResources().getConfiguration());
        }
        setPreview$2();
    }

    public final void setPreview$2() {
        LayoutPreference layoutPreference =
                (LayoutPreference) getPreferenceScreen().getPreference(0);
        LinearLayout linearLayout =
                (LinearLayout)
                        layoutPreference.mRootView.findViewById(R.id.help_animation_container);
        ((TextView) layoutPreference.mRootView.findViewById(R.id.help_description_text))
                .setText(
                        getResources()
                                .getString(
                                        R.string
                                                .notification_intelligence_settings_description_text));
        linearLayout.semSetRoundedCorners(15);
        linearLayout.semSetRoundedCornerColor(
                15, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
    }
}
