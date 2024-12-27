package com.samsung.android.settings.accessibility.hearing;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.provider.SearchIndexableData;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment;
import com.android.settings.bluetooth.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.base.search.AccessibilityBaseSearchIndexProvider;
import com.samsung.android.settings.accessibility.hearing.controllers.SecAccessibilityHearingAidPreferenceController;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HearingAidPreferenceFragment extends AccessibilityShortcutPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(
                    R.xml.accessibility_settings_hearing_aids,
                    "com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment",
                    "hearing_aid_support");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.hearing.HearingAidPreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends AccessibilityBaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            LocalBluetoothProfileManager localBluetoothProfileManager =
                    Utils.getLocalBluetoothManager(context).mProfileManager;
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (AccessibilityRune.getFloatingFeatureBooleanValue(
                            "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")
                    || defaultAdapter == null) {
                return null;
            }
            List supportedProfiles = defaultAdapter.getSupportedProfiles();
            if (!supportedProfiles.contains(21) && !supportedProfiles.contains(28)) {
                return null;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            CharSequence text = context.getText(R.string.bluetooth_hearing_aid_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityConstant.COMPONENT_NAME_HEARING_AID_SUPPORT_SHORTCUT
                            .flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            return List.of(searchIndexableRaw);
        }
    }

    public HearingAidPreferenceFragment() {
        super(null);
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final ComponentName getComponentName() {
        return AccessibilityConstant.COMPONENT_NAME_HEARING_AID_SUPPORT_SHORTCUT;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final CharSequence getLabelName() {
        return getResources().getText(R.string.bluetooth_hearing_aid_title);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "HearingAidPrefFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4006;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_settings_hearing_aids;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityConstant.COMPONENT_NAME_HEARING_AID_SUPPORT_SHORTCUT.flattenToString();
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((SecAccessibilityHearingAidPreferenceController)
                        use(SecAccessibilityHearingAidPreferenceController.class))
                .setFragmentManager(getFragmentManager());
    }
}
