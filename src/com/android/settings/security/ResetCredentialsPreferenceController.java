package com.android.settings.security;

import android.content.Context;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;

import androidx.preference.PreferenceScreen;

import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;

import java.security.KeyStore;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ResetCredentialsPreferenceController
        extends RestrictedEncryptionPreferenceController implements LifecycleObserver, OnResume {
    public final KeyStore mKeyStore;
    public RestrictedPreference mPreference;
    public final KeyStore mWifiKeyStore;

    public ResetCredentialsPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        KeyStore keyStore;
        KeyStore keyStore2 = null;
        try {
            keyStore = KeyStore.getInstance(CertProvisionProfile.PROVIDER_ANDROID);
            keyStore.load(null);
        } catch (Exception unused) {
            keyStore = null;
        }
        this.mKeyStore = keyStore;
        if (context.getUser().isSystem()) {
            try {
                KeyStore keyStore3 = KeyStore.getInstance(CertProvisionProfile.PROVIDER_ANDROID);
                keyStore3.load(new AndroidKeyStoreLoadStoreParameter(102));
                keyStore2 = keyStore3;
            } catch (Exception unused2) {
            }
        }
        this.mWifiKeyStore = keyStore2;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedPreference) preferenceScreen.findPreference("credentials_reset");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "credentials_reset";
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004b, code lost:

       if (r5 == false) goto L23;
    */
    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            r7 = this;
            java.lang.String r0 = "ResetCredentials"
            java.lang.String r1 = "Preference enabled system="
            com.android.settingslib.RestrictedPreference r2 = r7.mPreference
            if (r2 == 0) goto L7c
            com.android.settingslib.RestrictedPreferenceHelper r2 = r2.mHelper
            boolean r2 = r2.mDisabledByAdmin
            if (r2 != 0) goto L7c
            r2 = 0
            java.security.KeyStore r3 = r7.mKeyStore     // Catch: java.security.KeyStoreException -> L4e
            r4 = 1
            if (r3 == 0) goto L20
            java.util.Enumeration r3 = r3.aliases()     // Catch: java.security.KeyStoreException -> L4e
            boolean r3 = r3.hasMoreElements()     // Catch: java.security.KeyStoreException -> L4e
            if (r3 == 0) goto L20
            r3 = r4
            goto L21
        L20:
            r3 = r2
        L21:
            java.security.KeyStore r5 = r7.mWifiKeyStore     // Catch: java.security.KeyStoreException -> L4e
            if (r5 == 0) goto L31
            java.util.Enumeration r5 = r5.aliases()     // Catch: java.security.KeyStoreException -> L4e
            boolean r5 = r5.hasMoreElements()     // Catch: java.security.KeyStoreException -> L4e
            if (r5 == 0) goto L31
            r5 = r4
            goto L32
        L31:
            r5 = r2
        L32:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.security.KeyStoreException -> L4e
            r6.<init>(r1)     // Catch: java.security.KeyStoreException -> L4e
            r6.append(r3)     // Catch: java.security.KeyStoreException -> L4e
            java.lang.String r1 = ", wifi="
            r6.append(r1)     // Catch: java.security.KeyStoreException -> L4e
            r6.append(r5)     // Catch: java.security.KeyStoreException -> L4e
            java.lang.String r1 = r6.toString()     // Catch: java.security.KeyStoreException -> L4e
            android.util.Log.i(r0, r1)     // Catch: java.security.KeyStoreException -> L4e
            if (r3 != 0) goto L4f
            if (r5 == 0) goto L4e
            goto L4f
        L4e:
            r4 = r2
        L4f:
            com.android.settingslib.RestrictedPreference r1 = r7.mPreference
            r1.setEnabled(r4)
            android.content.Context r1 = r7.mContext
            java.lang.String r3 = "content://com.sec.knox.provider/CertificatePolicy"
            java.lang.String r4 = "isUserRemoveCertificatesAllowed"
            int r1 = com.android.settings.Utils.getEnterprisePolicyEnabled(r1, r3, r4)
            java.lang.String r3 = "false"
            java.lang.String[] r3 = new java.lang.String[]{r3}
            android.content.Context r4 = r7.mContext
            java.lang.String r5 = "content://com.sec.knox.provider2/vpnPolicy"
            java.lang.String r6 = "isUserChangeProfilesAllowed"
            int r3 = com.android.settings.Utils.getEnterprisePolicyEnabled(r4, r5, r6, r3)
            if (r1 == 0) goto L72
            if (r3 != 0) goto L7c
        L72:
            java.lang.String r1 = "onResume() UserChange(or Remove) has disallowed by IT admin."
            android.util.Log.d(r0, r1)
            com.android.settingslib.RestrictedPreference r7 = r7.mPreference
            r7.setEnabled(r2)
        L7c:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.security.ResetCredentialsPreferenceController.onResume():void");
    }
}
