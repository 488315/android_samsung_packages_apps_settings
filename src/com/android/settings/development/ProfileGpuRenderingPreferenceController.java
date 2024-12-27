package com.android.settings.development;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;
import com.android.settingslib.development.SystemPropPoker;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProfileGpuRenderingPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final String[] mListSummaries;
    public final String[] mListValues;

    public ProfileGpuRenderingPreferenceController(Context context) {
        super(context);
        this.mListValues = context.getResources().getStringArray(R.array.track_frame_time_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.track_frame_time_entries);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "track_frame_time";
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(
                "debug.hwui.profile", obj == null ? ApnSettings.MVNO_NONE : obj.toString());
        SystemPropPoker.sInstance.poke();
        updateTrackFrameTimeOptions();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateTrackFrameTimeOptions();
    }

    public final void updateTrackFrameTimeOptions() {
        String[] strArr;
        String str = SystemProperties.get("debug.hwui.profile", ApnSettings.MVNO_NONE);
        int i = 0;
        int i2 = 0;
        while (true) {
            strArr = this.mListValues;
            if (i2 >= strArr.length) {
                break;
            }
            if (TextUtils.equals(str, strArr[i2])) {
                i = i2;
                break;
            }
            i2++;
        }
        ListPreference listPreference = (ListPreference) this.mPreference;
        listPreference.setValue(strArr[i]);
        listPreference.setSummary(this.mListSummaries[i]);
    }
}
