package com.samsung.android.knox.ucm.plugin.service;

import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class UcmSpiUtil {
    public static final String TAG = "UcmSpiUtil";

    public static Object getSpi(String str, Class cls, String str2, Provider provider) {
        Object obj = null;
        if (provider == null || cls == null) {
            return null;
        }
        try {
            Provider.Service service = provider.getService(str, str2);
            if (service == null) {
                Log.e(
                        TAG,
                        "failed to find service ["
                                + provider.getName()
                                + "], ["
                                + str
                                + "], ["
                                + str2
                                + "]");
                return null;
            }
            Object newInstance = service.newInstance(null);
            try {
                if (newInstance == null) {
                    Log.e(TAG, "newInstance is null");
                    return null;
                }
                if (cls.isAssignableFrom(newInstance.getClass())) {
                    return newInstance;
                }
                Log.e(TAG, "failed to find subclass");
                return null;
            } catch (NoSuchAlgorithmException e) {
                obj = newInstance;
                e = e;
                Log.e(TAG, "NoSuchAlgorithmException");
                e.printStackTrace();
                return obj;
            }
        } catch (NoSuchAlgorithmException e2) {
            e = e2;
        }
    }
}
