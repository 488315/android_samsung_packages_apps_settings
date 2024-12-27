package com.samsung.android.settings.accessibility.dexterity.autoaction;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.advanced.SecAccessibilityControlTimeoutPreferenceFragment$$ExternalSyntheticLambda0;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;
import com.samsung.android.util.SemLog;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoActionPreferenceFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.accessibility_settings_auto_action_after_pointer_stops);
    public final String TAG = "AutoActionPreferenceFragment";
    public final AnonymousClass1 contentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.dexterity.autoaction.AutoActionPreferenceFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    SemLog.d(
                            AutoActionPreferenceFragment.this.TAG,
                            "selfChange :" + z + "uri:" + uri);
                    AutoActionPreferenceFragment.this.updatePreferenceStates();
                }
            };
    public Context mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.dexterity.autoaction.AutoActionPreferenceFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return (SecAccessibilityUtils.isDexMode(context) || Utils.isNewDexMode(context))
                    ? false
                    : true;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return this.TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5008;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_settings_auto_action_after_pointer_stops;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.mStorage = 1;
        preferenceManager.mSharedPreferences = null;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        DescriptionPreference descriptionPreference;
        super.onCreatePreferences(bundle, str);
        FragmentActivity activity = getActivity();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (activity == null
                || preferenceScreen == null
                || (descriptionPreference =
                                (DescriptionPreference) findPreference("guide_description"))
                        == null) {
            return;
        }
        activity.addOnConfigurationChangedListener(
                new SecAccessibilityControlTimeoutPreferenceFragment$$ExternalSyntheticLambda0(
                        descriptionPreference));
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        SemLog.d(this.TAG, "onStart :");
        List.of(
                        Settings.Secure.getUriFor("accessibility_auto_click_paused_state"),
                        Settings.System.getUriFor("any_screen_enabled"),
                        Settings.Secure.getUriFor("accessibility_auto_action_type"),
                        Settings.Secure.getUriFor("accessibility_auto_action_delay"),
                        Settings.Secure.getUriFor("accessibility_corner_actions"))
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.accessibility.dexterity.autoaction.AutoActionPreferenceFragment$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AutoActionPreferenceFragment autoActionPreferenceFragment =
                                        AutoActionPreferenceFragment.this;
                                autoActionPreferenceFragment
                                        .mContext
                                        .getContentResolver()
                                        .registerContentObserver(
                                                (Uri) obj,
                                                false,
                                                autoActionPreferenceFragment.contentObserver);
                            }
                        });
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mContext.getContentResolver().unregisterContentObserver(this.contentObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void updatePreferenceStates() {
        super.updatePreferenceStates();
        Preference findPreference = findPreference("auto_click_switch");
        boolean z =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "accessibility_auto_click_paused_state",
                                0)
                        != 0;
        if (findPreference != null) {
            findPreference.setSummary(
                    z ? this.mContext.getString(R.string.accessibility_auto_click_paused) : null);
            SecPreferenceUtils.applySummaryColor((SecSwitchPreference) findPreference, true);
        }
    }
}
