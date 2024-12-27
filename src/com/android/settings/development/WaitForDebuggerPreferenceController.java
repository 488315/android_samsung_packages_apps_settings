package com.android.settings.development;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Intent;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WaitForDebuggerPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener,
                PreferenceControllerMixin,
                OnActivityResultListener {
    static final int SETTING_VALUE_OFF = 0;
    static final int SETTING_VALUE_ON = 1;

    public IActivityManager getActivityManagerService() {
        return ActivityManager.getService();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wait_for_debugger";
    }

    @Override // com.android.settings.development.OnActivityResultListener
    public final boolean onActivityResult(int i, int i2, Intent intent) {
        if (i != 1 || i2 != -1) {
            return false;
        }
        updateState(this.mPreference, intent.getAction());
        return true;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        try {
            getActivityManagerService().setDebugApp((String) null, false, false);
        } catch (RemoteException unused) {
        }
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        try {
            getActivityManagerService()
                    .setDebugApp(
                            Settings.Global.getString(
                                    this.mContext.getContentResolver(), "debug_app"),
                            booleanValue,
                            true);
        } catch (RemoteException unused) {
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateState(
                this.mPreference,
                Settings.Global.getString(this.mContext.getContentResolver(), "debug_app"));
    }

    public final void updateState(Preference preference, String str) {
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        boolean z =
                Settings.Global.getInt(this.mContext.getContentResolver(), "wait_for_debugger", 0)
                        != 0;
        try {
            getActivityManagerService().setDebugApp(str, z, true);
        } catch (RemoteException unused) {
        }
        twoStatePreference.setChecked(z);
        twoStatePreference.setEnabled(!TextUtils.isEmpty(str));
    }
}
