package com.samsung.android.settings.accessibility.hearing;

import android.content.Context;
import android.content.DialogInterface;
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

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accessibility.ToggleFeaturePreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.search.AccessibilityBaseSearchIndexProvider;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;
import com.samsung.android.settings.accessibility.recommend.RecommendedForYouUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MuteAllSoundFragment extends ToggleFeaturePreferenceFragment
        implements DialogInterface.OnClickListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(
                    0,
                    "com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment",
                    "all_sound_off_key");
    public final AnonymousClass2 contentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.hearing.MuteAllSoundFragment.2
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    MuteAllSoundFragment.this.updateSwitchBarToggleSwitch();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.hearing.MuteAllSoundFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends AccessibilityBaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            if (!isPageSearchEnabled(context)) {
                return null;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            CharSequence text = context.getText(R.string.mute_all_sounds_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityConstant.COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT.flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            return List.of(searchIndexableRaw);
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getExclusiveTaskName() {
        return "mute_all_sound";
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "MuteAllSoundFragment";
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4011;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final List getPreferenceOrderList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("guide_description");
        arrayList.add(
                AccessibilityConstant.COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT.flattenToString());
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_settings_mute_all_sounds;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityConstant.COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT.flattenToString();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        this.mToggleServiceSwitchBar.setCheckedInternal(false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        Preference findPreference = getPreferenceScreen().findPreference("guide_description");
        if (findPreference instanceof DescriptionPreference) {
            ((DescriptionPreference) findPreference)
                    .setTitle(
                            (!Utils.isTablet() || Utils.isVoiceCapable(getPrefContext()))
                                    ? getText(R.string.mute_all_sounds_description)
                                    : getText(
                                            R.string
                                                    .mute_all_sounds_description_not_supporting_call_feature));
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mComponentName = AccessibilityConstant.COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT;
        this.mPackageName = getText(R.string.mute_all_sounds_title);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {
        if ((Settings.Global.getInt(getContentResolver(), "all_sound_off", 0) == 1) != z
                && SecAccessibilityUtils.checkAutoAnsweringMemo(z, getPrefContext(), null, this)) {
            SecAccessibilityUtils.enableMuteAllSounds(getPrefContext(), z);
            RecommendedForYouUtils.updateFeatureConditionForProfile(
                    getPrefContext(),
                    "enable_time_mute_all_sounds",
                    "use_count_mute_all_sounds",
                    z);
        }
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

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("all_sound_off"), false, this.contentObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getContentResolver().unregisterContentObserver(this.contentObserver);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateSwitchBarToggleSwitch() {
        boolean z = Settings.Global.getInt(getContentResolver(), "all_sound_off", 0) == 1;
        if (((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked() != z) {
            this.mToggleServiceSwitchBar.setCheckedInternal(z);
        }
    }
}
