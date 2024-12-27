package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.hardware.display.ColorDisplayManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.TwoStatePreference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ColorAndMotionFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.accessibility_color_and_motion);

    @VisibleForTesting static final String TOGGLE_FORCE_INVERT = "toggle_force_invert";
    public Preference mDisplayDaltonizerPreferenceScreen;
    public AccessibilitySettingsContentObserver mSettingsContentObserver;
    public final List mShortcutFeatureKeys = new ArrayList();
    public TwoStatePreference mToggleDisableAnimationsPreference;
    public TwoStatePreference mToggleLargePointerIconPreference;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ColorAndMotionFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1918;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_color_and_motion;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDisplayDaltonizerPreferenceScreen = findPreference("daltonizer_preference");
        this.mToggleDisableAnimationsPreference =
                (TwoStatePreference) findPreference("toggle_disable_animations");
        this.mToggleLargePointerIconPreference =
                (TwoStatePreference) findPreference("toggle_large_pointer_icon");
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) getPreferenceScreen().findPreference("experimental_category");
        if (ColorDisplayManager.isColorTransformAccelerated(getContext())) {
            getPreferenceScreen().removePreference(preferenceCategory);
        } else {
            getPreferenceScreen().removePreference(this.mDisplayDaltonizerPreferenceScreen);
            getPreferenceScreen().removePreference(this.mToggleDisableAnimationsPreference);
            getPreferenceScreen().removePreference(this.mToggleLargePointerIconPreference);
            preferenceCategory.addPreference(this.mDisplayDaltonizerPreferenceScreen);
            preferenceCategory.addPreference(this.mToggleDisableAnimationsPreference);
            preferenceCategory.addPreference(this.mToggleLargePointerIconPreference);
        }
        ((ArrayList) this.mShortcutFeatureKeys).add("accessibility_display_inversion_enabled");
        ((ArrayList) this.mShortcutFeatureKeys).add("accessibility_display_daltonizer_enabled");
        ((ArrayList) this.mShortcutFeatureKeys).add("accessibility_shortcut_target_service");
        ((ArrayList) this.mShortcutFeatureKeys).add("accessibility_button_targets");
        ((ArrayList) this.mShortcutFeatureKeys).add("accessibility_qs_targets");
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                new AccessibilitySettingsContentObserver(new Handler());
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(
                this.mShortcutFeatureKeys,
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.android.settings.accessibility.ColorAndMotionFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                ColorAndMotionFragment.SEARCH_INDEX_DATA_PROVIDER;
                        final ColorAndMotionFragment colorAndMotionFragment =
                                ColorAndMotionFragment.this;
                        colorAndMotionFragment.getClass();
                        ArrayList arrayList = new ArrayList();
                        colorAndMotionFragment
                                .getPreferenceControllers()
                                .forEach(
                                        new AccessibilitySettings$$ExternalSyntheticLambda3(
                                                0, arrayList));
                        arrayList.forEach(
                                new Consumer() { // from class:
                                                 // com.android.settings.accessibility.ColorAndMotionFragment$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ColorAndMotionFragment colorAndMotionFragment2 =
                                                ColorAndMotionFragment.this;
                                        AbstractPreferenceController abstractPreferenceController =
                                                (AbstractPreferenceController) obj;
                                        BaseSearchIndexProvider baseSearchIndexProvider2 =
                                                ColorAndMotionFragment.SEARCH_INDEX_DATA_PROVIDER;
                                        colorAndMotionFragment2.getClass();
                                        abstractPreferenceController.updateState(
                                                colorAndMotionFragment2.findPreference(
                                                        abstractPreferenceController
                                                                .getPreferenceKey()));
                                    }
                                });
                    }
                });
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mSettingsContentObserver.register(getContentResolver());
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                this.mSettingsContentObserver;
        ContentResolver contentResolver = getContentResolver();
        accessibilitySettingsContentObserver.getClass();
        contentResolver.unregisterContentObserver(accessibilitySettingsContentObserver);
    }
}
