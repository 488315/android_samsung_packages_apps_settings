package com.android.settings.development;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.os.RemoteException;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackgroundProcessLimitPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final String[] mListSummaries;
    public final String[] mListValues;

    public BackgroundProcessLimitPreferenceController(Context context) {
        super(context);
        this.mListValues = context.getResources().getStringArray(R.array.app_process_limit_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.app_process_limit_entries);
    }

    public IActivityManager getActivityManagerService() {
        return ActivityManager.getService();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "app_process_limit";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsEnabled() {
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy1",
                        "isBackgroundProcessLimitEnabled",
                        null);
        if (enterprisePolicyEnabled != -1) {
            this.mPreference.setEnabled(enterprisePolicyEnabled == 1);
        } else {
            super.onDeveloperOptionsEnabled();
        }
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        writeAppProcessLimitOptions(null);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        writeAppProcessLimitOptions(obj);
        updateAppProcessLimitOptions();
        return true;
    }

    public final void updateAppProcessLimitOptions() {
        String[] strArr = this.mListValues;
        try {
            int processLimit = getActivityManagerService().getProcessLimit();
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= strArr.length) {
                    break;
                }
                if (Integer.parseInt(strArr[i2]) >= processLimit) {
                    i = i2;
                    break;
                }
                i2++;
            }
            ListPreference listPreference = (ListPreference) this.mPreference;
            listPreference.setValue(strArr[i]);
            listPreference.setSummary(this.mListSummaries[i]);
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateAppProcessLimitOptions();
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy1",
                        "isBackgroundProcessLimitEnabled",
                        null);
        if (enterprisePolicyEnabled != -1) {
            preference.setEnabled(enterprisePolicyEnabled == 1);
        }
    }

    public final void writeAppProcessLimitOptions(Object obj) {
        int parseInt;
        if (obj != null) {
            try {
                parseInt = Integer.parseInt(obj.toString());
            } catch (RemoteException unused) {
                return;
            }
        } else {
            parseInt = -1;
        }
        getActivityManagerService().setProcessLimit(parseInt);
        updateAppProcessLimitOptions();
    }
}
