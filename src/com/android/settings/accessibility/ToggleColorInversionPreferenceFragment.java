package com.android.settings.accessibility;

import android.content.Context;
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

import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;
import com.samsung.android.settings.accessibility.recommend.RecommendedForYouUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleColorInversionPreferenceFragment extends ToggleFeaturePreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.accessibility_color_inversion_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accessibility.ToggleColorInversionPreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            CharSequence text =
                    context.getText(R.string.accessibility_display_inversion_preference_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME
                            .flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            return List.of(searchIndexableRaw);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ToggleColorInversionPreferenceFragment";
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3060;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_color_inversion_settings;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final CharSequence getShortcutTitle() {
        return getString(
                R.string.accessibility_shortcut_title,
                getText(R.string.accessibility_display_inversion_preference_title));
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mComponentName = AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME;
        this.mPackageName = getText(R.string.accessibility_display_inversion_preference_title);
        this.mPackageNameResourceId = R.string.accessibility_display_inversion_preference_title;
        CharSequence text = getText(R.string.color_inversion_desc);
        DescriptionPreference descriptionPreference = new DescriptionPreference(requireContext());
        descriptionPreference.setTitle(text);
        getPreferenceScreen().addPreference(descriptionPreference);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {
        if (z
                == (Settings.Secure.getInt(
                                getContentResolver(), "accessibility_display_inversion_enabled", 0)
                        == 1)) {
            return;
        }
        if (z) {
            this.mNeedsQSTooltipType = 1;
        }
        AccessibilityStatsLogUtils.logAccessibilityServiceEnabled(this.mComponentName, z);
        Settings.Secure.putInt(
                getContentResolver(), "accessibility_display_inversion_enabled", z ? 1 : 0);
        if (z) {
            RecommendedForYouUtils.updateFeatureConditionForProfile(
                    getPrefContext(), null, "use_count_color_inversion", true);
        }
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

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        View peekDecorView = getActivity().getWindow().peekDecorView();
        if (peekDecorView != null) {
            peekDecorView.setAccessibilityPaneTitle(
                    getString(R.string.accessibility_display_inversion_preference_title));
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void registerKeysToObserverCallback(
            AccessibilitySettingsContentObserver accessibilitySettingsContentObserver) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add("accessibility_display_inversion_enabled");
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(
                arrayList,
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.android.settings.accessibility.ToggleColorInversionPreferenceFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                ToggleColorInversionPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER;
                        ToggleColorInversionPreferenceFragment.this.updateSwitchBarToggleSwitch();
                    }
                });
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateSwitchBarToggleSwitch() {
        boolean z =
                Settings.Secure.getInt(
                                getContentResolver(), "accessibility_display_inversion_enabled", 0)
                        == 1;
        if (((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked() != z) {
            this.mToggleServiceSwitchBar.setChecked(z);
        }
    }
}
