package com.android.settings.gestures;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.widget.CompoundButton;

import androidx.fragment.app.FragmentActivity;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.widget.IllustrationPreference;
import com.android.settingslib.widget.MainSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class OneHandedSettings extends AccessibilityShortcutPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.one_handed_settings);
    public String mFeatureName;
    public OneHandedSettingsUtils mUtils;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.gestures.OneHandedSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return OneHandedSettingsUtils.isSupportOneHandedMode();
        }
    }

    public OneHandedSettings() {
        super(null);
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final ComponentName getComponentName() {
        return AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        int dialogMetricsCategory = super.getDialogMetricsCategory(i);
        if (dialogMetricsCategory == 0) {
            return 1841;
        }
        return dialogMetricsCategory;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final CharSequence getLabelName() {
        return this.mFeatureName;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "OneHandedSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1841;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.one_handed_settings;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final String getShortcutPreferenceKey() {
        return "one_handed_shortcuts_preference";
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final CharSequence getShortcutTitle() {
        return getText(R.string.one_handed_mode_shortcut_title);
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        this.mFeatureName = getContext().getString(R.string.one_handed_title);
        super.onCreate(bundle);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        OneHandedSettingsUtils oneHandedSettingsUtils = new OneHandedSettingsUtils(getContext());
        this.mUtils = oneHandedSettingsUtils;
        oneHandedSettingsUtils.registerToggleAwareObserver(
                new OneHandedSettingsUtils
                        .TogglesCallback() { // from class:
                                             // com.android.settings.gestures.OneHandedSettings$$ExternalSyntheticLambda1
                    @Override // com.android.settings.gestures.OneHandedSettingsUtils.TogglesCallback
                    public final void onChange(Uri uri) {
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                OneHandedSettings.SEARCH_INDEX_DATA_PROVIDER;
                        final OneHandedSettings oneHandedSettings = OneHandedSettings.this;
                        FragmentActivity activity = oneHandedSettings.getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(
                                    new Runnable() { // from class:
                                                     // com.android.settings.gestures.OneHandedSettings$$ExternalSyntheticLambda2
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OneHandedSettings oneHandedSettings2 =
                                                    OneHandedSettings.this;
                                            BaseSearchIndexProvider baseSearchIndexProvider2 =
                                                    OneHandedSettings.SEARCH_INDEX_DATA_PROVIDER;
                                            oneHandedSettings2.updatePreferenceStates();
                                        }
                                    });
                        }
                    }
                });
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mUtils.unregisterToggleAwareObserver();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void updatePreferenceStates() {
        OneHandedSettingsUtils.sCurrentUserId = UserHandle.myUserId();
        super.updatePreferenceStates();
        ((IllustrationPreference) getPreferenceScreen().findPreference("one_handed_header"))
                .setLottieAnimationResId(
                        OneHandedSettingsUtils.isSwipeDownNotificationEnabled(getContext())
                                ? R.raw.lottie_swipe_for_notifications
                                : R.raw.lottie_one_hand_mode);
        ((MainSwitchPreference)
                        getPreferenceScreen()
                                .findPreference("gesture_one_handed_mode_enabled_main_switch"))
                .addOnSwitchChangeListener(
                        new CompoundButton
                                .OnCheckedChangeListener() { // from class:
                                                             // com.android.settings.gestures.OneHandedSettings$$ExternalSyntheticLambda0
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(
                                    CompoundButton compoundButton, boolean z) {
                                OneHandedSettings oneHandedSettings = OneHandedSettings.this;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        OneHandedSettings.SEARCH_INDEX_DATA_PROVIDER;
                                oneHandedSettings.getClass();
                                compoundButton.setChecked(z);
                                if (z) {
                                    oneHandedSettings.mNeedsQSTooltipType = 1;
                                }
                            }
                        });
    }
}
