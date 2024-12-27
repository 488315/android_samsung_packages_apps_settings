package com.samsung.android.knox.zt.service.wrapper;

import android.content.Context;
import android.util.Log;

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AttestationUtils {
    public static final String ERROR_MESSAGE_PERMISSION_ERROR = "permission error";
    public static final String ERROR_MESSAGE_WRONG_ARGUMENT = "wrong argument";
    public static final String PERMISSION_KNOX_ZT =
            "com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE";
    public static final String TAG = "AttestationUtils";
    public final Context mContext;
    public final Object mInstance;

    public AttestationUtils(Context context) {
        this.mContext = context;
        try {
            Class[] clsArr = new Class[0];
            this.mInstance =
                    ClassLoaderHelper.getInstance()
                            .getSakClassLoader()
                            .loadClass("com.samsung.android.security.keystore.AttestationUtils")
                            .getDeclaredConstructor(null)
                            .newInstance(null);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public Iterable<byte[]> attestDevice(Object obj) {
        if (!hasPermission()) {
            throw new SecurityException(ERROR_MESSAGE_PERMISSION_ERROR);
        }
        if (obj == null) {
            Log.e(TAG, "Mandatory argument is missing!");
            throw new IllegalArgumentException(ERROR_MESSAGE_WRONG_ARGUMENT);
        }
        try {
            return (Iterable)
                    this.mInstance
                            .getClass()
                            .getMethod("attestDevice", obj.getClass())
                            .invoke(this.mInstance, obj);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public Iterable<byte[]> attestKey(Object obj) {
        if (!hasPermission()) {
            throw new SecurityException(ERROR_MESSAGE_PERMISSION_ERROR);
        }
        if (obj == null) {
            Log.e(TAG, "Mandatory argument is missing!");
            throw new IllegalArgumentException(ERROR_MESSAGE_WRONG_ARGUMENT);
        }
        try {
            return (Iterable)
                    this.mInstance
                            .getClass()
                            .getMethod("attestKey", obj.getClass())
                            .invoke(this.mInstance, obj);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public final boolean hasPermission() {
        Context context = this.mContext;
        if (context == null) {
            return false;
        }
        int checkSelfPermission = context.checkSelfPermission(PERMISSION_KNOX_ZT);
        if (checkSelfPermission != 0) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(checkSelfPermission, "hasPermission:", TAG);
        }
        return checkSelfPermission == 0;
    }

    public void storeCertificateChain(String str, Iterable<byte[]> iterable) {
        if (!hasPermission()) {
            throw new SecurityException(ERROR_MESSAGE_PERMISSION_ERROR);
        }
        if (str == null || iterable == null) {
            Log.e(TAG, "Mandatory argument is missing!");
            throw new IllegalArgumentException(ERROR_MESSAGE_WRONG_ARGUMENT);
        }
        try {
            this.mInstance
                    .getClass()
                    .getMethod("storeCertificateChain", String.class, Iterable.class)
                    .invoke(this.mInstance, str, iterable);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }
}
