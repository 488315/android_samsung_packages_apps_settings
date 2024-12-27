package com.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accounts.AccountRestrictionHelper;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.moreconnection.EmergencyAlertsPreferenceController;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EmergencyBroadcastPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public Preference mEmeregencyAlerts;
    public final AccountRestrictionHelper mHelper;
    public final PackageManager mPm;
    public final String mPrefKey;
    public final UserManager mUserManager;

    public EmergencyBroadcastPreferenceController(Context context) {
        this(
                context,
                new AccountRestrictionHelper(context),
                "app_and_notif_cell_broadcast_settings");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(this.mPrefKey);
        this.mEmeregencyAlerts = findPreference;
        if (findPreference == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        if (isEnabledPackage$3(EmergencyAlertsPreferenceController.AOSP_GOOGLE_CMAS_PACKAGE)) {
            DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                    EmergencyAlertsPreferenceController.AOSP_GOOGLE_CMAS_PACKAGE,
                    EmergencyAlertsPreferenceController.CLASS_CELL_BROADCAST_SETTINGS,
                    intent);
        } else if (isEnabledPackage$3(EmergencyAlertsPreferenceController.AOSP_CMAS_PACKAGE)) {
            DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                    EmergencyAlertsPreferenceController.AOSP_CMAS_PACKAGE,
                    EmergencyAlertsPreferenceController.CLASS_CELL_BROADCAST_SETTINGS,
                    intent);
        } else {
            if (!isEnabledPackage$3(EmergencyAlertsPreferenceController.AOSP_CMAS_MODULE_PACKAGE)) {
                Log.d("PrefControllerMixin", "Activity not found");
                return;
            }
            DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                    EmergencyAlertsPreferenceController.AOSP_CMAS_MODULE_PACKAGE,
                    EmergencyAlertsPreferenceController.CLASS_CELL_BROADCAST_SETTINGS,
                    intent);
        }
        this.mEmeregencyAlerts.setIntent(intent);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mPrefKey;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!preference.getKey().equals(this.mPrefKey)) {
            return false;
        }
        LoggingHelper.insertEventLogging(1859, 59504);
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x003c, code lost:

       if (r5.mPm.getApplicationEnabledSetting(r3) == 2) goto L16;
    */
    @Override // com.android.settingslib.core.AbstractPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAvailable() {
        /*
            r5 = this;
            android.os.UserManager r0 = r5.mUserManager
            boolean r0 = r0.isAdminUser()
            r1 = 0
            if (r0 == 0) goto L52
            android.content.Context r0 = r5.mContext
            android.content.res.Resources r0 = r0.getResources()
            r2 = 17891620(0x1110124, float:2.6633112E-38)
            boolean r0 = r0.getBoolean(r2)
            r2 = 1
            if (r0 == 0) goto L2a
            android.content.Context r0 = r5.mContext
            android.content.res.Resources r0 = r0.getResources()
            r3 = 17891650(0x1110142, float:2.6633196E-38)
            boolean r0 = r0.getBoolean(r3)
            if (r0 != 0) goto L2a
            r0 = r2
            goto L2b
        L2a:
            r0 = r1
        L2b:
            if (r0 == 0) goto L3f
            android.content.Context r3 = r5.mContext     // Catch: java.lang.IllegalArgumentException -> L3e
            java.lang.String r3 = com.android.internal.telephony.CellBroadcastUtils.getDefaultCellBroadcastReceiverPackageName(r3)     // Catch: java.lang.IllegalArgumentException -> L3e
            if (r3 == 0) goto L3e
            android.content.pm.PackageManager r4 = r5.mPm     // Catch: java.lang.IllegalArgumentException -> L3e
            int r3 = r4.getApplicationEnabledSetting(r3)     // Catch: java.lang.IllegalArgumentException -> L3e
            r4 = 2
            if (r3 != r4) goto L3f
        L3e:
            r0 = r1
        L3f:
            if (r0 == 0) goto L52
            int r0 = android.os.UserHandle.myUserId()
            com.android.settings.accounts.AccountRestrictionHelper r5 = r5.mHelper
            android.content.Context r5 = r5.mContext
            java.lang.String r3 = "no_config_cell_broadcasts"
            boolean r5 = com.android.settingslib.RestrictedLockUtilsInternal.hasBaseUserRestriction(r5, r0, r3)
            if (r5 != 0) goto L52
            r1 = r2
        L52:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.EmergencyBroadcastPreferenceController.isAvailable():boolean");
    }

    public final boolean isEnabledPackage$3(String str) {
        try {
            int applicationEnabledSetting =
                    this.mContext.getPackageManager().getApplicationEnabledSetting(str);
            if (applicationEnabledSetting == 2 || applicationEnabledSetting == 3) {
                Log.d("PrefControllerMixin", str.concat(" is disabled"));
                return false;
            }
            Log.d("PrefControllerMixin", str.concat(" is enabled"));
            return true;
        } catch (IllegalArgumentException unused) {
            Log.d("PrefControllerMixin", str.concat(" is not installed"));
            return false;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (preference instanceof RestrictedPreference) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if ("ATT".equals(Utils.getSalesCode())) {
                preference.setTitle(R.string.att_emergency_alert_title);
            } else {
                preference.setTitle(R.string.wireless_emergency_alerts);
            }
            ((RestrictedPreference) preference)
                    .checkRestrictionAndSetDisabled("no_config_cell_broadcasts");
        }
    }

    public EmergencyBroadcastPreferenceController(
            Context context, AccountRestrictionHelper accountRestrictionHelper, String str) {
        super(context);
        this.mPrefKey = str;
        this.mHelper = accountRestrictionHelper;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mPm = this.mContext.getPackageManager();
    }
}
