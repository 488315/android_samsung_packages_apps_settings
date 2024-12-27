package com.android.settings.fuelgauge.batterysaver;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatterySaverSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.battery_saver_settings);

    /* renamed from: $r8$lambda$U1XfVIS-FYghtrhHb_Q5ZyZcxWo, reason: not valid java name */
    public static /* synthetic */ void m872$r8$lambda$U1XfVISFYghtrhHb_Q5ZyZcxWo(
            BatterySaverSettings batterySaverSettings) {
        batterySaverSettings.mMetricsFeatureProvider.action(
                batterySaverSettings.getContext(), 1779, new Pair[0]);
        batterySaverSettings.startActivityForResult(
                HelpUtils.getHelpIntent(
                        batterySaverSettings.getContext(),
                        batterySaverSettings.getString(R.string.help_url_battery_saver_settings),
                        ApnSettings.MVNO_NONE),
                0);
    }

    public void addHelpLink() {
        FooterPreference footerPreference =
                (FooterPreference)
                        getPreferenceScreen().findPreference("battery_saver_footer_preference");
        if (footerPreference != null) {
            footerPreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.fuelgauge.batterysaver.BatterySaverSettings$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            BatterySaverSettings.m872$r8$lambda$U1XfVISFYghtrhHb_Q5ZyZcxWo(
                                    BatterySaverSettings.this);
                        }
                    });
            footerPreference.setLearnMoreText(getString(R.string.battery_saver_link_a11y));
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final String getLogTag() {
        return "BatterySaverSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1881;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.battery_saver_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME",
                                "com.samsung.android.lool");
        Intent intent = new Intent("com.samsung.android.sm.ACTION_BATTERY_SAVER_SETTINGS");
        intent.addFlags(268435456);
        intent.setPackage(string);
        try {
            getContext().startActivity(intent);
            finish();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        setupFooter();
    }

    public void setupFooter() {
        if (TextUtils.isEmpty(getString(R.string.help_url_battery_saver_settings))) {
            return;
        }
        addHelpLink();
    }
}
