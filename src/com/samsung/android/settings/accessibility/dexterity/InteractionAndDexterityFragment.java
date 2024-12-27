package com.samsung.android.settings.accessibility.dexterity;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment;
import com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesTutorialFragment;
import com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationTutorialFragment;
import com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayTutorialFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class InteractionAndDexterityFragment extends AccessibilityDashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.accessibility_interaction_and_dexterity);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.dexterity.InteractionAndDexterityFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean supportDynamicRawIndexingWithController() {
            return true;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "A11y_Dexterity";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5000;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_interaction_and_dexterity;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        boolean z =
                SecAccessibilityUtils.getAccessibilitySharedPreferences(getPrefContext())
                        .getBoolean("touch_hold_delay_tutorial", false);
        boolean z2 =
                SecAccessibilityUtils.getAccessibilitySharedPreferences(getPrefContext())
                        .getBoolean("tap_duration_tutorial", false);
        boolean z3 =
                SecAccessibilityUtils.getAccessibilitySharedPreferences(getPrefContext())
                        .getBoolean("ignore_repeated_tutorial", false);
        if ("select_long_press_timeout_preference".equals(key) && !z) {
            setFragment(
                    R.string.touch_and_hold_delay_header,
                    TouchHoldDelayTutorialFragment.class.getName());
            return false;
        }
        if ("tap_duration_key".equals(key) && !z2) {
            setFragment(R.string.tap_duration_title, TapDurationTutorialFragment.class.getName());
            return false;
        }
        if (!"ignore_repeat_key".equals(key) || z3) {
            return super.onPreferenceTreeClick(preference);
        }
        setFragment(
                R.string.accessibility_ignore_repeat,
                IgnoreRepeatedTouchesTutorialFragment.class.getName());
        return false;
    }

    public final void setFragment(int i, String str) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getPrefContext());
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mSourceMetricsCategory = 5000;
        subSettingLauncher.addFlags(268435456);
        subSettingLauncher.setTitleRes(i, null);
        subSettingLauncher.launch();
    }
}
