package com.samsung.android.settings.location;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.LayoutPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LocationCmccAgps extends RadioButtonPickerFragment {
    public ContentResolver mContentResolver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CmccAgpsrModeCandidateInfo extends CandidateInfo {
        public final String mKey;
        public final CharSequence mLabel;

        public CmccAgpsrModeCandidateInfo(String str, CharSequence charSequence) {
            super(true);
            this.mLabel = charSequence;
            this.mKey = str;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.mKey;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mLabel;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void addStaticPreferences(PreferenceScreen preferenceScreen) {
        LayoutPreference layoutPreference =
                new LayoutPreference(
                        preferenceScreen.getContext(), R.layout.sec_agps_function_switch_note);
        layoutPreference.setSelectable(false);
        preferenceScreen.addPreference(layoutPreference);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        Context context = getContext();
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new CmccAgpsrModeCandidateInfo(
                        "agps_mode_home_network",
                        context.getText(R.string.agps_on_for_home_network)));
        arrayList.add(
                new CmccAgpsrModeCandidateInfo(
                        "agps_mode_all_network",
                        context.getText(R.string.agps_on_for_all_network)));
        arrayList.add(
                new CmccAgpsrModeCandidateInfo(
                        "agps_mode_off", context.getText(R.string.agps_off)));
        return arrayList;
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final ContentResolver getContentResolver() {
        Context context = getContext();
        if (context != null) {
            this.mContentResolver = context.getContentResolver();
        }
        return this.mContentResolver;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        int i = Settings.System.getInt(getContentResolver(), "agps_function_switch", 1);
        return i != 2
                ? i != 3 ? "agps_mode_home_network" : "agps_mode_off"
                : "agps_mode_all_network";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 64;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.layout.sec_agps_function_switch_list;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        str.getClass();
        switch (str) {
            case "agps_mode_home_network":
                Settings.System.putInt(getContentResolver(), "agps_function_switch", 1);
                return true;
            case "agps_mode_off":
                Settings.System.putInt(getContentResolver(), "agps_function_switch", 3);
                return true;
            case "agps_mode_all_network":
                Settings.System.putInt(getContentResolver(), "agps_function_switch", 2);
                return true;
            default:
                Settings.System.putInt(getContentResolver(), "agps_function_switch", 1);
                return true;
        }
    }
}
