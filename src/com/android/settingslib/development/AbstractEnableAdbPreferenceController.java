package com.android.settingslib.development;

import android.app.ActivityManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;
import com.android.settings.development.AdbPreferenceController;
import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settings.development.EnableAdbWarningDialog;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractEnableAdbPreferenceController extends DeveloperOptionsPreferenceController implements Preference.OnPreferenceChangeListener {
    public RestrictedSwitchPreference mPreference;

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (isAvailable()) {
            this.mPreference = (RestrictedSwitchPreference) preferenceScreen.findPreference("enable_adb");
        }
    }

    public final void enablePreference(boolean z) {
        if (isAvailable()) {
            this.mPreference.setEnabled(z);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enable_adb";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return ((UserManager) this.mContext.getSystemService(UserManager.class)).isAdminUser();
    }

    public boolean isUserAMonkey() {
        return ActivityManager.isUserAMonkey();
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        if (isAvailable()) {
            this.mPreference.setDisabledByAdmin(RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(this.mContext, UserHandle.myUserId()));
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (isUserAMonkey() || !TextUtils.equals("enable_adb", preference.getKey())) {
            return false;
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "adb_enabled", 0) != 0) {
            writeAdbSetting(false);
            return true;
        }
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment = ((AdbPreferenceController) this).mFragment;
        FragmentManagerImpl supportFragmentManager = developmentSettingsDashboardFragment.getActivity().getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("EnableAdbDialog") != null) {
            return true;
        }
        EnableAdbWarningDialog enableAdbWarningDialog = new EnableAdbWarningDialog();
        enableAdbWarningDialog.setTargetFragment(developmentSettingsDashboardFragment, 0);
        enableAdbWarningDialog.show(supportFragmentManager, "EnableAdbDialog");
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v6, types: [int] */
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        ?? r11;
        if (preference != null) {
            ((TwoStatePreference) preference).setChecked(Settings.Global.getInt(this.mContext.getContentResolver(), "adb_enabled", 0) != 0);
            if (isAvailable()) {
                ((RestrictedSwitchPreference) preference).setDisabledByAdmin(RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(this.mContext, UserHandle.myUserId()));
            }
            Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3"), null, "isUsbDebuggingEnabled", null, null);
            if (query != null) {
                try {
                    query.moveToFirst();
                } catch (Exception unused) {
                } finally {
                    query.close();
                }
                RecordingInputConnection$$ExternalSyntheticOutline0.m(r11, "projectionArgs:isUsbDebuggingEnabled/", "SettingsEdm");
                if (r11 != 0 || Settings.Secure.getInt(this.mContext.getContentResolver(), "rampart_blocked_adb_cmd", 0) == 1) {
                    enablePreference(false);
                } else {
                    if (RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(this.mContext, UserHandle.myUserId()) == null) {
                        enablePreference(true);
                        return;
                    }
                    return;
                }
            }
            r11 = -1;
            RecordingInputConnection$$ExternalSyntheticOutline0.m(r11, "projectionArgs:isUsbDebuggingEnabled/", "SettingsEdm");
            if (r11 != 0) {
            }
            enablePreference(false);
        }
    }

    public final void writeAdbSetting(boolean z) {
        Settings.Global.putInt(this.mContext.getContentResolver(), "adb_enabled", z ? 1 : 0);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent("com.android.settingslib.development.AbstractEnableAdbController.ENABLE_ADB_STATE_CHANGED"));
    }
}
