package com.samsung.android.settings.accessibility.hearing;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.widget.CompoundButton;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.CandidateInfo;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.AccessibilityRadioButtonPickerFragment;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MonoAudioFragment extends AccessibilityRadioButtonPickerFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public Context context;
    public final MonoAudioFragment$$ExternalSyntheticLambda0 switchChangeListener =
            new CompoundButton
                    .OnCheckedChangeListener() { // from class:
                                                 // com.samsung.android.settings.accessibility.hearing.MonoAudioFragment$$ExternalSyntheticLambda0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    Settings.System.putInt(
                            MonoAudioFragment.this.context.getContentResolver(),
                            "master_mono",
                            z ? 1 : 0);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.hearing.MonoAudioFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            for (CandidateInfo candidateInfo : MonoAudioFragment.createCandidates(context)) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = candidateInfo.getKey();
                searchIndexableRaw.title = candidateInfo.loadLabel().toString();
                searchIndexableRaw.screenTitle =
                        context.getString(R.string.accessibility_toggle_primary_mono_title);
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return SecAccessibilityUtils.isSupportStereoSpeaker();
        }
    }

    public static List createCandidates(Context context) {
        if (context == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new AccessibilityRadioButtonPickerFragment.RadioButtonCandidateInfo(
                        "mono_audio_type:connected",
                        context.getText(R.string.mono_audio_type_connected_audio)));
        arrayList.add(
                new AccessibilityRadioButtonPickerFragment.RadioButtonCandidateInfo(
                        "mono_audio_type:all", context.getText(R.string.mono_audio_type_all)));
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void addStaticPreferences(PreferenceScreen preferenceScreen) {
        DescriptionPreference descriptionPreference = new DescriptionPreference(this.context);
        descriptionPreference.setTitle(getText(R.string.mono_audio_description));
        preferenceScreen.addPreference(descriptionPreference);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        return createCandidates(this.context);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        return Settings.System.getInt(this.context.getContentResolver(), "mono_audio_type", 0) == 0
                ? "mono_audio_type:connected"
                : "mono_audio_type:all";
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityRadioButtonPickerFragment
    public final CompoundButton.OnCheckedChangeListener getMainSwitchChangeListener() {
        return this.switchChangeListener;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4013;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityRadioButtonPickerFragment
    public final List getObservableUriList() {
        return List.of(
                Settings.System.getUriFor("master_mono"),
                Settings.System.getUriFor("mono_audio_type"),
                Settings.Global.getUriFor("all_sound_off"));
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityRadioButtonPickerFragment, com.android.settings.widget.RadioButtonPickerFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        Settings.System.putInt(
                this.context.getContentResolver(),
                "mono_audio_type",
                !"mono_audio_type:connected".equals(str) ? 1 : 0);
        return true;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityRadioButtonPickerFragment
    public final void updatePreferenceStates() {
        ContentResolver contentResolver = getPrefContext().getContentResolver();
        boolean z = Settings.System.getInt(contentResolver, "all_sound_off", 0) != 0;
        boolean z2 = Settings.System.getInt(contentResolver, "master_mono", 0) != 0;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setEnabled(!z);
            settingsMainSwitchBar.setChecked(z2);
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.setEnabled(!z);
        }
        updateCheckedState$1(getDefaultKey());
    }
}
