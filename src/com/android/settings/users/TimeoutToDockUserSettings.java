package com.android.settings.users;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.widget.CandidateInfo;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TimeoutToDockUserSettings extends RadioButtonPickerFragment {
    public String[] mEntries;
    public String[] mValues;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TimeoutCandidateInfo extends CandidateInfo {
        public final String mKey;
        public final CharSequence mLabel;

        public TimeoutCandidateInfo(String str, CharSequence charSequence) {
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
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        if (this.mEntries != null && this.mValues != null) {
            int i = 0;
            while (true) {
                String[] strArr = this.mValues;
                if (i >= strArr.length) {
                    break;
                }
                arrayList.add(new TimeoutCandidateInfo(strArr[i], this.mEntries[i]));
                i++;
            }
        }
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        String stringForUser =
                Settings.Secure.getStringForUser(
                        getContext().getContentResolver(),
                        "timeout_to_dock_user",
                        UserHandle.myUserId());
        return stringForUser != null ? stringForUser : this.mValues[1];
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1916;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.user_timeout_to_dock_user_settings;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mEntries =
                getContext()
                        .getResources()
                        .getStringArray(R.array.switch_to_dock_user_when_docked_timeout_entries);
        this.mValues =
                getContext()
                        .getResources()
                        .getStringArray(R.array.switch_to_dock_user_when_docked_timeout_values);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        Settings.Secure.putStringForUser(
                getContext().getContentResolver(),
                "timeout_to_dock_user",
                str,
                UserHandle.myUserId());
        return true;
    }
}
