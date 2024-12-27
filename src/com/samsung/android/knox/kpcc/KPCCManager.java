package com.samsung.android.knox.kpcc;

import android.os.Process;
import android.os.ServiceManager;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KPCCManager {
    public static final int DRX_1280_MSEC = 3;
    public static final int DRX_2560_MSEC = 4;
    public static final int DRX_320_MSEC = 1;
    public static final int DRX_640_MSEC = 2;
    public static final int DRX_DEFAULT = 0;
    public static final int DRX_EMPTY = -1;
    public static final int ERROR_ADMIN_ALREADY_SET = -3;
    public static final int ERROR_FAIL = -1;
    public static final int ERROR_INVALID_VALUE = -4;
    public static final int ERROR_NOT_SUPPORTED = -2;
    public static final int OFF = 0;
    public static final int ON = 1;
    public static final int SUCCESS = 0;
    public static final String TAG = "KPCCManager";
    public static final Object mSync = new Object();
    public static volatile KPCCManager sKPCCManager;
    public ContextInfo mContextInfo;
    public IKPCCManager mService;

    public KPCCManager(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public static KPCCManager getInstance() {
        KPCCManager kPCCManager = sKPCCManager;
        if (kPCCManager == null) {
            synchronized (mSync) {
                try {
                    kPCCManager = sKPCCManager;
                    if (kPCCManager == null) {
                        KPCCManager kPCCManager2 =
                                new KPCCManager(new ContextInfo(Process.myUid()));
                        sKPCCManager = kPCCManager2;
                        kPCCManager = kPCCManager2;
                    }
                } finally {
                }
            }
        }
        return kPCCManager;
    }

    public int allowRestrictedNetworkCapability(int i, String str, int i2) {
        return -2;
    }

    public int getDrxValue() {
        return 0;
    }

    public List<String> getPackagesAllowedOnRestrictedNetworks() {
        return null;
    }

    public final IKPCCManager getService() {
        if (this.mService == null) {
            this.mService = IKPCCManager.Stub.asInterface(ServiceManager.getService("kpcc"));
        }
        return this.mService;
    }

    public int getTelephonyDrxValue() {
        return 0;
    }

    public List<Integer> getUnrestrictedNetworkCapabilities(String str) {
        return null;
    }

    public int setDrxValue(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KPCCManager.setDrxValue");
        return -2;
    }

    public int setPackageOnRestrictedNetworks(int i, String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "KPCCManager.setPackageOnRestrictedNetworks");
        return -2;
    }
}
