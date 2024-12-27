package com.android.settings.vpn2;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;

import androidx.preference.Preference;

import com.android.internal.net.VpnConfig;
import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppPreference extends ManageablePreference {
    public final String mName;
    public final String mPackageName;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppPreference(Context context, int i, String str) {
        super(context, null, R.style.VpnAppManagementPreferenceStyle, 0);
        Drawable drawable = null;
        this.mUserId = i;
        this.mHelper.checkRestrictionAndSetDisabled(i, "no_config_vpn");
        this.mPackageName = str;
        if (!this.mHelper.mDisabledByAdmin
                && str.equals(
                        ((DevicePolicyManager)
                                        getContext()
                                                .createContextAsUser(UserHandle.of(this.mUserId), 0)
                                                .getSystemService(DevicePolicyManager.class))
                                .getAlwaysOnVpnPackage())) {
            setDisabledByAdmin(
                    RestrictedLockUtils.getProfileOrDeviceOwner(
                            getContext(), null, UserHandle.of(this.mUserId)));
        }
        try {
            Context createPackageContextAsUser =
                    getContext()
                            .createPackageContextAsUser(
                                    getContext().getPackageName(), 0, UserHandle.of(this.mUserId));
            PackageManager packageManager = createPackageContextAsUser.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                if (packageInfo != null) {
                    drawable = packageInfo.applicationInfo.loadIcon(packageManager);
                    str = VpnConfig.getVpnLabel(createPackageContextAsUser, str).toString();
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (drawable == null) {
                drawable = packageManager.getDefaultActivityIcon();
            }
        } catch (PackageManager.NameNotFoundException unused2) {
        }
        this.mName = str;
        setTitle(str);
        setIcon(drawable);
    }

    @Override // com.android.settingslib.RestrictedPreference
    public final String getPackageName() {
        return this.mPackageName;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.preference.Preference, java.lang.Comparable
    public final int compareTo(Preference preference) {
        if (!(preference instanceof AppPreference)) {
            return preference instanceof LegacyVpnPreference
                    ? -((LegacyVpnPreference) preference).compareTo((Preference) this)
                    : super.compareTo(preference);
        }
        AppPreference appPreference = (AppPreference) preference;
        int i = appPreference.mState - this.mState;
        if (i != 0) {
            return i;
        }
        int compareToIgnoreCase = this.mName.compareToIgnoreCase(appPreference.mName);
        if (compareToIgnoreCase != 0) {
            return compareToIgnoreCase;
        }
        int compareTo = this.mPackageName.compareTo(appPreference.mPackageName);
        return compareTo == 0 ? this.mUserId - appPreference.mUserId : compareTo;
    }
}
