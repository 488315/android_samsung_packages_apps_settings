package com.samsung.android.knox;

import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessController {
    public static final String TAG = "EnterpriseDeviceManager";

    public static ContextInfo enforceActiveAdminPermissionByContext(
            ContextInfo contextInfo, List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                return getService().enforceActiveAdminPermissionByContext(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public static ContextInfo enforceOwnerOnlyAndActiveAdminPermission(
            ContextInfo contextInfo, List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                return getService().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public static boolean enforceWpcod() {
        try {
            if (getService() == null) {
                return false;
            }
            getService().enforceWpcod(Process.myUid(), true);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static IEnterpriseDeviceManager getService() {
        return IEnterpriseDeviceManager.Stub.asInterface(
                ServiceManager.getService("enterprise_policy"));
    }

    public static void throwIfParentInstance(ContextInfo contextInfo, String str) {
        if (contextInfo.mParent) {
            throw new SecurityException(
                    AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                            str, " cannot be called on the parent instance"));
        }
    }
}
