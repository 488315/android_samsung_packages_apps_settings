package com.android.settings.development;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;
import com.android.settingslib.development.SystemPropPoker;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HdcpCheckingPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String HDCP_CHECKING_PROPERTY = "persist.sys.hdcp_checking";
    static final String USER_BUILD_TYPE = "user";
    public final String[] mListSummaries;
    public final String[] mListValues;

    public HdcpCheckingPreferenceController(Context context) {
        super(context);
        this.mListValues =
                this.mContext.getResources().getStringArray(R.array.hdcp_checking_values);
        this.mListSummaries =
                this.mContext.getResources().getStringArray(R.array.hdcp_checking_summaries);
    }

    public String getBuildType() {
        return Build.TYPE;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "hdcp_checking";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !TextUtils.equals(USER_BUILD_TYPE, getBuildType());
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(HDCP_CHECKING_PROPERTY, obj.toString());
        updateHdcpValues((ListPreference) this.mPreference);
        SystemPropPoker.sInstance.poke();
        return true;
    }

    public final void updateHdcpValues(ListPreference listPreference) {
        String[] strArr;
        String str = SystemProperties.get(HDCP_CHECKING_PROPERTY);
        int i = 0;
        while (true) {
            strArr = this.mListValues;
            if (i >= strArr.length) {
                i = 1;
                break;
            } else if (TextUtils.equals(str, strArr[i])) {
                break;
            } else {
                i++;
            }
        }
        listPreference.setValue(strArr[i]);
        listPreference.setSummary(this.mListSummaries[i]);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateHdcpValues((ListPreference) this.mPreference);
    }
}
