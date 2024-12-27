package com.samsung.android.settings.deviceinfo.softwareinfo;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.keystore.CertificatePolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CCModeStatus extends SettingsPreferenceFragment {
    public CertificatePolicy mCertificatePolicy;
    public Context mContext;
    public DevicePolicyManager mDevicePolicyManager;
    public PasswordPolicy mPasswordPolicy;
    public RestrictionPolicy mRestrictionPolicy;
    public UserManager mUserManager;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("CCModeStatus", "onCreate");
        this.mContext = getContext();
        addPreferencesFromResource(R.xml.sec_cc_mode_status);
        this.mDevicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        this.mCertificatePolicy =
                EnterpriseKnoxManager.getInstance(this.mContext).getCertificatePolicy();
        this.mPasswordPolicy =
                EnterpriseDeviceManager.getInstance(this.mContext).getPasswordPolicy();
        this.mRestrictionPolicy =
                EnterpriseDeviceManager.getInstance(this.mContext).getRestrictionPolicy();
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0098  */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.softwareinfo.CCModeStatus.onResume():void");
    }

    public final void setSummaryText$1(String str, String str2) {
        Preference findPreference = findPreference(str);
        if (findPreference == null) {
            Log.e("CCModeStatus", "setSummaryText - preference is null");
            return;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = this.mContext.getResources().getString(R.string.device_info_default);
        }
        findPreference.setSummary(str2);
    }
}
