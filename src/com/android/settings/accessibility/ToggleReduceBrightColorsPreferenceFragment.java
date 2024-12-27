package com.android.settings.accessibility;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import android.os.Bundle;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SeslSwitchBar;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleReduceBrightColorsPreferenceFragment extends ToggleFeaturePreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.reduce_bright_colors_settings);
    public ColorDisplayManager mColorDisplayManager;
    public ReduceBrightColorsIntensityPreferenceController mRbcIntensityPreferenceController;
    public ReduceBrightColorsPersistencePreferenceController mRbcPersistencePreferenceController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accessibility.ToggleReduceBrightColorsPreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            if (!SecAccessibilityUtils.isSupportReduceBrightness(context)
                    || !ColorDisplayManager.isReduceBrightColorsAvailable(context)) {
                return null;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            CharSequence text = context.getText(R.string.reduce_bright_colors_preference_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME
                            .flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            return List.of(searchIndexableRaw);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return ColorDisplayManager.isReduceBrightColorsAvailable(context);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ToggleReduceBrightColorsPreferenceFragment";
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3170;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final List getPreferenceOrderList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("description_preference");
        arrayList.add(
                AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME
                        .flattenToString());
        arrayList.add("empty_category");
        arrayList.add("rbc_intensity");
        arrayList.add("rbc_persist");
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.reduce_bright_colors_settings;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME
                .flattenToString();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final CharSequence getShortcutTitle() {
        return getString(
                R.string.accessibility_shortcut_title,
                getText(R.string.reduce_bright_colors_preference_title));
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mComponentName = AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME;
        this.mPackageName = getText(R.string.reduce_bright_colors_preference_title);
        this.mPackageNameResourceId = R.string.reduce_bright_colors_preference_title;
        this.mRbcIntensityPreferenceController =
                new ReduceBrightColorsIntensityPreferenceController(getContext(), "rbc_intensity");
        this.mRbcPersistencePreferenceController =
                new ReduceBrightColorsPersistencePreferenceController(getContext(), "rbc_persist");
        this.mRbcIntensityPreferenceController.displayPreference(getPreferenceScreen());
        this.mRbcPersistencePreferenceController.displayPreference(getPreferenceScreen());
        this.mColorDisplayManager =
                (ColorDisplayManager) getContext().getSystemService(ColorDisplayManager.class);
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mToggleServiceSwitchPreference.setTitle(R.string.reduce_bright_colors_switch_title);
        return onCreateView;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {
        if (z) {
            this.mNeedsQSTooltipType = 1;
        }
        AccessibilityStatsLogUtils.logAccessibilityServiceEnabled(this.mComponentName, z);
        this.mColorDisplayManager.setReduceBrightColorsActivated(z);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onRemoveSwitchPreferenceToggleSwitch() {
        this.mToggleServiceSwitchBar.removeOnSwitchChangeListener(this);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateSwitchBarToggleSwitch();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void registerKeysToObserverCallback(
            AccessibilitySettingsContentObserver accessibilitySettingsContentObserver) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add("reduce_bright_colors_activated");
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(
                arrayList,
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.android.settings.accessibility.ToggleReduceBrightColorsPreferenceFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                ToggleReduceBrightColorsPreferenceFragment
                                        .SEARCH_INDEX_DATA_PROVIDER;
                        ToggleReduceBrightColorsPreferenceFragment.this
                                .updateSwitchBarToggleSwitch();
                    }
                });
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateSwitchBarToggleSwitch() {
        boolean z =
                Settings.Secure.getInt(
                                getContext().getContentResolver(),
                                "reduce_bright_colors_activated",
                                0)
                        == 1;
        this.mRbcIntensityPreferenceController.updateState(
                getPreferenceScreen().findPreference("rbc_intensity"));
        this.mRbcPersistencePreferenceController.updateState(
                getPreferenceScreen().findPreference("rbc_persist"));
        if (((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked() != z) {
            this.mToggleServiceSwitchBar.setChecked(z);
        }
    }
}
