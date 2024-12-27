package com.android.settings.development;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VerifyAppsOverUsbPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int SETTING_VALUE_OFF = 0;
    static final int SETTING_VALUE_ON = 1;
    public final PackageManager mPackageManager;
    public final RestrictedLockUtilsDelegate mRestrictedLockUtils;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class RestrictedLockUtilsDelegate {}

    public VerifyAppsOverUsbPreferenceController(Context context) {
        super(context);
        this.mRestrictedLockUtils = new RestrictedLockUtilsDelegate();
        this.mPackageManager = context.getPackageManager();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "verify_apps_over_usb";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Settings.Global.getInt(
                        this.mContext.getContentResolver(), "verifier_setting_visible", 1)
                > 0;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        updateState(this.mPreference);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "verifier_verify_adb_installs",
                ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        boolean z;
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) preference;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "adb_enabled", 0) == 0) {
            z = false;
        } else {
            Intent intent = new Intent("android.intent.action.PACKAGE_NEEDS_VERIFICATION");
            intent.setType("application/vnd.android.package-archive");
            intent.addFlags(1);
            z = !this.mPackageManager.queryBroadcastReceivers(intent, 0).isEmpty();
        }
        if (!z) {
            restrictedSwitchPreference.setChecked(false);
            restrictedSwitchPreference.setDisabledByAdmin(null);
            restrictedSwitchPreference.setEnabled(false);
            return;
        }
        Context context = this.mContext;
        int myUserId = UserHandle.myUserId();
        this.mRestrictedLockUtils.getClass();
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        context, myUserId, "ensure_verify_apps");
        if (checkIfRestrictionEnforced != null) {
            restrictedSwitchPreference.setChecked(true);
            restrictedSwitchPreference.setDisabledByAdmin(checkIfRestrictionEnforced);
        } else {
            restrictedSwitchPreference.setEnabled(true);
            restrictedSwitchPreference.setChecked(
                    Settings.Global.getInt(
                                    this.mContext.getContentResolver(),
                                    "verifier_verify_adb_installs",
                                    1)
                            != 0);
        }
    }
}
