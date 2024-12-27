package com.android.settings.dream;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.dream.DreamBackend;
import com.android.settingslib.widget.CandidateInfo;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WhenToDreamPicker extends RadioButtonPickerFragment {
    public DreamBackend mBackend;
    public boolean mDreamsSupportedOnBattery;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WhenToDreamCandidateInfo extends CandidateInfo {
        public final String key;
        public final String name;

        public WhenToDreamCandidateInfo(String str, String str2) {
            super(true);
            this.name = str;
            this.key = str2;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.key;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.name;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        String[] stringArray =
                this.mDreamsSupportedOnBattery
                        ? getResources().getStringArray(R.array.when_to_start_screensaver_entries)
                        : getResources()
                                .getStringArray(
                                        R.array.when_to_start_screensaver_entries_no_battery);
        String[] stringArray2 =
                this.mDreamsSupportedOnBattery
                        ? getResources().getStringArray(R.array.when_to_start_screensaver_values)
                        : getResources()
                                .getStringArray(
                                        R.array.when_to_start_screensaver_values_no_battery);
        ArrayList arrayList = new ArrayList();
        if (stringArray == null || stringArray.length <= 0) {
            return null;
        }
        if (stringArray2 == null || stringArray2.length != stringArray.length) {
            throw new IllegalArgumentException("Entries and values must be of the same length.");
        }
        for (int i = 0; i < stringArray.length; i++) {
            arrayList.add(new WhenToDreamCandidateInfo(stringArray[i], stringArray2[i]));
        }
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        int whenToDreamSetting = this.mBackend.getWhenToDreamSetting();
        BaseSearchIndexProvider baseSearchIndexProvider = DreamSettings.SEARCH_INDEX_DATA_PROVIDER;
        return whenToDreamSetting != 0
                ? whenToDreamSetting != 1
                        ? whenToDreamSetting != 2 ? "never" : "either_charging_or_docked"
                        : "while_docked_only"
                : "while_charging_only";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2036;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.when_to_dream_settings;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mBackend = DreamBackend.getInstance(context);
        this.mDreamsSupportedOnBattery =
                getResources().getBoolean(android.R.bool.config_earcEnabled_userConfigurable);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void onSelectionPerformed(boolean z) {
        getActivity().finish();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        char c;
        DreamBackend dreamBackend = this.mBackend;
        BaseSearchIndexProvider baseSearchIndexProvider = DreamSettings.SEARCH_INDEX_DATA_PROVIDER;
        switch (str.hashCode()) {
            case -1592701525:
                if (str.equals("while_docked_only")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -294641318:
                if (str.equals("either_charging_or_docked")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 104712844:
                if (str.equals("never")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1019349036:
                if (str.equals("while_charging_only")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        char c2 = c != 0 ? c != 1 ? c != 2 ? (char) 3 : (char) 2 : (char) 1 : (char) 0;
        dreamBackend.getClass();
        Settings.Secure.putInt(
                dreamBackend.mContext.getContentResolver(), "screensaver_enabled", c2 != 3 ? 1 : 0);
        dreamBackend.logDreamSettingChangeToStatsd(1);
        if (c2 == 0) {
            Settings.Secure.putInt(
                    dreamBackend.mContext.getContentResolver(), "screensaver_activate_on_dock", 0);
            Settings.Secure.putInt(
                    dreamBackend.mContext.getContentResolver(), "screensaver_activate_on_sleep", 1);
        } else if (c2 == 1) {
            Settings.Secure.putInt(
                    dreamBackend.mContext.getContentResolver(), "screensaver_activate_on_dock", 1);
            Settings.Secure.putInt(
                    dreamBackend.mContext.getContentResolver(), "screensaver_activate_on_sleep", 0);
        } else if (c2 == 2) {
            Settings.Secure.putInt(
                    dreamBackend.mContext.getContentResolver(), "screensaver_activate_on_dock", 1);
            Settings.Secure.putInt(
                    dreamBackend.mContext.getContentResolver(), "screensaver_activate_on_sleep", 1);
        }
        dreamBackend.logDreamSettingChangeToStatsd(3);
        return true;
    }
}
