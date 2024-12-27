package com.android.settings.inputmethod;

import android.content.Context;
import android.os.Bundle;
import android.util.FeatureFlagUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ModifierKeysSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_modifier_keys_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.inputmethod.ModifierKeysSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return FeatureFlagUtils.isEnabled(context, "settings_new_keyboard_modifier_key")
                    && !((ArrayList) PhysicalKeyboardFragment.getHardKeyboards(context)).isEmpty();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ModifierKeysSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1961;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_modifier_keys_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((com.samsung.android.settings.inputmethod.ModifierKeysPreferenceController)
                        use(
                                com.samsung.android.settings.inputmethod
                                        .ModifierKeysPreferenceController.class))
                .setFragment(this);
        ((com.samsung.android.settings.inputmethod.ModifierKeysRestorePreferenceController)
                        use(
                                com.samsung.android.settings.inputmethod
                                        .ModifierKeysRestorePreferenceController.class))
                .setFragment(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (FeatureFlagUtils.isEnabled(getContext(), "settings_new_keyboard_modifier_key")) {
            return;
        }
        Log.i("ModifierKeysSettings", "This device is not support modifier key settings");
        finish();
    }
}
