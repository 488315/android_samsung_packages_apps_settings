package com.samsung.android.settings.accessibility.vision;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.search.SearchIndexableRaw;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HighContrastFontFragment extends AccessibilityShortcutPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public final AnonymousClass2 contentObserver;
    public SettingsMainSwitchBar mainSwitchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.vision.HighContrastFontFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            Resources resources = context.getResources();
            BaseSearchIndexProvider baseSearchIndexProvider =
                    HighContrastFontFragment.SEARCH_INDEX_DATA_PROVIDER;
            CharSequence text = resources.getText(R.string.high_contrast_font_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityConstant.COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT
                            .flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            return List.of(searchIndexableRaw);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.accessibility.vision.HighContrastFontFragment$2] */
    public HighContrastFontFragment() {
        super(null);
        this.contentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.accessibility.vision.HighContrastFontFragment.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        HighContrastFontFragment highContrastFontFragment =
                                HighContrastFontFragment.this;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                HighContrastFontFragment.SEARCH_INDEX_DATA_PROVIDER;
                        SettingsMainSwitchBar settingsMainSwitchBar =
                                highContrastFontFragment.mainSwitchBar;
                        if (settingsMainSwitchBar != null) {
                            settingsMainSwitchBar.setCheckedInternal(
                                    Settings.Secure.getInt(
                                                    highContrastFontFragment.getContentResolver(),
                                                    "high_text_contrast_enabled",
                                                    0)
                                            != 0);
                        }
                    }
                };
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final ComponentName getComponentName() {
        return AccessibilityConstant.COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final CharSequence getLabelName() {
        return getResources().getText(R.string.high_contrast_font_title);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "HighContrastFontFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3020;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return 0;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityConstant.COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT.flattenToString();
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        DescriptionPreference descriptionPreference = new DescriptionPreference(requireContext());
        descriptionPreference.setTitle(getText(R.string.high_contrast_fonts_desc));
        getPreferenceScreen().addPreference(descriptionPreference);
        WizardManagerHelper.isUserSetupComplete(getPrefContext());
        this.mPackageNameResourceId = R.string.high_contrast_font_title;
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (settingsActivity != null) {
            SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
            this.mainSwitchBar = settingsMainSwitchBar;
            settingsMainSwitchBar.show();
            this.mainSwitchBar.addOnSwitchChangeListener(
                    new CompoundButton
                            .OnCheckedChangeListener() { // from class:
                                                         // com.samsung.android.settings.accessibility.vision.HighContrastFontFragment$$ExternalSyntheticLambda0
                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                        public final void onCheckedChanged(
                                CompoundButton compoundButton, boolean z) {
                            HighContrastFontFragment highContrastFontFragment =
                                    HighContrastFontFragment.this;
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    HighContrastFontFragment.SEARCH_INDEX_DATA_PROVIDER;
                            Settings.Secure.putInt(
                                    highContrastFontFragment.getContentResolver(),
                                    "high_text_contrast_enabled",
                                    z ? 1 : 0);
                            compoundButton.announceForAccessibility(
                                    highContrastFontFragment.getString(
                                            z
                                                    ? R.string.switch_on_text
                                                    : R.string.switch_off_text));
                        }
                    });
            this.mainSwitchBar.setSessionDescription(getLabelName().toString());
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setCheckedInternal(
                    Settings.Secure.getInt(getContentResolver(), "high_text_contrast_enabled", 0)
                            != 0);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("high_text_contrast_enabled"),
                        false,
                        this.contentObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getContentResolver().unregisterContentObserver(this.contentObserver);
    }
}
