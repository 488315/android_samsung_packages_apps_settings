package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ChangeLanguageShortcutOptionFragment extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public SecSwitchPreference mControlSpace;
    public SecSwitchPreference mLeftAltShift;
    public SecSwitchPreference mShiftSpace;
    public static final String TAG = ChangeLanguageShortcutOptionFragment.class.getName();
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.ChangeLanguageShortcutOptionFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_change_language_shortcut_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.ChangeLanguageShortcutOptionFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            Log.d(ChangeLanguageShortcutOptionFragment.TAG, "resetSettings called");
            Settings.System.putInt(
                    context.getContentResolver(), "enable_language_change_combination_key", 7);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_change_language_shortcut_settings);
        this.mShiftSpace = (SecSwitchPreference) findPreference("shift_space");
        this.mControlSpace = (SecSwitchPreference) findPreference("control_space");
        this.mLeftAltShift = (SecSwitchPreference) findPreference("leftalt_shift");
        this.mShiftSpace.setOnPreferenceChangeListener(this);
        this.mControlSpace.setOnPreferenceChangeListener(this);
        this.mLeftAltShift.setOnPreferenceChangeListener(this);
        this.mShiftSpace.setChecked(
                EnableCombinationKeyHelper.isEnableCombinationKey(getActivity(), 1));
        this.mControlSpace.setChecked(
                EnableCombinationKeyHelper.isEnableCombinationKey(getActivity(), 2));
        this.mLeftAltShift.setChecked(
                EnableCombinationKeyHelper.isEnableCombinationKey(getActivity(), 4));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Log.d(TAG, "onPreferenceChange Called");
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int i =
                preference.equals(this.mShiftSpace)
                        ? 1
                        : preference.equals(this.mControlSpace)
                                ? 2
                                : preference.equals(this.mLeftAltShift) ? 4 : -1;
        FragmentActivity activity = getActivity();
        int i2 =
                Settings.System.getInt(
                        activity.getContentResolver(), "enable_language_change_combination_key", 7);
        if (booleanValue) {
            Settings.System.putInt(
                    activity.getContentResolver(),
                    "enable_language_change_combination_key",
                    i | i2);
        } else {
            Settings.System.putInt(
                    activity.getContentResolver(),
                    "enable_language_change_combination_key",
                    (~i) & i2);
        }
        return true;
    }
}
