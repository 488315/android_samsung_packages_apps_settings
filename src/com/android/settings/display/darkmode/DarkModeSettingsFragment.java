package com.android.settings.display.darkmode;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DarkModeSettingsFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.dark_mode_settings);
    public DarkModeObserver mContentObserver;
    public DarkModeCustomPreferenceController mCustomEndController;
    public DarkModeCustomPreferenceController mCustomStartController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.display.darkmode.DarkModeSettingsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return !((PowerManager) context.getSystemService(PowerManager.class)).isPowerSaveMode();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList(2);
        this.mCustomStartController =
                new DarkModeCustomPreferenceController(getContext(), "dark_theme_start_time", this);
        this.mCustomEndController =
                new DarkModeCustomPreferenceController(getContext(), "dark_theme_end_time", this);
        arrayList.add(this.mCustomStartController);
        arrayList.add(this.mCustomEndController);
        return arrayList;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i != 0) {
            return i != 1 ? 0 : 1826;
        }
        return 1825;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DarkModeSettingsFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1698;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.dark_mode_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContentObserver = new DarkModeObserver(getContext());
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        return (i == 0 || i == 1)
                ? i == 0
                        ? this.mCustomStartController.getDialog()
                        : this.mCustomEndController.getDialog()
                : super.onCreateDialog(i);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if ("dark_theme_end_time".equals(preference.getKey())) {
            showDialog(1);
            return true;
        }
        if (!"dark_theme_start_time".equals(preference.getKey())) {
            return super.onPreferenceTreeClick(preference);
        }
        showDialog(0);
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mContentObserver.subscribe(
                new Runnable() { // from class:
                                 // com.android.settings.display.darkmode.DarkModeSettingsFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DarkModeSettingsFragment darkModeSettingsFragment =
                                DarkModeSettingsFragment.this;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                DarkModeSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                        PreferenceScreen preferenceScreen =
                                darkModeSettingsFragment.getPreferenceScreen();
                        darkModeSettingsFragment.mCustomStartController.displayPreference(
                                preferenceScreen);
                        darkModeSettingsFragment.mCustomEndController.displayPreference(
                                preferenceScreen);
                        darkModeSettingsFragment.updatePreferenceStates();
                    }
                });
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mContentObserver.unsubscribe();
    }
}
