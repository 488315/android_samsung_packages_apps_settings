package com.samsung.android.knox.hdm;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HdmManager {
    public static final String CURRENT_HDM_VERSION = "1.0";
    public static final int ERROR_FAIL = -1;
    public static final String TAG = "HDM - HdmManager";
    public ContextInfo mContextInfo;
    public IHdmManager mService;

    public HdmManager(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
        Log.d(TAG, "HdmManager.java Started");
    }

    public static String getHdmVersion() {
        Log.d(TAG, "getHdmVersion() on HdmManager.java");
        if (SignalSeverity.NONE.equals(
                SystemProperties.get(
                        "ro.vendor.hdm.hdm_supported_subsystem", SignalSeverity.NONE))) {
            return "2.0 - 1D";
        }
        return "2.0 - "
                + SystemProperties.get(
                        "ro.vendor.hdm.hdm_supported_subsystem", SignalSeverity.NONE);
    }

    public String getHdmId(String str) throws RemoteException {
        String str2 = TAG;
        Log.d(str2, "getHdmId() on HdmManager.java");
        if (getService() != null) {
            Log.d(str2, "Calling getHdmId() using HDM Service on HdmManager.java");
            return this.mService.getHdmId(this.mContextInfo, str);
        }
        Log.e(str2, "Fail to call getHdmId() using HDM Service on HdmManager.java");
        return null;
    }

    public String getHdmPolicy(String str, String str2) throws RemoteException {
        String str3 = TAG;
        Log.d(str3, "getHdmPolicy() on HdmManager.java");
        if (getService() != null) {
            Log.d(str3, "Calling getHdmPolicy() using HDM Service on HdmManager.java");
            return this.mService.getHdmPolicy(this.mContextInfo, str, str2);
        }
        Log.e(str3, "Fail to call getHdmPolicy() using HDM Service on HdmManager.java");
        return null;
    }

    public final IHdmManager getService() {
        if (this.mService == null) {
            this.mService = IHdmManager.Stub.asInterface(ServiceManager.getService("hdm_service"));
        }
        return this.mService;
    }

    public boolean isNFCBlockedByHDM() throws RemoteException {
        String str = TAG;
        Log.d(str, "isNFCBlockedByHDM() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling isNFCBlockedByHDM() using HDM Service on HdmManager.java");
            return this.mService.isNFCBlockedByHDM(this.mContextInfo);
        }
        Log.e(str, "Fail to call isNFCBlockedByHDM() using HDM Service on HdmManager.java");
        return false;
    }

    public boolean isSwBlockEnabled() throws RemoteException {
        String str = TAG;
        Log.d(str, "isSwBlockEnabled() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling isSwBlockEnabled() using HDM Service on HdmManager.java");
            return this.mService.isSwBlockEnabled(this.mContextInfo);
        }
        Log.e(str, "Fail to call isSwBlockEnabled() using HDM Service on HdmManager.java");
        return false;
    }

    public String setHdmPolicy(String str) throws RemoteException {
        String str2 = TAG;
        Log.d(str2, "setHdmPolicy() on HdmManager.java");
        if (getService() != null) {
            Log.d(str2, "Calling setHdmPolicy() using HDM Service on HdmManager.java");
            return this.mService.setHdmPolicy(this.mContextInfo, str);
        }
        Log.e(str2, "Fail to call setHdmPolicy() using HDM Service on HdmManager.java");
        return null;
    }

    public int setHdmTaCmd(int i) throws RemoteException {
        String str = TAG;
        Log.d(str, "setHdmTaCmd() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling setHdmTaCmd() using HDM Service on HdmManager.java");
            return this.mService.setHdmTaCmd(this.mContextInfo, i);
        }
        Log.e(str, "Fail to call setHdmTaCmd() using HDM Service on HdmManager.java");
        return -1;
    }

    public boolean setSwBlock(boolean z) throws RemoteException {
        String str = TAG;
        Log.d(str, "setSwBlock() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling setSwBlock() using HDM Service on HdmManager.java");
            return this.mService.setSwBlock(this.mContextInfo, z);
        }
        Log.e(str, "Fail to call setSwBlock() using HDM Service on HdmManager.java");
        return false;
    }

    public int syncSwBlockFromBoot() throws RemoteException {
        String str = TAG;
        Log.d(str, "syncSwBlockFromBoot() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling syncSwBlockFromBoot() using HDM Service on HdmManager.java");
            return this.mService.syncSwBlockFromBoot();
        }
        Log.e(str, "Fail to call syncSwBlockFromBoot() using HDM Service on HdmManager.java");
        return -1;
    }
}
