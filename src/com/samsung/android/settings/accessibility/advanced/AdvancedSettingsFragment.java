package com.samsung.android.settings.accessibility.advanced;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutContentObserver;
import com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment;
import com.samsung.android.settings.notification.reminder.NotificationReminderActivity;
import com.samsung.android.settings.widget.SecRelativeLinkView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AdvancedSettingsFragment extends AccessibilityDashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.accessibility_advanced_settings);
    public final AnonymousClass1 shortcutContentObserver =
            new ShortcutContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.advanced.AdvancedSettingsFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    AdvancedSettingsFragment advancedSettingsFragment =
                            AdvancedSettingsFragment.this;
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            AdvancedSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                    advancedSettingsFragment.updatePreferenceStates();
                }
            };

    public static /* synthetic */ void $r8$lambda$CT78zNSLBF6RN6iZ6JvOQ5veKdQ(
            AdvancedSettingsFragment advancedSettingsFragment,
            SecRelativeLinkView secRelativeLinkView,
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData) {
        advancedSettingsFragment.mMetricsFeatureProvider.clicked(6000, "A11Y6006");
        secRelativeLinkView.startFooterViewLink(settingsPreferenceFragmentLinkData);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "A11y_Advanced";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 6000;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_advanced_settings;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment, androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        Context prefContext = getPrefContext();
        if (Rune.supportRelativeLink()) {
            final SecRelativeLinkView secRelativeLinkView = new SecRelativeLinkView(prefContext);
            final SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData.intent =
                    new Intent(prefContext, (Class<?>) NotificationReminderActivity.class)
                            .putExtra("from_relative_link", true);
            settingsPreferenceFragmentLinkData.topLevelKey = "top_level_notifications";
            settingsPreferenceFragmentLinkData.titleRes = R.string.repeat_notification_alerts_label;
            secRelativeLinkView
                    .pushLinkData(settingsPreferenceFragmentLinkData)
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.accessibility.advanced.AdvancedSettingsFragment$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    AdvancedSettingsFragment.$r8$lambda$CT78zNSLBF6RN6iZ6JvOQ5veKdQ(
                                            AdvancedSettingsFragment.this,
                                            secRelativeLinkView,
                                            settingsPreferenceFragmentLinkData);
                                }
                            });
            secRelativeLinkView.create(this);
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        register(getContentResolver());
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        AnonymousClass1 anonymousClass1 = this.shortcutContentObserver;
        ContentResolver contentResolver = getContentResolver();
        anonymousClass1.getClass();
        contentResolver.unregisterContentObserver(anonymousClass1);
    }
}
