package com.samsung.android.settings.development;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SaLoggingCheckingPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final Context mContext;
    public final String[] mListSummaries;
    public final String[] mListValues;
    public int mSelectedCheckType;

    public SaLoggingCheckingPreferenceController(Context context) {
        super(context);
        this.mContext = context;
        this.mListValues =
                context.getResources().getStringArray(R.array.sa_logging_checking_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.sa_logging_checking_titles);
        this.mSelectedCheckType =
                Settings.System.getInt(context.getContentResolver(), "sa_logging_check_state", 0);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "sa_logging_checking";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !TextUtils.equals("user", Build.TYPE);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.System.putInt(this.mContext.getContentResolver(), "sa_logging_check_state", 0);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "sa_logging_check_state",
                Integer.parseInt((String) obj));
        updateSaLoggingValues((ListPreference) this.mPreference);
        return true;
    }

    public final void updateSaLoggingValues(ListPreference listPreference) {
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "sa_logging_check_state", 0);
        if (i > 3) {
            i = 3;
        }
        this.mSelectedCheckType = i;
        TooltipPopup$$ExternalSyntheticOutline0.m(
                new StringBuilder("crom updateSaLoggingValues : "),
                this.mSelectedCheckType,
                "com.samsung.android.settings.development.SaLoggingCheckingPreferenceController");
        listPreference.setValue(this.mListValues[this.mSelectedCheckType]);
        listPreference.setSummary(this.mListSummaries[this.mSelectedCheckType]);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateSaLoggingValues((ListPreference) this.mPreference);
    }
}
