package com.android.settings.utils;

import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.util.Log;

import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AndroidKeystoreAliasLoader {
    public final Collection mKeyCertAliases = new ArrayList();
    public final Collection mCaCertAliases = new ArrayList();

    public AndroidKeystoreAliasLoader(Integer num) {
        try {
            KeyStore keyStore = KeyStore.getInstance(CertProvisionProfile.PROVIDER_ANDROID);
            if (num == null || num.intValue() == -1) {
                keyStore.load(null);
            } else {
                keyStore.load(new AndroidKeyStoreLoadStoreParameter(num.intValue()));
            }
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String nextElement = aliases.nextElement();
                try {
                    Key key = keyStore.getKey(nextElement, null);
                    if (key != null) {
                        if (key instanceof PrivateKey) {
                            ((ArrayList) this.mKeyCertAliases).add(nextElement);
                            Certificate[] certificateChain =
                                    keyStore.getCertificateChain(nextElement);
                            if (certificateChain != null && certificateChain.length >= 2) {
                                ((ArrayList) this.mCaCertAliases).add(nextElement);
                            }
                        }
                    } else if (keyStore.getCertificate(nextElement) != null) {
                        ((ArrayList) this.mCaCertAliases).add(nextElement);
                    }
                } catch (KeyStoreException
                        | NoSuchAlgorithmException
                        | UnrecoverableKeyException e) {
                    Log.e(
                            "SettingsKeystoreUtils",
                            "Failed to load alias: "
                                    + nextElement
                                    + " from Android Keystore. Ignoring.",
                            e);
                }
            }
        } catch (Exception e2) {
            Log.e("SettingsKeystoreUtils", "Failed to open Android Keystore.", e2);
        }
    }
}
